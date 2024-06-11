package com.ays.ms.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chapter")
@Data
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String name;
    protected String duration;
    @Lob
    private String synopsis;
    private long number;
    private String url;
    private String img;

    @OneToMany(mappedBy = "id.chapter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserChapterWatching> currentUsers;
}
