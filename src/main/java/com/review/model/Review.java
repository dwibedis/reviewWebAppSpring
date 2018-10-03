package com.review.model;

import lombok.NonNull;

/**
 * 
 * @author satyad
 *
 */
public class Review {
	private String sourceName;
	private int ratingInStars;
	private String reviewStatement;

	public Review() {
		this.sourceName = null;
		this.ratingInStars = 0;
		this.reviewStatement = null;
	}

	public Review(@NonNull String source, int rating, @NonNull String review) {
		this.sourceName = source;
		this.ratingInStars = rating;
		this.reviewStatement = review;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public void setRatingInStars(int ratingInStars) {
		this.ratingInStars = ratingInStars;
	}

	public void setReviewStatement(String reviewStatement) {
		this.reviewStatement = reviewStatement;
	}

	public String getSourceName() {
		return sourceName;
	}

	public int getRatingInStars() {
		return ratingInStars;
	}

	public String getReviewStatement() {
		return reviewStatement;
	}

}
