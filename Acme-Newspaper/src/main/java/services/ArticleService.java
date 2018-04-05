package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ArticleRepository;
import domain.Article;
import domain.User;

@Service
@Transactional
public class ArticleService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ArticleRepository articleRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService userService;
	
	@Autowired
	private AdministratorService administratorService;

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
		if (article.getId() != 0) {
			Assert.isTrue(article.getDraftmode() == false);
		}
		Article result = article;
		Assert.notNull(article);
		result = this.articleRepository.save(result);
		return result;
	}

	public void delete(final Article article) {
		this.administratorService.checkAuthority();
		Assert.notNull(article);
		Assert.isTrue(article.getId() != 0);
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
	
	public void flush() {
		this.articleRepository.flush();
	}

}
