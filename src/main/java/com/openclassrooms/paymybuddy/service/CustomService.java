package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.*;
import com.openclassrooms.paymybuddy.model.*;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.openclassrooms.paymybuddy.constants.Fee;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides custom services such as sending money to a bank account,
 * sending money to another user, getting account details, and getting transaction details.
 */
@Service
public class CustomService {
    @Autowired
    UserService userService;
    TransactionMapper mapper= Mappers.getMapper(TransactionMapper.class);
    @Autowired
    BankService bankService;
    @Autowired
    ConnectionService connectionService;
    @Autowired
    ConnectionTransactionService connectionTransactionService;
    @Autowired
    BankTransactionService bankTransactionService;

    /**
     * Retrieves the account details of a given user, including first name, last name, email, balance,
     * and list of connected bank accounts.
     *
     * @param user details
     * @return AccountBalanceBanksDTO containing the user's account details
     */
    public AccountBalanceBanksDTO getAccount(User user){
        AccountBalanceBanksDTO account = new AccountBalanceBanksDTO();

        account.setFirstName(user.getFirstName());
        account.setLastName(user.getLastName());
        account.setEmail(user.getEmail());
        account.setBalance(user.getBalance());
        account.setCurrentBank(user.getBankList().get(0));

        List<Bank> banks = user.getBankList().stream().skip(1)
                .collect(Collectors.toList());

        account.setBankList(banks);

        return account;
    }

