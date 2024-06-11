package com.ays.ms.service;

import com.ays.ms.model.Film;
import com.ays.ms.model.User;
import com.ays.ms.model.UserFilmWatching;
import com.ays.ms.model.UserFilmWatchingId;
import com.ays.ms.respository.UserFilmWatchingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFilmWatchingService {

    @Autowired
    private UserFilmWatchingRepository userFilmWatchingRepository;

    public UserFilmWatching getFilmWatching(User user, Film film) {
        UserFilmWatchingId filmWatchingId = new UserFilmWatchingId(user, film);
        return userFilmWatchingRepository.getFilmWatching(filmWatchingId);
    }

    public void deleteFilmWatching(UserFilmWatchingId id) { userFilmWatchingRepository.deleteFilmWatching(id); }

    public void saveFilmWatching(UserFilmWatching userFilmWatching) { userFilmWatchingRepository.saveFilmWatching(userFilmWatching);}

}
