package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "connection_transaction")
public class ConnectionTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private int idTransaction;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private float amount;

    @Column(name = "fees")
    private float fees;

    @ManyToOne
    @JoinColumn(name="id_connection")
    private Connection connection;
}
