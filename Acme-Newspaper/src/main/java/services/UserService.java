
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.User;

@Service
@Transactional
public class UserService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserRepository	userRepository;
	
	// Supporting services ----------------------------------------------------

//	@Autowired
//	private Validator validator;

	// Constructor ------------------------------------------------------------

	public UserService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public User create() {
		User result;
		result = new User();
		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.USER);
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		return result;
	}

	public Collection<User> findAll() {
		Collection<User> result;
		result = this.userRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public User findOne(final int userId) {
		User result;
		result = this.userRepository.findOne(userId);
		return result;
	}

	public User save(final User user) {
		User result = user;
		Assert.notNull(user);
		if (user.getId() == 0) {
			String pass = user.getUserAccount().getPassword();
			final Md5PasswordEncoder code = new Md5PasswordEncoder();
			pass = code.encodePassword(pass, null);
			user.getUserAccount().setPassword(pass);
		}
		result = this.userRepository.save(result);
		return result;
	}

	public void delete(final User user) {
		Assert.notNull(user);
		Assert.isTrue(user.getId() != 0);
		this.userRepository.delete(user);
	}

	// Other business method --------------------------------------------------

	public User findByPrincipal() {
		User e;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		e = this.userRepository.findByPrincipal(userAccount.getId());
		return e;
	}

	public User findArticleCreator(final int id) {
		User result;
		result = this.userRepository.findArticleCreator(id);
		Assert.notNull(result);
		return result;
	}

	public boolean checkUserLogged() {
		boolean result = false;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("USER");
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
		res.setAuthority("USER");
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
		this.userRepository.flush();
	}
}
