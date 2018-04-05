package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper")
public class NewspaperController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private NewspaperService newspaperService;
	
	// Supporting services --------------------------------------------------

	// Constructors ---------------------------------------------------------

	public NewspaperController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Newspaper> newspaper;

		newspaper = this.newspaperService.findAll();

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
		res = new ModelAndView("article/list");
		res.addObject("article", newspapers);
		res.addObject("requestURI", "article/list.do");
		return res;
	}

}
