package com.review.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
		mockMVC.perform(get("/undefinedController")).andExpect(status().is(404));
	}

	@Test
	public final void testWelcome() throws Exception {
		mockMVC.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	public final void testSearch() throws Exception {
		mockMVC.perform(get("/search").param("searchQuery", "testQuery")).andExpect(status().isOk());
	}

	@Test
	public final void testSubmit() throws Exception {
		mockMVC.perform(get("/submit").param("movieName", "testMovieName").param("rating", "5")
				.param("reviewStatement", "testReviewStatement").param("isNewReviewer", "true"))
				.andExpect(status().isOk());
	}

}
