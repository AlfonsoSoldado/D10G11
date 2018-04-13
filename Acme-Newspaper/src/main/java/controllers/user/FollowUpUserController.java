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
import services.FollowUpService;
import services.UserService;
import controllers.AbstractController;
import domain.Article;
import domain.FollowUp;
import domain.User;

@Controller
@RequestMapping("/followUp/user")
public class FollowUpUserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private FollowUpService followUpService;
	
	// Supporting services --------------------------------------------------
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UserService userService;
	
	// Constructors ---------------------------------------------------------

	public FollowUpUserController() {
		super();
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		FollowUp followUp;

		followUp = this.followUpService.create();
		res = this.createEditModelAndView(followUp);
		
		return res;
	}

	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int followUpId) {
		ModelAndView result;
		FollowUp followUp;
		User user;

		user = this.userService.findByPrincipal();
		followUp = this.followUpService.findOne(followUpId);
		result = this.createEditModelAndView(followUp);
		if (followUp.getArticle().getWriter().equals(user)) {
			followUp = this.followUpService.findOne(followUpId);
			result = this.createEditModelAndView(followUp);
			result.addObject("followUp", followUp);
		} else {
			result = new ModelAndView("redirect:../../");
		}
		
		return result;
	}

	// Saving --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid FollowUp followUp,
			final BindingResult binding) {
		ModelAndView res;
		followUp = this.followUpService.reconstruct(followUp, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(followUp,
					"followUp.params.error");
		else
			try {
				this.followUpService.save(followUp);
				res = new ModelAndView("redirect:../list.do?articleId=" + followUp.getArticle().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(followUp,
						"followUp.commit.error");
			}
		return res;
	}

	// Deleting --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final FollowUp followUp,
			final BindingResult binding) {
		ModelAndView res;
		try {
			this.followUpService.delete(followUp);
			res = new ModelAndView("redirect:../../");
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(followUp,
					"followUp.commit.error");
		}
		return res;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final FollowUp followUp) {
		ModelAndView result;

		result = this.createEditModelAndView(followUp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FollowUp followUp,
			final String message) {
		ModelAndView result;
		User user;
		
		user = userService.findByPrincipal();
		Collection<Article> articlesPublicated = new ArrayList<Article>();
		articlesPublicated = articleService.findArticlePublishedByUser(user.getId());
		
		result = new ModelAndView("followUp/user/edit");
		result.addObject("followUp", followUp);
		result.addObject("article", articlesPublicated);
		result.addObject("message", message);
		result.addObject("requestURI", "followUp/user/edit.do");

		return result;
	}

}
