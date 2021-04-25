package com.myclass.controller;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
	
	@GetMapping("/coursedetail/{courseId}")
	public String coursedetail(@PathVariable int courseId) {
		return "details";
	}
	
	@GetMapping("/405")
	public String e405() {
		return "error/405";
	}
}
