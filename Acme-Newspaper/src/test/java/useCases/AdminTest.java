/*
 * SampleTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package useCases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Administrator;
import services.AdministratorService;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdminTest extends AbstractTest {

	@Autowired
	private AdministratorService administratorService;

	@Test
	public void administratorTest() {

		final Object testingData[][] = {
				// test login succes and fail
				{ "admin", null }, { "adminNotRegister", IllegalArgumentException.class },
				//test only the admin can display the dashboard
				{ "admin", null }, { "adminNotRegister", IllegalArgumentException.class } };

		for (int i = 0; i < 2; i++)
			this.loginAdministratorTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

		for (int i = 2; i < 4; i++)
			this.displayDashBoard((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void loginAdministratorTemplate(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
//only test two data, all functions have loggin check
	public void displayDashBoard(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);
			administratorService.averageArticlesPerNewspaper();
			administratorService.AverageRatioPrivateVsPublicNewspaperPerPublisher();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
