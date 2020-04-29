package com.IDS.LinkStack.controllers;

import com.IDS.LinkStack.domain.Link;
import com.IDS.LinkStack.services.LinkService;
import com.IDS.LinkStack.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class MainController {
    private final LinkService linkService;
    private final UserService userService;

    public MainController(LinkService linkService, UserService userService) {
        this.linkService = linkService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model){
        List<Link> links = linkService.GetAll();
        model.addAttribute("links", links);
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", defaultValue = "World") String name, Model model, HttpSession session){
        model.addAttribute("name", name);
        model.addAttribute("title", "hello");

        return userService.isAuth("hello", session);
    }
}
