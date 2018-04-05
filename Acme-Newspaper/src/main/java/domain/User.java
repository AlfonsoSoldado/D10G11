package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	// Attributes ----------------------------------------------

	// Relationships -------------------------------------------

	private Collection<Article> articles;
	private Collection<Chirp> chirps;
	private Collection<Newspaper> newspapers;
	private Collection<User> following;
	private Collection<User> followers;

	@Valid
	@OneToMany(mappedBy = "writer")
	public Collection<Article> getArticles() {
		return articles;
	}

	public void setArticles(Collection<Article> articles) {
		this.articles = articles;
	}

	@Valid
	@OneToMany(mappedBy = "user")
	public Collection<Chirp> getChirps() {
		return chirps;
	}

	public void setChirps(Collection<Chirp> chirps) {
		this.chirps = chirps;
	}

	@Valid
	@OneToMany(mappedBy = "publisher")
	public Collection<Newspaper> getNewspapers() {
		return newspapers;
	}

	public void setNewspapers(Collection<Newspaper> newspapers) {
		this.newspapers = newspapers;
	}

	@Valid
	@ManyToMany
	public Collection<User> getFollowing() {
		return following;
	}

	public void setFollowing(Collection<User> following) {
		this.following = following;
	}

	@Valid
	@ManyToMany(mappedBy = "following")
	public Collection<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Collection<User> followers) {
		this.followers = followers;
	}

}
