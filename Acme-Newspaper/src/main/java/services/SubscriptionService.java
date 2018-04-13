package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SubscriptionRepository;
import domain.CreditCard;
import domain.Customer;
import domain.Newspaper;
import domain.Subscription;

@Service
@Transactional
public class SubscriptionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private Validator validator;

	// Constructor ------------------------------------------------------------

	public SubscriptionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Subscription create(Newspaper newspaper) {
		this.customerService.checkAuthority();
		Customer customer;
		Subscription result;
		
		customer = customerService.findByPrincipal();
		result = new Subscription();
		result.setCustomer(customer);
		result.setNewspaper(newspaper);
		
		return result;
	}

	public Collection<Subscription> findAll() {
		Collection<Subscription> res;
		res = this.subscriptionRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Subscription findOne(int id) {
		Assert.isTrue(id != 0);
		Subscription res;
		res = this.subscriptionRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Subscription save(Subscription subscription) {
		Assert.notNull(subscription);
		Subscription res;
		Assert.isTrue(this.checkCreditCard(subscription.getCreditcard()));
		res = this.subscriptionRepository.save(subscription);
		return res;
	}

	public void delete(Subscription subscription) {
		Assert.notNull(subscription);
		Assert.isTrue(subscription.getId() != 0);
		Assert.isTrue(this.subscriptionRepository.exists(subscription.getId()));
		this.subscriptionRepository.delete(subscription);
	}

	// Other business method --------------------------------------------------
	
	public Collection<Subscription> findSubscriptionByNewspaper(int id) {
		Collection<Subscription> res;
		res = this.subscriptionRepository.findSubscriptionByNewspaper(id);
		return res;
	}

	public Collection<Subscription> findSubscriptionByCustomer(int id) {
		Collection<Subscription> res;
		res = this.subscriptionRepository.findSubscriptionByCustomer(id);
		return res;
	}
	
	public Subscription reconstruct(final Subscription subscription, final BindingResult binding) {
		Subscription res;
		
		if (subscription.getId() == 0) {
			
			Customer customer;
			customer = this.customerService.findByPrincipal();
			subscription.setCustomer(customer);

			res = subscription;
		} else {
			res = subscription;
		}
		this.validator.validate(res, binding);
		return res;
	}
	
	public boolean checkCreditCard(final CreditCard creditCard) {
		boolean res;
		Calendar calendar;
		int actualYear;

		res = false;
		calendar = new GregorianCalendar();
		actualYear = calendar.get(Calendar.YEAR);
		actualYear = actualYear % 100;
		
		if (creditCard.getExpirationYear() != null) {
			if (creditCard.getExpirationYear() > actualYear) {
				res = true;
			} else if (creditCard.getExpirationYear() == actualYear && creditCard.getExpirationMonth() >= calendar.get(Calendar.MONTH)) {
				res = true;
			} 
		} 
		return res;
	}

}
