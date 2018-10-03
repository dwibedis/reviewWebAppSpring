package com.review.dbapi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.review.exception.MovieReviewException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = com.review.dbapi.MovieDBAPI.class)
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
@WebAppConfiguration
public class MovieDBAPITest {

	private static final String TEST_MOVIE_NAME = "testMovieName";
	private static final String TEST_USER = "testUser";
	private static final int TEST_RATING = 4;
	private static final String TEST_REVIEW_STATEMENT = "test Review Statement";

	private static MovieDBAPI movieDBAPI = new MovieDBAPI();

	@BeforeClass
	public static void setUp() throws Exception {
		movieDBAPI.insert(TEST_MOVIE_NAME.hashCode(), TEST_USER, TEST_RATING, TEST_REVIEW_STATEMENT);
	}

	@AfterClass
	public static void tearDown() throws MovieReviewException {
		movieDBAPI.delete(TEST_MOVIE_NAME.hashCode(), TEST_USER);
	}

	@Test
	public final void testReadInt() throws SQLException {
		ResultSet result = movieDBAPI.read(TEST_MOVIE_NAME.hashCode());
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.next());
		Assert.assertEquals(TEST_USER, result.getString(2));
		Assert.assertEquals(TEST_RATING, result.getInt(3));
		Assert.assertEquals(TEST_REVIEW_STATEMENT, result.getString(4));
	}

	@Test
	public final void testReadCount() throws SQLException {
		ResultSet result = movieDBAPI.readCount(TEST_USER, TEST_MOVIE_NAME.hashCode());
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.next());
		Assert.assertEquals(1, result.getInt(1));
	}

	@Test
	public final void testUpdate() throws SQLException {
		movieDBAPI.update(TEST_MOVIE_NAME.hashCode(), TEST_USER, 3, "changed Review Statement");
		ResultSet result = movieDBAPI.read(TEST_MOVIE_NAME.hashCode());
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.next());
		Assert.assertEquals(3, result.getInt(3));
		Assert.assertEquals("changed Review Statement", result.getString(4));
	}

	@Test
	public final void testInsert() throws SQLException, MovieReviewException {
		movieDBAPI.insert("insertMovieTest".hashCode(), "testUserInsert", 2, "test Review Statement For InsertTest");
		ResultSet result = movieDBAPI.read("insertMovieTest".hashCode());
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.next());
		Assert.assertEquals("testUserInsert", result.getString(2));
		Assert.assertEquals(2, result.getInt(3));
		Assert.assertEquals("test Review Statement For InsertTest", result.getString(4));
		movieDBAPI.delete("insertMovieTest".hashCode(), "testUserInsert");
	}

}
