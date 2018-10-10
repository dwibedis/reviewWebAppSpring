package com.review.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.review.constants.MovieReviewConstants;
import com.review.model.Model;
import com.review.model.Movie;
import com.review.model.Review;

/**
 * The Repository class interacting with movie_review_db table.
 * 
 * @author satyad
 *
 */
@Repository
public class MovieReviewDBImpl implements MovieReviewDB {

	private static final String BEAN_CONFIG_FILE = "spring.xml";
	private static final String DATASOURCE_BEAN_NAME = "dataSource";
	private static final String USER_NAME = "sourceName";
	private static final String RATING = "rating";
	private static final String REVIEW_STATEMENT = "reviewStatement";

	private static DataSource dataSource;
	private static JdbcTemplate jdbcTemplate;

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(DataSource dataSource) {
		MovieReviewDBImpl.dataSource = dataSource;
	}

	static {
		ApplicationContext context = new ClassPathXmlApplicationContext(BEAN_CONFIG_FILE);
		dataSource = (DataSource) context.getBean(DATASOURCE_BEAN_NAME);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Review> readReviews(int id) {
		String query = String.format(MovieReviewConstants.SELECTION_QUERY_WITH_MOVIE_ID, id);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
		List<Review> reviews = new ArrayList<Review>();

		for (Map<String, Object> row : rows) {
			reviews.add(new Review((String) row.get(USER_NAME), (Integer) row.get(RATING),
					(String) row.get(REVIEW_STATEMENT)));
		}

		return reviews;
	}

	public int readCount(String userName, int id) {
		String query = String.format(MovieReviewConstants.QUERY_LIST_COUNT_REVIEWER_BY_NAME_AND_MOVIE, id, userName);
		return jdbcTemplate.queryForInt(query);
	}

	public void update(int id, String userName, int rating, String reviewStatement) {
		String query = String.format(MovieReviewConstants.UPDATE_QUERY_WITH_ALL_VALUES, rating, reviewStatement, id,
				userName);
		jdbcTemplate.update(query);
	}

	public void create(int id, String userName, int rating, String reviewStatement) {
		String query = String.format(MovieReviewConstants.INSERT_QUERY_WITH_ALL_VALUES, id, userName, rating,
				reviewStatement);
		jdbcTemplate.update(query);
	}

	public void delete(int id, String userName) {
		String query = String.format(MovieReviewConstants.DELETE_ROW, id, userName);
		jdbcTemplate.update(query);
	}
}
