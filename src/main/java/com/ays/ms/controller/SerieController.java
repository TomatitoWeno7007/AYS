package com.ays.ms.controller;

import com.ays.ms.controller.dto.request.SerieRequest;
import com.ays.ms.model.Season;
import com.ays.ms.model.Serie;
import com.ays.ms.service.GenreService;
import com.ays.ms.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

        serieService.addSerie(newSerie);

        return "redirect:/admin/v/serie";
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

    @GetMapping("/v/season/{id}")
    public String getSeason(@PathVariable("id") long id, Model model) {
        Serie serie = serieService.getSerie(id);
        model.addAttribute("serie", serie.getName());
        model.addAttribute("listSeason", serie.getSeasons());
        return "admin/season";
    }

    @PostMapping("/v/season/{number}")
    public String getSeasonPrint(@PathVariable("number") long number, Model model) {
//        Season season = seasonService.get
//        model.addAttribute("serie", serie.getName());
//        model.addAttribute("listSeason", serie.getSeasons());
//        return "admin/season";
    }


}
