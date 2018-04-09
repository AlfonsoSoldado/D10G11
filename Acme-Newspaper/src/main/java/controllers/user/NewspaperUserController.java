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

import services.NewspaperService;
import controllers.AbstractController;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper/user")
public class NewspaperUserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private NewspaperService newspaperService;

	// Supporting services --------------------------------------------------

	// Constructors ---------------------------------------------------------

	public NewspaperUserController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/listNotPublicated", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Newspaper> newspaper;
		Collection<Newspaper> all;

		this.newspaperService.checkTabooWords();

		all = this.newspaperService.findAll();
		newspaper = this.newspaperService.findNewspapersPublicated();
		all.removeAll(newspaper);
		all.removeAll(this.newspaperService.findNewspaperTaboo());

		result = new ModelAndView("newspaper/list");
		result.addObject("newspaper", all);
		result.addObject("requestURI", "newspaper/list.do");

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Newspaper newspaper;

		newspaper = this.newspaperService.create();
		res = this.createEditModelAndView(newspaper);

		return res;
	}

	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int newspaperId) {
		ModelAndView result;
		Newspaper newspaper;

		newspaper = this.newspaperService.findOne(newspaperId);
		result = this.createEditModelAndView(newspaper);
		result.addObject("newspaper", newspaper);

		return result;
	}

	// Saving --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Newspaper newspaper,
			final BindingResult binding) {
		ModelAndView res;
		newspaper = this.newspaperService.reconstruct(newspaper, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(newspaper,
					"newspaper.params.error");
		else
			try {
				this.newspaperService.save(newspaper);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(newspaper,
						"newspaper.commit.error");
			}
		return res;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Newspaper newspaper) {
		ModelAndView result;

		result = this.createEditModelAndView(newspaper, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Newspaper newspaper,
			final String message) {
		ModelAndView result;
		final Collection<Boolean> hide = new ArrayList<>();

		hide.add(false);
		hide.add(true);

		result = new ModelAndView("newspaper/user/edit");
		result.addObject("newspaper", newspaper);
		result.addObject("hide", hide);
		result.addObject("message", message);
		result.addObject("requestURI", "newspaper/user/edit.do");

		return result;
	}

}
