package com.review.utility;

import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.review.exception.MovieReviewException;
import com.review.model.Movie;
import com.review.model.Review;
import com.review.repositories.MovieReviewDB;
import com.review.service.MovieDBAPIImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { com.review.service.MovieDBAPIImpl.class, com.review.repositories.MovieReviewDBImpl.class })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
@WebAppConfiguration
public class MovieDBHelperImplTest {

	private static final String TEST_MOVIE_NAME = "testMovieName";
	private static final String TEST_REVIEW_STATEMENT = "test Review Statement";
	private static final String TEST_USER = "testUser";
	private static final int TEST_RATING = 4;

	@Mock
	private MovieReviewDB movieDB;

	@Mock
	private Review review;

	@InjectMocks
	@Autowired
	private MovieDBAPIImpl movieDBAPIHelper;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(movieDB.readReviews(TEST_MOVIE_NAME.hashCode())).thenReturn(Arrays.asList(review));
		Mockito.when(movieDB.readCount(TEST_USER, TEST_MOVIE_NAME.hashCode())).thenReturn(1);
	}

	@Test
	public final void testRetrieveMovieWithReviews() {
		Movie movie = movieDBAPIHelper.retrieve(TEST_MOVIE_NAME);

		Assert.assertEquals(TEST_MOVIE_NAME, movie.getMovieName());
		Assert.assertEquals(1, movie.getReviews().size());
	}

	@Test
	public final void testRetrieveMovieNoReviews() {
		Movie movie = movieDBAPIHelper.retrieve("random");

		Assert.assertEquals("random", movie.getMovieName());
		Assert.assertEquals(0, movie.getReviews().size());
	}

	@Test
	public final void testHasUserAlreadyReviewedTrue() throws MovieReviewException {
		Assert.assertTrue(movieDBAPIHelper.hasUserAlreadyReviewed(TEST_USER, TEST_MOVIE_NAME));
	}

	@Test
	public final void testHasUserAlreadyReviewedFalse() throws MovieReviewException {
		Mockito.when(movieDB.readCount("randomUser", TEST_MOVIE_NAME.hashCode())).thenReturn(0);

		Assert.assertFalse(movieDBAPIHelper.hasUserAlreadyReviewed("randomUser", TEST_MOVIE_NAME));
	}

	@Test
	public final void testHasUserAlreadyReviewedException() throws MovieReviewException {
		exception.expect(MovieReviewException.class);
		exception.expectMessage(
				"DB Error - user:randomUser has " + 2 + "reviews for movieID " + TEST_MOVIE_NAME.hashCode());

		Mockito.when(movieDB.readCount("randomUser", TEST_MOVIE_NAME.hashCode())).thenReturn(2);

		movieDBAPIHelper.hasUserAlreadyReviewed("randomUser", TEST_MOVIE_NAME);
	}

	@Test
	public final void testUpdateInsert() throws MovieReviewException {

		Mockito.when(movieDB.readCount("randomUser", TEST_MOVIE_NAME.hashCode())).thenReturn(0);

		movieDBAPIHelper.update(TEST_MOVIE_NAME, TEST_RATING, TEST_REVIEW_STATEMENT, "randomUser");
		Mockito.verify(movieDB, Mockito.times(1)).create(TEST_MOVIE_NAME.hashCode(), "randomUser", TEST_RATING,
				TEST_REVIEW_STATEMENT);
		Mockito.verify(movieDB, Mockito.times(0)).update(TEST_MOVIE_NAME.hashCode(), "randomUser", TEST_RATING,
				TEST_REVIEW_STATEMENT);
	}

	@Test
	public final void testUpdateUpdate() throws SQLException, MovieReviewException {

		movieDBAPIHelper.update(TEST_MOVIE_NAME, 4, TEST_REVIEW_STATEMENT, TEST_USER);
		Mockito.verify(movieDB, Mockito.times(0)).create(TEST_MOVIE_NAME.hashCode(), TEST_USER, TEST_RATING,
				TEST_REVIEW_STATEMENT);
		Mockito.verify(movieDB, Mockito.times(1)).update(TEST_MOVIE_NAME.hashCode(), TEST_USER, TEST_RATING,
				TEST_REVIEW_STATEMENT);
	}
}
