package com.ays.ms.service;

import com.ays.ms.controller.dto.request.ChapterRequest;
import com.ays.ms.controller.dto.request.FilmRequest;
import com.ays.ms.exceptions.ResourceNotFoundException;
import com.ays.ms.model.*;
import com.ays.ms.respository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    public Chapter getChapterById(long id) { return chapterRepository.getChapter(id); }

    public Chapter addChapter(ChapterRequest chapterRequest) {
        Chapter chapter = new Chapter();
        chapter.setName(chapterRequest.getName());
        chapter.setSynopsis(chapterRequest.getSynopsis());
        chapter.setNumber(chapterRequest.getNumber());

        if (! chapterRequest.getImg().isEmpty()) {
            chapter.setImg(String.valueOf(chapterRequest.getImg()));
        }
        if (! chapterRequest.getUrl().isEmpty()) {
            chapter.setUrl(String.valueOf(chapterRequest.getUrl()));
        }

        this.chapterRepository.saveChapter(chapter);

        return chapter;
    }

    @Transactional
    public void editChapter(ChapterRequest chapterRequest) {
        Chapter chapter = this.getChapterById(chapterRequest.getId());

        if (chapter == null) {
            throw new ResourceNotFoundException("No existe el capítulo a editar");
        }


        if (! chapterRequest.getImg().isEmpty()) {
            chapter.setImg(String.valueOf(chapterRequest.getImg()));
        }
        if (! chapterRequest.getUrl().isEmpty()) {
            chapter.setUrl(String.valueOf(chapterRequest.getUrl()));
        }

        chapter.setName(chapterRequest.getName());
        chapter.setSynopsis(chapterRequest.getSynopsis());

    }


    public void deleteChapter(Chapter chapter) {
        this.chapterRepository.deleteChapter(chapter);
    }

    public Long getLastEpisodeBySeason(Season season) {
        List<Chapter> listChapters = season.getChapters();
        Long numberEpisode =
                (listChapters == null || listChapters.isEmpty()) ?
                        1L :
                        listChapters.get(listChapters.size()-1).getNumber()+1;

        return numberEpisode;
    }

}
