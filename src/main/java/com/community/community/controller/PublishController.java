package com.community.community.controller;

import com.community.community.dto.QuestionDTO;
import com.community.community.model.Question;
import com.community.community.model.User;
import com.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

	@Autowired
	private QuestionService questionService;

	@GetMapping("/publish/{id}")
	public String edit(@PathVariable(name = "id") Integer id,
					   Model model) {
		QuestionDTO questionDTO = questionService.getById(id);
		model.addAttribute("title", questionDTO.getTitle());
		model.addAttribute("description", questionDTO.getDescription());
		model.addAttribute("tag", questionDTO.getTag());
		model.addAttribute("id", id);
		return "publish";
	}

	@GetMapping("/publish")
	public String publish(Model model) {
		return "publish";
	}

	@PostMapping("/publish")
	public String dopublish(
			@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("tag") String tag,
			@RequestParam("id") Integer id,
			HttpServletRequest request,
			Model model) {
		model.addAttribute("title", title);
		model.addAttribute("description", description);
		model.addAttribute("tag", tag);

		if (title==null || title.equals("")) {
			model.addAttribute("error", "问题标题不能为空");
			return "publish";
		}if (description==null || description.equals("")) {
			model.addAttribute("error", "问题补充不能为空");
			return "publish";
		}

		User user = (User) request.getSession().getAttribute("user");
		if (user==null) {
			model.addAttribute("error", "用户未登录");
			return "publish";
		}


		Question question = new Question();
		question.setId(id);
		question.setTitle(title);
		question.setDescription(description);
		question.setTag(tag);
		question.setCreator(user.getId());
		question.setGmtCreate(System.currentTimeMillis());
		question.setGmtModified(question.getGmtCreate());
		questionService.updateOrCreateQuestion(question);
		return "redirect:/";
	}
}
