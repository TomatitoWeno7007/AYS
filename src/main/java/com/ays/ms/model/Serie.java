package com.ays.ms.model;

import javax.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "serie")
@Data
public class Serie extends Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany
    private List<Season> seasons;
}
