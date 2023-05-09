package com.openclassrooms.paymybuddy.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransactionConnectionDescriptionAmountDTO {

    private Timestamp date;
    private String connection;
    private String description;
    private float amount;

    public TransactionConnectionDescriptionAmountDTO(Timestamp date, String connection, String description, float amount) {
        this.date = date;
        this.connection = connection;
        this.description = description;
        this.amount = amount;
    }
}
