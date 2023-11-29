package com.ays.ms.controller.dto.response;

import com.ays.ms.controller.dto.request.FilmRequest;
import com.ays.ms.model.Genres;
import lombok.Data;
import java.util.List;

@Data
public class FilmResponse {

    private long id;
    private List<Genres> genres;
    private String name;
    private int views;
    private String description;
    private int rating;
    private String img;
    private String url;
    private String duration;

    private FilmRequest editFilm;

}
