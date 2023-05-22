package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Bank;
import com.openclassrooms.paymybuddy.model.User;

import com.openclassrooms.paymybuddy.repository.BankRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BankServiceTest {
    @Mock
    private BankRepository bankRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private BankService bankService;

    @Test
    public void testSaveBank() {
        //GIVEN
        Bank bank = new Bank();
        Mockito.when(bankRepository.save(Mockito.any(Bank.class))).thenReturn(bank);

        //WHEN
        Bank result = bankService.save(bank);

        //THEN
        Assertions.assertNotNull(result);
        Mockito.verify(bankRepository, Mockito.times(1)).save(bank);
    }

    @Test
    public void testAddBank() {
        //GIVEN
        User user = new User();
        Bank bank = new Bank();
        String iban = "1234567890";
        String swift = "ABCDEF";

        bank.setUser(user);
        bank.setIban(iban);
        bank.setSwift(swift);

        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(bankRepository.save(Mockito.any(Bank.class))).thenReturn(bank);

        //WHEN
        Bank result = bankService.addBank(iban, swift);

        //THEN
        Assertions.assertNotNull(result);
        Mockito.verify(bankRepository, Mockito.times(1)).save(bank);
    }
}
