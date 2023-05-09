package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Bank;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private UserService userService;

    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }

    public Bank addBank(String iban, String swift) {
        Bank bank = new Bank();
        User user = userService.getCurrentUser();

        bank.setUser(user);
        bank.setIban(iban);
        bank.setSwift(swift);

        return bankRepository.save(bank);
    }

    public Bank findByIban(String iban) {
        return bankRepository.findByIban(iban);
    }
}
