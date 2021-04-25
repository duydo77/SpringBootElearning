package com.myclass.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/page")
public class AdminIndexController {

	@GetMapping("role")
	public String role() {
		return "admin/role-list";
	}
	
	@GetMapping("login")
	public String login() {
		return "admin/login";
	}
	
	@GetMapping("category")
	public String category() {
		return "admin/category";
	}
	
	@GetMapping("user")
	public String user() {
		return "admin/user";
	}
	
	@GetMapping("")
	public String home() {
		return "admin/index";
	}
	
	@GetMapping("profile")
	public String profile() {
		return "admin/profile";
	}
}
