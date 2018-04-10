package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

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
		
		publisher = userService.findByPrincipal();
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

	public Newspaper findOne(int id) {
		Assert.isTrue(id != 0);
		Newspaper res;
		res = this.newspaperRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Newspaper save(Newspaper newspaper) {
		this.userService.checkAuthority();
		Assert.notNull(newspaper);
		Newspaper res;
		res = this.newspaperRepository.save(newspaper);
		return res;
	}

	public void delete(Newspaper newspaper) {
		this.administratorService.checkAuthority();
		Assert.notNull(newspaper);
		Assert.isTrue(newspaper.getId() != 0);
		Assert.isTrue(this.newspaperRepository.exists(newspaper.getId()));
		Collection<Article> articles;
		articles = new ArrayList<Article>(this.articleService.findArticleByNewspaper(newspaper.getId()));
		for (final Article s : articles)
			this.articleService.delete(s);
		Collection<Subscription> subscriptions;
		subscriptions = new ArrayList<Subscription>(this.subscriptionService.findSubscriptionByNewspaper(newspaper.getId()));
		for (final Subscription s : subscriptions)
			if (s != null) {
				this.subscriptionService.delete(s);
			}
		this.newspaperRepository.delete(newspaper);
	}

	// Other business method --------------------------------------------------
	
	public Collection<Newspaper> searchNewspaper(String criteria) {
		Collection<Newspaper> res = new ArrayList<Newspaper>();
		res.addAll(newspaperRepository.searchNewspaper(criteria));
		return res;
	}
	
	public Collection<Newspaper> findNewspapersPublicated() {
		Collection<Newspaper> res = new ArrayList<Newspaper>();
		res.addAll(newspaperRepository.findNewspapersPublicated());
		return res;
	}
	
	public Collection<Newspaper> findNewspapersNotPublicated() {
		Collection<Newspaper> res = new ArrayList<Newspaper>();
		res.addAll(newspaperRepository.findNewspapersNotPublicated());
		return res;
	}
	
	public Collection<Newspaper> findNewspapersPublic() {
		Collection<Newspaper> res = new ArrayList<Newspaper>();
		res.addAll(newspaperRepository.findNewspapersPublic());
		return res;
	}
	
	public Collection<Newspaper> findNewspapersPrivate() {
		Collection<Newspaper> res = new ArrayList<Newspaper>();
		res.addAll(newspaperRepository.findNewspapersPrivate());
		return res;
	}

	public void checkTabooWords() {
		Collection<String> tabooWords = new ArrayList<String>();
		tabooWords = configurationService.findTabooWords();

		Collection<Newspaper> newspapers = new ArrayList<Newspaper>();
		newspapers = this.findAll();

		for (String s : tabooWords) {
			for (Newspaper n : newspapers) {
				if (n.getTitle().toLowerCase().contains(s.toLowerCase()) || n.getDescription().toLowerCase().contains(s.toLowerCase())) {
					n.setTaboo(true);
				}
			}
		}
	}
	
	public Collection<Newspaper> findNewspaperTaboo(){
		Collection<Newspaper> res = new ArrayList<Newspaper>();
		res.addAll(newspaperRepository.findNewspaperTaboo());
		return res;
	}
	
	public void flush() {
		this.newspaperRepository.flush();
	}
	
	public Newspaper reconstruct(final Newspaper newspaper, final BindingResult binding) {
		Newspaper res;
		Newspaper newspaperFinal;
		if (newspaper.getId() == 0)
			res = newspaper;
		else {
			newspaperFinal = this.findOne(newspaper.getId());
			newspaper.setPublisher(this.userService.findByPrincipal());
			newspaperFinal.setTitle(newspaper.getTitle());
			newspaperFinal.setPublication(newspaper.getPublication());
			newspaperFinal.setDescription(newspaper.getDescription());
			newspaperFinal.setPicture(newspaper.getPicture());
			newspaperFinal.setHide(newspaper.getHide());
			newspaperFinal.setArticles(newspaper.getArticles());
			newspaperFinal.setPublisher(newspaper.getPublisher());
			res = newspaperFinal;
		}

		return res;
	}

}
