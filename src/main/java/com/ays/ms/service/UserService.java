package com.ays.ms.service;

import com.ays.ms.controller.dto.request.UserLoginRequest;
import com.ays.ms.controller.dto.request.UserRegisterRequest;
import com.ays.ms.model.Film;
import com.ays.ms.model.Serie;
import com.ays.ms.model.User;
import com.ays.ms.respository.UserRepository;
import com.ays.ms.respository.springdata.FilmSpringDataRepository;
import com.ays.ms.service.utils.MathUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private FilmSpringDataRepository filmSpringDataRepository;

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public void save(UserRegisterRequest userRegisterRequest) {
        User user = modelMapper.map(userRegisterRequest, User.class);

        user.setRecommendedSeries(this.getRecommendedSeriesDefault());
        user.setRecommendedFilms(this.getRecommendedFilmsDefault());
        this.userRepository.save(user);
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

    public List<Serie> getRecommendedSeriesDefault() {

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
            if(!seriesIds.contains(numSerie)) {
                // Se obtiene esa serie con el id y se añade a la lista
                Serie serie = serieService.getSerie(numSerie);
                recommendedSeries.add(serie);
                seriesIds.add(serie.getId());
            } else {
                numberRecomendedSeries++;
            }
        }

        return recommendedSeries;
    }

    public List<Film> getRecommendedFilmsDefault() {
        List<Film> recommendedFilms = new ArrayList<>();

        Long numberFilms = this.filmService.getNumberFilms();
        Long controlNumberRecommendedFilms = 10L;
        Long numberRecomendedFilms = (numberFilms > controlNumberRecommendedFilms) ?
                controlNumberRecommendedFilms :
                numberFilms;

        List<Long> filmsIds = new ArrayList<>();

        for (; numberRecomendedFilms > 0; numberRecomendedFilms--) {

            long numFilm = MathUtils.randomNumber(1, numberFilms.intValue());

            if(!filmsIds.contains(numFilm)) {
                Film film = filmService.getFilm(numFilm);
                recommendedFilms.add(film);
                filmsIds.add(film.getId());
            } else {
                numberRecomendedFilms++;
            }
        }

        return recommendedFilms;
    }

}
