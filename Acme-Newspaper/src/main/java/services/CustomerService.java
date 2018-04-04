
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.Subscription;
import forms.CustomerForm;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CustomerRepository	customerRepository;
	
	// Supporting services ----------------------------------------------------

	@Autowired
	private Validator validator;

	// Constructor ------------------------------------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Customer create() {
		Customer result;
		result = new Customer();
		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		return result;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> result;
		result = this.customerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Customer findOne(final int customerId) {
		Customer result;
		result = this.customerRepository.findOne(customerId);
		return result;
	}

	public Customer save(final Customer customer) {
		Customer result = customer;
		Assert.notNull(customer);
		if (customer.getId() == 0) {
			String pass = customer.getUserAccount().getPassword();
			final Md5PasswordEncoder code = new Md5PasswordEncoder();
			pass = code.encodePassword(pass, null);
			customer.getUserAccount().setPassword(pass);
		}
		result = this.customerRepository.save(result);
		return result;
	}

	public void delete(final Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(customer.getId() != 0);
		this.customerRepository.delete(customer);
	}

	// Other business method --------------------------------------------------

	public Customer findByPrincipal() {
		Customer c;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		c = this.customerRepository.findByPrincipal(userAccount.getId());
		return c;
	}

	public boolean checkCustomerLogged() {
		boolean result = false;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("CUSTOMER");
		if (authority.contains(res))
			result = true;
		return result;
	}

	public void checkAuthority() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("CUSTOMER");
		Assert.isTrue(authority.contains(res));
	}

	public CustomerForm reconstruct(final CustomerForm customerForm, final BindingResult binding) {
		Customer res;
		CustomerForm customerFinal = null;
		res = customerForm.getCustomer();
		if (res.getId() == 0) {
			Collection<Subscription> subscriptions;
			UserAccount userAccount;
			Authority authority;
			userAccount = customerForm.getCustomer().getUserAccount();
			authority = new Authority();
			subscriptions = new ArrayList<Subscription>();
			customerForm.getCustomer().setUserAccount(userAccount);
			authority.setAuthority(Authority.CUSTOMER);
			userAccount.addAuthority(authority);
			customerForm.getCustomer().setSubscriptions(subscriptions);
			customerFinal = customerForm;
		} else {
			res = this.customerRepository.findOne(customerForm.getCustomer().getId());
			customerForm.getCustomer().setId(res.getId());
			customerForm.getCustomer().setVersion(res.getVersion());
			customerForm.getCustomer().setUserAccount(res.getUserAccount());
			customerForm.getCustomer().setSubscriptions(res.getSubscriptions());
			customerFinal = customerForm;
		}
		this.validator.validate(customerFinal, binding);
		return customerFinal;
	}

	public Customer reconstruct(final Customer customer, final BindingResult binding) {
		Customer res;
		Customer customerFinal;
		if (customer.getId() == 0) {
			UserAccount userAccount;
			Authority authority;
			userAccount = customer.getUserAccount();
			customer.setUserAccount(userAccount);
			authority = new Authority();
			authority.setAuthority(Authority.CUSTOMER);
			userAccount.addAuthority(authority);
			String password = "";
			password = customer.getUserAccount().getPassword();
			customer.getUserAccount().setPassword(password);
			customerFinal = customer;
		} else {
			res = this.customerRepository.findOne(customer.getId());
			customer.setId(res.getId());
			customer.setVersion(res.getVersion());
			customer.setUserAccount(res.getUserAccount());
			customer.getUserAccount().setPassword(customer.getUserAccount().getPassword());
			customer.getUserAccount().setAuthorities(customer.getUserAccount().getAuthorities());
			customerFinal = customer;
		}
		this.validator.validate(customerFinal, binding);
		return customerFinal;
	}

	public void flush() {
		this.customerRepository.flush();
	}
}
