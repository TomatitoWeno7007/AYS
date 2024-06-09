package com.ays.ms.service;

import com.ays.ms.exceptions.ResourceNotFoundException;
import com.ays.ms.model.Chapter;
import com.ays.ms.model.Season;
import com.ays.ms.model.Serie;
import com.ays.ms.respository.ChapterRepository;
import com.ays.ms.respository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SeasonService {

    @Autowired
    SeasonRepository seasonRepository;
    @Autowired
    SerieService serieService;

    @Autowired
    ChapterService chapterService;

    public Season getSeason(Long id) { return this.seasonRepository.getSeason(id); }

    public Season addSeason(Serie serie) {
        List<Season> listSeason =  serie.getSeasons();

        Long numberSeason = listSeason.size() == 0 ? 1 :
                listSeason.get(listSeason.size()-1).getNumber() + 1;

        Season season = new Season();
        season.setNumber(numberSeason);

        listSeason.add(season);
        serie.setSeasons(listSeason);

        return this.seasonRepository.addSeason(season);

    }

    @Transactional
    public void deleteSeason(Long idSerie, Long number) {

        Serie serie = serieService.getSerie(idSerie);
        Season season = serie.getSeasons().get((int) (number-1));

        if (season == null) {
            throw new ResourceNotFoundException("No existe la temporada a eliminar");
        }

        serie.getSeasons().remove((int) (number-1));
        for (int i = 0; i < serie.getSeasons().size(); i++) {
            serie.getSeasons().get(i).setNumber(i+1);
        }

        this.serieService.saveOrUpdate(serie);

    }

    @Transactional
    public void deleteChapter(Long number, Long idSeason) {
        Season season = this.getSeason(idSeason);
        Chapter chapter = season.getChapters().get((int) (number-1));

        if (chapter == null) {
            throw new ResourceNotFoundException("No existe el capítulo a eliminar");
        }

        season.getChapters().remove((int) (number-1));
        for (int i = 0; i < season.getChapters().size(); i++) {
            season.getChapters().get(i).setNumber(i+1);
        }
    }

    public Season getLastSeasonBySerie(Serie serie) {
        List<Season> listSeason = serie.getSeasons();
        Season season = listSeason.get(listSeason.size()-1);
        return season;
    }

}
