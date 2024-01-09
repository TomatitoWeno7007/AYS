package com.ays.ms.controller.dto.response;

import com.ays.ms.controller.dto.request.SerieRequest;
import com.ays.ms.model.Genres;
import com.ays.ms.model.Season;
import lombok.Data;
import java.util.List;

@Data
public class SerieResponse {

    private long id;
    private List<Genres> genres;
    private String name;
    private int views;
    private String description;
    private int rating;
    private String img;
    private List<Season> seasons;

    private SerieRequest editSerie;

}
