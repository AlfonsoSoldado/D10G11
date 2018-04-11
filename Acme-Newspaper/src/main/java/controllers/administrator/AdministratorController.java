/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import services.AdministratorService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService administratorService;

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result = new ModelAndView("administrator/display");
		result.addObject("averageNewspaperPerUser", this.administratorService.averageNewspaperPerUser());
		result.addObject("standardDesviationNewspaperPerUser",
				this.administratorService.standardDesviationNewspaperPerUser());
		result.addObject("averageArticlesPerUser", this.administratorService.averageArticlesPerUser());
		result.addObject("standardDesviationArticlesPerUser",
				this.administratorService.standardDesviationArticlesPerUser());
		result.addObject("averageArticlesPerNewspaper", this.administratorService.averageArticlesPerNewspaper());
		result.addObject("standardDesviationArticlesPerNewspaper",
				this.administratorService.standardDesviationArticlesPerNewspaper());
		result.addObject("newspapers10moreThanAvereage", this.administratorService.newspapers10moreThanAvereage());
		result.addObject("newspapers10fewerThanAvereage", this.administratorService.newspapers10fewerThanAvereage());
		result.addObject("ratioUsersCreatedEverNewspaper", this.administratorService.ratioUsersCreatedEverNewspaper());
		result.addObject("ratioUsersEverWrittenArticle", this.administratorService.ratioUsersEverWrittenArticle());
		result.addObject("averageFollowupsPerArticle", this.administratorService.averageFollowupsPerArticle());
		result.addObject("averageFollowupsPerArticleToOneWeekPublishedArticle",
				this.administratorService.averageFollowupsPerArticleToOneWeekPublishedArticle());
		result.addObject("averageFollowupsPerArticleToTwoWeekPublishedArticle",
				this.administratorService.averageFollowupsPerArticleToTwoWeekPublishedArticle());
		result.addObject("averageChirpsPerUser", this.administratorService.averageChirpsPerUser());
		result.addObject("standardDesviationChirpsPerUser",
				this.administratorService.standardDesviationChirpsPerUser());
		result.addObject("ratioUsersMorePosted75ChirpsOfAveragePerUser",
				this.administratorService.ratioUsersMorePosted75ChirpsOfAveragePerUser());
		result.addObject("ratioPublicVsPrivateNewspaper", this.administratorService.ratioPublicVsPrivateNewspaper());
		result.addObject("averageArticlesPerNewspaperPrivates",
				this.administratorService.averageArticlesPerNewspaperPrivates());
		result.addObject("averageArticlesPerNewspaperPublics",
				this.administratorService.averageArticlesPerNewspaperPublics());
		result.addObject("ratioPrivateNewspaperSubsciptionsVsTotalCustomers",
				this.administratorService.ratioPrivateNewspaperSubsciptionsVsTotalCustomers());
		result.addObject("AverageRatioPrivateVsPublicNewspaperPerPublisher",
				this.administratorService.AverageRatioPrivateVsPublicNewspaperPerPublisher());

		return result;
	}

}
