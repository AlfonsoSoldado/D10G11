package useCases;

import java.text.SimpleDateFormat;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Chirp;
import services.ChirpService;
import services.UserService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class ChirpTest extends AbstractTest {

	@Autowired
	private ChirpService chirpService;
	@Autowired
	private UserService userService;

	// Test---------------------------------------------------------------
	@Test
	public void announcementTest() {
		final Object testingData[][] = {

				// User create a chirp
				{ "user1", "2017/04/15 15:25", "title 1", "description 1", false, null }, {
						// Create create a chirp(without authentication)
						null, "2017/04/15 15:25", "title 2", "description 1", false, IllegalArgumentException.class },
				{
						// Listing chirps (without authentication)
						null, IllegalArgumentException.class },
				{
						// Listing his/her chirps (with authentication)
						"user1", null },
				{
						// Deleting chirp (Admin)
						"admin", "chirp1", null },
				{
						// Deleting chirp (User)
						"user1", "chirp2", IllegalArgumentException.class } };

		for (int i = 0; i < 2; i++)
			this.createChirpTemplate((String) testingData[i][0], (String) testingData[i][1],
					(String) testingData[i][2], (String) testingData[i][3], (Boolean) testingData[i][4],(Class<?>) testingData[i][5]);

		 for (int i = 2; i < 4; i++)
		 this.listTemplate((String) testingData[i][0],
		 (Class<?>) testingData[i][1]);
		
		 for (int i = 4; i < testingData.length; i++)
		 this.deleteTemplate((String) testingData[i][0], (String) testingData[i][1],
		 (Class<?>) testingData[i][2]);

	}

	protected void createChirpTemplate(final String user, final String moment, final String title,
			final String description, boolean taboo, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			// -----------------Announcement-------------------
			this.authenticate(user);

			final Chirp chirp = this.chirpService.create();
			chirp.setTitle(title);
			chirp.setDescription(description);

			final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");

			chirp.setMoment(format.parse(moment));
			chirp.setTaboo(taboo);

			this.chirpService.save(chirp);
			this.unauthenticate();
			//this.chirpService.flush();
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
			this.chirpService.findChirpByUser(userService.findByPrincipal().getId());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void deleteTemplate(final String user, final String chirp, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			// -----------------Delete Rendezvous-------------------
			this.authenticate(user);
			final int chirpId = this.getEntityId(chirp);
			final Chirp chirpFinded = this.chirpService.findOne(chirpId);
			this.chirpService.delete(chirpFinded);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
