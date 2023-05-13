package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.*;
import com.openclassrooms.paymybuddy.model.*;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.paymybuddy.constants.Fee;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public boolean sendMoneyToUser(UserTransferDTO transaction){
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

    public ContactsAndTransactionsListDTO getTransferDetails(){
        User user = userService.getCurrentUser();
        ContactsAndTransactionsListDTO transferDetails = new ContactsAndTransactionsListDTO();
        transferDetails.setContacts(user.getContactList());

        List<Connection> debitFromUser = connectionService.findBySender(user);
        List<Connection> creditFromUser = connectionService.findByReceiver(user);
        List<TransactionConnectionDescriptionAmountDTO> transactions = new ArrayList<>();

        Bank currentBank = user.getBankList().get(0);
        List<BankTransaction> creditFromBank = currentBank.getTransactionList();

        List<Bank> banks = user.getBankList().stream().skip(1).toList();

        transactions.addAll(banks.stream()
                .flatMap(bank -> bank.getTransactionList().stream())
                .map(t -> mapper.debitFromBankTransaction(t))
                .toList());

        transactions.addAll(creditFromBank.stream()
                .map(t -> mapper.creditFromBankTransaction(t))
                .toList());

        transactions.addAll(debitFromUser.stream()
                .flatMap(c -> c.getTransactionList().stream()
                        .map(t -> {
                            User receiver = c.getReceiver();
                            TransactionConnectionDescriptionAmountDTO transaction = mapper.debitFromUserTransaction(t);
                            transaction.setConnection(receiver.getLastName() + " " + receiver.getFirstName());
                            return transaction;
                        }))
                .toList());

        transactions.addAll(creditFromUser.stream().flatMap(c -> c.getTransactionList().stream()
                         .map(t -> {
                             User sender = c.getSender();
                             TransactionConnectionDescriptionAmountDTO transaction = mapper.creditFromUserTransaction(t);
                             transaction.setConnection(sender.getLastName() + " " + sender.getFirstName());
                             return transaction;
                         }))
                .toList());

        transactions = transactions.stream()
                .sorted(Comparator.comparing(TransactionConnectionDescriptionAmountDTO::getDate).reversed())
                .collect(Collectors.toList());

        transferDetails.setTransactions(transactions);
        return transferDetails;
    }
}