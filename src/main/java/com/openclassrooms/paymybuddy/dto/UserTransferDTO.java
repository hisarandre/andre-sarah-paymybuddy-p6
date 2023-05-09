package com.openclassrooms.paymybuddy.dto;

import lombok.Data;

@Data
public class UserTransferDTO {

    private int idReceiver;
    private String description;
    private float amount;
}
