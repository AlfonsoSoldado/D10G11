package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository administratorRepository;

	// Supporting services ----------------------------------------------------

	// @Autowired
	// private Validator validator;

	// Constructor ------------------------------------------------------------

	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Administrator create() {
		Administrator result;
		result = new Administrator();
		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;
		result = this.administratorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Administrator findOne(final int administratorId) {
		Administrator result;
		result = this.administratorRepository.findOne(administratorId);
		return result;
	}

	public Administrator save(final Administrator administrator) {
		Administrator result = administrator;
		Assert.notNull(administrator);
		if (administrator.getId() == 0) {
			String pass = administrator.getUserAccount().getPassword();
			final Md5PasswordEncoder code = new Md5PasswordEncoder();
			pass = code.encodePassword(pass, null);
			administrator.getUserAccount().setPassword(pass);
		}
		result = this.administratorRepository.save(result);
		return result;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getId() != 0);
		this.administratorRepository.delete(administrator);
	}

	// Other business method --------------------------------------------------

	public Administrator findByPrincipal() {
		Administrator c;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		c = this.administratorRepository.findByPrincipal(userAccount.getId());
		return c;
	}

	public boolean checkAdministratorLogged() {
		boolean result = false;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("ADMIN");
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
		res.setAuthority("ADMIN");
		Assert.isTrue(authority.contains(res));
	}

	public void flush() {
		this.administratorRepository.flush();
	}
}
