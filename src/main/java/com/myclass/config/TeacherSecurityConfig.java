package com.myclass.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.myclass.filter.AuthFilter;

@Configuration
@EnableWebSecurity
@Order(value = 2)
public class TeacherSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private UserDetailsService userDetailsService;
	
	public TeacherSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder()); // .passwordEncoder(passwordEncoder())
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.antMatcher("/api/teacher/**") // link gui len phai bat dau voi /api thi moi duoc chap nhan
		.authorizeRequests()
		.antMatchers("/api/teacher/login")
		.permitAll()
		.antMatchers("/api/teacher/target")
		.permitAll()
		.antMatchers("/teacher/index")
		.permitAll()
		.antMatchers("/teacher/**")
		.hasAnyRole("TEACHER")
		.antMatchers("/api/teacher/**")
		.hasAnyRole("TEACHER")
		.anyRequest()
		.authenticated();

		http.addFilter(new AuthFilter(authenticationManager(), userDetailsService));

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/v2/api-docs", 
				"/configuration/ui", 
				"/swagger-resources/**",
				"/configuration/security", 
				"/swagger-ui.html", 
				"/webjars/**");
	}
}
