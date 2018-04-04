
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CustomerRepository	customerRepository;
	
	// Supporting services ----------------------------------------------------

//	@Autowired
//	private Validator validator;

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

//	public UserForm reconstruct(final UserForm userForm, final BindingResult binding) {
//		User res;
//		UserForm userFinal = null;
//		res = userForm.getUser();
//		if (res.getId() == 0) {
//			Collection<Comment> comment;
//			Collection<Question> question;
//			Collection<Rendezvous> rendezvous;
//			Collection<RSVP> rsvp;
//			UserAccount userAccount;
//			Authority authority;
//			userAccount = userForm.getUser().getUserAccount();
//			authority = new Authority();
//			comment = new ArrayList<Comment>();
//			question = new ArrayList<Question>();
//			rendezvous = new ArrayList<Rendezvous>();
//			rsvp = new ArrayList<RSVP>();
//			userForm.getUser().setUserAccount(userAccount);
//			authority.setAuthority(Authority.USER);
//			userAccount.addAuthority(authority);
//			userForm.getUser().setComment(comment);
//			userForm.getUser().setQuestion(question);
//			userForm.getUser().setRendezvous(rendezvous);
//			userForm.getUser().setRsvp(rsvp);
//			userFinal = userForm;
//		} else {
//			res = this.userRepository.findOne(userForm.getUser().getId());
//			userForm.getUser().setId(res.getId());
//			userForm.getUser().setVersion(res.getVersion());
//			userForm.getUser().setUserAccount(res.getUserAccount());
//			userForm.getUser().setComment(res.getComment());
//			userForm.getUser().setQuestion(res.getQuestion());
//			userForm.getUser().setRendezvous(res.getRendezvous());
//			userForm.getUser().setRsvp(res.getRsvp());
//			userFinal = userForm;
//		}
//		this.validator.validate(userFinal, binding);
//		return userFinal;
//	}
//
//	public User reconstruct(final User user, final BindingResult binding) {
//		User res;
//		User userFinal;
//		if (user.getId() == 0) {
//			UserAccount userAccount;
//			Authority authority;
//			userAccount = user.getUserAccount();
//			user.setUserAccount(userAccount);
//			authority = new Authority();
//			authority.setAuthority(Authority.USER);
//			userAccount.addAuthority(authority);
//			String password = "";
//			password = user.getUserAccount().getPassword();
//			user.getUserAccount().setPassword(password);
//			userFinal = user;
//		} else {
//			res = this.userRepository.findOne(user.getId());
//			user.setId(res.getId());
//			user.setVersion(res.getVersion());
//			user.setUserAccount(res.getUserAccount());
//			user.getUserAccount().setPassword(user.getUserAccount().getPassword());
//			user.getUserAccount().setAuthorities(user.getUserAccount().getAuthorities());
//			userFinal = user;
//		}
//		this.validator.validate(userFinal, binding);
//		return userFinal;
//	}

	public void flush() {
		this.customerRepository.flush();
	}
}
