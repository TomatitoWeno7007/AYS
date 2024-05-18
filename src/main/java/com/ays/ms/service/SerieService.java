package com.ays.ms.service;

//import com.ays.ms.model.ProgramGenre;
import com.ays.ms.controller.dto.request.ChapterRequest;
import com.ays.ms.controller.dto.request.SerieRequest;
import com.ays.ms.controller.dto.response.ChapterResponse;
import com.ays.ms.controller.dto.response.SerieResponse;
import com.ays.ms.exceptions.ResourceNotFoundException;
import com.ays.ms.model.Chapter;
import com.ays.ms.model.Genres;
import com.ays.ms.model.Season;
import com.ays.ms.model.Serie;
import com.ays.ms.respository.SerieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private GenreService genreService;

    @Autowired
    ModelMapper modelMapper;

    public List<Serie> getSeries() { return serieRepository.getSeries(); }

    public List<SerieResponse> getSerieFromView() {
        List<Serie> series = this.getSeries();

        List<SerieResponse> responses = series.stream()
                .map( serie -> modelMapper.map(serie, SerieResponse.class))
                .collect(Collectors.toList());

        responses.forEach( response ->  {

            SerieRequest request = new SerieRequest();
            request.setId(response.getId());
            request.setName(response.getName());
            request.setDescription(response.getDescription());
            request.setGenres(response.getGenres().stream().map(Genres::getName)
                    .collect(Collectors.toList()));
            response.setEditSerie(request);
        });

        return responses;
    }

    public Serie getSerie(Long id) {
        return this.serieRepository.getSerie(id);
    }

    public Long getNumberSeries () {
        return this.serieRepository.getNumberSeries();
    }

    @Transactional
    public void deleteSerie(Long id) {
        Serie serie = this.getSerie(id);

        if (serie == null) {
            throw new ResourceNotFoundException("No existe la serie a eliminar");
        }

        serie.getGenres().forEach(genre -> genre.getSerieGenre().remove(serie));

        serie.setUsersLiked(null);
        serie.setUsersWatched(null);
        serie.setUsersRecommended(null);
        this.serieRepository.deleteSerie(id);
    }

    public Serie saveOrUpdate(SerieRequest serieRequest) {

        Serie serie = new Serie();
        serie.setName(serieRequest.getName());
        serie.setDescription(serieRequest.getDescription());
        List<Genres> genres = genreService.getGenresByName(serieRequest.getGenres());
        serie.setGenres(genres);
        MultipartFile imgFile = serieRequest.getImg();
        if (! serieRequest.getImg().isEmpty()) {
            String imgSerieName = StringUtils.cleanPath(imgFile.getOriginalFilename());
            // Crea una carpeta con el nombre de la peli para tenerlo más ordenado
            Path path = Paths.get("static", "media", "img" , serie.getName()).resolve(imgSerieName);
            try {
                // Verificar si el directorio existe, si no, lo crea
                Files.createDirectories(path.getParent());
                Files.write(path, imgFile.getBytes());
                serie.setImg(serieRequest.getImg().getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Season season = new Season();
        season.setNumber(1);
        List<Season> listSeason = new ArrayList<>();
        listSeason.add(season);
        serie.setSeasons(listSeason);

        return this.saveOrUpdate(serie);
    }

    public Serie saveOrUpdate(Serie serie) {
        return this.serieRepository.addSerie(serie);
    }

    @Transactional
    public void editSerie(SerieRequest serieRequest) {
        Serie serie = this.getSerie(serieRequest.getId());

        if (serie == null) {
            throw new ResourceNotFoundException("No existe la serie a editar");
        }


        if (! serieRequest.getImg().isEmpty()) {
            serie.setImg(String.valueOf(serieRequest.getImg()));
        }

        serie.setDescription(serieRequest.getDescription());
        serie.setName(serieRequest.getName());

        List<Genres> genres = genreService.getGenresByName(serieRequest.getGenres());
        serie.setGenres(genres);

    }

    public List<ChapterResponse> getChaptersFromView(Season season) {
        List<Chapter> chapters = season.getChapters();

        List<ChapterResponse> responses = chapters.stream()
                .map( chapter -> modelMapper.map(chapter, ChapterResponse.class))
                .collect(Collectors.toList());

        responses.forEach( response ->  {
            ChapterRequest request = new ChapterRequest();
            request.setId(response.getId());
            request.setName(response.getName());
            request.setNumber(response.getNumber());
            request.setSynopsis(response.getSynopsis());
            request.setSynopsis(response.getSynopsis());
            request.setSynopsis(response.getSynopsis());
        });

        return responses;
    }

    public void editSeason(Serie serie, List<Season> listSeason) {
        serie.setSeasons(listSeason);
    }



//    public Serie getSerieBySeason(Season season) {
//        List<Serie> listSerie = serieRepository.getSeries();
//    }

//    public Serie getSerieByIdSeason(Long id) {
//        List<Serie> listSerie = serieRepository.getSeries();
//        for (int x = 0; x < listSerie.size()-1; x++) {
//            for (int y = 0; y < listSerie.get(x).getSeasons().size()-1; y++) {
//
//            }
//        }
//    }

}
