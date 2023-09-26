package com.ays.ms.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "serie")
@Data
public class Serie extends Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String season;
    private String chapter;
}
