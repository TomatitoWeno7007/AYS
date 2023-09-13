package com.ays.ms.controller;

import com.ays.ms.controller.dto.request.UserLoginRequest;
import com.ays.ms.controller.dto.request.UserRegisterRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    @GetMapping
    public String index(@ModelAttribute("userRegister") UserRegisterRequest userRegister){
        return "user/not-loggin";
    }

}
