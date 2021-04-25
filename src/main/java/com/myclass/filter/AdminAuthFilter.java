package com.myclass.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AdminAuthFilter extends BasicAuthenticationFilter{

	private UserDetailsService userDetailSerice;
	
	public AdminAuthFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailSerice) {
		super(authenticationManager);
		this.userDetailSerice = userDetailSerice;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String authorizationHeader = request.getHeader("Authorization");
		
		if (!authorizationHeader.isBlank()) {
			
			String token = authorizationHeader.replace("Bearer ", "");

			String email = Jwts.parser()
							.setSigningKey("admin_123654")
							.parseClaimsJws(token)
							.getBody()
							.getSubject();
			
			UserDetails user = userDetailSerice.loadUserByUsername(email);
			
		}
		
		chain.doFilter(request, response);
	}
}
