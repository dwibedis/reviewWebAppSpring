package com.review.service;

import org.json.JSONObject;

/**
 * 
 * @author satyad
 *
 */
public interface OMDBHelper {

	/**
	 * 
	 * @param movieName
	 * @return
	 */
	public JSONObject fetchMovieDetails(String movieName);
}
