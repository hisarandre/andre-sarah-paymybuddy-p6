package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class BankTransactionServiceTest {

    @InjectMocks
    private BankTransactionService bankTransactionService;

    @Mock
    private BankTransactionRepository bankTransactionRepository;

    @Test
    public void testSaveBankTransaction() {
        //GIVEN
        BankTransaction bankTransaction = new BankTransaction();
        when(bankTransactionRepository.save(Mockito.any(BankTransaction.class))).thenReturn(bankTransaction);

        //WHEN
        BankTransaction result = bankTransactionService.save(bankTransaction);

        //THEN
        Assertions.assertNotNull(result);
        Mockito.verify(bankTransactionRepository, Mockito.times(1)).save(bankTransaction);
    }
}
