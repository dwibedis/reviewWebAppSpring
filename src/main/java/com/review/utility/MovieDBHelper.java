package com.review.utility;

import com.review.exception.MovieReviewException;
import com.review.model.Movie;

/**
 * Interface to define the implemented business logic.
 * 
 * @author satyad
 *
 */
public interface MovieDBHelper {

	/**
	 * 
	 * @param movieName name of the movie.
	 * @return the movie object containing details from DB.
	 * @throws MovieReviewException
	 */
	public Movie retrieve(String movieName) throws MovieReviewException;

	/**
	 * 
	 * @param userName name of the user or source.
	 * @param movieName the name of movie.
	 * @return status if the user/source has already reviewed the movie or not.
	 */
	public boolean hasUserAlreadyReviewed(String userName, String movieName);

	/**
	 * 
	 * @param movieName name of the movie.
	 * @param rating the rating of movie given by particular source or user. 
	 * @param reviewStatement the review statement by the user.
	 * @param userName the name of the user/source.
	 */
	public void update(String movieName, int rating, String reviewStatement, String userName);

}
