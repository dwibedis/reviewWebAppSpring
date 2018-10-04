package com.review.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.review.constants.MovieReviewConstants;
import com.review.exception.MovieReviewException;
import com.review.utility.MovieDBHelper;

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
	private MovieDBHelper movieDBAPIHelper;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView welcome() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(VIEW_NAME);
		return modelAndView;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(VIEW_NAME);
		String movieName = request.getParameter(MovieReviewConstants.SEARCH_ATTRIBUTE);
		modelAndView.addObject(MovieReviewConstants.MOVIE_ATTRIBUTE, movieDBAPIHelper.retrieve(movieName));
		modelAndView.addObject(MovieReviewConstants.USER_NAME_ATTRIBUTE, MovieReviewConstants.USER_NAME);
		try {
			modelAndView.addObject(MovieReviewConstants.IS_NEW_REVIEWER_ATTRIBUTE,
					!movieDBAPIHelper.hasUserAlreadyReviewed(MovieReviewConstants.USER_NAME, movieName));
		} catch (MovieReviewException e) {
			// TODO perform action in case of controller exception
		}
		return modelAndView;
	}

	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public ModelAndView submit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		String movieName = request.getParameter(MovieReviewConstants.MOVIE_NAME_ATTRIBUTE);
		Integer rating = Integer.parseInt(request.getParameter(MovieReviewConstants.RATING_ATTRIBUTE));
		String reviewStatement = request.getParameter(MovieReviewConstants.REVIEW_STATEMENT_ATTRIBUTE);
		try {
			movieDBAPIHelper.update(movieName, rating, reviewStatement, MovieReviewConstants.USER_NAME);
		} catch (MovieReviewException e) {
			System.out.println("here");
		}
		if (request.getParameter(MovieReviewConstants.IS_NEW_REVIEWER_ATTRIBUTE).equals("false")) {
			modelAndView.addObject(MovieReviewConstants.ALERT_ATTRIBUTE, true);
		}
		modelAndView.setViewName(VIEW_NAME);
		return modelAndView;
	}
}
