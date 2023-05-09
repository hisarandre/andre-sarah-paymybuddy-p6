package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankTransactionService {

    @Autowired
    BankTransactionRepository bankTransactionRepository;
    public BankTransaction save(BankTransaction bankTransaction) {
        return bankTransactionRepository.save(bankTransaction);
    }
}
