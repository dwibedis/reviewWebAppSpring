package com.review.repositories;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Delas with valid_movie_names_db.
 * @author satyad
 *
 */
@Repository
public class MovieNamesDBImpl implements MovieNamesDB {

	private static final String DATASOURCE_BEAN_NAME = "dataSource";
	private static final String BEAN_CONFIG_FILE = "spring.xml";

	private static DataSource dataSource;
	private static JdbcTemplate jdbcTemplate;

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(DataSource dataSource) {
		MovieNamesDBImpl.dataSource = dataSource;
	}

	static {
		ApplicationContext context = new ClassPathXmlApplicationContext(BEAN_CONFIG_FILE);
		dataSource = (DataSource) context.getBean(DATASOURCE_BEAN_NAME);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Map<String, Object>> getMovieNames(String query) {
		String queryStatement = String.format("SELECT movieName FROM valid_movie_names_db WHERE movieName LIKE '%s%%'",
				query);
		return jdbcTemplate.queryForList(queryStatement);
	}

	public void insert(String movieName) {
		String query = String.format("Insert into valid_movie_names_db values ('%s')", movieName.replaceAll("'", "''"));
		jdbcTemplate.update(query);
	}

}
