package com.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.review.utility.MovieDBHelper;

@Controller
public class AutoSuggestController {

	@Autowired
	private MovieDBHelper movieDBHelper;

	@RequestMapping(value = "/getMovieNamesList", method = RequestMethod.GET)
	public @ResponseBody List<String> getMoviesList(@RequestParam("term") String query) {
		return movieDBHelper.getMovies(query);
	}
}
