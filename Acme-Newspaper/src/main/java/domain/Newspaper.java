package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Newspaper extends DomainEntity {

	// Attributes ----------------------------------------------

	private String title;
	private Date publication;
	private String description;
	private String picture;
	private Boolean hide;
	private Boolean taboo;

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getPublication() {
		return publication;
	}

	public void setPublication(Date publication) {
		this.publication = publication;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Boolean getHide() {
		return hide;
	}

	public void setHide(Boolean hide) {
		this.hide = hide;
	}
	
	public Boolean getTaboo() {
		return taboo;
	}

	public void setTaboo(Boolean taboo) {
		this.taboo = taboo;
	}

	// Relationships -------------------------------------------

	private Collection<Article> articles;
	private User publisher;

	@Valid
	@OneToMany(mappedBy = "newspaper", cascade = CascadeType.REMOVE)
	public Collection<Article> getArticles() {
		return articles;
	}

	public void setArticles(Collection<Article> articles) {
		this.articles = articles;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}

}
