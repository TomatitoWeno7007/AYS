package com.ays.ms.controller.dto.request;

import com.ays.ms.model.Season;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SerieRequest {

    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    private MultipartFile img;

    @NotNull
    @NotEmpty
    private List<String> genres;
    private List<Season> seasons;

}
