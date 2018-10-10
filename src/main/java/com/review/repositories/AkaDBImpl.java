package com.review.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.sql.RowSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.review.exception.DBAccessException;
import com.review.exception.MovieReviewException;

/**
 * Deals with aka_db.
 * @author satyad
 *
 */
@Repository
public class AkaDBImpl implements AkaDB {

	private static DataSource dataSource;
	private static JdbcTemplate jdbcTemplate;

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(DataSource dataSource) {
		AkaDBImpl.dataSource = dataSource;
	}

	static {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		dataSource = (DataSource) context.getBean("dataSource");
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int getMovieID(int id) {
		String query = String.format("select movieid from aka_db where aka_title_id = %d", id);
		try {
			return jdbcTemplate.queryForInt(query);
		} catch (EmptyResultDataAccessException d) {
			return 0;
		}
	}

	public void insert(int akaId, int movieId) throws MovieReviewException {
		String query = String.format("insert into aka_db values (%d, %d)", akaId, movieId);
		try {
			jdbcTemplate.update(query);
		} catch (DataAccessException d) {
			throw new MovieReviewException("akaID : " + akaId + "is already mapped to some other movie name");
		}
	}

	public void delete(int akaId) throws MovieReviewException {
		String query = String.format("delet from aka_db where aka_title_id = %d", akaId);
		try {
			jdbcTemplate.update(query);
		} catch (DataAccessException d) {
			throw new MovieReviewException("akaId : " + "does not exist and yet you are trying to delete it");
		}
	}

}
