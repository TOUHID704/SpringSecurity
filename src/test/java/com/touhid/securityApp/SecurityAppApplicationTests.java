package com.touhid.securityApp;

import com.touhid.securityApp.entities.User;
import com.touhid.securityApp.services.JwtService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityAppApplicationTests {

	@Autowired
	JwtService jwtService;

	@Test
	void contextLoads() {

		User user = new User(4L,"Aamir","a@gmail.com","A@123");

		String token = jwtService.generateToken(user);

		System.out.println("Token: "+token);

		Long id = jwtService.getUserIdFromToken(token);

		System.out.println("UserId: "+id);

	}

}
