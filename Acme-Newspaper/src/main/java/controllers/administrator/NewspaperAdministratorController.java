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

import services.NewspaperService;
import controllers.AbstractController;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper/administrator")
public class NewspaperAdministratorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private NewspaperService newspaperService;

	// Supporting services --------------------------------------------------

	// Constructors ---------------------------------------------------------

	public NewspaperAdministratorController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Newspaper> newspaper;

		newspaperService.checkTabooWords();
		newspaper = this.newspaperService.findNewspaperTaboo();

		result = new ModelAndView("newspaper/administrator/list");
		result.addObject("newspaper", newspaper);
		result.addObject("requestURI", "newspaper/administrator/list.do");

		return result;
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

	// Deleting --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Newspaper newspaper,
			final BindingResult binding) {
		ModelAndView res;
		try {
			this.newspaperService.delete(newspaper);
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

		result = new ModelAndView("newspaper/administrator/edit");
		result.addObject("newspaper", newspaper);
		result.addObject("message", message);
		result.addObject("requestURI", "newspaper/administrator/edit.do");

		return result;
	}

}
