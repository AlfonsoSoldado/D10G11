package useCases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.NewspaperService;
import services.SubscriptionService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Newspaper;
import domain.Subscription;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class SubscriptionTest extends AbstractTest {

	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	private NewspaperService	newspaperService;

	// Test---------------------------------------------------------------
	@Test
	public void subscriptionTest() {
		final Object testingData[][] = {

				{
						// User creates a subscription.
						"customer1", null }, {
						// User creates a subscription.
						"user2", IllegalArgumentException.class }, {
						} };

		for (int i = 0; i < 1; i++)
			this.createTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void createTemplate(final String customer, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			// -----------------Create Subscription-------------------
			this.authenticate(customer);

			CreditCard cc = new CreditCard();
			cc.setBrandName("MasterCard");
			cc.setCVV(334);
			cc.setExpirationMonth(12);
			cc.setExpirationYear(19);
			cc.setHolderName("Raul");
			cc.setNumber("5574588374439106");
			
			int newspaperId = this.getEntityId("newspaper1");
			Newspaper newspaper = newspaperService.findOne(newspaperId);
			newspaper.setHide(true);
			
			Subscription subscription = subscriptionService.create(newspaper);
			subscription.setCreditcard(cc);

			this.subscriptionService.save(subscription);
			this.unauthenticate();
			this.subscriptionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
