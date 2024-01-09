package com.ays.ms.respository;

import com.ays.ms.model.Film;
import com.ays.ms.model.Genres;
import com.ays.ms.respository.springdata.FilmSpringDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository {

    @Autowired
    private FilmSpringDataRepository filmSpringDataRepository;

    public List<Film> getFilms() { return filmSpringDataRepository.findAll(); }

    public Film getFilm(Long id) {
        Optional<Film> optionalFilm = this.filmSpringDataRepository.findById(id);
        return optionalFilm.orElse(null);
    }

    public Long getNumberFilms() {
        return filmSpringDataRepository.count();
    }

    public void deleteFilm(Long id) {
        filmSpringDataRepository.deleteById(id);
    }

    public void addFilm(Film film) {
        filmSpringDataRepository.save(film);
    }
}
