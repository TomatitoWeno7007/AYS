package com.ays.ms.controller;

import com.ays.ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping
    public void save() {

    }

    @GetMapping("/{id}")
    public String getUsers(@PathVariable("id") long id) {
        return "Devuelvo el usuario con id " + id;
    }

}
