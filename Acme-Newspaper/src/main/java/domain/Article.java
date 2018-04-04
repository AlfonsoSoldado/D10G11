package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
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
public class Article extends DomainEntity {

	// Attributes ----------------------------------------------

	private String Title;
	private Date moment;
	private String summary;
	private String body;
	private Collection<String> pictures;
	private Boolean draftmode;

	@NotBlank
	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@NotBlank
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@ElementCollection
	public Collection<String> getPictures() {
		return pictures;
	}

	public void setPictures(Collection<String> pictures) {
		this.pictures = pictures;
	}

	public Boolean getDraftmode() {
		return draftmode;
	}

	public void setDraftmode(Boolean draftmode) {
		this.draftmode = draftmode;
	}

	// Relationships -------------------------------------------

	private Collection<FollowUp> followUps;
	private Newspaper newspaper;
	private User user;

	@Valid
	@OneToMany
	public Collection<FollowUp> getFollowUps() {
		return followUps;
	}

	public void setFollowUps(Collection<FollowUp> followUps) {
		this.followUps = followUps;
	}

	@Valid
	@ManyToOne(optional = true)
	public Newspaper getNewspaper() {
		return newspaper;
	}

	public void setNewspaper(Newspaper newspaper) {
		this.newspaper = newspaper;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}