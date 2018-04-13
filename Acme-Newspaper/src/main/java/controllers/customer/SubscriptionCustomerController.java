package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;
import services.SubscriptionService;
import controllers.AbstractController;
import domain.Newspaper;
import domain.Subscription;

@Controller
@RequestMapping("/subscription/customer")
public class SubscriptionCustomerController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private SubscriptionService subscriptionService;
	
	// Supporting services --------------------------------------------------
	
	@Autowired
	private NewspaperService newspaperService;
	
	// Constructors ---------------------------------------------------------

	public SubscriptionCustomerController() {
		super();
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int newspaperId) {
		ModelAndView res;
		Subscription subscription;
		Newspaper newspaper;
		
		newspaper = this.newspaperService.findOne(newspaperId);
		subscription = this.subscriptionService.create(newspaper);
		
		res = this.createEditModelAndView(subscription);
		
		return res;
	}

	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int subscriptionId) {
		ModelAndView result;
		Subscription subscription;

		subscription = this.subscriptionService.findOne(subscriptionId);
		result = this.createEditModelAndView(subscription);
		result.addObject("subscription", subscription);
		
		return result;
	}

	// Saving --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Subscription subscription,
			final BindingResult binding) {
		ModelAndView res;
		subscription = this.subscriptionService.reconstruct(subscription, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(subscription,
					"subscription.params.error");
		else
			try {
				this.subscriptionService.save(subscription);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(subscription,
						"subscription.commit.error");
			}
		return res;
	}

	// Deleting --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Subscription subscription,
			final BindingResult binding) {
		ModelAndView res;
		try {
			this.subscriptionService.delete(subscription);
			res = new ModelAndView("redirect:../../");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			res = this.createEditModelAndView(subscription,
					"subscription.commit.error");
		}
		return res;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Subscription subscription) {
		ModelAndView result;

		result = this.createEditModelAndView(subscription, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Subscription subscription,
			final String message) {
		ModelAndView result;

		result = new ModelAndView("subscription/customer/edit");
		result.addObject("subscription", subscription);
		result.addObject("message", message);
		result.addObject("requestURI", "subscription/customer/edit.do");

		return result;
	}

}
