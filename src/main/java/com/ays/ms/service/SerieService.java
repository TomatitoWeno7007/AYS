package com.ays.ms.service;

//import com.ays.ms.model.ProgramGenre;
import com.ays.ms.controller.dto.request.SerieRequest;
import com.ays.ms.controller.dto.response.SerieResponse;
import com.ays.ms.exceptions.ResourceNotFoundException;
import com.ays.ms.model.Genres;
import com.ays.ms.model.Serie;
import com.ays.ms.respository.SerieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public void addSerie(SerieRequest serieRequest) {

        Serie serie = new Serie();
        serie.setName(serieRequest.getName());
        serie.setDescription(serieRequest.getDescription());
        List<Genres> genres = genreService.getGenresByName(serieRequest.getGenres());
        serie.setGenres(genres);

        this.serieRepository.addSerie(serie);
    }

    @Transactional
    public void editSerie(SerieRequest serieRequest) {
        Serie serie = this.getSerie(serieRequest.getId());

        if (serie == null) {
            throw new ResourceNotFoundException("No existe la serie a editar");
        }

        serie.setDescription(serieRequest.getDescription());
        serie.setName(serieRequest.getName());
        serie.setImg(String.valueOf(serieRequest.getImg()));

        List<Genres> genres = genreService.getGenresByName(serieRequest.getGenres());
        serie.setGenres(genres);

    }

}
