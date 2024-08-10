package com.touhid.securityApp.services;

import com.touhid.securityApp.dto.LoginDto;
import com.touhid.securityApp.dto.LoginResponseDto;
import com.touhid.securityApp.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public LoginResponseDto login(LoginDto loginDto) {
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
        );

       User user = (User) authentication.getPrincipal();
       String accessToken = jwtService.generateAccessToken(user);
       String refreshToken = jwtService.generateRefreshToken(user);

       return new LoginResponseDto(user.getUserId(),accessToken,refreshToken);

    }

    public LoginResponseDto refreshToken(String refreshToken) {

        System.out.println("RefreshToken :"+refreshToken);

        Long userId = jwtService.getUserIdFromToken(refreshToken);

        System.out.println("userId :"+userId);

        User user = userService.getUserById(userId);

        System.out.println("user: "+user);

        String accessToken = jwtService.generateRefreshToken(user);

        System.out.println("accessToken"+ accessToken);


        return new LoginResponseDto(user.getUserId(),accessToken,refreshToken);

    }
}
