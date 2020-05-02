package com.IDS.LinkStack.services;

import com.IDS.LinkStack.domain.User;
import com.IDS.LinkStack.repos.UserRepository;
import com.IDS.LinkStack.domain.Role;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getMd5(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32)
                hashtext = "0" + hashtext;
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean saveUser(User user){
        user.setPassword(getMd5(user.getPassword()));
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
