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
import java.io.File;
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

    public Chapter addChapter(ChapterRequest chapterRequest, Serie serie, Season season) throws IOException {
        Chapter chapter = new Chapter();
        chapter.setName(chapterRequest.getName());
        chapter.setSynopsis(chapterRequest.getSynopsis());
        chapter.setNumber(chapterRequest.getNumber());
        MultipartFile imgFile = chapterRequest.getImg();
        MultipartFile urlFile = chapterRequest.getUrl();

        Path path = null;

        if (!chapterRequest.getImg().isEmpty()) {
            String imgChapterName = StringUtils.cleanPath(imgFile.getOriginalFilename());
            path = Paths.get("static", "media", "img" , serie.getName(), String.valueOf(season.getNumber()), String.valueOf(chapter.getNumber())).resolve(imgChapterName);
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
            Path pathUrl = Paths.get("static", "media", "video" , serie.getName(), String.valueOf(season.getNumber()), String.valueOf(chapter.getNumber())).resolve(urlFilmName);

            try {
                Files.createDirectories(pathUrl.getParent());
                Files.write(pathUrl, urlFile.getBytes());
                chapter.setUrl(chapterRequest.getUrl().getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.chapterRepository.saveChapter(chapter);

        return chapter;
    }

    @Transactional
    public void editChapter(ChapterRequest chapterRequest) throws IOException {
        Chapter chapter = this.getChapterById(chapterRequest.getId());

        if (chapter == null) {
            throw new ResourceNotFoundException("No existe el capítulo a editar");
        }

        if (! chapterRequest.getImg().isEmpty()) {
            MultipartFile imgFile = chapterRequest.getImg();

            // Borra la anterior ruta, obteniendo el cap antes de editarla
            if (chapter.getImg() != null) {
                Path pathDelete = Paths.get("static", "media", "img" , chapter.getName()).resolve(chapter.getImg());
                Files.deleteIfExists(pathDelete);
            }

            String imgFilmName = StringUtils.cleanPath(imgFile.getOriginalFilename());
            Path path = Paths.get("static", "media", "img" , chapter.getName()).resolve(imgFilmName);

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
            MultipartFile urlFile = chapterRequest.getUrl();

            // Borra la anterior ruta, obteniendo la peli antes de editarla
            if (chapter.getUrl() != null) {
                Path pathDelete = Paths.get("static", "media", "video" , chapter.getName()).resolve(chapter.getUrl());
                Files.deleteIfExists(pathDelete);
            }

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
