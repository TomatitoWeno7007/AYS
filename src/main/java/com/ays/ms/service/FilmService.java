package com.ays.ms.service;

import com.ays.ms.controller.dto.request.FilmRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        MultipartFile imgFile = filmRequest.getImg();
        MultipartFile urlFile = filmRequest.getUrl();
        film.setDuration(filmRequest.getDuration());

        if (! filmRequest.getImg().isEmpty()) {
            String imgFilmName = StringUtils.cleanPath(imgFile.getOriginalFilename());
            // Crea una carpeta con el nombre de la peli para tenerlo más ordenado
            Path path = Paths.get("static", "media", "img" , film.getName()).resolve(imgFilmName);
            try {
                // Verificar si el directorio existe, si no, lo crea
                Files.createDirectories(path.getParent());
                Files.write(path, imgFile.getBytes());
                film.setImg(filmRequest.getImg().getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (! filmRequest.getUrl().isEmpty()) {
            String urlFilmName = StringUtils.cleanPath(urlFile.getOriginalFilename());
            Path path = Paths.get("static", "media", "video" , film.getName()).resolve(urlFilmName);



            try {
                Files.createDirectories(path.getParent());
                Files.write(path, urlFile.getBytes());
                film.setUrl(filmRequest.getUrl().getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.filmRepository.addFilm(film);
    }

    @Transactional
    public void editFilm(FilmRequest filmRequest) throws IOException {
        Film film = this.getFilm(filmRequest.getId());

        if (film == null) {
            throw new ResourceNotFoundException("No existe la película a editar");
        }

        if (! filmRequest.getImg().isEmpty()) {
            MultipartFile imgFile = filmRequest.getImg();

            // Borra la anterior ruta, obteniendo la peli antes de editarla
            if (film.getImg() != null) {
                Path pathDelete = Paths.get("static", "media", "img" , film.getName()).resolve(film.getImg());
                Files.deleteIfExists(pathDelete);
            }

            String imgFilmName = StringUtils.cleanPath(imgFile.getOriginalFilename());
            Path path = Paths.get("static", "media", "img" , film.getName()).resolve(imgFilmName);

            try {
                // Verificar si el directorio existe, si no, lo crea
                Files.createDirectories(path.getParent());
                Files.write(path, imgFile.getBytes());
                film.setImg(filmRequest.getImg().getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (! filmRequest.getUrl().isEmpty()) {
            MultipartFile urlFile = filmRequest.getUrl();

            // Borra la anterior ruta, obteniendo la peli antes de editarla
            if (film.getUrl() != null) {
                Path pathDelete = Paths.get("static", "media", "video" , film.getName()).resolve(film.getUrl());
                Files.deleteIfExists(pathDelete);
            }

            String urlFilmName = StringUtils.cleanPath(urlFile.getOriginalFilename());
            Path path = Paths.get("static", "media", "video" , film.getName()).resolve(urlFilmName);

            try {
                Files.createDirectories(path.getParent());
                Files.write(path, urlFile.getBytes());
                film.setUrl(filmRequest.getUrl().getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        film.setDescription(filmRequest.getDescription());
        film.setName(filmRequest.getName());

        List<Genres> genres = genreService.getGenresByName(filmRequest.getGenres());
        film.setGenres(genres);

    }

    @Transactional
    public void deleteFilm(Long id) throws IOException {
        Film film = this.getFilm(id);

        if (film == null) {
            throw new ResourceNotFoundException("No existe la pelicula a eliminar");
        }

        film.getGenres().forEach(genre -> genre.getSerieGenre().remove(film));

        if (film.getImg() != null) {
            Path pathDelete = Paths.get("static", "media", "img" , film.getName()).resolve(film.getImg());
            Files.deleteIfExists(pathDelete);
        }

        if (film.getUrl() != null) {
            Path pathDelete = Paths.get("static", "media", "video" , film.getName()).resolve(film.getUrl());
            Files.deleteIfExists(pathDelete);
        }

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
