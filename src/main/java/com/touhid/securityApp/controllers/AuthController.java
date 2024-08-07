package com.touhid.securityApp.controllers;

import com.touhid.securityApp.entities.SignUpDto;
import com.touhid.securityApp.entities.UserDto;
import com.touhid.securityApp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping
    public String auth() {
        return "auth Page";
    }

    @PostMapping(value = "/signup", consumes = "application/json", produces = "application/json")
    public UserDto signUp(@RequestBody SignUpDto signUpDto) {
        return userService.signUp(signUpDto);
    }
}
