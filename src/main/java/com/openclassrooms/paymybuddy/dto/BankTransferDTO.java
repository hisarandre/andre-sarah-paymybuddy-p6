package com.openclassrooms.paymybuddy.dto;

import lombok.Data;
@Data
public class BankTransferDTO {

        private String iban;
        private String description;
        private float amount;
}
