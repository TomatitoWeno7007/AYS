package com.ays.ms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "serie")
@Data
public class Serie extends Program {

    @Id
    private long id;
    private String season;
    private String chapter;
}
