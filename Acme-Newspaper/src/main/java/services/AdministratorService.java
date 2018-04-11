package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Newspaper;
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

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

	// The average and the standard deviation of newspapers created per user.
	public double averageNewspaperPerUser() {
		checkAdministratorLogged();
		return this.administratorRepository.averageNewspaperPerUser();

	}

	public double standardDesviationNewspaperPerUser() {
		checkAdministratorLogged();
		return this.administratorRepository.standardDesviationNewspaperPerUser();
	}
	// The average and the standard deviation of articles written by writer.

	public double averageArticlesPerUser() {
		checkAdministratorLogged();
		return this.administratorRepository.averageArticlesPerUser();

	}

	public double standardDesviationArticlesPerUser() {
		checkAdministratorLogged();
		return this.administratorRepository.standardDesviationArticlesPerUser();

	}

	// The average and the standard deviation of articles per newspaper.
	public double averageArticlesPerNewspaper() {
		checkAdministratorLogged();
		return this.administratorRepository.averageArticlesPerNewspaper();
		

	}

	public double standardDesviationArticlesPerNewspaper() {
		checkAdministratorLogged();
		return this.administratorRepository.standardDesviationArticlesPerNewspaper();

	}

	// The newspapers that have at least 10% more articles than the average.
	public List<Newspaper> newspapers10moreThanAvereage() {
		checkAdministratorLogged();
		List<Newspaper> a = new ArrayList<>(this.administratorRepository.newspapers10moreThanAvereage());
		if (a == null || a.isEmpty()) {
			a = null;
		}
		return a;
	}

	// The newspapers that have at least 10% fewer articles than the average.
	public List<Newspaper> newspapers10fewerThanAvereage() {
		checkAdministratorLogged();
		List<Newspaper> a = new ArrayList<>(this.administratorRepository.newspapers10fewerThanAvereage());

		if (a == null || a.isEmpty()) {
			a = null;
		}
		return a;
	}

	// The ratio of users who have ever created a newspaper.
	public double ratioUsersCreatedEverNewspaper() {
		checkAdministratorLogged();
		return this.administratorRepository.ratioUsersCreatedEverNewspaper();

	}

	// The ratio of users who have ever written an article.
	public double ratioUsersEverWrittenArticle() {
		checkAdministratorLogged();
		return this.administratorRepository.ratioUsersEverWrittenArticle();
	}

	// The average number of follow-ups per article.
	public double averageFollowupsPerArticle() {
		checkAdministratorLogged();
		return this.administratorRepository.averageFollowupsPerArticle();
	}
	// The average number of follow-ups per article up to one week after the
	// corresponding
	// newspapers been published.

	public double averageFollowupsPerArticleToOneWeekPublishedArticle() {
		checkAdministratorLogged();
				long dias = TimeUnit.DAYS.toMillis(7);
				Date moment = new Date(System.currentTimeMillis() - dias);
		return this.administratorRepository.averageFollowupsPerArticleToOneWeekPublishedArticle(moment);
	}

	// The average number of follow-ups per article up to two weeks after the
	// corresponding
	// newspapers been published
	public double averageFollowupsPerArticleToTwoWeekPublishedArticle() {
		checkAdministratorLogged();
		long dias = TimeUnit.DAYS.toMillis(14);
		Date moment = new Date(System.currentTimeMillis() - dias);
		return this.administratorRepository.averageFollowupsPerArticleToTwoWeekPublishedArticle(moment);
	}

	// The average and the standard deviation of the number of chirps per user.
	public double averageChirpsPerUser() {
		checkAdministratorLogged();
		return this.administratorRepository.averageChirpsPerUser();
	}

	public double standardDesviationChirpsPerUser() {
		checkAdministratorLogged();
		return this.administratorRepository.standardDesviationChirpsPerUser();
	}

	// The ratio of users who have posted above 75% the average number of chirps per
	// user.
	public double ratioUsersMorePosted75ChirpsOfAveragePerUser() {
		checkAdministratorLogged();
		return this.administratorRepository.ratioUsersMorePosted75ChirpsOfAveragePerUser();
	}

	// The ratio of public versus private newspapers.
	public double[] ratioPublicVsPrivateNewspaper() {
		checkAdministratorLogged();
		double ratio[] = new double[2];
		ratio[0] = this.administratorRepository.ratioPublicNewspaper();
		ratio[1] = this.administratorRepository.ratioPrivateNewspaper();
		return ratio;

	}

	// The average number of articles per private newspapers.
	public Double averageArticlesPerNewspaperPrivates() {
		checkAdministratorLogged();
		Double a = this.administratorRepository.averageArticlesPerNewspaperPrivates();
		if (a == null) {
			a = 0.;
		}
		return a;
	}

	// The average number of articles per public newspapers.
	public Double averageArticlesPerNewspaperPublics() {
		checkAdministratorLogged();
		Double a = this.administratorRepository.averageArticlesPerNewspaperPublics();
		if (a == null) {
			a = 0.;
		}
		return a;
	}

	// The ratio of subscribers per private newspaper versus the total number of
	// customers.
	public double[] ratioPrivateNewspaperSubsciptionsVsTotalCustomers() {
		checkAdministratorLogged();
		double ratio[] = new double[2];
		ratio[0] = this.administratorRepository.ratioPrivateNewspaperSubsciptions();
		ratio[1] = this.administratorRepository.numberOfCustomers();
		return ratio;
	}

	// The average ratio of private versus public newspapers per publisher.
	public double[] AverageRatioPrivateVsPublicNewspaperPerPublisher() {
		checkAdministratorLogged();
		double ratio[] = new double[4];
		ratio[0] = this.administratorRepository.ratioPrivateNewspaperPerPublisher();
		ratio[1] = this.administratorRepository.AveragePrivateNewspaperPerPublisher();
		ratio[2] = this.administratorRepository.ratioPublicNewspaperPerPublisher();
		ratio[3] = this.administratorRepository.AveragePublicNewspaperPerPublisher();
		return ratio;
	}
}
