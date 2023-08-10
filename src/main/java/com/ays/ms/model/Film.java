package com.ays.ms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "film")
@Data

public class Film extends Program {

}
