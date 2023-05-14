package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing bank transactions.
 */
@Service
public class BankTransactionService {

    @Autowired
    BankTransactionRepository bankTransactionRepository;

    /**
     * Saves the given bank transaction to the database.
     *
     * @param bankTransaction the bank transaction to save
     * @return the saved bank transaction
     */
    public BankTransaction save(BankTransaction bankTransaction) {
        return bankTransactionRepository.save(bankTransaction);
    }
}
