package com.example.pelican.controller;

import com.example.pelican.model.User;
import com.example.pelican.repository.WishRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WishController {

    WishRepository wishRepository;

    public WishController(WishRepository u) {
        wishRepository = u;
    }


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users",wishRepository.userList());
        return "index";
    }


    @GetMapping("/createNew.html")
    public String create(){
        return "createNew";
    }

    @PostMapping("/createNew")
    public String create(@ModelAttribute User user){
        wishRepository.createUser(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{userID}")
    public  String deleteUser(@PathVariable("userID") int deleteID) {
        wishRepository.deleteUserById(deleteID);
        return "redirect:/";
    }
}
