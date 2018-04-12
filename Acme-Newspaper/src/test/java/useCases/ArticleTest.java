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

import services.ArticleService;
import utilities.AbstractTest;
import domain.Article;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ArticleTest extends AbstractTest {

	@Autowired
	private ArticleService	articleService;


	// Test---------------------------------------------------------------
	@Test
	public void articleTest() {
		final Object testingData[][] = {

			//An actor who is not authenticated must be able to: 
			//Search for a published article using a single key word that must appear somewhere
			//in its title, summary, or body.

			//An actor who is authenticated must be able to:
			//Do the same as an actor who is not authenticated

			//An actor who is authenticated as a user must be able to:
			//1.Write an article and attach it to any newspaper that has not been published, yet.

			//An actor who is authenticated as an administrator must be able to:
			//Remove an article that he or she thinks is inappropriate.

			{
				//Not authenticated actor list articles.
				null, null
			}, {
				//User list articles.
				"user1", null
			}, {
				//User not created list articles.
				"user3", IllegalArgumentException.class
			}, {
				//User creates article.
				"user2", "title1", "summary1", "body1", false, null
			}, {
				//User creates article.
				"user1", "title2", "summary2", "body2", false, null
			}, {
				//Not authenticated actor creates article.
				null, "title3", "summary3", "body3", false, IllegalArgumentException.class
			}, {
				//Admin delete a article
				"admin", "article1", null
			}, {
				//User delete a article that he or she has created.
				"user2", "article2", IllegalArgumentException.class
			}, {
				//User delete a article that he or she has not created.
				"user1", "article2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < 3; i++)
			this.listTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

		for (int i = 3; i < 6; i++)
			this.createTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (boolean) testingData[i][4], (Class<?>) testingData[i][5]);

		for (int i = 6; i < 9; i++)
			this.deleteTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void deleteTemplate(final String user, final String article, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Delete Article-------------------
			this.authenticate(user);
			final int articleId = this.getEntityId(article);
			final Article articleFinded = this.articleService.findOne(articleId);
			this.articleService.delete(articleFinded);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void createTemplate(final String user, final String title, final String summary, final String body, final boolean draftMode, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Create Article-------------------
			this.authenticate(user);
			final Article article = this.articleService.create();
			article.setTitle(title);
			article.setSummary(summary);
			article.setBody(body);
			article.setDraftmode(draftMode);

			this.articleService.save(article);
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

			//-----------------List Article-------------------
			this.authenticate(user);
			this.articleService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
