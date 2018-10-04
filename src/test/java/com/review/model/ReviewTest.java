package com.review.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReviewTest {

	private Review review;

	@Before
	public void setUp() throws Exception {
		review = new Review();
	}

	@Test
	public final void testReview() {
		Assert.assertNull(review.getSourceName());
		Assert.assertNull(review.getReviewStatement());
		Assert.assertEquals(0, review.getRatingInStars());
	}

	@Test
	public final void testReviewWithArguments() {
		Review reviewTest = new Review("testSource", 3, "test review statement");
		Assert.assertTrue(reviewTest instanceof Review );
		Assert.assertEquals("testSource", reviewTest.getSourceName());
		Assert.assertEquals(3, reviewTest.getRatingInStars());
		Assert.assertEquals("test review statement", reviewTest.getReviewStatement());
	}

	@Test
	public final void testSetSourceName() {
		review.setSourceName("testSource");
		Assert.assertEquals("testSource", review.getSourceName());
	}

	@Test
	public final void testSetRatingInStars() {
		review.setRatingInStars(5);
		Assert.assertEquals(5, review.getRatingInStars());
	}

	@Test
	public final void testSetReviewStatement() {
		review.setReviewStatement("test Review Statement");
		Assert.assertEquals("test Review Statement", review.getReviewStatement());
	}

}
