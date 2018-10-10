package com.review.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.review.constants.MovieReviewConstants;
import com.review.exception.DBAccessException;
import com.review.exception.MovieReviewException;
import com.review.exception.OMDBException;
import com.review.model.Movie;
import com.review.model.Review;
import com.review.repositories.AkaDB;
import com.review.repositories.MovieReviewDB;
import com.review.repositories.MovieNamesDB;

/**
 * The service layer providing utility methods.
 * 
 * @author satyad
 *
 */
@Service
public class MovieDBAPIImpl implements MovieDBAPI {

	private static final int MOVIE_NOT_FOUND = 0;

	private static final String RESPONSE = "Response";
	private static final String TITLE = "Title";
	private static final String IMDB_RATING = "imdbRating";
	private static final String PLOT = "Plot";
	private static final String MOVIE_NAME = "movieName";
	private static final String IMDB = "imdb";
	private static final String TRUE = "True";

	@Autowired
	private MovieReviewDB movieDB;
	@Autowired
	private AkaDB akaDB;
	@Autowired
	private OMDBHelper omdbAPIHelper;
	@Autowired
	private MovieNamesDB movieNamesDB;

	public Movie retrieve(String movieForQuery) throws MovieReviewException {

		movieForQuery = preProcess(movieForQuery);
		int movieId = akaDB.getMovieID(movieForQuery.hashCode());
		if (movieId == 0) {
			movieId = fetchAndUpdate(movieForQuery);
		}
		return new Movie(movieForQuery, movieDB.readReviews(movieId));
	}

	public boolean hasUserAlreadyReviewed(String userName, String movieName) throws MovieReviewException {

		int reviewCount = movieDB.readCount(userName, preProcess(movieName).hashCode());
		switch (reviewCount) {
		case 0:
			return false;
		case 1:
			return true;
		default:
			throw new MovieReviewException(
					"DB Error - user:" + userName + " has " + reviewCount + "reviews for movieID " + movieName);
		}
	}

	public void update(String movieName, String movieTitle, int rating, String reviewStatement, String userName)
			throws MovieReviewException {

		movieName = preProcess(movieName);
		movieTitle = preProcess(movieTitle);
		reviewStatement = preProcess(reviewStatement);

		boolean isNewMovie = (akaDB.getMovieID(movieName.hashCode()) == MOVIE_NOT_FOUND);
		boolean isNewAka = (akaDB.getMovieID(movieTitle.hashCode()) == MOVIE_NOT_FOUND);

		if (isNewAka) {
			akaDB.insert(movieTitle.hashCode(), movieName.hashCode());
			if (isNewMovie) {
				movieNamesDB.insert(movieName);
			}
			if (hasUserAlreadyReviewed(userName, movieName)) {
				movieDB.update(movieName.hashCode(), userName, rating, reviewStatement);
			} else {
				movieDB.create(movieName.hashCode(), userName, rating, reviewStatement);
			}
		} else {
			if (akaDB.getMovieID(movieTitle.hashCode()) == movieName.hashCode()) {
				if (hasUserAlreadyReviewed(userName, movieName)) {
					movieDB.update(movieName.hashCode(), userName, rating, reviewStatement);
				} else {
					movieDB.create(movieName.hashCode(), userName, rating, reviewStatement);
				}
			} else {
				throw new MovieReviewException("Error while trying to override a previously present data");
			}
		}
	}

	public List<String> getMovies(String query) {
		List<Map<String, Object>> results = movieNamesDB.getMovieNames(query);
		List<String> movieNames = new ArrayList<String>();
		movieNames.add(query);
		for (Map<String, Object> mapElement : results) {
			movieNames.add((String) mapElement.get(MOVIE_NAME));
		}
		return movieNames;
	}

	private String preProcess(String movieName) {
		return movieName.trim().replaceAll(MovieReviewConstants.MULTI_SPACE, MovieReviewConstants.SINGLE_SPACE)
				.replaceAll(MovieReviewConstants.SINGLE_QUOTE, MovieReviewConstants.DOUBLE_QUOTE).toLowerCase();
	}

	// Fetches if present and updates
	private int fetchAndUpdate(String movieForQuery) throws MovieReviewException {
		JSONObject json = omdbAPIHelper.fetchMovieDetails(movieForQuery);
		if (json.getString(RESPONSE).equals(TRUE)) {
			try {
				update(json.getString(TITLE), (String) json.get(TITLE),
						(int) Float.parseFloat(json.getString(IMDB_RATING)), json.getString(PLOT), IMDB);
			} catch (JSONException e) {

			} catch (NumberFormatException e) {

			}
			return preProcess(json.getString(TITLE)).hashCode();
		}
		return 0;
	}
}
