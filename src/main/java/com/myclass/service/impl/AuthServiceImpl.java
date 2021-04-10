package com.myclass.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.myclass.dto.LoginDto;
import com.myclass.service.AuthService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public String login(LoginDto loginDto) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
				loginDto.getPassword());
		authenticationManager.authenticate(authentication);

		// Tao token
		Date nowDate = new Date();
		String token = Jwts.builder().setSubject(loginDto.getEmail()).setIssuedAt(nowDate)
				.setExpiration(new Date(nowDate.getTime() + 864000000L))
				.signWith(SignatureAlgorithm.HS512, "dotruongduy").compact();

		return token;
	}

}
