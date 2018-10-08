package com.review.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.review.dbapi.AkaDB;
import com.review.dbapi.MovieDB;
import com.review.dbapi.MovieNamesDB;
import com.review.exception.DBAccessException;
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
	@Autowired
	private AkaDB akaDB;
	@Autowired
	private MovieNamesDB movieNamesDB;

	public Movie retrieve(String movieForQuery) {

		List<Review> reviews = null;
		try {
			int movieId = akaDB.getMovieID(processMovieName(movieForQuery).hashCode());
			reviews = movieDB.readReviews(movieId);
			return new Movie(movieForQuery, reviews);
		} catch (DBAccessException e) {
			reviews = new ArrayList<Review>();
			return new Movie(movieForQuery, reviews);
		}
	}

	public boolean hasUserAlreadyReviewed(String userName, String movieName) throws MovieReviewException {

		movieName = processMovieName(movieName);
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

	public void update(String movieName, String movieTitle, int rating, String reviewStatement, String userName)
			throws MovieReviewException {
		movieName = processMovieName(movieName);
		movieTitle = processMovieName(movieTitle);
		reviewStatement = reviewStatement.replaceAll("'", "''");
		if (isNewMovie(movieName.hashCode())) {
			akaDB.insert(movieName.hashCode(), movieName.hashCode());
			movieNamesDB.addMovieName(movieName);
		}
		if (isNewAka(movieTitle.hashCode())) {
			akaDB.insert(movieTitle.hashCode(), movieName.hashCode());
		}
		if (hasUserAlreadyReviewed(userName, movieName)) {
			movieDB.update(movieName.hashCode(), userName, rating, reviewStatement);
		} else {
			movieDB.insert(movieName.hashCode(), userName, rating, reviewStatement);
		}
	}

	private boolean isNewAka(int akaId) {
		try {
			akaDB.getMovieID(akaId);
		} catch (DBAccessException e) {
			return true;
		}
		return false;
	}

	private boolean isNewMovie(int movieId) {
		try {
			akaDB.getMovieID(movieId);
		} catch (DBAccessException e) {
			return true;
		}
		return false;
	}

	private String processMovieName(String movieName) {
		return movieName.trim().replaceAll(" +", " ").toLowerCase();
	}

	public List<String> getMovies(String query) {
		List<Map<String, Object>> results = movieNamesDB.getMovieNames(query);
		List<String> movieNames = new ArrayList<String>();
		movieNames.add(query);
		for (Map<String, Object> mapElement : results) {
			movieNames.add((String) mapElement.get("movieName"));
		}
		return movieNames;
	}
}
