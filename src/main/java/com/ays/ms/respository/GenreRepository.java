package com.ays.ms.respository;

import com.ays.ms.model.Genres;
import com.ays.ms.model.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ays.ms.respository.springdata.GenreSpringDataRepository;

import java.util.List;

@Repository
public class GenreRepository {

    @Autowired
    private GenreSpringDataRepository genreSpringDataRepository;

    public List<Genres> getGenres() { return genreSpringDataRepository.findAll(); }

    public List<Genres> getGenresByName(List<String> names) {
        return genreSpringDataRepository.findByNameIn(names);
    }

}
