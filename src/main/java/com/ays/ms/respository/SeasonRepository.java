package com.ays.ms.respository;

import com.ays.ms.model.Season;
import com.ays.ms.model.Serie;
import com.ays.ms.respository.springdata.SeasonSpringDataRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class SeasonRepository {

    @Autowired
    SeasonSpringDataRepository seasonSpringDataRepository;

    public Season getSeason(Long id) {
        Optional<Season> optionalSeason = this.seasonSpringDataRepository.findById(id);
        return optionalSeason.orElse(null);
    }

}
