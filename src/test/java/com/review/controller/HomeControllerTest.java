package com.review.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

	private static final String SEARCH_QUERY_PARAMETER = "searchQuery";
	private static final String MOVIE_NAME_PARAMETER = "movieName";
	private static final String RATING_PARAMETER = "rating";
	private static final String REVIEW_STATEMENT_PARAMETER = "reviewStatement";
	private static final String IS_NEW_REVIEWER_PARAMETER = "isNewReviewer";
	private static final String MOVIE_PARAMETER = "movie";
	private static final String USER_NAME_PARAMETER = "userName";
	private static final String ALERT_STATUS_PARAMETER = "alertStatus";

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMVC;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mockMVC = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public final void testContollerNotDefined() throws Exception {
		mockMVC.perform(get(UNDEFINED_URL)).andExpect(status().is(404));
	}

	@Test
	public final void testWelcome() throws Exception {
		mockMVC.perform(get(HOME_URL)).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	@Test
	public final void testSearch() throws Exception {
		mockMVC.perform(get(SEARCH_URL).param(SEARCH_QUERY_PARAMETER, "testQuery")).andExpect(status().isOk())
				.andExpect(model().attributeExists(MOVIE_PARAMETER))
				.andExpect(model().attributeExists(IS_NEW_REVIEWER_PARAMETER))
				.andExpect(model().attributeExists(USER_NAME_PARAMETER)).andExpect(view().name("index"));
	}

	@Test
	public final void testSubmitOldReviewer() throws Exception {
		mockMVC.perform(get(SUBMIT_URL).param(MOVIE_NAME_PARAMETER, "testMovieName").param(RATING_PARAMETER, "5")
				.param(REVIEW_STATEMENT_PARAMETER, "testReviewStatement").param(IS_NEW_REVIEWER_PARAMETER, "false"))
				.andExpect(status().isOk()).andExpect(model().attributeExists(ALERT_STATUS_PARAMETER))
				.andExpect(view().name("index"));
	}

	@Test
	public final void testSubmitNewReviewer() throws Exception {
		mockMVC.perform(get(SUBMIT_URL).param(MOVIE_NAME_PARAMETER, "testMovieName").param(RATING_PARAMETER, "4")
				.param(REVIEW_STATEMENT_PARAMETER, "testReviewStatement").param(IS_NEW_REVIEWER_PARAMETER, "true"))
				.andExpect(status().isOk()).andExpect(model().attributeDoesNotExist(ALERT_STATUS_PARAMETER))
				.andExpect(view().name("index"));

	}
}
