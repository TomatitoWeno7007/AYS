package com.ays.ms.service;

import com.ays.ms.model.*;
import com.ays.ms.respository.UserChapterWatchingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserChapterWatchingService {

    @Autowired
    private UserChapterWatchingRepository userChapterWatchingRepository;

    public UserChapterWatching getChapterWatching(User user, Chapter chapter) {
        UserChapterWatchingId chapterWatchingId = new UserChapterWatchingId(user, chapter);
        return userChapterWatchingRepository.getChapterWatching(chapterWatchingId);
    }

    public void deleteChapterWatching(UserChapterWatchingId id) { userChapterWatchingRepository.deleteChapterWatching(id); }

    public void saveChapterWatching(UserChapterWatching userChapterWatching) { userChapterWatchingRepository.saveChapterWatching(userChapterWatching);}

}
