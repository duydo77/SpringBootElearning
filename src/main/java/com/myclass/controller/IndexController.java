package com.myclass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/profile")
	public String profile() {
		return "profile";
	}
	
	@GetMapping("/mycourse")
	public String mycouse() {
		return "course";
	}
	
	@GetMapping("/coursedetail")
	public String coursedetail() {
		return "detail";
	}
	
	@GetMapping("/405")
	public String e405() {
		return "error/405";
	}
}
