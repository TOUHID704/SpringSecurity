package com.touhid.securityApp.controllers;

import com.touhid.securityApp.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping(produces = "application/json")
    private String homePage(){
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Home Page" +"user:"+user;
    }

}
