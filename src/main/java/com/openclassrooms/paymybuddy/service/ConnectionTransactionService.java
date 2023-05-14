package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.ConnectionTransaction;
import com.openclassrooms.paymybuddy.repository.ConnectionTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service class handles operations related to Connection Transactions.
 */
@Service
public class ConnectionTransactionService {
    @Autowired
    private ConnectionTransactionRepository connectionTransactionRepository;

    /**
     * Saves a ConnectionTransaction.
     *
     * @param connectionTransaction The ConnectionTransaction to save.
     * @return The saved ConnectionTransaction.
     */
    public ConnectionTransaction save(ConnectionTransaction connectionTransaction) {
        return connectionTransactionRepository.save(connectionTransaction);
    }

}
