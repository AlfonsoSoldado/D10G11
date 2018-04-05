package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
	
	@Query("select a from Article a where a.newspaper.id=?1")
	Collection<Article> findArticleByNewspaper(int id);
	
	@Query("select a from Article a where (a.title like %?1% or a.summary like %?1% or a.body like %?1%)")
	Collection<Article> searchArticle(String criteria);

}
