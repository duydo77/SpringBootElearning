package com.myclass.controller.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class TeacherIndexController {

	@Controller
	@RequestMapping("teacher/page")
	public class AdminIndexController {
		public String course() {
			return "";
		}
	}

}
