package com.example.pelican.controller;

import com.example.pelican.model.User;
import com.example.pelican.model.Wish;
import com.example.pelican.model.Wishlist;
import com.example.pelican.repository.UserRepository;
import com.example.pelican.repository.WishRepository;
import com.fasterxml.jackson.databind.ext.SqlBlobSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;

@Controller
public class WishController {

    WishRepository wishRepository;
    UserRepository userRepository;

    public WishController(WishRepository w, UserRepository u) {
        wishRepository = w;
        userRepository = u;
    }


    @GetMapping("/")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userRepository.userList());
        return "getAllUsers";
    }

    @GetMapping("/index")
    public String index(@RequestParam("username")String username,@RequestParam("password")String password,Model model) {
        boolean b = userRepository.loginCheck(username, password);
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        if (b == true) {
            User user = userRepository.findUserByUserName(username);
            userRepository.setCurrentUser(user);
            ArrayList<Wish> wishList = wishRepository.viewWishList(user);
            model.addAttribute("wishList", wishList);
            ArrayList<Wish> wishLists = wishRepository.viewSharedWishLists(user);
            ArrayList<Wishlist> wishlistArray = userRepository.wishlists(wishLists);
            model.addAttribute("wishlistArray", wishlistArray);
            return "index";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/create")
    public String postCreate(@RequestParam("title") String title, @RequestParam("link") String link) {
        wishRepository.insertWish(userRepository.getCurrentUser().getUserID(), title, link);
        return "redirect:/";
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
    public String create(@RequestParam("userName") String userName, @RequestParam("email") String email, @RequestParam("fName") String fName, @RequestParam("lName") String lName, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("userName", userName);
        redirectAttributes.addAttribute("email", email);
        redirectAttributes.addAttribute("fName", fName);
        redirectAttributes.addAttribute("lName", lName);
        redirectAttributes.addAttribute("password", password);
        userRepository.insertUser(email, userName, fName, lName, password);
        return "redirect:/";
    }

    @GetMapping("/delete/{userID}")
    public String deleteUser(@PathVariable("userID") int deleteID) {
        userRepository.deleteUserById(deleteID);
        return "redirect:/";
    }

    @PostMapping("/addRelation/email")
    public String addRelationByEmail(@RequestParam("email") String email,RedirectAttributes redirectAttributes){
        //model.addAttribute("email",email);
        redirectAttributes.addAttribute("email",email);
        User u = userRepository.findUserByEmail(email);
        userRepository.addRelation(u);
        return "redirect:/";
    }
    @PostMapping("/addRelation/userName")
    public String addRelationByUserName(@RequestParam("userName") String userName,RedirectAttributes redirectAttributes){
        //model.addAttribute("userName",userName);
        redirectAttributes.addAttribute("userName",userName);
        User u = userRepository.findUserByEmail(userName);
        userRepository.addRelation(u);
        return "redirect:/";
    }

    @GetMapping("/delete/{title}/delete/{userID}")
    public String deleteUser(@PathVariable("title") String deleteTitle, @PathVariable("userID") int deleteID) {
        wishRepository.deleteWishByTitleAndUserID(deleteTitle, deleteID);
        return "redirect:/";
    }

    @GetMapping("/reserve/{title}/reserve/{userID}")
    public String reserevWish(@PathVariable("title") String reserveTitle, @PathVariable("userID") int reserveID) {
        wishRepository.reserveWishByTitleAndUserID(reserveTitle, reserveID);
        return "redirect:/";
    }


    @PostMapping("/login")
    public String logincheck(@RequestParam("username") String username, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("username",username);
        redirectAttributes.addAttribute("password",password);
        return "redirect:/login";

    }
    @GetMapping("/login")
    public String login(){
        return "login";
        }
    @GetMapping("/createwish")
    public String createWish(){
        return "createwish";
    }
    @PostMapping("/createwish")
    public String creatWish(@RequestParam("title")String title,@RequestParam("link")String link, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("title",title);
        redirectAttributes.addAttribute("link",link);
        wishRepository.insertWish(userRepository.getCurrentUser().getUserID(),title,link);
        return "redirect:/index";
    }
}

