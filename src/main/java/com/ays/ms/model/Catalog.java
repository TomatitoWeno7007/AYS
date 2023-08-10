package com.ays.ms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "catalog")
@Data
public class Catalog {

    @Id
    private long id;
    private List<Program> watchlists;
    private List<Program> likedSeries;

}
