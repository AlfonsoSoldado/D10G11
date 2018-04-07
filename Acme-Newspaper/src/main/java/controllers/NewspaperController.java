package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.NewspaperService;
import domain.Article;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper")
public class NewspaperController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private NewspaperService newspaperService;
	
	// Supporting services --------------------------------------------------
	
	@Autowired
	private ArticleService articleService;
	
	// Constructors ---------------------------------------------------------

	public NewspaperController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Newspaper> newspaper;
		
		this.newspaperService.checkTabooWords();

		newspaper = this.newspaperService.findNewspapersPublicated();
		newspaper.removeAll(this.newspaperService.findNewspaperTaboo());

		result = new ModelAndView("newspaper/list");
		result.addObject("newspaper", newspaper);
		result.addObject("requestURI", "newspaper/list.do");

		return result;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchList(@RequestParam String criteria) {
		ModelAndView res;
		Collection<Newspaper> newspapers;
		newspapers = this.newspaperService.searchNewspaper(criteria);
		newspapers.removeAll(this.newspaperService.findNewspaperTaboo());
		res = new ModelAndView("newspaper/list");
		res.addObject("newspaper", newspapers);
		res.addObject("requestURI", "newspaper/list.do");
		return res;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int newspaperId) {
		ModelAndView result;
		Collection<Article> articles = articleService.findArticleByNewspaper(newspaperId); 
		final Newspaper newspaper = this.newspaperService.findOne(newspaperId);

		result = new ModelAndView("newspaper/display");
		result.addObject("newspaper", newspaper);
		result.addObject("articles", articles);
		result.addObject("requestURI", "newspaper/display.do");

		return result;
	}

}
