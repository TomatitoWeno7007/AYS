package com.ays.ms.model;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Client extends User {
    private String cardUser;
    private int cardNumber;
    private LocalDateTime expirationDate;
    private String cvv;


}
