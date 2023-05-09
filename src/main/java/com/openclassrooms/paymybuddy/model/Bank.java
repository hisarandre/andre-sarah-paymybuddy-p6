package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bank")
    private int idBank;

    @Column(name = "iban")
    private String iban;

    @Column(name = "swift")
    private String swift;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name="id_user")
    private User user;

    @OneToMany(
            mappedBy = "bank",
            cascade = CascadeType.ALL,
            orphanRemoval = true
            )
    List<BankTransaction> transactionList= new ArrayList<>();
}