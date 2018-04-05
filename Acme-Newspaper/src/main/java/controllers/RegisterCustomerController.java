package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import domain.Customer;
import forms.CustomerForm;

@Controller
@RequestMapping("/customer")
public class RegisterCustomerController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CustomerService customerService;

	// Constructors ---------------------------------------------------------

	public RegisterCustomerController() {
		super();
	}

	// Registering ----------------------------------------------------------

	@RequestMapping(value = "/register_Customer", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Customer customer;
		customer = this.customerService.create();

		CustomerForm customerForm;
		customerForm = new CustomerForm(customer);

		res = new ModelAndView("customer/register_Customer");
		res.addObject("customerForm", customerForm);

		return res;
	}

	@RequestMapping(value = "/register_Customer", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("customerForm") CustomerForm customerForm,
			final BindingResult binding) {
		ModelAndView res;
		customerForm = this.customerService.reconstruct(customerForm, binding);
		if (binding.hasErrors()) {
			res = this.createEditModelAndView(customerForm, "actor.params.error");
		} else {
			try {
				if ((customerForm.getCustomer().getId() == 0)) {
					Assert.isTrue(customerForm.getCustomer().getUserAccount()
							.getPassword()
							.equals(customerForm.getConfirmPassword()),
							"password does not match");
				}
				this.customerService.save(customerForm.getCustomer());
				res = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("password does not match"))
					res = this.createEditModelAndView(customerForm,
							"actor.password.check");
				else if (oops
						.getMessage()
						.equals("could not execute statement; SQL [n/a]; constraint [null]"
								+ "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					res = this.createEditModelAndView(customerForm,
							"actor.commit.duplicate");
				else
					res = this.createEditModelAndView(customerForm,
							"actor.commit.error");
			}
		}
		return res;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final CustomerForm customerForm) {
		ModelAndView result;

		result = this.createEditModelAndView(customerForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CustomerForm customerForm,
			final String message) {
		ModelAndView result;

		result = new ModelAndView("customer/register_Customer");
		result.addObject("customer", customerForm);
		result.addObject("message", message);

		return result;
	}
}
