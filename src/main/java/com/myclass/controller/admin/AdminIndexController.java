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
	
}
