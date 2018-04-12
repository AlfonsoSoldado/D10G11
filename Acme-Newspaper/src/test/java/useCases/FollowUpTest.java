package useCases;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Article;
import domain.FollowUp;
import services.ArticleService;
import services.FollowUpService;
import services.UserService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class FollowUpTest extends AbstractTest {

	@Autowired
	private FollowUpService followUpService;
	@Autowired
	private ArticleService articleService;

	@Autowired
	private UserService userService;

	// Test---------------------------------------------------------------
	@Test
	public void followUpTest() {
		final Object testingData[][] = {

				// User write follow up to article
				{ "user1", "title 1", "2017/04/15 15:25", "sumary 1", "text 1", "www.picture.com", null }, {
						// User write follow up to article(without authentication)
						null, "title 1", "2017/04/15 15:25", "sumary 1", "text 1", "www.picture.com",
						IllegalArgumentException.class },
				{
						// Listing followps (without authentication)
						null, IllegalArgumentException.class },
				{
						// Listing his/her followps (with authentication)
						"user1", null },
				{
						// Deleting chirp (Admin)
						"admin", "chirp1", null },
				{
						// Deleting chirp (User)
						"user1", "chirp2", IllegalArgumentException.class } };

		for (int i = 0; i < 2; i++)
			this.createFollowTemplate((String) testingData[i][0], (String) testingData[i][1],
					(String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (Class<?>) testingData[i][6]);

		for (int i = 2; i < 4; i++)
			this.listTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);


	}

	protected void createFollowTemplate(final String user, final String title, final String moment, final String sumary,
			String text, String picture, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			// -----------------Announcement-------------------
			this.authenticate(user);

			final FollowUp follow = this.followUpService.create();
			follow.setTitle(title);
			follow.setSummary(sumary);

			final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
			ArrayList<String> photos = new ArrayList<>();
			photos.add(picture);
			follow.setPictures(photos);
			follow.setMoment(format.parse(moment));
			follow.setText(text);
			Article article = this.articleService.findOne(this.getEntityId("article1"));
			follow.setArticle(article);
			ArrayList<FollowUp> followUps = new ArrayList<>(article.getFollowUps());
			followUps.add(follow);
			this.followUpService.save(follow);
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

			// -----------------List Announcement-------------------
			this.authenticate(user);
			ArrayList<Article> articles = new ArrayList<>(this.userService.findByPrincipal().getArticles());
			this.followUpService.findFollowUpByArticle(articles.get(0).getId());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
