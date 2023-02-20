package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping()
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @PersistenceContext
    private final EntityManager entityManager;

    public AdminController(UserService userService, UserRepository userRepository, RoleService roleService, EntityManager entityManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.entityManager = entityManager;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("activeUser", userService.getByUsername(principal.getName()));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", new User());
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "adminPage";
    }


    @RequestMapping(value = "users/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";

    }

    @GetMapping(value = "users/delete")
    public String deleteUser(@RequestParam int id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "users/update")
    public String updateUser(@ModelAttribute("user") User newUser, Integer oldUserId) {
        userService.updateUser(newUser, oldUserId);
        return "redirect:/admin";
    }
}
