package com.ays.ms.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "card")
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String cardUser;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cvv;


}
