package com.ays.ms.controller;

import com.ays.ms.controller.dto.request.UserLoginRequest;
import com.ays.ms.controller.dto.request.UserRegisterRequest;
import com.ays.ms.model.Film;
import com.ays.ms.model.Serie;
import com.ays.ms.service.AuthenticationService;
import com.ays.ms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdministratorController {

    @GetMapping("/v/film")
    public String getFilm(@ModelAttribute("userLogin") UserLoginRequest userLogin,
                          @ModelAttribute("userRegister") UserRegisterRequest userRegister) {
        return "admin/film";
    }

    @GetMapping("/v/serie")
    public String getSerie() {
        return "admin/serie";
    }

}
