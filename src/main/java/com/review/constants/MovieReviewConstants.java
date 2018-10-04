package com.review.constants;

/**
 * The class containing all the constants.
 * 
 * @author satyad
 *
 */
public class MovieReviewConstants {
	// constant variables
	public static final String SEARCH_QUERY = "searchQuery";
	public static final String INDEX = "/index.jsp";
	public static final String MOVIE_ATTRIBUTE = "movie";
	public static final String MOVIE_NAME_ATTRIBUTE = "movieName";
	public static final String RATING_ATTRIBUTE = "rating";
	public static final String REVIEW_STATEMENT_ATTRIBUTE = "reviewStatement";
	public static final String USER_NAME = System.getProperty("user.name");
	public static final String USER_NAME_ATTRIBUTE = "userName";
	public static final String HOME = "/reviews";

	// query statements
	public static final String SELECTION_QUERY_WITH_MOVIE_ID = "Select * from movie_review_db where movieID = %d order by rating desc";
	public static final String INSERT_QUERY_WITH_ALL_VALUES = "Insert into movie_review_db values (%d, '%s', %d, '%s') ";
	public static final String QUERY_LIST_COUNT_REVIEWER_BY_NAME_AND_MOVIE = "Select count(sourceName) from movie_review_db where movieID = %d and sourceName = '%s'";
	public static final String UPDATE_QUERY_WITH_ALL_VALUES = "Update movie_review_db SET rating = %d, reviewStatement = '%s' WHERE movieID = %d and sourceName = '%s'";
	public static final String DELETE_ROW = "DELETE from movie_review_db where movieID = %d and sourceName = '%s'";

	// regular expressions
	public static final String SPACE = "\\s+";

	private MovieReviewConstants() {
		throw new IllegalStateException("Invalid Declaration Of Constants Class");
	}
}
