package com.ays.ms.service;

import com.ays.ms.model.Genres;
import com.ays.ms.respository.GenreRepository;
import com.ays.ms.respository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genres> getGenres() { return genreRepository.getGenres(); }

    public List<Genres> getGenresByName(List<String> names) {
        return genreRepository.getGenresByName(names);
    }

}
