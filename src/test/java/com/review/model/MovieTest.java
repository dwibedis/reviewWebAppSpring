package com.review.model;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MovieTest {

	private Movie movie;

	@Before
	public void setUp() throws Exception {
		movie = new Movie();
	}

	@After
	public void destroy() {
		movie = null;
	}

	@Test
	public final void testMovie() {
		Movie movieTest = new Movie();
		Assert.assertNull(movieTest.getMovieName());
		Assert.assertNull(movieTest.getReviews());
	}

	@Test(expected = NullPointerException.class)
	public final void testMovieStringListOfReviewNull() {
		new Movie(null, null);
	}

	@Test
	public final void testMovieStringListOfReview() {
		Movie movieTest = new Movie("testMovie", getSampleReviews());
		Assert.assertEquals("testMovie", movieTest.getMovieName());
		Assert.assertEquals(1, movieTest.getReviews().size());
		Assert.assertEquals("testSource", movieTest.getReviews().get(0).getSourceName());
		Assert.assertEquals(0, movieTest.getReviews().get(0).getRatingInStars());
		Assert.assertEquals("test Review Statement", movieTest.getReviews().get(0).getReviewStatement());
	}

	private List<Review> getSampleReviews() {
		return Arrays.asList(new Review("testSource", 0, "test Review Statement"));
	}

	@Test
	public final void testSetMovieName() {
		movie.setMovieName("testMovie");
		Assert.assertEquals("testMovie", movie.getMovieName());
	}

	@Test
	public final void testSetReviews() {
		movie.setReviews(getSampleReviews());
		Assert.assertEquals(1, movie.getReviews().size());
		Assert.assertEquals("testSource", movie.getReviews().get(0).getSourceName());
		Assert.assertEquals(0, movie.getReviews().get(0).getRatingInStars());
		Assert.assertEquals("test Review Statement", movie.getReviews().get(0).getReviewStatement());
	}

}
