package com.openclassrooms.paymybuddy.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "authorities")
public class Authorities {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "authority", nullable = false, insertable = false)
    private String authority;

}
