package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.ChirpService;
import services.NewspaperService;
import services.UserService;
import domain.Article;
import domain.Chirp;
import domain.Newspaper;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private UserService userService;

	// Supporting services --------------------------------------------------

	@Autowired
	private ChirpService chirpService;

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private NewspaperService newspaperService;

	// Constructors ---------------------------------------------------------

	public UserController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> user;

		user = this.userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("user", user);
		result.addObject("requestURI", "user/list.do");

		return result;
	}

	// Display --------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int userId) {
		ModelAndView result;
		User user;
		Collection<Chirp> chirps;
		Collection<Article> articles;
		Collection<Newspaper> newspapersPublished;

		user = this.userService.findOne(userId);
		chirps = chirpService.findChirpByUser(userId);
		articles = articleService.findArticlePublishedByUser(userId);
		newspapersPublished = newspaperService.findNewspapersPublicated();
		
		for(Article a: articles){
			if(!newspapersPublished.contains(a.getNewspaper())){
				articles.remove(a);
			}
		}
		
		result = new ModelAndView("user/display");
		result.addObject("user", user);
		result.addObject("chirps", chirps);
		result.addObject("articles", articles);
		result.addObject("followTable", user);
		result.addObject("requestURI", "user/display.do");

		return result;
	}
	
	// Followers and Following
	
	@RequestMapping(value = "/listFollowers", method = RequestMethod.GET)
	public ModelAndView listFollowers(@RequestParam int userId) {
		ModelAndView result;
		User user;
		Collection<User> followers;

		user = this.userService.findOne(userId);
		followers = user.getFollowers();

		result = new ModelAndView("user/listFollowers");
		result.addObject("user", followers);
		result.addObject("requestURI", "user/listFollowers.do");

		return result;
	}
	
	@RequestMapping(value = "/listFollowing", method = RequestMethod.GET)
	public ModelAndView listFollowing(@RequestParam int userId) {
		ModelAndView result;
		User user;
		Collection<User> following;

		user = this.userService.findOne(userId);
		following = user.getFollowing();

		result = new ModelAndView("user/listFollowing");
		result.addObject("user", following);
		result.addObject("requestURI", "user/listFollowing.do");

		return result;
	}
	
	// Follow
	
	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView follow(@RequestParam int userId) {
		ModelAndView result;
		User user;
		
		user = userService.findOne(userId);
		userService.follow(userId);
		result = this.createEditModelAndView(user);

		return result;
	}
	
	@RequestMapping(value = "/unfollow", method = RequestMethod.GET)
	public ModelAndView unfollow(@RequestParam int userId) {
		ModelAndView result;
		User user;
		
		user = userService.findOne(userId);
		userService.unfollow(userId);
		result = this.createEditModelAndViewUnfollow(user);

		return result;
	}
	
	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final User user) {
		ModelAndView result;

		result = this.createEditModelAndView(user, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final User user,
			final String message) {
		ModelAndView result;
		result = new ModelAndView("user/follow");
		result.addObject("message", message);
		result.addObject("requestUri", "user/follow.do");
		return result;

	}
	
	protected ModelAndView createEditModelAndViewUnfollow(final User user) {
		ModelAndView result;

		result = this.createEditModelAndView(user, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewUnfollow(final User user,
			final String message) {
		ModelAndView result;
		result = new ModelAndView("user/unfollow");
		result.addObject("message", message);
		result.addObject("requestUri", "user/unfollow.do");
		return result;

	}

}
