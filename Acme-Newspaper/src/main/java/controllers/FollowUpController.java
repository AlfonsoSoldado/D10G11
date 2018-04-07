package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FollowUpService;
import domain.FollowUp;

@Controller
@RequestMapping("/followUp")
public class FollowUpController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private FollowUpService followUpService;
	
	// Supporting services --------------------------------------------------
	
	// Constructors ---------------------------------------------------------

	public FollowUpController() {
		super();
	}
	
	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int articleId) {
		ModelAndView result;
		Collection<FollowUp> followUp;
		
		followUp = this.followUpService.findFollowUpByArticle(articleId);

		result = new ModelAndView("followUp/list");
		result.addObject("followUp", followUp);
		result.addObject("requestURI", "followUp/list.do");

		return result;
	}

}
