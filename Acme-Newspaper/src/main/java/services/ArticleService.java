
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ArticleRepository;
import domain.Article;

@Service
@Transactional
public class ArticleService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ArticleRepository	articleRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService		userService;

	// Constructor ------------------------------------------------------------

	public ArticleService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Article create() {
		this.userService.checkAuthority();
		Article result;
		result = new Article();
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
		Assert.isTrue(this.userService.findArticleCreator(article.getWriter().getId()) == this.userService.findByPrincipal());
		Article result = article;
		Assert.notNull(article);
		result = this.articleRepository.save(result);
		return result;
	}

	public void delete(final Article article) {
		Assert.notNull(article);
		Assert.isTrue(article.getId() != 0);
		this.articleRepository.delete(article);
	}

	// Other business method --------------------------------------------------

}
