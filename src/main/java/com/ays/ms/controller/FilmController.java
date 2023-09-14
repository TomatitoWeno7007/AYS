package com.ays.ms.controller;

import com.ays.ms.model.Film;
import com.ays.ms.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @PostMapping
    public void saveFilm() {

    }

    @DeleteMapping
    public String deleteFilm(@PathVariable("id") long id) { return "Eliminada la pelicula " + id; } // Como la peli no tiene nombre, lo tiene el programa, habia q hacer un metodo de buscar

    @GetMapping
    public List<Film> getFilms() { return filmService.getFilms(); }

    @GetMapping("/{id}")
    public String getFilm(@PathVariable("id") long id) {
        return "Devuelvo la pelicula con id " + id;
    }

}
