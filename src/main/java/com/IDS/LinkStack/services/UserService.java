package com.IDS.LinkStack.services;

import com.IDS.LinkStack.domain.User;
import com.IDS.LinkStack.repos.UserRepository;
import com.IDS.LinkStack.domain.Role;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean saveUser(User user){
        user.setPassword(user.getPassword());
        if(userRepository.findByUsername(user.getUsername()) != null)
            return false;
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(false);
        userRepository.save(user);
        return true;
    }

    public void changeActive(String username, Boolean active){
        User user = userRepository.findByUsername(username);
        user.setActive(true);
        userRepository.save(user);
    }

    public Boolean isAuthenticated(String username){
        User user = userRepository.findByUsername(username);
        return user.getActive();
    }

    public boolean isAdminUser(String username){
        User user = userRepository.findByUsername(username);
        for(Role i : user.getRoles())
            if(i == Role.ADMIN)
                return true;
        return false;
    }

    public boolean isUserExist(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password) != null ? true : false;
    }

    public String isAuth(String temp, HttpSession session){
        return session.getAttribute("username") != null ? isAuthenticated( (String)session.getAttribute("username") )
                        ? temp : "redirect:/log_in" : "redirect:/log_in";
    }

}
