package com.review.repositories;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author satyad
 *
 */
public interface MovieNamesDB {

	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> getMovieNames(String query);

	/**
	 * 
	 * @param movieName
	 */
	public void insert(String movieName);
}
