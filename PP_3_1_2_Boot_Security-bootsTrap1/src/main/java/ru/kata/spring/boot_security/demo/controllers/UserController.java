package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/")
    public String index() {

        return "redirect:/login";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("Users", List.of(userServiceImpl.findUserByUserName(principal.getName())));
        model.addAttribute("user", userServiceImpl.findUserByUserName(principal.getName()));
        return "mainPage";
    }
}
