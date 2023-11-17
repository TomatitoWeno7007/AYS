package com.ays.ms.controller;

import com.ays.ms.controller.dto.request.UserConfigurationRequest;
import com.ays.ms.controller.dto.request.UserLoginRequest;
import com.ays.ms.controller.dto.request.UserRegisterRequest;
import com.ays.ms.model.Film;
import com.ays.ms.model.Serie;
import com.ays.ms.service.AuthenticationService;
import com.ays.ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/v/principal/content")
    public String getPrincipalContentView(Model model) {

        List<Serie> recommendedSerie = userService.getRecommendedUserSeries();
        List<Film> recommendedFilm = userService.getRecommendedUserFilms();

        model.addAttribute("recommendedSeries", recommendedSerie);
        model.addAttribute("recommendedFilms", recommendedFilm);

        return "user/principal-content";
    }

    @GetMapping("/v/configuration")
    public String getConfigurationView(Model model) {
        model.addAttribute("userInfo", userService.getUser(authenticationService.getIdLoginUser()));
        model.addAttribute("userConfiguration", new UserConfigurationRequest());
        return "user/configuration";
    }

    @PostMapping
    public String register(@ModelAttribute("userRegister") @Valid UserRegisterRequest userRegister,
            BindingResult result, Model model) {

        model.addAttribute("userLogin", new UserLoginRequest());

        if(result.hasErrors()) {
            return "user/not-loggin";
        }

        userService.save(userRegister);

        return "redirect:/user/v/principal/content";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("userLogin") @Valid UserLoginRequest userLoginRequest,
                        BindingResult result, Model model) {

        model.addAttribute("userRegister", new UserRegisterRequest());

        if(result.hasErrors()) {
            return "user/not-loggin";
        }

        Boolean isLogged = this.userService.login(userLoginRequest);

        if (Boolean.FALSE.equals(isLogged)) {
            ObjectError objectError = new ObjectError("globalError",
                    "Usuario o contraseña incorrecto");
            result.addError(objectError);
            return "user/not-loggin";
        }

        if (authenticationService.isAdmin()) {
            return "redirect:/admin/v/serie";
        }

        return "redirect:/user/v/principal/content";
    }

    @PostMapping("/configuration")
    public String saveConfiguration(@ModelAttribute("userConfiguration") @Valid UserConfigurationRequest userConfigurationRequest,
                           BindingResult result, Model model) {

        userService.configuration(userConfigurationRequest);

        return "redirect:/user/v/configuration";
    }

    @PostMapping("/configuration/card")
    public String saveConfigurationCard() {
        return "redirect:/user/v/configuration";
    }

    @GetMapping("/logout")
    public String logout() {
        authenticationService.logout();
        return "redirect:/";
    }

}
