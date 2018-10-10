package com.review.model;

public class AKATitle implements Model {
	private String alternateTitle;
	private String movieName;

	public String getAkaTitle() {
		return alternateTitle;
	}

	public void setAkaTitle(String akaTitle) {
		this.alternateTitle = akaTitle;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
}
