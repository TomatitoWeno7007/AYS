package com.ays.ms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "program")
@Data
public class Program {

    @Id
    private long id;

    private String name;
    private String genre;
    private int views;
    private String duration;
    private String description;
    private int rating;
    private String img;
    private String url; //URL de la serie/peli en mega

}
