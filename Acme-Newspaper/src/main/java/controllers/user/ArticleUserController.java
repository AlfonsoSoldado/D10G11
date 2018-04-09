package controllers.user;

import java.util.ArrayList;
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
import services.NewspaperService;
import controllers.AbstractController;
import domain.Article;
import domain.Newspaper;

@Controller
@RequestMapping("/article/user")
public class ArticleUserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private ArticleService articleService;
	
	// Supporting services --------------------------------------------------
	
	@Autowired
	private NewspaperService newspaperService;
	
	// Constructors ---------------------------------------------------------

	public ArticleUserController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Article article;

		article = this.articleService.create();
		res = this.createEditModelAndView(article);
		
		return res;
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

	// Saving --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Article article,
			final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createEditModelAndView(article,
					"article.params.error");
		else
			try {
				this.articleService.save(article);
				res = new ModelAndView("redirect:../list.do?newspaperId=" + article.getNewspaper().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(article,
						"article.commit.error");
			}
		return res;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Article article) {
		ModelAndView result;

		result = this.createEditModelAndView(article, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Article article,
			final String message) {
		ModelAndView result;
		final Collection<Boolean> draftmode = new ArrayList<>();
		Collection<Newspaper> newspaper;
		
		draftmode.add(false);
		draftmode.add(true);
		newspaper = newspaperService.findAll();
		newspaper.removeAll(newspaperService.findNewspapersPublicated());

		result = new ModelAndView("article/user/edit");
		result.addObject("article", article);
		result.addObject("draftmode", draftmode);
		result.addObject("newspaper", newspaper);
		result.addObject("message", message);
		result.addObject("requestURI", "article/user/edit.do");

		return result;
	}

}
