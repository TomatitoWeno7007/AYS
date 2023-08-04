package com.ays.ms.model;

import jakarta.persistence.Entity;

@Entity
public class Series extends Program {
    private String season;
    private String chapter;
}
