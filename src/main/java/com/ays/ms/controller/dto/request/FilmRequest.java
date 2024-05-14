package com.ays.ms.controller.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class FilmRequest {

    private long id;

    @NotEmpty
    private String name;


    private String duration;

    @NotEmpty
    private String description;


    private MultipartFile img;

    private MultipartFile url;

    @NotNull
    @NotEmpty
    private List<String> genres;

}
