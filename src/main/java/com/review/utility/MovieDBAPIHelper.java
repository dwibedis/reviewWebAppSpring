package com.review.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.review.dbapi.MovieDB;
import com.review.exception.MovieReviewException;
import com.review.model.Movie;
import com.review.model.Review;

@Service
public class MovieDBAPIHelper implements MovieDBHelper {
	private static final String SOURCE_NAME = "sourceName";
	private static final String RATING = "rating";
	private static final String REVIEW = "reviewStatement";

	@Autowired
	private MovieDB movieDB;

	public Movie retrieve(String movieName) throws MovieReviewException {
		ResultSet resultSet = movieDB.read(movieName.hashCode());
		List<Review> reviews = new ArrayList<Review>();
		try {
			if (!resultSet.wasNull()) {
				while (resultSet.next()) {
					reviews.add(new Review(resultSet.getString(SOURCE_NAME), resultSet.getInt(RATING),
							resultSet.getString(REVIEW)));
				}
			}
		} catch (SQLException e) {
			throw new MovieReviewException("Error while reading ResultSet");
		}
		return new Movie(movieName, reviews);
	}

	public boolean hasUserAlreadyReviewed(String userName, String movieName) {
		ResultSet resultSet = movieDB.readCount(userName, movieName.hashCode());
		try {
			if (resultSet.next()) {
				// TODO handle if some how a user reviewed more than once.
				if (resultSet.getInt(1) == 1) {
					return true;
				}
			}
		} catch (SQLException e) {
			new MovieReviewException("Error while reading ResultSet");
		}
		return false;
	}

	public void update(String movieName, int rating, String reviewStatement, String userName) {
		if (hasUserAlreadyReviewed(userName, movieName)) {
			movieDB.update(movieName.hashCode(), userName, rating, reviewStatement);
		} else {
			movieDB.insert(movieName.hashCode(), userName, rating, reviewStatement);
		}
	}

}
