package com.ays.ms.model;

import javax.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public class Program {

    protected String name;
    protected String genre;
    protected int views;
    protected String duration;
    protected String description;
    protected int rating;
    protected String img;

}
