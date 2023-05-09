package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.ConnectionTransaction;
import com.openclassrooms.paymybuddy.repository.ConnectionTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionTransactionService {
    @Autowired
    private ConnectionTransactionRepository connectionTransactionRepository;
    public ConnectionTransaction save(ConnectionTransaction connectionTransaction) {
        return connectionTransactionRepository.save(connectionTransaction);
    }

}
