package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import controllers.AbstractController;
import domain.Article;

@Controller
@RequestMapping("/article/administrator")
public class ArticleAdministratorController extends AbstractController{

	// Services -------------------------------------------------------------

		@Autowired
		private ArticleService articleService;

		// Supporting services --------------------------------------------------

		// Constructors ---------------------------------------------------------

		public ArticleAdministratorController() {
			super();
		}

		// Listing --------------------------------------------------------------

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			Collection<Article> article;

			articleService.checkTabooWords();
			article = this.articleService.findArticleTaboo();

			result = new ModelAndView("article/administrator/list");
			result.addObject("article", article);
			result.addObject("requestURI", "article/administrator/list.do");

			return result;
		}
	
}
