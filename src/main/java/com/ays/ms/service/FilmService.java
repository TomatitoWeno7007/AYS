package com.ays.ms.service;

import com.ays.ms.controller.dto.request.FilmRequest;
import com.ays.ms.controller.dto.request.SerieRequest;
import com.ays.ms.controller.dto.response.FilmResponse;
import com.ays.ms.exceptions.ResourceNotFoundException;
import com.ays.ms.model.Film;
import com.ays.ms.model.Genres;
import com.ays.ms.model.Serie;
import org.modelmapper.ModelMapper;
import com.ays.ms.respository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private GenreService genreService;
    @Autowired
    ModelMapper modelMapper;

    public List<Film> getFilms() { return filmRepository.getFilms(); }

    public Film getFilm(Long id) { return filmRepository.getFilm(id); }

    public Long getNumberFilms () {
        return this.filmRepository.getNumberFilms();
    }

    public void addFilm(FilmRequest filmRequest) {

        Film film = new Film();
        film.setName(filmRequest.getName());
        film.setDescription(filmRequest.getDescription());
        List<Genres> genres = genreService.getGenresByName(filmRequest.getGenres());
        film.setGenres(genres);
        if (! filmRequest.getImg().isEmpty()) {
            film.setImg(String.valueOf(filmRequest.getImg()));
        }
        if (! filmRequest.getUrl().isEmpty()) {
            film.setUrl(String.valueOf(filmRequest.getUrl()));
        }

        this.filmRepository.addFilm(film);
    }

    @Transactional
    public void editFilm(FilmRequest filmRequest) {
        Film film = this.getFilm(filmRequest.getId());

        if (film == null) {
            throw new ResourceNotFoundException("No existe la película a editar");
        }


        if (! filmRequest.getImg().isEmpty()) {
            film.setImg(String.valueOf(filmRequest.getImg()));
        }
        if (! filmRequest.getUrl().isEmpty()) {
            film.setUrl(String.valueOf(filmRequest.getUrl()));
        }
        film.setDescription(filmRequest.getDescription());
        film.setName(filmRequest.getName());

        List<Genres> genres = genreService.getGenresByName(filmRequest.getGenres());
        film.setGenres(genres);

    }

    @Transactional
    public void deleteFilm(Long id) {
        Film film = this.getFilm(id);

        if (film == null) {
            throw new ResourceNotFoundException("No existe la pelicula a eliminar");
        }

        film.getGenres().forEach(genre -> genre.getSerieGenre().remove(film));

        film.setUsersLiked(null);
        film.setUsersWatched(null);
        film.setUsersRecommended(null);
        this.filmRepository.deleteFilm(id);
    }

    public List<FilmResponse> getFilmFromView() {
        List<Film> films = this.getFilms();

        List<FilmResponse> responses = films.stream()
                .map( film -> modelMapper.map(film, FilmResponse.class))
                .collect(Collectors.toList());

        responses.forEach( response ->  {

            FilmRequest request = new FilmRequest();
            request.setId(response.getId());
            request.setName(response.getName());
            request.setDescription(response.getDescription());
            request.setGenres(response.getGenres().stream().map(Genres::getName)
                    .collect(Collectors.toList()));
            response.setEditFilm(request);
        });

        return responses;
    }

}
