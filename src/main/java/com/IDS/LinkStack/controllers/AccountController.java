package com.IDS.LinkStack.controllers;

import com.IDS.LinkStack.domain.User;
import com.IDS.LinkStack.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller("/account")
public class AccountController {

    public final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/log_in")
    public String login(){
        return "loginpage";
    }

    @PostMapping("/log_in")
    public String login(User user, HttpSession session){
        if(!userService.isUserExist(user.getUsername(), user.getPassword()))
            return "redirect:/log_in?error";
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRoles());
        userService.changeActive(user.getUsername(), true);
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(User user){
        return userService.saveUser(user) ? "redirect:/log_in" : "redirect:/signup";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        userService.changeActive( (String)session.getAttribute("username"), false );
        session.removeAttribute("username");
        session.removeAttribute("role");
        return "redirect:/log_in?logout";
    }
}
