package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Newspaper;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Integer> {
	
	@Query("select n from Newspaper n where (n.title like %?1% or n.description like %?1%)")
	Collection<Newspaper> searchNewspaper(String criteria);
	
	@Query("select n from Newspaper n join n.articles a where draftmode = false")
	Collection<Newspaper> findNewspapersPublicated();
	
	@Query("select n from Newspaper n join n.articles a where draftmode = true")
	Collection<Newspaper> findNewspapersNotPublicated();

	@Query("select n from Newspaper n where n.taboo=true")
	Collection<Newspaper> findNewspaperTaboo();
	
	@Query("select n from Newspaper n where n.hide = false")
	Collection<Newspaper> findNewspapersPublic();
	
	@Query("select n from Newspaper n where n.hide = true")
	Collection<Newspaper> findNewspapersPrivate();
}
