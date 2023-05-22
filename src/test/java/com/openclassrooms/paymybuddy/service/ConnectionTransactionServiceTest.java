package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.ConnectionTransaction;
import com.openclassrooms.paymybuddy.repository.ConnectionTransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ConnectionTransactionServiceTest {

    @InjectMocks
    private ConnectionTransactionService connectionTransactionService;

    @Mock
    private ConnectionTransactionRepository connectionTransactionRepository;

    @Test
    public void testSaveConnectionTransaction() {

        //GIVEN
        ConnectionTransaction connectionTransaction = new ConnectionTransaction();
        when(connectionTransactionRepository.save(Mockito.any(ConnectionTransaction.class))).thenReturn(connectionTransaction);

        //WHEN
        ConnectionTransaction result = connectionTransactionService.save(connectionTransaction);

        //THEN
        Assertions.assertNotNull(result);
        Mockito.verify(connectionTransactionRepository, Mockito.times(1)).save(connectionTransaction);
    }
}
