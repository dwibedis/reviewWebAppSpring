package com.review.dbapi;

import java.util.List;

import com.review.model.Review;

/**
 * Specifying the methods implemented for interaction with data base.
 * 
 * @author satyad
 *
 */
public interface MovieDB {

	/**
	 * Method reads the reviews of given movie ID from DB.
	 * 
	 * @param id movieID
	 * @return the list of reviews of movie present in db and the list is empty if
	 *         no reviews present.
	 */
	public List<Review> readReviews(int id);

	/**
	 * Method reads the number of reviews from DB done by a given user for given
	 * movie.
	 * 
	 * @param user Name name of user
	 * @param id   movie ID
	 * @return integer count
	 */
	public int readCount(String userName, int id);

	/**
	 * Method updates the entry corresponding to the given movie name and user name
	 * in db.
	 * 
	 * @param id              movieID
	 * @param userName        name of user
	 * @param rating          rating of movie
	 * @param reviewStatement movie review
	 */
	public void update(int id, String userName, int rating, String reviewStatement);

	/**
	 * Method inserts a new entry to the db with the details provided.
	 * 
	 * @param id              movieID
	 * @param userName        name of user.
	 * @param rating          rating of movie.
	 * @param reviewStatement movie review.
	 */
	public void insert(int id, String userName, int rating, String reviewStatement);

	/**
	 * Method deletes a particular entry corresponding to given movieID and user.
	 * 
	 * @param id       movieID
	 * @param userName name of user
	 */
	public void delete(int id, String userName);
}
