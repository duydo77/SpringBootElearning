package com.myclass.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminIndexController {

	@GetMapping("page/role")
	public String role() {
		return "admin/role-list";
	}
	
	@GetMapping("page/login")
	public String login() {
		return "admin/login";
	}
	
	@GetMapping("page/category")
	public String category() {
		return "admin/category";
	}
	
	@GetMapping("page/user")
	public String user() {
		return "admin/user";
	}
	
	@GetMapping(value = {"", "page"})
	public String home() {
		return "admin/index";
	}
	
	@GetMapping("page/profile")
	public String profile() {
		return "admin/profile";
	}
}
