package com.ays.ms.controller;

import com.ays.ms.controller.dto.request.*;
import com.ays.ms.model.Film;
import com.ays.ms.model.Genres;
import com.ays.ms.model.Serie;
import com.ays.ms.service.*;
import com.ays.ms.service.utils.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private SerieService serieService;
    @Autowired
    private FilmService filmService;
    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/v/principal/content")
    public String getPrincipalContentView(Model model) {

        List<Serie> recommendedSerie = userService.getRecommendedUserSeries();
        List<Film> recommendedFilm = userService.getRecommendedUserFilms();

        model.addAttribute("recommendedSeries", recommendedSerie);
        model.addAttribute("recommendedFilms", recommendedFilm);

        return "user/principal-content";
    }

    @GetMapping("/v/configuration")
    public String getConfigurationView(Model model) {
        if (!model.containsAttribute("userInfo")) {
            long idUser = authenticationService.getIdLoginUser();
            model.addAttribute("userInfo", userService.getUser(idUser));
            model.addAttribute("userConfigurationCard", userService.getUserFromView(idUser));
            model.addAttribute("userConfiguration", new UserConfigurationRequest());
            model.addAttribute("userPass", new UserConfigurationPassRequest());
        }


        return "user/configuration";
    }

    @PostMapping
    public String register(@ModelAttribute("userRegister") @Valid UserRegisterRequest userRegister,
            BindingResult result, Model model) {

        model.addAttribute("userLogin", new UserLoginRequest());

        if(result.hasErrors()) {
            return "user/not-loggin";
        }

        userService.save(userRegister);

        return "redirect:/user/v/principal/content";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("userLogin") @Valid UserLoginRequest userLoginRequest,
                        BindingResult result, Model model) {

        model.addAttribute("userRegister", new UserRegisterRequest());

        if(result.hasErrors()) {
            return "user/not-loggin";
        }

        Boolean isLogged = this.userService.login(userLoginRequest);

        if (Boolean.FALSE.equals(isLogged)) {
            ObjectError objectError = new ObjectError("globalError",
                    "Usuario o contraseña incorrecto");
            result.addError(objectError);
            return "user/not-loggin";
        }

        if (authenticationService.isAdmin()) {
            return "redirect:/admin/v/principal/content";
        }

        return "redirect:/user/v/principal/content";
    }

    @PostMapping("/configuration")
    public String saveConfiguration(@ModelAttribute("userConfiguration") @Valid UserConfigurationRequest userConfigurationRequest,
                           BindingResult result, Model model) {

        userService.configuration(userConfigurationRequest);

        return "redirect:/user/v/configuration";
    }

    @PostMapping("/configuration/card")
    public String saveConfigurationCard(@ModelAttribute("userConfigurationCard") @Valid UserConfigurationCardRequest userConfigurationCardRequest,
                                    BindingResult result, Model model) {

        if(result.hasErrors()) {
            long idUser = authenticationService.getIdLoginUser();
            model.addAttribute("userInfo", userService.getUser(idUser));
            model.addAttribute("userConfigurationCard", userConfigurationCardRequest);
            model.addAttribute("userConfiguration", new UserConfigurationRequest());
            model.addAttribute("userPass", new UserConfigurationPassRequest());
            return "user/configuration";
        }

        userService.configurationCard(userConfigurationCardRequest);

        return "redirect:/user/v/configuration";
    }

    @PostMapping("/changePass")
    public String saveCard(@ModelAttribute("userPass") @Valid UserConfigurationPassRequest userConfigurationPassRequest,
                           BindingResult result, Model model) {


        FieldError errors = userService.changePass(userConfigurationPassRequest);

        if (errors != null) {
            long idUser = authenticationService.getIdLoginUser();
            model.addAttribute("userInfo", userService.getUser(idUser));
            model.addAttribute("userConfigurationCard", userService.getUserFromView(idUser));
            model.addAttribute("userConfiguration", new UserConfigurationRequest());
            model.addAttribute("userPass", userConfigurationPassRequest);
            result.addError(errors);
            return "user/configuration";
        }

        return "redirect:/user/v/configuration";
    }

    @GetMapping("/logout")
    public String logout() {
        authenticationService.logout();
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteUser() {
        userService.delete();
        return "redirect:/";
    }

    @GetMapping("/v/serie/{idSerie}/description")
    public String getSerieDescription(@PathVariable("idSerie") long idSerie,
                                      Model model) {

        Serie serie = serieService.getSerie(idSerie);
        model.addAttribute("serie", serie);
        model.addAttribute("seasonChoose", serie.getSeasons().get(0));
        model.addAttribute("listChapters", serie.getSeasons().get(0).getChapters());
        return "user/serie-description";

    }

    @GetMapping("/v/serie/{idSerie}/description/{seasonNumber}")
    public String getSerieSeasonChoose(@PathVariable("idSerie") long idSerie,
                                       @PathVariable("seasonNumber") long seasonNumber,
                                      Model model) {

        Serie serie = serieService.getSerie(idSerie);
        model.addAttribute("serie", serie);
        model.addAttribute("seasonChoose", serie.getSeasons().get((int) (seasonNumber-1)));
        model.addAttribute("listChapters", serie.getSeasons().get((int) (seasonNumber-1)).getChapters());
//        return "user/serie-description";
        return "user/serie-description :: .serieBox";

    }

    @GetMapping("/v/film/{idFilm}/description")
    public String getFilmDescription(@PathVariable("idFilm") long idFilm,
                                      Model model) {

        Film filmFind = filmService.getFilm(idFilm);
        List<Film> allFilms = filmService.getFilms();

        List<Film> filmsByGenre = allFilms.stream()
                .filter(film -> film.getGenres().stream().
                        anyMatch(genre -> filmFind.getGenres().contains(genre)))
                .collect(Collectors.toList());


        // Elimina la peli actual y ordena la lista al azar
        filmsByGenre.remove(filmFind);
        Collections.shuffle(filmsByGenre);

        if (filmsByGenre.size() > 5) {
            filmsByGenre.subList(5, filmsByGenre.size()).clear();
        }

        model.addAttribute("film", filmFind);
        model.addAttribute("recommendedFilm", filmsByGenre);
        return "user/film-description";

    }

    @GetMapping("/v/films")
    public String getAllFilms(Model model) {

        List<Film> allFilms = filmService.getFilms();

        List<Genres> listGenres = genreService.getGenres();

        List<Genres> clearGenres = listGenres.stream()
                        .filter(genre ->
                                allFilms.stream().anyMatch(film -> film.getGenres().contains(genre)))
                .collect(Collectors.toList());

        model.addAttribute("listGenres", clearGenres);
        model.addAttribute("listFilms", allFilms);
        return "user/film";

    }

    @GetMapping("/v/series")
    public String getAllSeries(Model model) {

        List<Serie> allSeries = serieService.getSeries();

        List<Genres> listGenres = genreService.getGenres();

        List<Genres> clearGenres = listGenres.stream()
                        .filter(genre ->
                                allSeries.stream().anyMatch(film -> film.getGenres().contains(genre)))
                .collect(Collectors.toList());

        model.addAttribute("listGenres", clearGenres);
        model.addAttribute("listSeries", allSeries);
        return "user/serie";

    }

}
