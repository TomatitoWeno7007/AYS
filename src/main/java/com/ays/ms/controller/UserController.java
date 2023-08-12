package com.ays.ms.controller;

import com.ays.ms.model.User;
import com.ays.ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping
    public void save() {

    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public String getUsers(@PathVariable("id") long id) {
        return "Devuelvo el usuario con id " + id;
    }

}