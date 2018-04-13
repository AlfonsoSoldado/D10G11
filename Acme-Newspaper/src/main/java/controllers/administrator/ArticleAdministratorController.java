package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import controllers.AbstractController;
import domain.Article;

@Controller
@RequestMapping("/article/administrator")
public class ArticleAdministratorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private ArticleService articleService;

	// Supporting services --------------------------------------------------

	// Constructors ---------------------------------------------------------

	public ArticleAdministratorController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Article> article;

		articleService.checkTabooWords();
		article = this.articleService.findArticleTaboo();

		result = new ModelAndView("article/administrator/list");
		result.addObject("article", article);
		result.addObject("requestURI", "article/administrator/list.do");

		return result;
	}

	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int articleId) {
		ModelAndView result;
		Article article;

		article = this.articleService.findOne(articleId);
		result = this.createEditModelAndView(article);
		result.addObject("article", article);

		return result;
	}

	// Deleting --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Article article,
			final BindingResult binding) {
		ModelAndView res;
		try {
			this.articleService.delete(article);
			res = new ModelAndView("redirect:../../");
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(article, "article.commit.error");
		}
		return res;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Article article) {
		ModelAndView result;

		result = this.createEditModelAndView(article, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Article article, final String message) {
		ModelAndView result;

		result = new ModelAndView("article/administrator/edit");
		result.addObject("article", article);
		result.addObject("message", message);
		result.addObject("requestURI", "article/administrator/edit.do");

		return result;
	}

}
