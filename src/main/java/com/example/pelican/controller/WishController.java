package com.example.pelican.controller;

import com.example.pelican.model.User;
import com.example.pelican.repository.UserRepository;
import com.example.pelican.repository.WishRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WishController {

    WishRepository wishRepository;
    UserRepository userRepository;

    public WishController(WishRepository w, UserRepository u) {
        wishRepository = w;
        userRepository = u;
    }


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users", userRepository.userList());
        return "index";
    }

    @GetMapping("/index")
    public String index(@RequestParam("username")String username,@RequestParam("password")String password,Model model) {
        boolean b = userRepository.loginCheck(username, password);
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        System.out.println(username);
        System.out.println(password);
        if (b == true) {
            return "index";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/index")
    public String postIndex(@RequestParam("username")String username,@RequestParam("password")String password, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("username",username);
        redirectAttributes.addAttribute("password",password);
        return "redirect:/index";
    }


    @GetMapping("/createNew")
    public String create() {
        return "createNew";
    }

    @PostMapping("/createNew")
    public String create(@ModelAttribute User user) {
        userRepository.createUser(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{userID}")
    public String deleteUser(@PathVariable("userID") int deleteID) {
        userRepository.deleteUserById(deleteID);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String logincheck(@RequestParam("username") String username, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("username",username);
        redirectAttributes.addAttribute("password",password);
        return "redirect://login";

    }
    @GetMapping("/login")
    public String login(){
        return "login";
        }
    }