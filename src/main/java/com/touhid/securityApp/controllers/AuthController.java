package com.touhid.securityApp.controllers;

import com.touhid.securityApp.dto.LoginDto;
import com.touhid.securityApp.dto.LoginResponseDto;
import com.touhid.securityApp.dto.SignUpDto;
import com.touhid.securityApp.dto.UserDto;
import com.touhid.securityApp.services.AuthService;
import com.touhid.securityApp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping(produces = "application/json")
    public String auth() {
        return "auth Page";
    }

    @PostMapping(value = "/signup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(userService.signUp(signUpDto));
    }

    @PostMapping(path = "/login",consumes = "application/json",produces = "application/json")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response){

        LoginResponseDto loginResponseDto = authService.login(loginDto);

        Cookie cookie  = new Cookie("refreshToken",loginResponseDto.getRefreshToken());
        cookie.setPath("/auth");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request)  {
            String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue )
                .orElseThrow(()->new AuthenticationServiceException("Refresh Token not found inside the cookie"));

           LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);

           return ResponseEntity.ok(loginResponseDto);
    }


}
