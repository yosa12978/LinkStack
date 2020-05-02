package com.IDS.LinkStack.controllers;

import com.IDS.LinkStack.domain.Link;
import com.IDS.LinkStack.services.LinkService;
import com.IDS.LinkStack.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", defaultValue = "World") String name, Model model, HttpSession session){
        model.addAttribute("name", name);
        model.addAttribute("title", "hello");

        return userService.isAuth("hello", session);
    }

    @GetMapping("/links")
    public String mylinks(Model model, HttpSession session){
        Iterable<Link> links = linkService.GetAll((String)session.getAttribute("username"));
        model.addAttribute("links", links);
        return userService.isAuth("mylinks", session);
    }

    @GetMapping("/create")
    public String create(HttpSession session){
        return userService.isAuth("create", session);
    }

    @PostMapping("/create")
    public String create(Link link, HttpSession session){
        linkService.saveLink(link, (String)session.getAttribute("username"));
        return userService.isAuth("redirect:/create?success", session);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name="id") Long id, HttpSession session){
        boolean isDeleted = linkService.deleteLink(id, (String)session.getAttribute("username"));
        return userService.isAuth((isDeleted ? "redirect:/links" : "redirect:/"), session);
    }
}
