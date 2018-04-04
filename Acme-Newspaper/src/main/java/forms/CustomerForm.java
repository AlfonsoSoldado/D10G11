package forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.Customer;

public class CustomerForm {
	
	@Valid
	private Customer customer;
	private String confirmPassword;
	private Boolean terms;
	
	public CustomerForm() {
		super();
	}

	public CustomerForm(final Customer customer) {
		this.customer = customer;
		this.confirmPassword = "";
		this.terms = false;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@NotNull
	public Boolean getTerms() {
		return terms;
	}
	public void setTerms(Boolean terms) {
		this.terms = terms;
	}

}
