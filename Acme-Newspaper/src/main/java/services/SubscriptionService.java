package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubscriptionRepository;
import domain.Subscription;

@Service
@Transactional
public class SubscriptionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public SubscriptionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Subscription create() {
		Subscription result;
		result = new Subscription();
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

}
