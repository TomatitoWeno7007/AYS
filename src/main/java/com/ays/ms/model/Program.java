package com.ays.ms.model;

import jakarta.persistence.Entity;

@Entity
public class Program {
    private String name;
    private String genre;
    private int views;
    private String duration;
    private String description;
    private int rating;
    private String img;
    private String url; //URL de la serie/peli en mega
}
