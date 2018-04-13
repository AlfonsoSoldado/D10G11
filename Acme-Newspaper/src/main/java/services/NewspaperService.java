package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.NewspaperRepository;
import domain.Article;
import domain.Newspaper;
import domain.Subscription;
import domain.User;

@Service
@Transactional
public class NewspaperService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private NewspaperRepository newspaperRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private UserService userService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private Validator validator;

	// Constructor ------------------------------------------------------------

	public NewspaperService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Newspaper create() {
		this.userService.checkAuthority();
		Newspaper result;
		User publisher;
		Date publication;
		Collection<Article> articles;

		publisher = this.userService.findByPrincipal();
		result = new Newspaper();
		articles = new ArrayList<Article>();

		publication = new Date(System.currentTimeMillis() - 1000);
		result.setPublication(publication);
		result.setPublisher(publisher);
		result.setArticles(articles);

		return result;
	}

	public Collection<Newspaper> findAll() {
		Collection<Newspaper> res;
		res = this.newspaperRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Newspaper findOne(final int id) {
		Assert.isTrue(id != 0);
		Newspaper res;
		res = this.newspaperRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Newspaper save(final Newspaper newspaper) {
		this.userService.checkAuthority();
		Assert.isTrue(newspaper.getPublisher().equals(this.userService.findByPrincipal()));
		Assert.notNull(newspaper);
		Newspaper res;
		res = this.newspaperRepository.save(newspaper);
		return res;
	}

	public void delete(final Newspaper newspaper) {
		this.administratorService.checkAuthority();
		Assert.notNull(newspaper);
		Assert.isTrue(newspaper.getId() != 0);
		Assert.isTrue(this.newspaperRepository.exists(newspaper.getId()));
		Collection<Article> articles;
		articles = new ArrayList<Article>(
				this.articleService.findArticleByNewspaper(newspaper.getId()));
		for (final Article s : articles)
			this.articleService.delete(s);
		Collection<Subscription> subscriptions;
		subscriptions = new ArrayList<Subscription>(
				this.subscriptionService.findSubscriptionByNewspaper(newspaper
						.getId()));
		for (final Subscription s : subscriptions)
			if (s != null)
				this.subscriptionService.delete(s);
		this.newspaperRepository.delete(newspaper);
	}

	// Other business method --------------------------------------------------

	public Collection<Newspaper> searchNewspaper(final String criteria) {
		final Collection<Newspaper> res = new ArrayList<Newspaper>();
		res.addAll(this.newspaperRepository.searchNewspaper(criteria));
		return res;
	}

	public Collection<Newspaper> findNewspapersPublicated() {
		final Collection<Newspaper> res = new ArrayList<Newspaper>();
		res.addAll(this.newspaperRepository.findNewspapersPublicated());
		return res;
	}
	
	public Collection<Newspaper> findNewspapersByUser(int id) {
		final Collection<Newspaper> res;
		res = this.newspaperRepository.findNewspapersByUser(id);
		return res;
	}

	public Collection<Newspaper> findNewspapersNotPublicated() {
		final Collection<Newspaper> res = new ArrayList<Newspaper>();
		res.addAll(this.newspaperRepository.findNewspapersNotPublicated());
		return res;
	}

	public Collection<Newspaper> findNewspapersPublic() {
		final Collection<Newspaper> res = new ArrayList<Newspaper>();
		res.addAll(this.newspaperRepository.findNewspapersPublic());
		return res;
	}

	public Collection<Newspaper> findNewspapersPrivate() {
		final Collection<Newspaper> res = new ArrayList<Newspaper>();
		res.addAll(this.newspaperRepository.findNewspapersPrivate());
		return res;
	}

	public void checkTabooWords() {
		Collection<String> tabooWords = new ArrayList<String>();
		tabooWords = this.configurationService.findTabooWords();

		Collection<Newspaper> newspapers = new ArrayList<Newspaper>();
		newspapers = this.findAll();

		for (final String s : tabooWords)
			for (final Newspaper n : newspapers)
				if (n.getTitle().toLowerCase().contains(s.toLowerCase())
						|| n.getDescription().toLowerCase()
								.contains(s.toLowerCase()))
					n.setTaboo(true);
	}

	public Collection<Newspaper> findNewspaperTaboo() {
		final Collection<Newspaper> res = new ArrayList<Newspaper>();
		res.addAll(this.newspaperRepository.findNewspaperTaboo());
		return res;
	}

	public void flush() {
		this.newspaperRepository.flush();
	}

	public Newspaper reconstruct(final Newspaper newspaper,
			final BindingResult binding) {
		Newspaper res;
		Newspaper newspaperFinal;
		Date publication;
		if (newspaper.getId() == 0) {
			User user;
			Collection<Article> articles;
			
			publication = new Date(System.currentTimeMillis() - 1000);
			newspaper.setPublication(publication);

			user = this.userService.findByPrincipal();
			articles = new ArrayList<Article>();
			newspaper.setArticles(articles);
			newspaper.setPublisher(user);
			
			res = newspaper;
		} else {
			newspaperFinal = this.newspaperRepository.findOne(newspaper.getId());
			
			newspaper.setId(newspaperFinal.getId());
			newspaper.setVersion(newspaperFinal.getVersion());
			newspaper.setPublication(newspaperFinal.getPublication());
			newspaper.setPublisher(newspaperFinal.getPublisher());
			
			if (newspaper.getArticles() == null) {
				newspaper.setArticles(new ArrayList<Article>());
			} else {
				newspaper.setArticles(newspaperFinal.getArticles());
			}
			res = newspaper;
		}
		this.validator.validate(res, binding);
		return res;
	}

}
