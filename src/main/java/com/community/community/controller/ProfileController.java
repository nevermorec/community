package com.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@GetMapping("/questions")
	public String profile(Model model) {
		model.addAttribute("sectionName", "我的问题");
		model.addAttribute("section", "questions");
		return "profile";
	}
}
