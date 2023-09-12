package com.ays.ms.controller;

import com.ays.ms.controller.dto.request.UserRegisterRequest;
import com.ays.ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String getUsers(@PathVariable("id") long id) {
        return "Devuelvo el usuario con id " + id;
    }

    @PostMapping
    public String addUser(UserRegisterRequest user, BindingResult result, Model model) {
        userService.save(user);
        return "gallery";
    }

}
