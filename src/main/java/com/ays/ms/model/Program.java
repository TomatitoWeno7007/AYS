package com.ays.ms.model;

import javax.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@MappedSuperclass
public class Program {

    protected String name;
    protected int views;
    protected String description;
    protected int rating;
    protected String img;

}
