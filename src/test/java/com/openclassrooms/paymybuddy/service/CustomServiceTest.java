package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.dto.BankTransferDTO;
import com.openclassrooms.paymybuddy.dto.UserTransferDTO;
import com.openclassrooms.paymybuddy.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class CustomServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private BankService bankService;

    @Mock
    private ConnectionService connectionService;

    @Mock
    private ConnectionTransactionService connectionTransactionService;

    @Mock
    private BankTransactionService bankTransactionService;

    @InjectMocks
    private CustomService customService;

    @Test
    public void testSendMoneyToBank_SufficientBalance_SuccessfulTransfer() {
        //GIVEN
        User sender = new User();
        sender.setBalance(1000.0f);
        when(userService.getCurrentUser()).thenReturn(sender);

        Bank bank = new Bank();
        when(bankService.findByIban(Mockito.anyString())).thenReturn(bank);

        BankTransferDTO bankTransfer = new BankTransferDTO();
        bankTransfer.setIban("receiver_iban");
        bankTransfer.setAmount(500.0f);
        bankTransfer.setDescription("Test bank transfer");

        //WHEN
        boolean result = customService.sendMoneyToBank(bankTransfer);

        //THEN
        Assertions.assertTrue(result);
        Assertions.assertEquals(475.0f, sender.getBalance());
        Mockito.verify(bankTransactionService, Mockito.times(1)).save(any(BankTransaction.class));
    }

    @Test
    public void testSendMoneyToBank_InsufficientBalance_FailedTransfer() {
        //GIVEN
        User sender = new User();
        sender.setBalance(100.0f);
        when(userService.getCurrentUser()).thenReturn(sender);

        Bank bank = new Bank();
        when(bankService.findByIban(Mockito.anyString())).thenReturn(bank);

        BankTransferDTO bankTransfer = new BankTransferDTO();
        bankTransfer.setIban("recipient_iban");
        bankTransfer.setAmount(500.0f);
        bankTransfer.setDescription("Test bank transfer");

        //WHEN
        boolean result = customService.sendMoneyToBank(bankTransfer);

        //THEN
        Assertions.assertFalse(result);
        Assertions.assertEquals(100.0f, sender.getBalance());
        Mockito.verify(bankTransactionService, Mockito.never()).save(any(BankTransaction.class));
    }

    @Test
    public void testSendMoneyToUser_SufficientBalance_SuccessfulTransfer() {
        //GIVEN
        User sender = new User();
        sender.setBalance(1000.0f);
        when(userService.getCurrentUser()).thenReturn(sender);

        User receiver = new User();
        when(userService.findById(Mockito.anyInt())).thenReturn(receiver);

        Connection connection = new Connection();
        when(connectionService.findBySenderAndReceiver(any(User.class), any(User.class))).thenReturn(connection);

        UserTransferDTO userTransfer = new UserTransferDTO();
        userTransfer.setIdReceiver(2);
        userTransfer.setAmount(500.0f);
        userTransfer.setDescription("Test user transfer");

        //WHEN
        boolean result = customService.sendMoneyToUser(userTransfer);

        //THEN
        Assertions.assertTrue(result);
        Assertions.assertEquals(475.0f, sender.getBalance());
        Assertions.assertEquals(500.0f, receiver.getBalance());
        Mockito.verify(connectionTransactionService, Mockito.times(1)).save(any(ConnectionTransaction.class));
    }

    @Test
    public void testSendMoneyToUser_InsufficientBalance_FailedTransfer() {
        //GIVEN
        User sender = new User();
        sender.setBalance(100.0f);
        when(userService.getCurrentUser()).thenReturn(sender);

        User receiver = new User();
        when(userService.findById(Mockito.anyInt())).thenReturn(receiver);

        Connection connection = new Connection();
        when(connectionService.findBySenderAndReceiver(any(User.class), any(User.class))).thenReturn(connection);

        UserTransferDTO userTransfer = new UserTransferDTO();
        userTransfer.setIdReceiver(2);
        userTransfer.setAmount(500.0f);
        userTransfer.setDescription("Test user transfer");

        //WHEN
        boolean result = customService.sendMoneyToUser(userTransfer);

        //THEN
        Assertions.assertFalse(result);
        Assertions.assertEquals(100.0f, sender.getBalance());
        Assertions.assertEquals(0.0f, receiver.getBalance());
        Mockito.verify(connectionTransactionService, Mockito.never()).save(any(ConnectionTransaction.class));
    }
}
