package com.ays.ms.controller;

import com.ays.ms.controller.dto.request.FilmRequest;
import com.ays.ms.controller.dto.request.SerieRequest;
import com.ays.ms.controller.dto.response.SerieResponse;
import com.ays.ms.model.Genres;
import com.ays.ms.model.Serie;
import com.ays.ms.service.FilmService;
import com.ays.ms.service.GenreService;
import com.ays.ms.service.SerieService;
import com.ays.ms.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("admin")
public class AdministratorController {

    @Autowired
    SerieService serieService;
    @Autowired
    GenreService genreService;
    @Autowired
    FilmService filmService;
    @Autowired
    UserService userService;

    @GetMapping("/v/serie")
    public String getSerie(Model model) {
        model.addAttribute("listSeries", serieService.getSerieFromView());
        model.addAttribute("newSerie", new SerieRequest());
        model.addAttribute("listGenres", genreService.getGenres());
        return "admin/serie";
    }

    @GetMapping("/v/film")
    public String getFilm(Model model) {
        model.addAttribute("listFilms", filmService.getFilmFromView());
        model.addAttribute("newFilm", new FilmRequest());
        model.addAttribute("listGenres", genreService.getGenres());
        return "admin/film";
    }
    @GetMapping("/v/users")
    public String getUsers(Model model) {
        model.addAttribute("listUsers", userService.getUsers());
        return "admin/users";
    }

}
