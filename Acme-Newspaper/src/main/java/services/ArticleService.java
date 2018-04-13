package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ArticleRepository;
import domain.Article;
import domain.FollowUp;
import domain.User;

@Service
@Transactional
public class ArticleService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ArticleRepository articleRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private FollowUpService followUpService;
	
	@Autowired
	private Validator validator;

	// Constructor ------------------------------------------------------------

	public ArticleService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Article create() {
		this.userService.checkAuthority();
		Article result;
		Date moment;
		User writer;
		
		result = new Article();
		writer = userService.findByPrincipal();
		moment = new Date(System.currentTimeMillis() - 1000);
		result.setMoment(moment);
		result.setWriter(writer);
		
		return result;
	}

	public Collection<Article> findAll() {
		Collection<Article> result;
		result = this.articleRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Article findOne(final int id) {
		Article result;
		result = this.articleRepository.findOne(id);
		return result;
	}

	public Article save(final Article article) {
		this.userService.checkAuthority();
		Article result = article;
		Assert.notNull(article);
		Assert.isTrue(article.getWriter().equals(this.userService.findByPrincipal()));
		Assert.isTrue(article.getNewspaper().getPublisher().equals(article.getWriter()));
		if (article.getId() != 0) {
			Assert.isTrue(article.getDraftmode() == false);
		}
		result = this.articleRepository.save(result);
		return result;
	}

	public void delete(final Article article) {
		this.administratorService.checkAuthority();
		Assert.notNull(article);
		Assert.isTrue(article.getId() != 0);
		Collection<FollowUp> followUps;
		followUps = new ArrayList<FollowUp>(this.followUpService.findFollowUpByArticle(article.getId()));
		for (final FollowUp f : followUps)
			if (f != null) {
				this.followUpService.delete(f);
			}
		this.articleRepository.delete(article);
	}

	// Other business method --------------------------------------------------

	public Collection<Article> findArticleByNewspaper(int id) {
		Collection<Article> res;
		res = this.articleRepository.findArticleByNewspaper(id);
		return res;
	}
	
	public Collection<Article> searchArticle(String criteria) {
		Collection<Article> res = new ArrayList<Article>();
		res.addAll(articleRepository.searchArticle(criteria));
		return res;
	}
	
	public void checkTabooWords() {
		Collection<String> tabooWords = new ArrayList<String>();
		tabooWords = configurationService.findTabooWords();

		Collection<Article> articles = new ArrayList<Article>();
		articles = this.findAll();

		for (String s : tabooWords) {
			for (Article a : articles) {
				if (a.getTitle().toLowerCase().contains(s.toLowerCase()) || a.getSummary().toLowerCase().contains(s.toLowerCase()) || a.getBody().toLowerCase().contains(s.toLowerCase())) {
					a.setTaboo(true);
				}
			}
		}
	}
	
	public Collection<Article> findArticleTaboo(){
		Collection<Article> res = new ArrayList<Article>();
		res.addAll(articleRepository.findArticleTaboo());
		return res;
	}
	
	public Collection<Article> findArticlePublishedByUser(int userId){
		Collection<Article> res = new ArrayList<Article>();
		res = articleRepository.findArticlePublishedByUser(userId);
		return res;
	}
	
	public void flush() {
		this.articleRepository.flush();
	}
	
	public Article reconstruct(final Article article, final BindingResult binding) {
		Article res;
		Article articleFinal;

		if (article.getId() == 0) {
			User userPrincipal;
			final Collection<FollowUp> followUps;

			userPrincipal = this.userService.findByPrincipal();

			article.setWriter(userPrincipal);
			followUps = new ArrayList<FollowUp>();
			article.setFollowUps(followUps);
			if (article.getDraftmode())
				article.setMoment(new Date(System.currentTimeMillis() - 1000));
			res = article;
		} else {
			articleFinal = this.articleRepository.findOne(article.getId());
			if (article.getDraftmode()) {
				article.setMoment(new Date(System.currentTimeMillis() - 1000));
			} 
			article.setWriter(articleFinal.getWriter());
			article.setFollowUps(articleFinal.getFollowUps());
			article.setNewspaper(articleFinal.getNewspaper());

			res = article;
		}
		this.validator.validate(res, binding);
		return res;
	}

}
