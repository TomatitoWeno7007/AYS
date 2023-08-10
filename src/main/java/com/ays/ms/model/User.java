package com.ays.ms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    private Long id;
    private String name;
    private String lastName;
    private String secondLastName;
    private String email;
    private LocalDateTime dateBirth;
    private String password;

    private Card card;

}
