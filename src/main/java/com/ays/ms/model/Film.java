package com.ays.ms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "film")
@Data
public class Film extends Program {

    @Id
    private long id;


}
