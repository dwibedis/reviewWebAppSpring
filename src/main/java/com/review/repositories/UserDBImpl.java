package com.review.repositories;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDBImpl implements UserDB {

	private static DataSource userDataSource;
	private static JdbcTemplate jdbcTemplate;

	public static DataSource getDataSource() {
		return userDataSource;
	}

	public static void setDataSource(DataSource dataSource) {
		UserDBImpl.userDataSource = dataSource;
	}

	static {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		userDataSource = (DataSource) context.getBean("userDataSource");
		jdbcTemplate = new JdbcTemplate(userDataSource);
	}

	public boolean validate(String userId, String password) {
		String userQuery = String.format("select user_password from userInfo where userId = '%s'", userId);
		try {
			if (jdbcTemplate.queryForObject(userQuery, String.class).equals(password)) {
				return true;
			} else {
				return false;
			}
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}

}
