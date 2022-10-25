package com.example.pelican.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WishController {

    @GetMapping("")
    public String index(){
        System.out.println("hej");
        return "index";
    }
}
