package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userServiceImpl;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RoleService roleService) {
        this.userServiceImpl = userServiceImpl;
        this.roleService = roleService;
    }



    @GetMapping("/")
    public String admin(Model model, Principal principal) {
        List<User> list = userServiceImpl.findAll();
        model.addAttribute("Users", list);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", userServiceImpl.findUserByUserName(principal.getName()));
        model.addAttribute("newUser", new User());
        return "mainPage";
    }

    @PostMapping(value = "/add")
    @Transactional
    public String addUser(@ModelAttribute("newUser") User user, @RequestParam(value = "userRole", required = false)ArrayList<Role> roles) {
        user.setRoles(new HashSet<>(roles));
        userServiceImpl.save(user);
        return "redirect:/admin/";
    }

//    @PostMapping(value = "/add")
//    @Transactional
//    public String addNewUser(@ModelAttribute("newUser") User user) {
//        userServiceImpl.save(user);
//        return "redirect:/";
//    }

    @PostMapping(value = "/edit/{id}")
    @Transactional
    public String edit(@PathVariable(name = "id") long id, @ModelAttribute(value = "user") User user,
                       @RequestParam(value = "userRole", required = false) ArrayList<Role> roles) {
        user.setRoles(new HashSet<>(roles));
        userServiceImpl.edit(user);
        return "redirect:/admin/";
    }

//    @PostMapping(value = "/edit/")
//    @Transactional
//    public String editUser(@ModelAttribute(value = "user") User user) {
//        userServiceImpl.edit(user);
//        return "redirect:/admin/";
//    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable(name = "id") long id) {
        userServiceImpl.remove(id);
        return "redirect:/admin/";
    }
}
