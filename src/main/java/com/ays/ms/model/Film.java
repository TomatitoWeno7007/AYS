package com.ays.ms.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "film")
@Data
public class Film extends Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


}
