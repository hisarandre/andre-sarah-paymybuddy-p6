package com.openclassrooms.paymybuddy.dto;

import com.openclassrooms.paymybuddy.model.User;
import lombok.Data;

import java.util.List;

@Data
public class ContactsAndTransactionsListDTO {

    private List<User> contacts;
    private List<TransactionConnectionDescriptionAmountDTO> transactions;

}
