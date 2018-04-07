package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;
import controllers.AbstractController;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper/administrator")
public class NewspaperAdministratorController extends AbstractController{

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
	
}
