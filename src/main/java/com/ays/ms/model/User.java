package com.ays.ms.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String lastName;
    private String secondLastName;
    private String email;
    private LocalDateTime dateBirth;
    private String password;
    @OneToOne
    private Card card;
    @OneToOne
    private Catalog catalog;

}
