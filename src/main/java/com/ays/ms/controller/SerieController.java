package com.ays.ms.controller;

import com.ays.ms.model.Serie;
import com.ays.ms.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("serie")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @PostMapping
    public void save() {}

    @GetMapping
    public List<Serie> getSeries() { return serieService.getSeries(); }

    @GetMapping("/{id}")
    public String getSeries(@PathVariable("id") long id) { return "Id de la Serie: " + id; }

}
