//package com.myclass.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import com.myclass.filter.AuthFilter;
//
//@Configuration
//@EnableWebSecurity
//public class ViewAdminSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	private UserDetailsService userDetailsService;
//
//	public ViewAdminSecurityConfig(UserDetailsService userDetailsService) {
//		this.userDetailsService = userDetailsService;
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder()); // .passwordEncoder(passwordEncoder())
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable()
//		.antMatcher("/admin/page/**") // link gui len phai bat dau voi /api thi moi duoc chap nhan
//		.authorizeRequests()
//		.antMatchers("/admin/page/login")
//		.permitAll()
//		.antMatchers("/admin/page/**")
//		.hasAnyRole("ADMIN")
//		.anyRequest()
//		.authenticated()
//		.and().formLogin().loginPage("/admin/page/login");
//
//		http.addFilter(new AuthFilter(authenticationManager(), userDetailsService));
//
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	}
//
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
//				"/configuration/security", "/swagger-ui.html", "/webjars/**");
//	}
//
//}
