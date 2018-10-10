package com.review.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.review.constants.MovieReviewConstants;
import com.review.exception.MovieReviewException;
import com.review.service.MovieDBAPI;

/**
 * Controller Utility.
 * 
 * @author satyad
 *
 */
@Controller
public class HomeController {

	private static final String VIEW_NAME = "index";

	@Autowired
	private MovieDBAPI movieDBAPIHelper;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView welcome() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(VIEW_NAME);
		return modelAndView;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws MovieReviewException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(VIEW_NAME);

		String movieForQuery = request.getParameter(MovieReviewConstants.SEARCH_ATTRIBUTE);

		modelAndView.addObject(MovieReviewConstants.MOVIE_ATTRIBUTE, movieDBAPIHelper.retrieve(movieForQuery));
		modelAndView.addObject(MovieReviewConstants.USER_NAME_ATTRIBUTE, MovieReviewConstants.USER_NAME);
		return modelAndView;
	}

	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public ModelAndView submit(HttpServletRequest request, HttpServletResponse response) throws MovieReviewException {
		ModelAndView modelAndView = new ModelAndView();
		String movieName = request.getParameter(MovieReviewConstants.MOVIE_NAME_ATTRIBUTE);
		String akaMovieTitle = request.getParameter(MovieReviewConstants.AKA_MOVIE_TITLE_ATTRIBUTE);
		Integer rating = Integer.parseInt(request.getParameter(MovieReviewConstants.RATING_ATTRIBUTE));
		String reviewStatement = request.getParameter(MovieReviewConstants.REVIEW_STATEMENT_ATTRIBUTE);
		movieDBAPIHelper.update(movieName, akaMovieTitle, rating, reviewStatement, MovieReviewConstants.USER_NAME);
		//TODO adding alert status successfully.
		modelAndView.setViewName(VIEW_NAME);
		return modelAndView;
	}

	@ExceptionHandler(MovieReviewException.class)
	public ModelAndView exceptionHandler(Exception e, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/");
		return model;
	}
}
