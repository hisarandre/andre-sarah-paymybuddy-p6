package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "connection")
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_connection")
    private int idConnection;

    @Column(name = "id_sender")
    private int idSender;

    @Column(name = "id_receiver")
    private int idReceiver;

    @OneToMany(
            mappedBy = "connection",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<ConnectionTransaction> transactionList= new ArrayList<>();

    @Override
    public String toString() {
        return "Connection{" +
                "idConnection='" + idConnection + '\'' +
                ", idSender='" + idSender + '\'' +
                ", idReceiver='" + idReceiver + '\'' +
                ", transactionList='" + transactionList.stream().map(ConnectionTransaction::getIdTransaction).collect(Collectors.toList()) + '\'' +
                '}';
    }
}
