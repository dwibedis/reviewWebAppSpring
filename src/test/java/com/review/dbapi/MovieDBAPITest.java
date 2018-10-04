package com.review.dbapi;

import java.util.List;

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

import com.review.model.Review;

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
	public static void setUp() {
		movieDBAPI.insert(TEST_MOVIE_NAME.hashCode(), TEST_USER, TEST_RATING, TEST_REVIEW_STATEMENT);
	}

	@AfterClass
	public static void tearDown() {
		movieDBAPI.delete(TEST_MOVIE_NAME.hashCode(), TEST_USER);
	}

	@Test
	public final void testReadInt() {
		List<Review> reviews = movieDBAPI.readReviews(TEST_MOVIE_NAME.hashCode());
		Assert.assertEquals(1, reviews.size());
		Assert.assertEquals(TEST_RATING, reviews.get(0).getRatingInStars());
		Assert.assertEquals(TEST_USER, reviews.get(0).getSourceName());
		Assert.assertEquals(TEST_REVIEW_STATEMENT, reviews.get(0).getReviewStatement());
	}

	@Test
	public final void testReadCount() {
		int count = movieDBAPI.readCount(TEST_USER, TEST_MOVIE_NAME.hashCode());
		Assert.assertEquals(1, count);
	}

	@Test
	public final void testUpdate() {
		movieDBAPI.update(TEST_MOVIE_NAME.hashCode(), TEST_USER, 3, "changed Review Statement");
		List<Review> reviews = movieDBAPI.readReviews(TEST_MOVIE_NAME.hashCode());
		Assert.assertEquals(1, reviews.size());
		Assert.assertEquals(3, reviews.get(0).getRatingInStars());
		Assert.assertEquals("changed Review Statement", reviews.get(0).getReviewStatement());
	}

	@Test
	public final void testInsert() {
		movieDBAPI.insert("insertMovieTest".hashCode(), "testUserInsert", 2, "test Review Statement For InsertTest");
		List<Review> reviews = movieDBAPI.readReviews("insertMovieTest".hashCode());
		Assert.assertEquals("testUserInsert", reviews.get(0).getSourceName());
		Assert.assertEquals(2, reviews.get(0).getRatingInStars());
		Assert.assertEquals("test Review Statement For InsertTest", reviews.get(0).getReviewStatement());
		movieDBAPI.delete("insertMovieTest".hashCode(), "testUserInsert");
	}

	@Test
	public final void testDelete() {
		movieDBAPI.insert("deleteMovieTest".hashCode(), "testUserDelete", 2, "test Review Statement for Delete");
		movieDBAPI.delete("deleteMovieTest".hashCode(), "testUserDelete");
		List<Review> reviews = movieDBAPI.readReviews("insertMovieTest".hashCode());
		Assert.assertEquals(0, reviews.size());
	}
}
