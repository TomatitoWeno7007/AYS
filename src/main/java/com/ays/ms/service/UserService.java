package com.ays.ms.service;

import com.ays.ms.controller.dto.request.*;
import com.ays.ms.exceptions.AuthenticationAYSException;
import com.ays.ms.model.*;
import com.ays.ms.respository.UserRepository;
import com.ays.ms.service.utils.MathUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
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

    public void delete() {
        int idUser = (int) authenticationService.getIdLoginUser();
        User user = this.getUser(idUser);
        userRepository.delete(user);
        authenticationService.logout();

    }

    public FieldError configuration(UserConfigurationRequest userConfigurationRequest) {
        User user = this.getUser(this.authenticationService.getIdLoginUser());

        FieldError error = null;

        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(userConfigurationRequest.getDateBirth(), DATEFORMATTER);
        LocalDate dateToday = LocalDate.parse(LocalDate.now().toString(), DATEFORMATTER);

        if (ld.isAfter(dateToday)) {
            error = new FieldError("userConfiguration", "dateBirth",
                    "La fecha de nacimiento no puede ser mayor a la fecha actual");
        } else {
            user.setDateBirth(ld);
            user.setName(userConfigurationRequest.getName());
            user.setLastName(userConfigurationRequest.getLastName());


            if (! userConfigurationRequest.getImg().isEmpty()) {
                MultipartFile imgFile = userConfigurationRequest.getImg();
                String imgUser = StringUtils.cleanPath(imgFile.getOriginalFilename());
                // Crea una carpeta con el nombre de la peli para tenerlo más ordenado
                Path path;
                if (user.getName() == null) {
                    path = Paths.get("static", "media", "user" , user.getEmail()).resolve(imgUser);
                } else {
                    path = Paths.get("static", "media", "user" , user.getName()).resolve(imgUser);
                }
                try {
                    // Verificar si el directorio existe, si no, lo crea
                    Files.createDirectories(path.getParent());
                    Files.write(path, imgFile.getBytes());
                    user.setImg(userConfigurationRequest.getImg().getOriginalFilename());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            userRepository.save(user);
        }

        return error;
    }

    @Transactional
    public void configurationCard(com.ays.ms.controller.dto.request.UserConfigurationCardRequest userConfigurationCardRequest) {
        User user = this.getUser(this.authenticationService.getIdLoginUser());

        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(userConfigurationCardRequest.getExpirationDate(), DATEFORMATTER);

        Card card = new Card();
        card.setId(user.getCard().getId());
        card.setCardNumber(userConfigurationCardRequest.getCardNumber());
        card.setCardUser(userConfigurationCardRequest.getCardUser());
        card.setCvv(userConfigurationCardRequest.getCvv());
        card.setExpirationDate(ld);
        user.setCard(card);
        userRepository.save(user);
    }

    public FieldError changePass(UserConfigurationPassRequest userConfigurationPassRequest) {

        FieldError errors = null;

        User user = this.getUser(this.authenticationService.getIdLoginUser());

        boolean isValid = ((userConfigurationPassRequest.getPass().equals(userConfigurationPassRequest.getRepeatPass()))
        && user.getPassword().equals(userConfigurationPassRequest.getOldPass()));

        if (Boolean.FALSE.equals(isValid)) {
            errors = new FieldError("userPass", "repeatPass",
                    "Las contraseñas no coinciden");
        } else {
            user.setPassword(userConfigurationPassRequest.getPass());
            userRepository.save(user);
        }
        return errors;
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

    public UserConfigurationCardRequest getUserFromView(long id) {
        User user = this.getUser(id);

        UserConfigurationCardRequest userConfigurationCardRequest = modelMapper.map(user.getCard(), UserConfigurationCardRequest.class);

        return userConfigurationCardRequest;
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

    public List<Film> getLikedUserFilms() {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        return user.getLikedFilms();
    }

    public List<Serie> getLikedUserSeries() {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        return user.getLikedSeries();
    }

    public List<Film> getWatchedUserFilms() {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        return user.getWatchFilms();
    }

    public List<Serie> getWatchedUserSeries() {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        return user.getWatchSeries();
    }


    public void setWatchedUserFilms(List<Film> watchedFilms) {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        user.setWatchFilms(watchedFilms);
        userRepository.save(user);
    }

    public void setWatchedUserSeries(List<Serie> watchedSeries) {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        user.setWatchSeries(watchedSeries);
        userRepository.save(user);
    }

    public List<UserFilmWatching> getWatchingUserFilms() {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        return user.getCurrentFilms();
    }

    public List<UserChapterWatching> getWatchingUserChapters() {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        return user.getCurrentChapters();
    }

    public void setWatchingUserFilms(List<UserFilmWatching> watchingFilms) {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        user.setCurrentFilms(watchingFilms);
        userRepository.save(user);
    }

    public void setWatchingUserChapters(List<UserChapterWatching> watchingChapters) {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        user.setCurrentChapters(watchingChapters);
        userRepository.save(user);
    }

    public void setLikedUserFilms(List<Film> likedFilms) {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        user.setLikedFilms(likedFilms);
        userRepository.save(user);
    }

    public void setLikedUserSeries(List<Serie> likedSeries) {
        long userId = authenticationService.getIdLoginUser();

        if (userId == 0) {
            throw new AuthenticationAYSException("Usuario no logueado");
        }

        User user = this.getUser(userId);
        user.setLikedSeries(likedSeries);
        userRepository.save(user);
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
