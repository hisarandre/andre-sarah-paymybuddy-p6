package com.openclassrooms.paymybuddy.model;

import lombok.Data;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int idUser;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private float balance;

    @ManyToMany
    @JoinTable(name = "connection", joinColumns = @JoinColumn(name="id_sender"), inverseJoinColumns = @JoinColumn(name="id_receiver"))
    private List<User> contactList = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = false
    )
    List<Bank> bankList = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactList='" + contactList + '\'' +
                '}';
    }
}
