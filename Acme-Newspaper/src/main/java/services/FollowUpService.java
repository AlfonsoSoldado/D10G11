package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FollowUpRepository;
import domain.FollowUp;

@Service
@Transactional
public class FollowUpService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FollowUpRepository followUpRepository;

	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public FollowUpService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public FollowUp create() {
		FollowUp result;
		result = new FollowUp();
		return result;
	}

	public Collection<FollowUp> findAll() {
		Collection<FollowUp> res;
		res = this.followUpRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public FollowUp findOne(int id) {
		Assert.isTrue(id != 0);
		FollowUp res;
		res = this.followUpRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public FollowUp save(FollowUp followUp) {
		Assert.notNull(followUp);
		FollowUp res;
		res = this.followUpRepository.save(followUp);
		return res;
	}

	public void delete(FollowUp followUp) {
		Assert.notNull(followUp);
		Assert.isTrue(followUp.getId() != 0);
		Assert.isTrue(this.followUpRepository.exists(followUp.getId()));
		this.followUpRepository.delete(followUp);
	}

	// Other business method --------------------------------------------------

}
