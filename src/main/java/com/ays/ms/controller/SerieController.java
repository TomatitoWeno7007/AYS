package com.ays.ms.controller;

import com.ays.ms.controller.dto.request.ChapterRequest;
import com.ays.ms.controller.dto.request.SerieRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ays.ms.model.Chapter;
import com.ays.ms.model.Season;
import com.ays.ms.model.Serie;
import com.ays.ms.service.ChapterService;
import com.ays.ms.service.GenreService;
import com.ays.ms.service.SeasonService;
import com.ays.ms.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("serie")
public class SerieController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private SerieService serieService;
    @Autowired
    private SeasonService seasonService;
    @Autowired
    private ChapterService chapterService;

    @PostMapping
    public void save() {}

    @GetMapping
    public List<Serie> getSeries() { return serieService.getSeries(); }

    @GetMapping("/{id}")
    public String getSeries(@PathVariable("id") long id) { return "Id de la Serie: " + id; }

    @PostMapping("/add-serie")
    public String add(@ModelAttribute("newSerie") @Valid SerieRequest newSerie, BindingResult result,
                      Model model) {

        if(result.hasErrors()) {
            model.addAttribute("listSeries", serieService.getSerieFromView());
            model.addAttribute("listGenres", genreService.getGenres());
            return "admin/serie";
        }


        Serie serie = serieService.saveOrUpdate(newSerie);
        Long idSeason = serie.getSeasons().get(0).getId();
        Season season = seasonService.getSeason(idSeason);


        model.addAttribute("seasonSelected", 1);
        model.addAttribute("seasonChoose", season.getId());

        return "redirect:/serie/" + serie.getId() + "/season/" + idSeason + "/chapters";
//        return "redirect:/serie/" + serie.getId() + "/seasons";
    }

    @PostMapping("/edit-serie")
    public String edit(@ModelAttribute("editSerie")  @Valid SerieRequest editSerie, BindingResult result,
                      Model model) {

        if(result.hasErrors()) {
            model.addAttribute("listSeries", serieService.getSerieFromView());
            model.addAttribute("listGenres", genreService.getGenres());
            return "admin/serie";
        }

        serieService.editSerie(editSerie);

        return "redirect:/admin/v/serie";
    }

    @PostMapping("/delete")
    public String deleteSerie(Long id, Model model) {
        serieService.deleteSerie(id);
        return "redirect:/admin/v/serie";
    }

    @PostMapping("/season/delete")
    public String deleteSeason(Long idSerie, Long number, Model model) {
        seasonService.deleteSeason(idSerie, number);
        return "redirect:/serie/" + idSerie + "/seasons";
    }

    @GetMapping("/{id}/seasons")
    public String getSeason(@PathVariable("id") long id, Model model) {
        Serie serie = serieService.getSerie(id);
//        session.setAttribute("serie", serie);
        model.addAttribute("serie", serie);
        model.addAttribute("listSeason", serie.getSeasons());
        model.addAttribute("newChapter", new ChapterRequest());
        return "admin/season";

    }

    @PostMapping("/{id}/season/")
    public String addSeason(@PathVariable("id") long id, Model model) {

        Serie serie = serieService.getSerie(id);
        Season season = seasonService.addSeason(serie);

        model.addAttribute("seasonSelected", season.getNumber());
        model.addAttribute("seasonChoose", season.getId());

        return "redirect:/serie/" + serie.getId() + "/season/" + season.getId() + "/chapters";
    }

    @GetMapping("/{id}/season/{idSeason}/chapters")
    public String getChapters(@PathVariable("id") long id,
                              @PathVariable("idSeason") long idSeason,
                              Model model) {
        Serie serie = serieService.getSerie(id);
        model.addAttribute("serie", serie);
        model.addAttribute("listSeason", serie.getSeasons());

        if (idSeason != -1) {
            Season season = seasonService.getSeason(idSeason);
            Long numberEpisode = chapterService.getLastEpisodeBySeason(season);
            model.addAttribute("seasonChoose", season.getId());
            model.addAttribute("listChapters", serieService.getChaptersFromView(season));
            model.addAttribute("seasonSelected", season.getNumber());
            model.addAttribute("lastChapter", numberEpisode);
            model.addAttribute("newChapter", new ChapterRequest());
        } else {
            model.addAttribute("seasonChoose", null);
            model.addAttribute("listChapters", null);
            model.addAttribute("seasonSelected", 0);
            model.addAttribute("newChapter", new ChapterRequest());
        }


        return "admin/season";
    }

    @PostMapping("/{id}/season/{idSeason}/edit-chapter")
    public String editChapter(@Valid ChapterRequest editChapter,
                              @PathVariable("id") long id,
                              @PathVariable("idSeason") long idSeason,
                              Model model, BindingResult result) {

        if(result.hasErrors()) {
            return "admin/serie";
        }

        chapterService.editChapter(editChapter);

        Serie serie = serieService.getSerie(id);
        Season season = seasonService.getSeason(idSeason);

        return "redirect:/serie/" + serie.getId() + "/season/" + season.getId() + "/chapters";

    }

    @PostMapping("/{id}/season/{idSeason}/add-chapter")
    public String addChapter(@ModelAttribute("newChapter") @Valid ChapterRequest newChapter,
                             BindingResult result, Model model,
                             @PathVariable("id") long id,
                             @PathVariable("idSeason") long idSeason) {

        if(result.hasErrors()) {
            return "admin/serie";
        }

        Serie serie = serieService.getSerie(id);
        Season season = seasonService.getSeason(idSeason);

        Chapter chapter = chapterService.addChapter(newChapter);
        List<Chapter> listChapters = season.getChapters();
        listChapters.add(chapter);
//        season.setChapters(listChapters);
//        List<Season> listSeasons = serie.getSeasons();
//        listSeasons.add((int) season.getNumber()-1, season);
//        serie.setSeasons(listSeasons);
        serieService.saveOrUpdate(serie);

        return "redirect:/serie/" + serie.getId() + "/season/" + season.getId() + "/chapters";

    }
    @PostMapping("/{id}/season/{idSeason}/delete")
    public String deleteChapter(@PathVariable("id") long id,
                                @PathVariable("idSeason") long idSeason,
                                @ModelAttribute("idChapter") long idChapter,
                                BindingResult result, Model model) {

        if(result.hasErrors()) {
            return "admin/serie";
        }

        Chapter chapter = chapterService.getChapterById(idChapter);
        seasonService.deleteChapter(chapter.getNumber(), idSeason);
        chapterService.deleteChapter(chapter);

        Serie serie = serieService.getSerie(id);
        Season season = seasonService.getSeason(idSeason);
        return "redirect:/serie/" + serie.getId() + "/season/" + season.getId() + "/chapters";

    }



}
