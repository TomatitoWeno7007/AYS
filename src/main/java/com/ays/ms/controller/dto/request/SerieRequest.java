package com.ays.ms.controller.dto.request;

import com.ays.ms.model.Genres;
import com.ays.ms.model.Season;
import com.ays.ms.model.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

}
