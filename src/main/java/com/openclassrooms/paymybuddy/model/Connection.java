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

    @ManyToOne
    @JoinColumn(name = "id_sender", referencedColumnName = "id_user")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "id_receiver", referencedColumnName = "id_user")
    private User receiver;

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
                ", Sender name='" + sender.getFirstName() + '\'' +
                ", Receiver name='" + receiver.getFirstName() + '\'' +
                ", transactionList='" + transactionList.stream().map(ConnectionTransaction::getIdTransaction).collect(Collectors.toList()) + '\'' +
                '}';
    }
}
