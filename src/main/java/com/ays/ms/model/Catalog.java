package com.ays.ms.model;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Catalog {
    private List<Program> watchlists;
    private List<Program> likedSeries;
}
