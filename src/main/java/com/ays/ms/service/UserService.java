package com.ays.ms.service;

import com.ays.ms.controller.dto.request.UserConfigurationRequest;
import com.ays.ms.controller.dto.request.UserLoginRequest;
import com.ays.ms.controller.dto.request.UserRegisterRequest;
import com.ays.ms.exceptions.AuthenticationAYSException;
import com.ays.ms.model.Film;
import com.ays.ms.model.Serie;
import com.ays.ms.model.User;
import com.ays.ms.respository.UserRepository;
import com.ays.ms.service.utils.MathUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SerieService serieService;
    @Autowired
    private FilmService filmService;
    @Autowired
    private CardService cardService;

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUser(long id) {
        return this.userRepository.getById(id);
    }

    public void save(UserRegisterRequest userRegisterRequest) {
        User user = modelMapper.map(userRegisterRequest, User.class);
        user.setAdmin(Boolean.FALSE);

        user.setRecommendedSeries(this.getRecommendedSeriesDefault());
        user.setRecommendedFilms(this.getRecommendedFilmsDefault());
        user.setCard(cardService.createCard());
        userRepository.save(user);
        authenticationService.login(user);
    }

    public void configuration(UserConfigurationRequest userConfigurationRequest) {
        User user = this.getUser(this.authenticationService.getIdLoginUser());

        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(userConfigurationRequest.getDateBirth(), DATEFORMATTER);
        LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
        user.setDateBirth(ld);
        user.setName(userConfigurationRequest.getName());
        user.setLastName(userConfigurationRequest.getLastName());
        user.setSecondLastName(userConfigurationRequest.getSecondLastName());
        userRepository.save(user);
    }

    public Boolean login(UserLoginRequest userLoginRequest) {
        User user = this.userRepository.getByEmailAndPassword(userLoginRequest.getEmail(),
                userLoginRequest.getPassword());

        Boolean isLogged = (user != null);

        if(isLogged) {
            authenticationService.login(user);
        }

        return isLogged;
    }

    public List<Serie> getRecommendedUserSeries() {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        return user.getRecommendedSeries();
    }

    public List<Film> getRecommendedUserFilms() {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        return user.getRecommendedFilms();
    }

    private List<Serie> getRecommendedSeriesDefault() {

        List<Serie> recommendedSeries = new ArrayList<>();

        Long numberSeries = this.serieService.getNumberSeries();
        Long controlNumberRecommendedSeries = 10L;

        // Minimo hay 10 series, obtengo el num más pequeño de series para el bucle
        Long numberRecomendedSeries = (numberSeries > controlNumberRecommendedSeries) ?
                controlNumberRecommendedSeries :
                numberSeries;

        List<Long> seriesIds = new ArrayList<>();
        for(; numberRecomendedSeries > 0; numberRecomendedSeries--) {

            long numSerie = MathUtils.randomNumber(1, numberSeries.intValue());
            Serie serie = serieService.getSerie(numSerie);
            if (serie == null) {
                for (; serieService.getSerie(numSerie) == null ||
                        seriesIds.contains(numSerie); ) {
                    numSerie++;
                }

                // Se obtiene esa serie con el id y se añade a la lista
                serie = serieService.getSerie(numSerie);
                recommendedSeries.add(serie);
                seriesIds.add(serie.getId());

            }
            else {
                if (!seriesIds.contains(numSerie)) {
                    // Se obtiene esa serie con el id y se añade a la lista
                    serie = serieService.getSerie(numSerie);
                    recommendedSeries.add(serie);
                    seriesIds.add(serie.getId());
                } else {
                    numberRecomendedSeries++;
                }
            }
        }

        return recommendedSeries;
    }

    private List<Film> getRecommendedFilmsDefault() {
        List<Film> recommendedFilms = new ArrayList<>();

        Long numberFilms = this.filmService.getNumberFilms();
        Long controlNumberRecommendedFilms = 10L;
        Long numberRecomendedFilms = (numberFilms > controlNumberRecommendedFilms) ?
                controlNumberRecommendedFilms :
                numberFilms;

        List<Long> filmsIds = new ArrayList<>();

        for (; numberRecomendedFilms > 0; numberRecomendedFilms--) {

            long numFilm = MathUtils.randomNumber(1, numberFilms.intValue());
            Film film = filmService.getFilm(numFilm);
            if (film == null) {
                for (; filmService.getFilm(numFilm) == null ||
                        filmsIds.contains(numFilm); ) {
                    numFilm++;
                }

                film = filmService.getFilm(numFilm);
                recommendedFilms.add(film);
                filmsIds.add(film.getId());
            }

            else {
                if(!filmsIds.contains(numFilm)) {
                    film = filmService.getFilm(numFilm);
                    recommendedFilms.add(film);
                    filmsIds.add(film.getId());
                } else {
                    numberRecomendedFilms++;
                }
            }

        }

        return recommendedFilms;
    }

}
