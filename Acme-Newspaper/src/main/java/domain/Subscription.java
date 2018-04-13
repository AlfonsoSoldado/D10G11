package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "newspaper_id,customer_id")})
public class Subscription extends DomainEntity {

	// Attributes ----------------------------------------------

	private CreditCard creditcard;

	@Valid
	public CreditCard getCreditcard() {
		return creditcard;
	}

	public void setCreditcard(CreditCard creditcard) {
		this.creditcard = creditcard;
	}

	// Relationships -------------------------------------------

	private Customer customer;
	private Newspaper newspaper;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Newspaper getNewspaper() {
		return newspaper;
	}

	public void setNewspaper(Newspaper newspaper) {
		this.newspaper = newspaper;
	}

}
