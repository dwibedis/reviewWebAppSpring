package com.review.model;

import java.util.List;

import lombok.NonNull;

/**
 * 
 * @author satyad
 *
 */
public class Movie {
	private String movieName;
	private List<Review> reviews ;

	public Movie() {
		this.movieName = null;
		this.reviews = null;
	}

	public Movie(@NonNull String name,@NonNull List<Review> reviewsArg) {
		this.movieName = name;
		this.reviews = reviewsArg;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public String getMovieName() {
		return movieName;
	}
	
	public List<Review> getReviews() {
		return reviews;
	}
}
