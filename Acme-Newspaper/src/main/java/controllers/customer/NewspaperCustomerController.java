package controllers.customer;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.NewspaperService;
import services.SubscriptionService;
import controllers.AbstractController;
import domain.Customer;
import domain.Newspaper;
import domain.Subscription;

@Controller
@RequestMapping("/newspaper/customer")
public class NewspaperCustomerController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private NewspaperService newspaperService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	// Constructors ---------------------------------------------------------

	public NewspaperCustomerController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Newspaper> newspaper;
		
		this.newspaperService.checkTabooWords();

		newspaper = this.newspaperService.findNewspapersPrivate();
		newspaper.removeAll(this.newspaperService.findNewspaperTaboo());
		newspaper.removeAll(this.newspaperService.findNewspapersNotPublicated());
		
		try {
			Customer c;
			c = customerService.findByPrincipal();
			Collection<Subscription> sC;
			sC = subscriptionService.findSubscriptionByCustomer(c.getId());
			for(Subscription s: sC){
				for(Newspaper n: newspaper){
					if(s.getNewspaper().getId() == n.getId()){
						newspaper.remove(n);
					}
				}
			}
		} catch (Exception e) {
			newspaper = new ArrayList<Newspaper>();
		}

		result = new ModelAndView("newspaper/customer/list");
		result.addObject("newspaper", newspaper);
		result.addObject("requestURI", "newspaper/customer/list.do");

		return result;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchList(@RequestParam String criteria) {
		ModelAndView res;
		Collection<Newspaper> newspapers;
		newspapers = this.newspaperService.searchNewspaper(criteria);
		newspapers.removeAll(this.newspaperService.findNewspaperTaboo());
		newspapers.removeAll(this.newspaperService.findNewspapersPrivate());
		
		Collection<Newspaper> acum;
		acum = this.newspaperService.searchNewspaper(criteria);
		acum.removeAll(this.newspaperService.findNewspaperTaboo());
		
		try {
			Customer c;
			c = customerService.findByPrincipal();
			Collection<Subscription> sC;
			sC = subscriptionService.findSubscriptionByCustomer(c.getId());
			for(Subscription s: sC){
				for(Newspaper n: acum){
					if(s.getNewspaper().getId() != n.getId()){
						newspapers.add(n);
					} else {
						newspapers.remove(n);
					}
				}
			}
		} catch (Exception e) {
			newspapers = new ArrayList<Newspaper>();
		}
		
		res = new ModelAndView("newspaper/customer/list");
		res.addObject("newspaper", newspapers);
		res.addObject("requestURI", "newspaper/customer/list.do");
		return res;
	}
}
