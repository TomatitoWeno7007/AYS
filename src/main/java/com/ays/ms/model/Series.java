package com.ays.ms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "program")
@Data
public class Series extends Program {
    private String season;
    private String chapter;
}
