package com.ays.ms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("admin")
public class AdministratorController {

    @GetMapping("/v/film")
    public String getFilm(Model model) {
        return "admin/film";
    }

    @GetMapping("/v/serie")
    public String getSerie(Model model) {
        return "admin/serie";
    }

}
