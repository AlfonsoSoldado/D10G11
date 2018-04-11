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

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Configuration;
import services.ConfigurationService;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationTest extends AbstractTest {

	@Autowired
	private ConfigurationService configurationService;

	@Test
	public void administratorTest() {

		final Object testingData[][] = {
				//test admin edit configuration of taboo words
				{ "admin", "new taboo", null },
				//test user cant edit configuration of taboo words
				{ "user1", "newtaboo", IllegalArgumentException.class } };

		for (int i = 0; i < 2; i++)
			this.editConfiguration((String) testingData[i][0], (String) testingData[i][1],
					(Class<?>) testingData[i][2]);

	}

	// only test two data, all functions have loggin check
	public void editConfiguration(final String user, final String taboo, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);
			Configuration confing = this.configurationService.findOne(this.getEntityId("configuration"));
			ArrayList<String> tabooWords = new ArrayList<>();
			tabooWords.add(taboo);
			confing.setTabooWords(tabooWords);
			this.configurationService.save(confing);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
