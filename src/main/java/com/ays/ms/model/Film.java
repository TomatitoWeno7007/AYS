package com.ays.ms.model;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "film")
@Data
public class Film extends Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String url;

}
