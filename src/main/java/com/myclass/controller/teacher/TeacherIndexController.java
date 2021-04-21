package com.myclass.controller.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("teacher")
public class TeacherIndexController {

	@GetMapping("course")
	public String course() {
		return "teacher/course";
	}
	
	@GetMapping("index")
	public String index() {
		return "teacher/index";
	}
	
	@GetMapping("detail")
	public String detail() {
		return "teacher/details";
	}
}
