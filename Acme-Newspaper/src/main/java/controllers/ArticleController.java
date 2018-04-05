package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import domain.Article;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private ArticleService articleService;

	// Supporting services ----------------------------------------------------

	// Constructors ---------------------------------------------------------

	public ArticleController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int newspaperId) {
		ModelAndView result;
		Collection<Article> article;

		article = this.articleService.findArticleByNewspaper(newspaperId);

		result = new ModelAndView("article/list");
		result.addObject("article", article);
		result.addObject("requestURI", "article/list.do");

		return result;
	}

	// Searching ----------------------------------------------------

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		final ModelAndView result;
		Article article;
		article = this.articleService.search();
		result = this.createEditModelAndView(article);
		return result;
	}

	@RequestMapping(value = "/searchList", method = RequestMethod.GET)
	public ModelAndView searchList() {
		ModelAndView res;
		Collection<Article> articles;
		articles = this.articleService.searchArticle("Sevilla FC");
		res = new ModelAndView("article/list");
		res.addObject("article", articles);
		res.addObject("requestURI", "article/list.do");
		return res;
	}

	// Ancillary methods -------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Article article) {
		ModelAndView result;
		result = this.createEditModelAndView(article, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Article article,
			final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("article/search");
		result.addObject("article", article);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "article/search.do");
		return result;
	}

}
