package com.ays.ms.controller.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class ChapterRequest {

    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String synopsis;
    private long number;
    private MultipartFile url;
    private MultipartFile img;

}
