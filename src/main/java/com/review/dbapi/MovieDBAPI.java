package com.review.dbapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.review.constants.MovieReviewConstants;
import com.review.exception.MovieReviewException;

/**
 * The Repository class interacting with DB.
 * 
 * @author satyad
 *
 */
@Repository
public class MovieDBAPI implements MovieDB {

	private static Connection connection;
	private static Statement statement;
	private static DataSource dataSource;

	private JdbcTemplate jdbcTemplate = new JdbcTemplate();

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(DataSource dataSource) {
		MovieDBAPI.dataSource = dataSource;
	}

	static {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		dataSource = (DataSource) context.getBean("dataSource");
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
		} catch (SQLException e) {
			new MovieReviewException("Error while setting up connection");
		}
	}

	public ResultSet read(int id) {
		ResultSet resultSet = null;
		try {
			String query = String.format(MovieReviewConstants.SELECTION_QUERY_WITH_MOVIE_ID, id);
			resultSet = statement.executeQuery(query);
		} catch (Exception ex) {
			new MovieReviewException(ex.getMessage());
		}

		return resultSet;
	}

	public ResultSet readCount(String userName, int id) {
		ResultSet resultSet = null;
		try {
			String query = String.format(MovieReviewConstants.QUERY_LIST_COUNT_REVIEWER_BY_NAME_AND_MOVIE, id, userName);
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			new MovieReviewException(e.getMessage());
		}
		return resultSet;
	}

	public void update(int id, String userName, int rating, String reviewStatement) {
		try {
			String query = String.format(MovieReviewConstants.UPDATE_QUERY_WITH_ALL_VALUES, rating, reviewStatement, id,
					userName);
			statement.executeQuery(query);
		} catch (SQLException ex) {
			new MovieReviewException(ex.getMessage());
		}
	}

	public void insert(int id, String userName, int rating, String reviewStatement) {
		try {
			String query = String.format(MovieReviewConstants.INSERT_QUERY_WITH_ALL_VALUES, id, userName, rating,
					reviewStatement);
			statement.executeQuery(query);
		} catch (SQLException ex) {
			new MovieReviewException(ex.getMessage());
		}
	}

	public void delete(int id, String sourceName) throws MovieReviewException {
		try {
			String query = String.format(MovieReviewConstants.DELETE_ROW, id, sourceName);
			statement.executeQuery(query);
		} catch (SQLException e) {
			throw new MovieReviewException("Row Doesn't Exist");
		}
	}

}
