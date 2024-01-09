package com.ays.ms.controller.dto.response;

import com.ays.ms.controller.dto.request.ChapterRequest;
import lombok.Data;

@Data
public class ChapterResponse {

    private long id;
    private String name;
    private String duration;
    private String synopsis;
    private long number;
    private String url;
    private String img;

    public ChapterRequest editChapter;

}
