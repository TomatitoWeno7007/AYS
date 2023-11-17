package com.ays.ms.model;

import lombok.Data;

import javax.persistence.*;

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
    private String expirationDate;
    private String cvv;


}
