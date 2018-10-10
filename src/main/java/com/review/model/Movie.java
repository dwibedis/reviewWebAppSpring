package com.review.model;

import java.util.List;

import lombok.NonNull;

/**
 * 
 * @author satyad
 *
 */
public class Movie implements Model {
	private String movieName;
	private List<Review> reviews;

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Movie(@NonNull String movieName, @NonNull List<Review> reviews) {
		this.movieName = movieName;
		this.reviews = reviews;
	}

	public Movie() {
		this.movieName = null;
		this.reviews = null;
	}
}
