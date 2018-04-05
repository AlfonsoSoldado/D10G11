package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import controllers.AbstractController;
import domain.Customer;

@Controller
@RequestMapping("/actor/customer")
public class ActorCustomerController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CustomerService customerService;

	// Constructors ---------------------------------------------------------

	public ActorCustomerController() {
		super();
	}

	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		result = this.createEditModelAndView(customer);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Customer customer, final BindingResult binding) {
		ModelAndView res;
		customer = this.customerService.reconstruct(customer, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(customer, "actor.params.error");
		else
			try {
				this.customerService.save(customer);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(customer, "actor.commit.error");
			}
		return res;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Customer customer) {
		ModelAndView result;

		result = this.createEditModelAndView(customer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Customer customer,
			final String message) {
		ModelAndView result;
		result = new ModelAndView("actor/customer/edit");
		result.addObject("actor", customer);
		result.addObject("message", message);
		result.addObject("requestUri", "actor/customer/edit.do");
		return result;

	}
	
}
