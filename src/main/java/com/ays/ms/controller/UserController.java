package com.ays.ms.controller;

import com.ays.ms.controller.dto.request.*;
import com.ays.ms.model.*;
import com.ays.ms.service.*;
import com.ays.ms.service.utils.MathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private ChapterService chapterService;
    @Autowired
    private FilmService filmService;
    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserFilmWatchingService userFilmWatchingService;
    @Autowired
    private UserChapterWatchingService userChapterWatchingService;

    @GetMapping("/v/principal/content")
    public String getPrincipalContentView(Model model) {

        List<Serie> recommendedSerie = userService.getRecommendedUserSeries();
        List<Film> recommendedFilm = userService.getRecommendedUserFilms();

        List<Film> likedFilms = userService.getLikedUserFilms();
        List<Serie> likedSeries = userService.getLikedUserSeries();

        List<UserFilmWatching> watchingFilms = userService.getWatchingUserFilms();
        List<UserChapterWatching> watchingChapters = userService.getWatchingUserChapters();

        List<Film> watchedFilms = userService.getWatchedUserFilms();
        List<Serie> watchedSeries = userService.getWatchedUserSeries();

        model.addAttribute("likedFilms", (likedFilms.isEmpty()) ? null : likedFilms );
        model.addAttribute("likedSeries", (likedSeries.isEmpty()) ? null : likedSeries );
        model.addAttribute("recommendedSeries", recommendedSerie);
        model.addAttribute("recommendedFilms", recommendedFilm);
        model.addAttribute("watchingFilms", (watchingFilms.isEmpty()) ? null : watchingFilms );
        model.addAttribute("watchingChapters", (watchingChapters.isEmpty()) ? null : watchingChapters );
        model.addAttribute("watchedFilms", (watchedFilms.isEmpty()) ? null : watchedFilms );
        model.addAttribute("watchedSeries", (watchedSeries.isEmpty()) ? null : watchedSeries );
        long idUser = authenticationService.getIdLoginUser();
        model.addAttribute("userInfo", userService.getUser(idUser));


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

    @PostMapping("/configuration")
    public String saveConfiguration(@ModelAttribute("userConfiguration") @Valid UserConfigurationRequest userConfigurationRequest,
                                    BindingResult result, Model model) {

        FieldError errors = userService.configuration(userConfigurationRequest);

        if (errors != null) {
            long idUser = authenticationService.getIdLoginUser();
            model.addAttribute("userInfo", userService.getUser(idUser));
            model.addAttribute("userConfigurationCard", userService.getUserFromView(idUser));
            model.addAttribute("userConfiguration", new UserConfigurationRequest());
            model.addAttribute("userPass", new UserConfigurationPassRequest());
            result.addError(errors);
            return "user/configuration";
        }
        return "redirect:/user/v/configuration";
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
            return "redirect:/admin/v/serie";
        }

        return "redirect:/user/v/principal/content";
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
        long idUser = authenticationService.getIdLoginUser();
        model.addAttribute("userInfo", userService.getUser(idUser));
        model.addAttribute("userConfigurationCard", userService.getUserFromView(idUser));
        model.addAttribute("userConfiguration", new UserConfigurationRequest());
        model.addAttribute("userPass", userConfigurationPassRequest);
        model.addAttribute("PassChange", null);

        if (errors != null) {
            result.addError(errors);
            return "user/configuration";
        }

        model.addAttribute("PassChange", true);
        return "user/configuration";
    }

    @GetMapping("/v/film/{idFilm}/player")
    public String getFilmPlayer(@PathVariable("idFilm") long idFilm,
                                   Model model) {
        long idUser = authenticationService.getIdLoginUser();
        model.addAttribute("userInfo", userService.getUser(idUser));


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

        // Para recoger el tiempo en caso de que lo tenga
        UserFilmWatching filmWatching = userFilmWatchingService.getFilmWatching(userService.getUser(idUser), filmFind);

        model.addAttribute("film", filmFind);
        model.addAttribute("recommendedFilm", filmsByGenre);
        model.addAttribute("filmActualTime", filmWatching == null ? null : filmWatching.getActualTime());
        return "user/playerFilm";

    }


    @PostMapping("/v/serie/{idChapter}/{actualDuration}")
    public ResponseEntity<String> getDurationChapter(@PathVariable("idChapter") long idChapter,
                                                  @PathVariable("actualDuration") int actualDuration,
                                                  Model model) {

        try {
            UserChapterWatching chapterWatching = new UserChapterWatching();
            long idUser = authenticationService.getIdLoginUser();
            User user = userService.getUser(idUser);
            Chapter chapterFind = chapterService.getChapterById(idChapter);

            chapterWatching.setId(new UserChapterWatchingId(user, chapterFind));
            chapterWatching.setActualTime(String.valueOf(actualDuration));
            userChapterWatchingService.saveChapterWatching(chapterWatching);

            List <UserChapterWatching> listChaptersWatching = userService.getWatchingUserChapters();

            listChaptersWatching.add(chapterWatching);
            userService.setWatchingUserChapters(listChaptersWatching);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("Error updating duration: " + e.getMessage());
            return ResponseEntity.status(404).body("Error updating duration: " + e.getMessage());
        }
    }

    @PostMapping("/v/film/{idFilm}/{actualDuration}")
    public ResponseEntity<String> getDurationFilm(@PathVariable("idFilm") long idFilm,
                                                  @PathVariable("actualDuration") int actualDuration,
                                                  Model model) {

        try {
            UserFilmWatching filmWatching = new UserFilmWatching();
            long idUser = authenticationService.getIdLoginUser();
            User user = userService.getUser(idUser);
            Film filmFind = filmService.getFilm(idFilm);

            filmWatching.setId(new UserFilmWatchingId(user, filmFind));
            filmWatching.setActualTime(String.valueOf(actualDuration));
            userFilmWatchingService.saveFilmWatching(filmWatching);

            List <UserFilmWatching> listFilmsWatching = userService.getWatchingUserFilms();

            listFilmsWatching.add(filmWatching);
            userService.setWatchingUserFilms(listFilmsWatching);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("Error updating duration: " + e.getMessage());
            return ResponseEntity.status(404).body("Error updating duration: " + e.getMessage());
        }
    }

    @PostMapping("/v/film/{idFilm}/watchedFilm")
    public ResponseEntity<String> getDurationFilm(@PathVariable("idFilm") long idFilm,
                                                  Model model) {

        try {
            long idUser = authenticationService.getIdLoginUser();
            User user = userService.getUser(idUser);
            Film filmFind = filmService.getFilm(idFilm);

            UserFilmWatchingId idFilmWatchDelete = new UserFilmWatchingId(user, filmFind);
            UserFilmWatching userFilmWatching = userFilmWatchingService.getFilmWatching(user, filmFind);

            List <UserFilmWatching> listFilmsWatching = userService.getWatchingUserFilms();
            listFilmsWatching.remove(userFilmWatching);
            userFilmWatchingService.deleteFilmWatching(idFilmWatchDelete);
            userService.setWatchingUserFilms(listFilmsWatching);

            List <Film> listUserWatchedFilms  = userService.getWatchedUserFilms();
            listUserWatchedFilms.add(filmFind);
            userService.setWatchedUserFilms(listUserWatchedFilms);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("Error updating duration: " + e.getMessage());
            return ResponseEntity.status(404).body("Error updating duration: " + e.getMessage());
        }
    }

    @GetMapping("/v/serie/{idSerie}/{seasonNumber}/player")
    public String getSeasonsPlayer(@PathVariable("idSerie") long idSerie,
                                   @PathVariable("seasonNumber") long seasonNumber,
                                   Model model) {
        long idUser = authenticationService.getIdLoginUser();
        model.addAttribute("userInfo", userService.getUser(idUser));

        Serie serie = serieService.getSerie(idSerie);

        // Para recoger el tiempo en caso de que lo tenga
        UserChapterWatching chapterWatching = userChapterWatchingService.getChapterWatching(userService.getUser(idUser),
                serie.getSeasons().get((int) (seasonNumber-1)).getChapters().get(0));

        model.addAttribute("serie", serie);
        model.addAttribute("seasonChoose", serie.getSeasons().get((int) (seasonNumber-1)));
        model.addAttribute("listChapters", serie.getSeasons().get((int) (seasonNumber-1)).getChapters());
        model.addAttribute("chapterActualTime", chapterWatching == null ? null : chapterWatching.getActualTime());
        return "user/playerSerie :: .serieBox";
    }

    @GetMapping("/v/serie/{idSerie}/{numberSeason}/{idChapter}/watchedChapter")
    public ResponseEntity<String> getWatchedSerie(@PathVariable("idSerie") long idSerie,
                                                  @PathVariable("numberSeason") int numberSeason,
                                                  @PathVariable("idChapter") long idChapter,
                                                  Model model) {

        try {
            long idUser = authenticationService.getIdLoginUser();
            User user = userService.getUser(idUser);
            Chapter chapterFind = chapterService.getChapterById(idChapter);
            Serie serieFind = serieService.getSerie(idSerie);
            Season seasonFind = serieFind.getSeasons().get(numberSeason-1);

            UserChapterWatchingId idChapterWatchDelete = new UserChapterWatchingId(user, chapterFind);
            UserChapterWatching userChapterWatching = userChapterWatchingService.getChapterWatching(user, chapterFind);

            List <UserChapterWatching> listChapterWatching = userService.getWatchingUserChapters();
            listChapterWatching.remove(userChapterWatching);
            userChapterWatchingService.deleteChapterWatching(idChapterWatchDelete);
            userService.setWatchingUserChapters(listChapterWatching);

            // Comprueba si acabó la serie
            if (seasonFind == serieFind.getSeasons().get(serieFind.getSeasons().size()-1)) {
                List <Serie> listUserWatchedSeries = userService.getWatchedUserSeries();
                listUserWatchedSeries.add(serieFind);
                userService.setWatchedUserSeries(listUserWatchedSeries);
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("Error updating duration: " + e.getMessage());
            return ResponseEntity.status(404).body("Error updating duration: " + e.getMessage());
        }
    }






    @GetMapping("/v/serie/{idSerie}/{seasonNumber}/{chapterNumber}/player")
    public String getSeriePlayer(@PathVariable("idSerie") long idSerie,
                                 @PathVariable("seasonNumber") long seasonNumber,
                                 @PathVariable("chapterNumber") int chapterNumber,
                                 Model model) {
        long idUser = authenticationService.getIdLoginUser();
        model.addAttribute("userInfo", userService.getUser(idUser));

        boolean isNext = false, isBefore = false;

        Serie serie = serieService.getSerie(idSerie);

        seasonNumber--;
        var seasonChoose = serie.getSeasons().get((int) (seasonNumber));
        Chapter chapter = null;

        // BtnAnterior en Season>1 y cap = 1 para ir a la anterior season
        if (chapterNumber == 0) {
            seasonNumber--;
            seasonChoose = serie.getSeasons().get((int) (seasonNumber));
            chapter = seasonChoose.getChapters().get(seasonChoose.getChapters().size()-1);
        } else if (chapterNumber > seasonChoose.getChapters().size()) {
            //BtnSiguiente si tiene más seasons
            seasonNumber++;
            seasonChoose = serie.getSeasons().get((int) (seasonNumber));
            chapter = seasonChoose.getChapters().get(0);
        } else {
            seasonChoose = serie.getSeasons().get((int) (seasonNumber));
            chapter = seasonChoose.getChapters().get(chapterNumber - 1);
        }

        model.addAttribute("serie", serie);
        model.addAttribute("chapter", chapter);
        model.addAttribute("seasonChoose", seasonChoose);
        model.addAttribute("listChapters", seasonChoose.getChapters());
        model.addAttribute("chapterNumber", chapter.getNumber());

        //BtnAnterior visible para anterior season/cap
        if (seasonNumber != 0) {
            isBefore = true;
        } else if (chapter.getNumber() != 1) {
            isBefore = true;
        }

        // Es size -2, por el cambio de valor de seasonNumber
        if (seasonNumber != serie.getSeasons().size()-1) {
            isNext = true;
        } else if (chapter.getNumber() != seasonChoose.getChapters().size()) {
            isNext = true;
        }


        // Para recoger el tiempo en caso de que lo tenga
        UserChapterWatching chapterWatching = userChapterWatchingService.getChapterWatching(userService.getUser(idUser),
                chapter);

        model.addAttribute("chapterActualTime", chapterWatching == null ? null : chapterWatching.getActualTime());
        model.addAttribute("isNext", isNext);
        model.addAttribute("isBefore", isBefore);
        List<UserChapterWatching> listWatchingChapters = userService.getWatchingUserChapters();
        model.addAttribute("listWatchingChapters", listWatchingChapters);


        return "user/playerSerie";
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
        long idUser = authenticationService.getIdLoginUser();
        model.addAttribute("userInfo", userService.getUser(idUser));

        Serie serie = serieService.getSerie(idSerie);
        boolean isLiked = false;

        // Busca si la serie está en favoritos
        List <Serie> listSeriesLiked = userService.getLikedUserSeries();
        if (listSeriesLiked.contains(serie)) {
            isLiked = true;
        }

        List<UserChapterWatching> listWatchingChapters = userService.getWatchingUserChapters();

        model.addAttribute("serie", serie);
        model.addAttribute("isLiked", isLiked);
        model.addAttribute("seasonChoose", serie.getSeasons().get(0));
        model.addAttribute("listChapters", serie.getSeasons().get(0).getChapters());
        model.addAttribute("listWatchingChapters", listWatchingChapters);
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

        List<UserChapterWatching> listWatchingChapters = userService.getWatchingUserChapters();
        model.addAttribute("listWatchingChapters", listWatchingChapters);
//        return "user/serie-description";
        return "user/serie-description :: .serieBox";

    }


    @GetMapping("/v/film/{idFilm}/description")
    public String getFilmDescription(@PathVariable("idFilm") long idFilm,
                                      Model model) {
        long idUser = authenticationService.getIdLoginUser();
        model.addAttribute("userInfo", userService.getUser(idUser));

        Film filmFind = filmService.getFilm(idFilm);
        List<Film> allFilms = filmService.getFilms();
        boolean isLiked = false;

        List<Film> filmsByGenre = allFilms.stream()
                .filter(film -> film.getGenres().stream().
                        anyMatch(genre -> filmFind.getGenres().contains(genre)))
                .collect(Collectors.toList());

        // Elimina la peli actual y ordena la lista al azar
        filmsByGenre.remove(filmFind);
        Collections.shuffle(filmsByGenre);

        // Busca si la peli está en favoritos
        List <Film> listFilmsLiked = userService.getLikedUserFilms();
        if (listFilmsLiked.contains(filmFind)) {
            isLiked = true;
        }

        if (filmsByGenre.size() > 5) {
            filmsByGenre.subList(5, filmsByGenre.size()).clear();
        }

        model.addAttribute("film", filmFind);
        model.addAttribute("isLiked", isLiked);
        model.addAttribute("recommendedFilm", filmsByGenre);
        return "user/film-description";

    }

    @GetMapping("/v/film/{idFilm}/description/{isLiked}")
    public String getLikedFilm(@PathVariable("idFilm") long idFilm,
                               @PathVariable("isLiked") boolean isLiked,
                               Model model) {

        Film filmFind = filmService.getFilm(idFilm);
        List <Film> listFilmsLiked = userService.getLikedUserFilms();


        if (isLiked) {
            listFilmsLiked.add(filmFind);
        } else {
            listFilmsLiked.remove(filmFind);
        }
        userService.setLikedUserFilms(listFilmsLiked);

        model.addAttribute("film", filmFind);
        model.addAttribute("isLiked", isLiked);

        return "user/film-description :: .descriptionBox";
    }

    @GetMapping("/v/serie/{idSerie}/description/showLike/{isLiked}")
    public String getLikedSerie(@PathVariable("idSerie") long idSerie,
                               @PathVariable("isLiked") boolean isLiked,
                               Model model) {

        Serie serieFind = serieService.getSerie(idSerie);
        List <Serie> listSeriesLiked = userService.getLikedUserSeries();

        if (isLiked) {
            listSeriesLiked.add(serieFind);
        } else {
            listSeriesLiked.remove(serieFind);
        }
        userService.setLikedUserSeries(listSeriesLiked);

        model.addAttribute("serie", serieFind);
        model.addAttribute("isLiked", isLiked);

        return "user/serie-description :: .descriptionBox";
    }

    @GetMapping("/v/films")
    public String getAllFilms(Model model) {
        long idUser = authenticationService.getIdLoginUser();
        model.addAttribute("userInfo", userService.getUser(idUser));

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
        long idUser = authenticationService.getIdLoginUser();
        model.addAttribute("userInfo", userService.getUser(idUser));

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
