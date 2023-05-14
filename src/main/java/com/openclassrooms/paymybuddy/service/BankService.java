package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Bank;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for Bank operations.
 */
@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private UserService userService;

    /**
     * Saves a Bank object in the database.
     *
     * @param bank The Bank object to be saved.
     * @return The saved Bank object.
     */
    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }

    /**
     * Creates a new Bank and saves it in the database.
     *
     * @param iban  The IBAN of the bank account to be associated with the user.
     * @param swift The SWIFT/BIC code of the bank to be associated with the user.
     * @return The new Bank object.
     */
    public Bank addBank(String iban, String swift) {
        Bank bank = new Bank();
        User user = userService.getCurrentUser();

        bank.setUser(user);
        bank.setIban(iban);
        bank.setSwift(swift);

        return bankRepository.save(bank);
    }

    /**
     * Retrieves a Bank object from the database based on the given IBAN.
     *
     * @param iban The IBAN to search for.
     * @return The Bank object with the given IBAN, or null if no Bank exists.
     */
    public Bank findByIban(String iban) {
        return bankRepository.findByIban(iban);
    }
}
