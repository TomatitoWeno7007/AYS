package com.ays.ms.respository;

import com.ays.ms.model.Film;
import com.ays.ms.respository.springdata.FilmSpringDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilmRepository {

    @Autowired
    private FilmSpringDataRepository filmSpringDataRepository;

    public List<Film> getFilms() { return filmSpringDataRepository.findAll(); }
}
