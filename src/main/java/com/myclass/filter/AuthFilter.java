package com.myclass.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

// can co @WebFilter
// doi voi secutiry thfi them vao admin secutiry config
public class AuthFilter extends BasicAuthenticationFilter {

	UserDetailsService userDetailsService;
	
	public AuthFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Laays token
		String authorizationHeader = request.getHeader("Authorization");
		
		if(authorizationHeader != null && !authorizationHeader.isEmpty()) {
			String token = authorizationHeader.replace("Bearer", "");
			// Giải mã token lấy eamil
			String email = Jwts.parser()
					.setSigningKey("dotruongduy")
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
			// Dùng email truy vấn DB lấy thông tin user
			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());

			// set thông tin user vào security context
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}
	chain.doFilter(request, response);
	}

}
