package com.ays.ms.controller;

import com.ays.ms.model.Season;
import com.ays.ms.service.SeasonService;
import com.ays.ms.service.SerieService;
import com.ays.ms.model.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("season")
public class SeasonController {

    @Autowired
    private SerieService serieService;
    @Autowired
    private SeasonService seasonService;

    private HttpSession session;



}
