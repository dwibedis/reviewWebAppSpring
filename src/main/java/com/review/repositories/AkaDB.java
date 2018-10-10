package com.review.repositories;

import com.review.exception.DBAccessException;
import com.review.exception.MovieReviewException;

/**
 * 
 * @author satyad
 *
 */
public interface AkaDB {

	/**
	 * 
	 * @param id
	 * @return
	 */
	public int getMovieID(int id) ;

	/**
	 * 
	 * @param akaId
	 * @param movieId
	 * @throws MovieReviewException
	 */
	public void insert(int akaId, int movieId) throws MovieReviewException;

	/**
	 * 
	 * @param akaId
	 * @throws MovieReviewException
	 */
	public void delete(int akaId) throws MovieReviewException;
}
