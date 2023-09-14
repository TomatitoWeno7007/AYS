package com.ays.ms.controller;

import com.ays.ms.controller.dto.request.UserLoginRequest;
import com.ays.ms.controller.dto.request.UserRegisterRequest;
import com.ays.ms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") long id) {
        return "Devuelvo el usuario con id " + id;
    }

    @PostMapping
    public String register(@ModelAttribute("userRegister") @Valid UserRegisterRequest userRegister,
            BindingResult result, Model model) {

        model.addAttribute("userLogin", new UserLoginRequest());

        if(result.hasErrors()) {
            return "user/not-loggin";
        }

        userService.save(userRegister);
        return "user/principal-content";
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

        model.addAttribute("userCatalog", this.userService.getUserCatalog());

        return "user/principal-content";
    }

}
