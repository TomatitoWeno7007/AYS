package com.ays.ms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "card")
@Data
public class Card {

    @Id
    private long id;
    private String cardUser;
    private int cardNumber;
    private LocalDateTime expirationDate;
    private String cvv;


}
