package com.myclass.controller.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("teacher")
public class TeacherIndexController {

	@GetMapping("")
	public String course() {
		return "teacher/course";
	}
	
//	@GetMapping("")
//	public String index() {
//		return "teacher/index";
//	}
	
	@GetMapping("login")
	public String login() {
		return "teacher/login";
	}
	
	@GetMapping("profile")
	public String profile() {
		return "teacher/profile";
	}
	
	@GetMapping("detail/{id}")
	public String detail() {
		return "teacher/details";
	}
}
