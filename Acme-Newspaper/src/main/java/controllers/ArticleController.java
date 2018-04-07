package controllers;

import java.util.ArrayList;
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

	// Supporting services --------------------------------------------------

	// Constructors ---------------------------------------------------------

	public ArticleController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int newspaperId) {
		ModelAndView result;
		Collection<Article> article;
		
		this.articleService.checkTabooWords();

		article = this.articleService.findArticleByNewspaper(newspaperId);
		article.removeAll(this.articleService.findArticleTaboo());

		result = new ModelAndView("article/list");
		result.addObject("article", article);
		result.addObject("requestURI", "article/list.do");

		return result;
	}

	// Searching ----------------------------------------------------
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchList(@RequestParam String criteria) {
		ModelAndView res;
		Collection<Article> articles;
		articles = this.articleService.searchArticle(criteria);
		res = new ModelAndView("article/list");
		res.addObject("article", articles);
		res.addObject("requestURI", "article/list.do");
		return res;
	}
	
	// Display ------------------------------------------------------
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int articleId) {
		ModelAndView result;
		
		Article article = this.articleService.findOne(articleId);
		
		Collection<String> pictures = new ArrayList<String>();
		pictures = article.getPictures();

		result = new ModelAndView("article/display");
		result.addObject("article", article);
		result.addObject("pictures", pictures);
		result.addObject("requestURI", "article/display.do");

		return result;
	}

}
