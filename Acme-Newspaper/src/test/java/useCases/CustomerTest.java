
package useCases;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Customer;
import domain.User;
import security.Authority;
import security.UserAccount;
import services.CustomerService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class CustomerTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	@Autowired
	private CustomerService customerService;
	// An actor who is not authenticated must be able to:
	// 1. Register to the system as a user.
	// 2. List the users of the system
	// . An actor who is authenticated as a user must be able to:
	// Do the same that unauthenticate actor but register

	@Test
	public void userTest() {

		final Object testingData[][] = { {
				// A existing user do login
				"customer1", null },
				{
						// A no existing user do login
						"customer28", IllegalArgumentException.class },
				{
						// A unauthenticated actor create a user
						null, null },
				{
						// A user create a user
						"customer1", RuntimeException.class },
				{
						// A user list users
						"customer1", null },
				{
						// A unhautenticated actor list users
						null, null } };
		for (int i = 0; i < 2; i++)
			this.logincustomerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

		for (int i = 2; i < 4; i++)
			this.createCustomerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

		for (int i = 4; i < testingData.length; i++)
			this.listUserTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void listUserTemplate(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);
			this.customerService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void logincustomerTemplate(final String customer, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(customer);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void createCustomerTemplate(final String customer, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(customer);
			final UserAccount userAccount = new UserAccount();
			userAccount.setUsername("customerTest");
			userAccount.setPassword("customerTest");
			final List<Authority> authorities = new ArrayList<>();
			final Authority aut = new Authority();
			aut.setAuthority("CUSTOMER");
			authorities.add(aut);
			userAccount.setAuthorities(authorities);

			final Customer customerTest = this.customerService.create();
			customerTest.setEmail("customer@gmail.com");
			customerTest.setName("customer");
			customerTest.setPhoneNumber("626253077");
			customerTest.setPostalAddress(41000);
			customerTest.setSurname("customer");
			customerTest.setUserAccount(userAccount);

			this.customerService.save(customerTest);
			this.unauthenticate();
			this.customerService.flush();
			this.authenticate(customerTest.getUserAccount().getUsername());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
