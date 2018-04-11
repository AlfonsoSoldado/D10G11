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

import services.NewspaperService;
import utilities.AbstractTest;
import domain.Newspaper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class NewspaperTest extends AbstractTest {

	@Autowired
	private NewspaperService	newspaperService;


	// Test---------------------------------------------------------------
	@Test
	public void rendezvousTest() {
		final Object testingData[][] = {

			//An actor who is not authenticated must be able to: 
			//List the newspapers that are published

			//An actor who is authenticated must be able to:
			//Do the same as an actor who is not authenticated

			//An actor who is authenticated as a user must be able to:
			//1.Create a newspaper.
			//2.Publish a newspaper that he or she is created. Note that no newspaper can be published
			//until each of the articles of which it is composed is saved in final mode. 

			//An actor who is authenticated as an administrator must be able to:
			//Remove a newspaper that he or she thinks is inappropriate. Removing a newspaper
			//implies removing all of the articles of which it is composed. 

			{
				//Not authenticated actor list newspapers.
				null, null
			}, {
				//User list newspapers.
				"user1", null
			}, {
				//User not created list newspapers.
				"user3", IllegalArgumentException.class
			}, {
				//User creates newspaper.
				"user2", "title1", "description1", false, null
			}, {
				//User creates newspaper.
				"user1", "title2", "description2", false, null
			}, {
				//User creates newspaper.
				"user2", "title3", "description3", true, null
			}, {
				//User creates newspaper.
				"user1", "title4", "description4", true, null
			}, {
				//Not authenticated actor creates newpspaper.
				null, "title", "description", false, IllegalArgumentException.class
			}, {
				//User edit a newspaper that he or she has created.
				"user2", "edited title", "newspaper2", null
			}, {
				//User edit a newspaper that he or she has created.
				"user1", "edited title", "newspaper1", null
			}, {
				//User edit a newspaper that he or she has not created.
				"user1", "edited title", "newspaper2", IllegalArgumentException.class
			}, {
				//Not authenticated actor edit a newspaper.
				null, "edited title", "newspaper1", IllegalArgumentException.class
			}, {
				//Admin delete a newspaper
				"admin", "newspaper2", null
			}, {
				//User delete a newspaper that he or she has created.
				"user2", "newspaper2", IllegalArgumentException.class
			}, {
				//User delete a newspaper that he or she has not created.
				"user1", "newspaper2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < 3; i++)
			this.listTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

		for (int i = 3; i < 8; i++)
			this.createTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (boolean) testingData[i][3], (Class<?>) testingData[i][4]);

		for (int i = 8; i < 12; i++)
			this.editTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

		for (int i = 12; i < 15; i++)
			this.deleteTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void deleteTemplate(final String user, final String newspaper, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Delete Rendezvous-------------------
			this.authenticate(user);
			final int newspaperId = this.getEntityId(newspaper);
			final Newspaper newspaperFinded = this.newspaperService.findOne(newspaperId);
			this.newspaperService.delete(newspaperFinded);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void editTemplate(final String user, final String title, final String newspaper, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Edit Rendezvous-------------------
			this.authenticate(user);
			final int newspaperId = this.getEntityId(newspaper);
			final Newspaper newspaperFinded = this.newspaperService.findOne(newspaperId);
			newspaperFinded.setTitle(title);
			this.newspaperService.save(newspaperFinded);
			this.unauthenticate();
			this.newspaperService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void createTemplate(final String user, final String title, final String description, final boolean hide, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Create Rendezvous-------------------
			this.authenticate(user);
			final Newspaper newspaper = this.newspaperService.create();
			newspaper.setTitle(title);
			newspaper.setDescription(description);
			newspaper.setHide(hide);

			this.newspaperService.save(newspaper);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void listTemplate(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------List Rendezvous-------------------
			this.authenticate(user);
			this.newspaperService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