    /**
     * Sends money from the user's PayMyBuddy account to their connected bank account.
     *
     * @param bankTransfer containing the details of the bank transfer
     *                     the amount and the recipient's bank account, and a description
     * @return true if the transfer is successful, false otherwise
     */
    @Transactional
    public boolean sendMoneyToBank(BankTransferDTO bankTransfer) {
        User sender = userService.getCurrentUser();
        Bank bank = bankService.findByIban(bankTransfer.getIban());

        float amountWithFees = bankTransfer.getAmount() + bankTransfer.getAmount() * Fee.CONNECTION_TRANSACTION;

        if(amountWithFees <= sender.getBalance()) {
            BankTransaction bankTransaction = new BankTransaction();
            bankTransaction.setAmount(bankTransfer.getAmount());
            bankTransaction.setBank(bank);
            bankTransaction.setDate(new Timestamp(System.currentTimeMillis()));
            bankTransaction.setDescription(bankTransfer.getDescription());
            bankTransaction.setFees(Fee.BANK_TRANSACTION);

            sender.setBalance(sender.getBalance() - amountWithFees);

            bankTransactionService.save(bankTransaction);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sends money from the user's account to another account.
     *
     * @param transaction containing the details of the user, the amount,
     *                    the recipient's user ID, and a description.
     * @return true if the transfer is successful, false otherwise
     */
    @Transactional
    public boolean sendMoneyToUser(UserTransferDTO transaction){
        //find the connection
        User receiver = userService.findById(transaction.getIdReceiver());
        User sender = userService.getCurrentUser();
        Connection connection = connectionService.findBySenderAndReceiver(sender, receiver);
        float amountWithFees = transaction.getAmount() + transaction.getAmount() * Fee.CONNECTION_TRANSACTION;

        if(amountWithFees <= sender.getBalance()){
            ConnectionTransaction connectionTransaction = new ConnectionTransaction();
            connectionTransaction.setAmount(transaction.getAmount());
            connectionTransaction.setConnection(connection);
            connectionTransaction.setDate(new Timestamp(System.currentTimeMillis()));
            connectionTransaction.setDescription(transaction.getDescription());
            connectionTransaction.setFees(Fee.CONNECTION_TRANSACTION);

            sender.setBalance(sender.getBalance() - amountWithFees);
            receiver.setBalance(receiver.getBalance() + connectionTransaction.getAmount());

            connectionTransactionService.save(connectionTransaction);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sends money to the user's account.
     *
     * @param bankTransfer containing the details of the amount
     */
    @Transactional
    public void addMoneyToAccount(BankTransferDTO bankTransfer) {
        User sender = userService.getCurrentUser();
        AccountBalanceBanksDTO account = getAccount(sender);
        Bank bank = account.getCurrentBank();

        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setAmount(bankTransfer.getAmount());
        bankTransaction.setBank(bank);
        bankTransaction.setDate(new Timestamp(System.currentTimeMillis()));
        bankTransaction.setDescription("Credit account");
        bankTransaction.setFees(Fee.BANK_TRANSACTION);

        sender.setBalance(sender.getBalance() + bankTransaction.getAmount());

        bankTransactionService.save(bankTransaction);
    }

    /**
     * Retrieves the transactions list for the current user, including contacts and transactions.
     * @return a ContactsAndTransactionsListDTO containing the user's contacts and transactions.
     */
    public ContactsAndTransactionsListDTO getTransferDetails(){
        User user = userService.getCurrentUser();
        ContactsAndTransactionsListDTO transferDetails = new ContactsAndTransactionsListDTO();
        transferDetails.setContacts(user.getContactList());

        // Get the transactions for the user's connections and banks
        List<Connection> debitFromUser = connectionService.findBySender(user);
        List<Connection> creditFromUser = connectionService.findByReceiver(user);
        List<TransactionConnectionDescriptionAmountDTO> transactions = new ArrayList<>();

        Bank currentBank = user.getBankList().get(0);
        List<BankTransaction> creditFromBank = currentBank.getTransactionList();

        List<Bank> banks = user.getBankList().stream().skip(1).toList();

        // Add the transactions from the user's other banks
        transactions.addAll(banks.stream()
                .flatMap(bank -> bank.getTransactionList().stream())
                .map(t -> mapper.debitFromBankTransaction(t))
                .toList());

        // Add the transactions from the current bank
        transactions.addAll(creditFromBank.stream()
                .map(t -> mapper.creditFromBankTransaction(t))
                .toList());

        // Add the transactions where the user is the sender
        transactions.addAll(debitFromUser.stream()
                .flatMap(c -> c.getTransactionList().stream()
                        .map(t -> {
                            User receiver = c.getReceiver();
                            TransactionConnectionDescriptionAmountDTO transaction = mapper.debitFromUserTransaction(t);
                            transaction.setConnection(receiver.getLastName() + " " + receiver.getFirstName());
                            return transaction;
                        }))
                .toList());

        // Add the transactions where the user is the receiver
        transactions.addAll(creditFromUser.stream().flatMap(c -> c.getTransactionList().stream()
                         .map(t -> {
                             User sender = c.getSender();
                             TransactionConnectionDescriptionAmountDTO transaction = mapper.creditFromUserTransaction(t);
                             transaction.setConnection(sender.getLastName() + " " + sender.getFirstName());
                             return transaction;
                         }))
                .toList());

        // Sort the transactions by date in descending order
        transactions = transactions.stream()
                .sorted(Comparator.comparing(TransactionConnectionDescriptionAmountDTO::getDate).reversed())
                .collect(Collectors.toList());

        // Set the transactions in the ContactsAndTransactionsListDTO object and return it
        transferDetails.setTransactions(transactions);
        return transferDetails;
    }

    public Page<TransactionConnectionDescriptionAmountDTO> findPaginated(Pageable pageable) {
        ContactsAndTransactionsListDTO transferDetails = getTransferDetails();
        List<TransactionConnectionDescriptionAmountDTO> transactions = transferDetails.getTransactions();

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<TransactionConnectionDescriptionAmountDTO> list;

        if (transactions.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, transactions.size());
            list = transactions.subList(startItem, toIndex);
        }

        Page<TransactionConnectionDescriptionAmountDTO> transactionPage
                = new PageImpl<TransactionConnectionDescriptionAmountDTO>(list, PageRequest.of(currentPage, pageSize), transactions.size());

        return transactionPage;
    }
}