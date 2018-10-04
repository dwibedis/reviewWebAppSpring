package com.review.utility;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.review.dbapi.MovieDB;
import com.review.exception.MovieReviewException;
import com.review.model.Movie;
import com.review.model.Review;

/**
 * The service layer providing utility methods.
 * 
 * @author satyad
 *
 */
@Service
public class MovieDBAPIHelper implements MovieDBHelper {

	@Autowired
	private MovieDB movieDB;

	public Movie retrieve(String movieName) {

		// Remove extra spaces before, after and between the words and replace with
		// single space.
		movieName = movieName.trim().replaceAll(" +", " ");
		// TODO convert the movieName all into lower case and this is to be done after
		// updating DB for present reviews.
		List<Review> reviews = movieDB.readReviews(movieName.hashCode());
		return new Movie(movieName, reviews);
	}

	public boolean hasUserAlreadyReviewed(String userName, String movieName) throws MovieReviewException {

		int reviewCount = movieDB.readCount(userName, movieName.hashCode());
		switch (reviewCount) {
		case 0:
			return false;
		case 1:
			return true;
		default:
			throw new MovieReviewException("DB Error - user:" + userName + " has " + reviewCount
					+ "reviews for movieID " + movieName.hashCode());
		}
	}

	public void update(String movieName, int rating, String reviewStatement, String userName)
			throws MovieReviewException {
		if (hasUserAlreadyReviewed(userName, movieName)) {
			movieDB.update(movieName.hashCode(), userName, rating, reviewStatement);
		} else {
			movieDB.insert(movieName.hashCode(), userName, rating, reviewStatement);
		}
	}

}
