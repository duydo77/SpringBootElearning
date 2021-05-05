package com.myclass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/search")
	public String mycouse(@RequestParam String key, @RequestParam String p) {
		return "search";
	}
	
	@GetMapping("/search/cate/{cateId}")
	public String mycouse(@PathVariable("cateId") int cateId) {
		return "cate";
	}
	
	@GetMapping("/coursedetail/{courseId}")
	public String coursedetail(@PathVariable int courseId) {
		return "details";
	}
	
	@GetMapping("/cart")
	public String cart() {
		return "cart";
	}
	
	@GetMapping("/405")
	public String e405() {
		return "error/405";
	}
}
