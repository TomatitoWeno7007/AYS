package com.ays.ms.controller;

import com.ays.ms.controller.dto.request.FilmRequest;
import com.ays.ms.model.Film;
import com.ays.ms.service.FilmService;
import com.ays.ms.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("film")
public class FilmController {

    @Autowired
    private FilmService filmService;
    @Autowired
    private GenreService genreService;

    @PostMapping
    public void saveFilm() { }

    @GetMapping
    public List<Film> getFilms() { return filmService.getFilms(); }

    @GetMapping("/{id}")
    public String getFilm(@PathVariable("id") long id) {
        return "Devuelvo la pelicula con id " + id;
    }

    @PostMapping("/add-film")
    public String add(@ModelAttribute("newFilm") @Valid FilmRequest newFilm, BindingResult result,
                      Model model) {

        if(result.hasErrors()) {
            model.addAttribute("listFilms", filmService.getFilmFromView());
            model.addAttribute("listGenres", genreService.getGenres());
            return "admin/film";
        }
        filmService.addFilm(newFilm);
        return "redirect:/admin/v/film";
    }

    @PostMapping("/edit-film")
    public String edit(@ModelAttribute("editFilm")  @Valid FilmRequest editFilm, BindingResult result,
                       Model model) {

        if(result.hasErrors()) {
            model.addAttribute("listFilms", filmService.getFilmFromView());
            model.addAttribute("listGenres", genreService.getGenres());
            return "admin/film";
        }

        try {
            filmService.editFilm(editFilm);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/admin/v/film";
    }

    @PostMapping("/delete")
    public String deleteFilm(Long id, Model model) {
        try {
            filmService.deleteFilm(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/v/film";
    }
}
