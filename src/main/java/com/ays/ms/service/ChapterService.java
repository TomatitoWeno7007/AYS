package com.ays.ms.service;

import com.ays.ms.controller.dto.request.ChapterRequest;
import com.ays.ms.controller.dto.request.FilmRequest;
import com.ays.ms.exceptions.ResourceNotFoundException;
import com.ays.ms.model.*;
import com.ays.ms.respository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        MultipartFile imgFile = chapterRequest.getImg();
        MultipartFile urlFile = chapterRequest.getUrl();


        if (! chapterRequest.getImg().isEmpty()) {
            String imgChapterName = StringUtils.cleanPath(imgFile.getOriginalFilename());
            // Crea una carpeta con el nombre del cap para tenerlo más ordenado
            Path path = Paths.get("static", "media", "img" , chapter.getName()).resolve(imgChapterName);

            try {
                // Verificar si el directorio existe, si no, lo crea
                Files.createDirectories(path.getParent());
                Files.write(path, imgFile.getBytes());
                chapter.setImg(chapterRequest.getImg().getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (! chapterRequest.getUrl().isEmpty()) {
            String urlFilmName = StringUtils.cleanPath(urlFile.getOriginalFilename());
            Path path = Paths.get("static", "media", "video" , chapter.getName()).resolve(urlFilmName);

            try {
                Files.createDirectories(path.getParent());
                Files.write(path, urlFile.getBytes());
                chapter.setUrl(chapterRequest.getUrl().getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
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
