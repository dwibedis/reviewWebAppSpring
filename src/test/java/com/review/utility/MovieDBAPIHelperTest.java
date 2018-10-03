package com.review.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

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

import com.review.dbapi.MovieDB;
import com.review.model.Movie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { com.review.utility.MovieDBAPIHelper.class, com.review.dbapi.MovieDBAPI.class })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
@WebAppConfiguration
public class MovieDBAPIHelperTest {

	private static final String TEST_MOVIE_NAME = "testMovieName";
	private static final String TEST_SOURCE_NAME = "testSource";
	private static final String TEST_REVIEW_STATEMENT = "test Review Statement";
	private static final String TEST_USER = "testUser";
	private static final int TEST_RATING = 4;

	@Mock
	private MovieDB movieDB;

	@Mock
	private ResultSet result;

	@InjectMocks
	@Autowired
	private MovieDBAPIHelper movieDBAPIHelper;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(movieDB.read(TEST_MOVIE_NAME.hashCode())).thenReturn(result);
		Mockito.when(movieDB.readCount(TEST_USER, TEST_MOVIE_NAME.hashCode())).thenReturn(result);
	}

	@Test
	public final void testRetrieveMovieWithZeroReviews() throws Exception {
		Mockito.when(result.wasNull()).thenReturn(true);

		Movie movie = movieDBAPIHelper.retrieve(TEST_MOVIE_NAME);
		Assert.assertEquals(TEST_MOVIE_NAME, movie.getMovieName());
		Assert.assertEquals(0, movie.getReviews().size());
	}

	@Test
	public final void testRetrieveMovieWithNonZeroReviews() throws Exception {
		Mockito.when(result.wasNull()).thenReturn(false);
		Mockito.when(result.next()).thenReturn(true).thenReturn(false);
		Mockito.when(result.getString(2)).thenReturn(TEST_SOURCE_NAME);
		Mockito.when(result.getInt(3)).thenReturn(TEST_RATING);
		Mockito.when(result.getString(4)).thenReturn(TEST_REVIEW_STATEMENT);

		Movie movie = movieDBAPIHelper.retrieve(TEST_MOVIE_NAME);
		Assert.assertEquals(TEST_MOVIE_NAME, movie.getMovieName());
		Assert.assertEquals(1, movie.getReviews().size());
		Assert.assertEquals(TEST_SOURCE_NAME, movie.getReviews().get(0).getSourceName());
		Assert.assertEquals(TEST_REVIEW_STATEMENT, movie.getReviews().get(0).getReviewStatement());
		Assert.assertEquals(TEST_RATING, movie.getReviews().get(0).getRatingInStars());
	}
//TODO test case for movie name not found.

	@Test
	public final void testHasUserAlreadyReviewedTrue() throws SQLException {
		Mockito.when(result.next()).thenReturn(true);
		Mockito.when(result.getInt(1)).thenReturn(1);

		boolean reviewStatus = movieDBAPIHelper.hasUserAlreadyReviewed(TEST_USER, TEST_MOVIE_NAME);
		Assert.assertTrue(reviewStatus);
	}

	@Test
	public final void testHasUserAlreadyReviewedFalse() throws SQLException {
		Mockito.when(result.next()).thenReturn(false);

		Assert.assertFalse(movieDBAPIHelper.hasUserAlreadyReviewed(TEST_USER, TEST_MOVIE_NAME));
	}

	@Test
	public final void testUpdateInsert() throws SQLException {
		Mockito.when(result.next()).thenReturn(false);

		movieDBAPIHelper.update(TEST_MOVIE_NAME, TEST_RATING, TEST_REVIEW_STATEMENT, TEST_USER);
		Mockito.verify(movieDB, Mockito.times(1)).insert(TEST_MOVIE_NAME.hashCode(), TEST_USER, TEST_RATING,
				TEST_REVIEW_STATEMENT);
		Mockito.verify(movieDB, Mockito.times(0)).update(TEST_MOVIE_NAME.hashCode(), TEST_USER, TEST_RATING,
				TEST_REVIEW_STATEMENT);
	}

	@Test
	public final void testUpdateUpdate() throws SQLException {
		Mockito.when(result.next()).thenReturn(true);
		Mockito.when(result.getInt(1)).thenReturn(1);

		movieDBAPIHelper.update(TEST_MOVIE_NAME, 4, TEST_REVIEW_STATEMENT, TEST_USER);
		Mockito.verify(movieDB, Mockito.times(0)).insert(TEST_MOVIE_NAME.hashCode(), TEST_USER, TEST_RATING,
				TEST_REVIEW_STATEMENT);
		Mockito.verify(movieDB, Mockito.times(1)).update(TEST_MOVIE_NAME.hashCode(), TEST_USER, TEST_RATING,
				TEST_REVIEW_STATEMENT);
	}
}
