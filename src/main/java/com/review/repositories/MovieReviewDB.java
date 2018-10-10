package com.review.repositories;

import java.util.List;

import com.review.model.Review;

/**
 * Specifying the methods implemented for interaction withmovie_review_db.
 * 
 * @author satyad
 *
 */
public interface MovieReviewDB {

	public List<Review> readReviews(int id);

	public int readCount(String userName, int id);

	public void update(int id, String userName, int rating, String reviewStatement);

	public void create(int id, String userName, int rating, String reviewStatement);

	public void delete(int id, String userName);
}
