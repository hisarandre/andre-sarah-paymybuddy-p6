package com.openclassrooms.paymybuddy.dto;

import com.openclassrooms.paymybuddy.model.Bank;
import lombok.Data;

import java.util.List;

@Data
public class AccountBalanceBanksDTO {

        private String email;

        private String firstName;

        private String lastName;

        private float balance;

        private Bank currentBank;
        private List<Bank> bankList;

}
