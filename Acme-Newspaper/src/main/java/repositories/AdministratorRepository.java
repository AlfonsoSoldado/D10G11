package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Newspaper;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a join a.userAccount ua where ua.id = ?1")
	Administrator findByPrincipal(int id);

	@Query("select avg(m.newspapers.size*1.0) from User m")
	double averageNewspaperPerUser();

	@Query("select stddev(m.newspapers.size*1.0) from User m")
	double standardDesviationNewspaperPerUser();

	@Query("select avg(m.articles.size*1.0) from User m")
	double averageArticlesPerUser();

	@Query("select stddev(m.articles.size*1.0) from User m")
	double standardDesviationArticlesPerUser();

	@Query("select avg(m.articles.size*1.0) from Newspaper m")
	double averageArticlesPerNewspaper();

	@Query("select stddev(m.articles.size*1.0) from Newspaper m")
	double standardDesviationArticlesPerNewspaper();

	@Query("select m from Newspaper m where m.articles.size > (select avg(v.articles.size)*1.1 from Newspaper v)")
	Newspaper newspapers10moreThanAvereage();

	@Query("select m from Newspaper m where m.articles.size < (select avg(v.articles.size)*1.1 from Newspaper v)")
	Newspaper newspapers10fewerThanAvereage();

	@Query("select (select count(a) from User a where a.newspapers.size>0)/count(ap)*1.0 from User ap")
	double ratioUsersCreatedEverNewspaper();

	/// The ratio of users who have ever written an article.
	@Query("select (select count(a) from User a where a.articles.size>0)/count(ap)*1.0 from User ap")
	double ratioUsersEverWrittenArticle();

	@Query("select avg(m.followUps.size*1.0) from Article m")
	double averageFollowupsPerArticle();

	// falta sumarle 7 dias al momento de publicacion
	@Query("select avg(m.followUps.size*1.0) from Article m  join m.followUps a where a.moment<= m.moment")
	double averageFollowupsPerArticleToOneWeekPublishedArticle();

	// The average number of follow-ups per article up to two weeks after the
	// corresponding
	// newspapers been published.
	// falta sumarle 14 dias al momento de publicacion
	@Query("select avg(m.followUps.size*1.0) from Article m  join m.followUps a where a.moment<= m.moment")
	double averageFollowupsPerArticleToTwoWeekPublishedArticle();

	@Query("select avg(m.chirps.size*1.0) from User m")
	double averageChirpsPerUser();

	@Query("select stddev(m.chirps.size*1.0) from User m")
	double standardDesviationChirpsPerUser();

	@Query("select count(m)/(select count(n) from User n) from User m where m.chirps.size > (select avg(v.chirps.size)*0.75 from User v))")
	double ratioUsersMorePosted75ChirpsOfAveragePerUser();

	@Query("select count(m)/(select count(v) from Newspaper v) from Newspaper m where m.hide=false")
	double ratioPublicNewspaper();

	@Query("select count(m)/(select count(v) from Newspaper v) from Newspaper m where m.hide=true")
	double ratioPrivateNewspaper();

	@Query("select avg(m.articles.size*1.0) from Newspaper m where m.hide=true")
	Double averageArticlesPerNewspaperPrivates();

	@Query("select avg(m.articles.size*1.0) from Newspaper m where m.hide=false")
	Double averageArticlesPerNewspaperPublics();

	@Query("select count(m)/(select count(v) from Subscription v) from Subscription m join m.newspaper n where n.hide=true")
	double ratioPrivateNewspaperSubsciptions();

	@Query("select count(m) from Customer m")
	double numberOfCustomers();

	@Query("select count(m)/(select count(v) from User v) from Newspaper m where m.hide=true")
	double ratioPrivateNewspaperPerPublisher();

	@Query("select count(m)/(select count(v) from User v) from Newspaper m where m.hide=false")
	double ratioPublicNewspaperPerPublisher();

	@Query("select count(m)/(select count(v) from Newspaper v) from Newspaper m where m.hide=true")
	double AveragePrivateNewspaperPerPublisher();

	@Query("select count(m)/(select count(v) from Newspaper v) from Newspaper m where m.hide=false")
	double AveragePublicNewspaperPerPublisher();
}
