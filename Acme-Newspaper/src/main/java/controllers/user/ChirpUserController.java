package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import controllers.AbstractController;
import domain.Chirp;

@Controller
@RequestMapping("/chirp/user")
public class ChirpUserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private ChirpService chirpService;
	
	// Supporting services --------------------------------------------------
	
	// Constructors ---------------------------------------------------------

	public ChirpUserController() {
		super();
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Chirp chirp;

		chirp = this.chirpService.create();
		res = this.createEditModelAndView(chirp);
		
		return res;
	}

	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int chirpId) {
		ModelAndView result;
		Chirp chirp;

		chirp = this.chirpService.findOne(chirpId);
		result = this.createEditModelAndView(chirp);
		result.addObject("chirp", chirp);
		
		return result;
	}

	// Saving --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Chirp chirp,
			final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createEditModelAndView(chirp,
					"chirp.params.error");
		else
			try {
				this.chirpService.save(chirp);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(chirp,
						"chirp.commit.error");
			}
		return res;
	}

	// Deleting --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Chirp chirp,
			final BindingResult binding) {
		ModelAndView res;
		try {
			this.chirpService.delete(chirp);
			res = new ModelAndView("redirect:../../");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			res = this.createEditModelAndView(chirp,
					"chirp.commit.error");
		}
		return res;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Chirp chirp) {
		ModelAndView result;

		result = this.createEditModelAndView(chirp, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Chirp chirp,
			final String message) {
		ModelAndView result;

		result = new ModelAndView("chirp/user/edit");
		result.addObject("chirp", chirp);
		result.addObject("message", message);
		result.addObject("requestURI", "chirp/user/edit.do");

		return result;
	}

}
