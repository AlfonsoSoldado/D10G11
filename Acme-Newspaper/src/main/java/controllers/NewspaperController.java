package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.CustomerService;
import services.NewspaperService;
import services.SubscriptionService;
import services.UserService;
import domain.Article;
import domain.Customer;
import domain.Newspaper;
import domain.Subscription;
import domain.User;

@Controller
@RequestMapping("/newspaper")
public class NewspaperController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private NewspaperService newspaperService;
	
	// Supporting services --------------------------------------------------
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	private UserService userService;
	
	// Constructors ---------------------------------------------------------

	public NewspaperController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Newspaper> newspaper;
		Collection<Newspaper> privateNewspaper;
		User currentUser;
		Integer currentUserId;
		
		this.newspaperService.checkTabooWords();

		newspaper = this.newspaperService.findNewspapersPublicated();
		newspaper.removeAll(this.newspaperService.findNewspaperTaboo());
		newspaper.removeAll(this.newspaperService.findNewspapersNotPublicated());
		newspaper.removeAll(this.newspaperService.findNewspapersPrivate());
		
		Collection<Newspaper> privates;
		privates = this.newspaperService.findNewspapersPrivate();
		privates.removeAll(this.newspaperService.findNewspaperTaboo());
		
		Collection<Newspaper> acum;
		acum = newspaperService.findNewspapersPrivate();
		acum.removeAll(this.newspaperService.findNewspaperTaboo());
		
		
		try {
			Customer c;
			c = customerService.findByPrincipal();
			
			Collection<Subscription> sC;
			sC = subscriptionService.findSubscriptionByCustomer(c.getId());

			for(Subscription s: sC){
				for(Newspaper n: privates){
					if(s.getNewspaper().getId() == n.getId()){
						newspaper.add(n);
					}
				}
			}
		} catch (Exception e) {
			privateNewspaper = newspaperService.findNewspapersPrivate();
			newspaper.removeAll(privateNewspaper);
		}
		
		try {
			User user;
			user = userService.findByPrincipal();
			
			for(Newspaper n: acum){
				if(n.getPublisher().getId() == user.getId()){
					newspaper.add(n);
				}
			}
		} catch (Exception e) {
		}
		newspaper.removeAll(this.newspaperService.findNewspapersNotPublicated());

		result = new ModelAndView("newspaper/list");
		result.addObject("newspaper", newspaper);
		try {
			currentUser = this.userService.findByPrincipal();
			currentUserId = currentUser.getId();
			result.addObject("currentUserId", currentUserId);
			result.addObject("requestURI", "newspaper/list.do");
		} catch (Exception e) {
			result.addObject("requestURI", "newspaper/list.do");
		}
			
		return result;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchList(@RequestParam String criteria) {
		ModelAndView res;
		Collection<Newspaper> newspapers;
		Collection<Newspaper> privateNewspaper;
		
		newspapers = this.newspaperService.searchNewspaper(criteria);
		newspapers.removeAll(this.newspaperService.findNewspaperTaboo());
		
		Collection<Newspaper> privates;
		privates = this.newspaperService.findNewspapersPrivate();
		privates.removeAll(this.newspaperService.findNewspaperTaboo());
		
		Collection<Newspaper> acum;
		acum = newspaperService.findNewspapersPrivate();
		acum.removeAll(this.newspaperService.findNewspaperTaboo());
		
		try {
			Customer c;
			c = customerService.findByPrincipal();
			
			Collection<Subscription> sC;
			sC = subscriptionService.findSubscriptionByCustomer(c.getId());

			for(Subscription s: sC){
				for(Newspaper n: privates){
					if(s.getNewspaper().getId() == n.getId()){
						newspapers.add(n);
					}
				}
			}
		} catch (Exception e) {
			privateNewspaper = newspaperService.findNewspapersPrivate();
			newspapers.removeAll(privateNewspaper);
		}
		
		try {
			User user;
			user = userService.findByPrincipal();
			
			for(Newspaper n: acum){
				if(n.getPublisher().getId() == user.getId()){
					newspapers.add(n);
				}
			}
		} catch (Exception e) {
		}
		
		
		res = new ModelAndView("newspaper/list");
		res.addObject("newspaper", newspapers);
		res.addObject("requestURI", "newspaper/list.do");
		return res;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int newspaperId) {
		ModelAndView result;
		Collection<Article> articles = articleService.findArticleByNewspaper(newspaperId); 
		final Newspaper newspaper = this.newspaperService.findOne(newspaperId);

		result = new ModelAndView("newspaper/display");
		result.addObject("newspaper", newspaper);
		result.addObject("articles", articles);
		result.addObject("requestURI", "newspaper/display.do");

		return result;
	}

}
