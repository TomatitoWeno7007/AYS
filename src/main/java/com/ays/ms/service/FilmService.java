package com.ays.ms.service;

import com.ays.ms.model.Film;
import com.ays.ms.respository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public List<Film> getFilms() { return filmRepository.getFilms(); }

}
