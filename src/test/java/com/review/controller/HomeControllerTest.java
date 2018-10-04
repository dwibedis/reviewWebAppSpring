package com.review.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.review.constants.MovieReviewConstants;
import com.review.dbapi.MovieDB;
import com.review.dbapi.MovieDBAPI;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { com.review.utility.MovieDBAPIHelper.class, com.review.dbapi.MovieDBAPI.class,
		com.review.controller.HomeController.class })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
@WebAppConfiguration
public class HomeControllerTest {

	private static final String HOME_URL = "/";
	private static final String SEARCH_URL = "/search";
	private static final String SUBMIT_URL = "/submit";
	// the url is designed for failing purpose and in case of introduction of new
	// url with following name the url has to be changed.
	private static final String UNDEFINED_URL = "/undefined";
	private static final String VIEW_NAME = "index";
	private static final String TEST_QUERY = "testQuery";
	private static final String TEST_MOVIE_NAME = "testMovieName";
	private static final String TEST_RATING = "5";
	private static final String TEST_REVIEW_STATEMENT = "testReviewStatement";

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMVC;

	@Before
	public void setUp() throws Exception {
		mockMVC = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@AfterClass
	public static void tearDown() {
		new MovieDBAPI().delete(TEST_MOVIE_NAME.hashCode(), MovieReviewConstants.USER_NAME);
	}

	@Test
	public final void testContollerNotDefined() throws Exception {
		mockMVC.perform(get(UNDEFINED_URL)).andExpect(status().is(404));
	}

	@Test
	public final void testWelcome() throws Exception {
		mockMVC.perform(get(HOME_URL)).andExpect(status().isOk()).andExpect(view().name(VIEW_NAME));
	}

	@Test
	public final void testSearch() throws Exception {
		mockMVC.perform(get(SEARCH_URL).param(MovieReviewConstants.SEARCH_QUERY, TEST_QUERY))
				.andExpect(status().isOk()).andExpect(model().attributeExists(MovieReviewConstants.MOVIE_ATTRIBUTE))
				.andExpect(model().attributeExists(MovieReviewConstants.IS_NEW_REVIEWER_ATTRIBUTE))
				.andExpect(model().attributeExists(MovieReviewConstants.USER_NAME_ATTRIBUTE))
				.andExpect(view().name(VIEW_NAME));
	}

	@Test
	public final void testSubmitOldReviewer() throws Exception {
		mockMVC.perform(get(SUBMIT_URL).param(MovieReviewConstants.MOVIE_NAME_ATTRIBUTE, TEST_MOVIE_NAME)
				.param(MovieReviewConstants.RATING_ATTRIBUTE, TEST_RATING)
				.param(MovieReviewConstants.REVIEW_STATEMENT_ATTRIBUTE, TEST_REVIEW_STATEMENT)
				.param(MovieReviewConstants.IS_NEW_REVIEWER_ATTRIBUTE, "false")).andExpect(status().isOk())
				.andExpect(model().attributeExists(MovieReviewConstants.ALERT_ATTRIBUTE))
				.andExpect(view().name(VIEW_NAME));
	}

	@Test
	public final void testSubmitNewReviewer() throws Exception {
		mockMVC.perform(get(SUBMIT_URL).param(MovieReviewConstants.MOVIE_NAME_ATTRIBUTE, TEST_MOVIE_NAME)
				.param(MovieReviewConstants.RATING_ATTRIBUTE, TEST_RATING)
				.param(MovieReviewConstants.REVIEW_STATEMENT_ATTRIBUTE, TEST_REVIEW_STATEMENT)
				.param(MovieReviewConstants.IS_NEW_REVIEWER_ATTRIBUTE, "true")).andExpect(status().isOk())
				.andExpect(model().attributeDoesNotExist(MovieReviewConstants.ALERT_ATTRIBUTE))
				.andExpect(view().name(VIEW_NAME));

	}
}
