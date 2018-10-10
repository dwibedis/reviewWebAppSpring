package com.review.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.review.repositories.UserDB;

@Controller
public class LogInController {

	@Autowired
	private UserDB userDB;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView displayProfile(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/");
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		System.out.println(userDB.validate(userId, password));
		System.out.println(request.getParameter("userId"));
		System.out.println(request.getParameter("password"));
		return model;
	}
}
