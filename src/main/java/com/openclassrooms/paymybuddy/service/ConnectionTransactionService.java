package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.ConnectionTransaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.ConnectionTransactionRepository;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.sql.Timestamp;

@Service
public class ConnectionTransactionService {
    @Autowired
    private ConnectionTransactionRepository connectionTransactionRepository;
    public ConnectionTransaction save(ConnectionTransaction connectionTransaction) {
        return connectionTransactionRepository.save(connectionTransaction);
    }

}
