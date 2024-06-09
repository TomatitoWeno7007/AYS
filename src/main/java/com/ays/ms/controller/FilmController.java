package com.ays.ms.controller;

import com.ays.ms.controller.dto.request.FilmRequest;
import com.ays.ms.controller.dto.response.FilmResponse;
import com.ays.ms.model.Film;
import com.ays.ms.model.User;
import com.ays.ms.service.FilmService;
import com.ays.ms.service.GenreService;
import com.ays.ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLConnection;
import java.util.List;

@Controller
@RequestMapping("film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private UserService userService;
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

        if (newFilm.getUrl().isEmpty() ||
            newFilm.getImg().isEmpty()) {
            if (newFilm.getUrl().isEmpty())
                result.rejectValue("url", "error.url", "El video es obligatorio");
            if (newFilm.getImg().isEmpty())
                result.rejectValue("img", "error.img", "La img es obligatoria");

        } else {
            if (!isImageFile(newFilm.getImg().getOriginalFilename())) {
                result.rejectValue("img", "error.img", "Tipo de img no valido");
            }
            if (!isVideoFile(newFilm.getUrl().getOriginalFilename())) {
                result.rejectValue("url", "error.url", "Tipo de video no valido");
            }
        }

        if(result.hasErrors()) {
            model.addAttribute("listFilms", filmService.getFilmFromView());
            model.addAttribute("listGenres", genreService.getGenres());
            return "admin/film";
        } else {
            filmService.addFilm(newFilm);
            return "redirect:/admin/v/film";
        }
    }

    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }
    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }

    @PostMapping("/edit-film")
    public String edit(@ModelAttribute("editFilm")  @Valid FilmRequest editFilm, BindingResult result,
                       Model model) {

        Film filmBeforeEdit = filmService.getFilm(editFilm.getId());

        if (!isImageFile(editFilm.getImg().getOriginalFilename())) {
            result.rejectValue("img", "error.img", "Tipo de img no valido");
        }
        if (!isVideoFile(editFilm.getUrl().getOriginalFilename())) {
            result.rejectValue("url", "error.url", "Tipo de video no valido");
        }

        if(result.hasErrors()) {
            model.addAttribute("listFilms", filmService.getFilmFromView());
            model.addAttribute("listGenres", genreService.getGenres());
            model.addAttribute("newFilm", new FilmRequest());
            model.addAttribute("idEditFilm", editFilm.getId());

            return "admin/film";
        }

        try {
            filmService.editFilm(editFilm);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/admin/v/film";
    }

    @Transactional
    @PostMapping("/delete")
    public String deleteFilm(Long id, Model model) {

        Film filmDelete = filmService.getFilm(id);
        List<User> listUsers = userService.getUsers();
        listUsers.forEach(user ->
                user.getLikedFilms().
                        removeIf(film -> film.equals(filmDelete))
        );

        listUsers.forEach(user ->
                user.getRecommendedFilms().
                        removeIf(film -> film.equals(filmDelete))
        );

        try {
            filmService.deleteFilm(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/v/film";
    }
}
