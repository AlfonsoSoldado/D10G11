package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FollowUpRepository;
import domain.FollowUp;

@Service
@Transactional
public class FollowUpService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FollowUpRepository followUpRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private Validator validator;

	// Constructor ------------------------------------------------------------

	public FollowUpService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public FollowUp create() {
		this.userService.checkAuthority();
		FollowUp result;
		Date moment;
		
		result = new FollowUp();
		moment = new Date(System.currentTimeMillis() - 1000);
		result.setMoment(moment);
		
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
		this.userService.checkAuthority();
		Assert.isTrue(followUp.getArticle().getWriter().equals(this.userService.findByPrincipal()));
		Assert.notNull(followUp);
		FollowUp res;
		res = this.followUpRepository.save(followUp);
		return res;
	}

	public void delete(FollowUp followUp) {
		this.administratorService.checkAuthority();
		Assert.notNull(followUp);
		Assert.isTrue(followUp.getId() != 0);
		Assert.isTrue(this.followUpRepository.exists(followUp.getId()));
		this.followUpRepository.delete(followUp);
	}

	// Other business method --------------------------------------------------
	
	public Collection<FollowUp> findFollowUpByArticle(int id) {
		Collection<FollowUp> res;
		res = this.followUpRepository.findFollowUpByArticle(id);
		return res;
	}
	
	public FollowUp reconstruct(final FollowUp followUp, final BindingResult binding) {
		FollowUp res;
		FollowUp followUpFinal;
		Date moment;

		if (followUp.getId() == 0) {
			moment = new Date(System.currentTimeMillis() - 1000);
			followUp.setMoment(moment);
			
			res = followUp;
		} else {
			followUpFinal = this.followUpRepository.findOne(followUp.getId());
			
			followUp.setId(followUpFinal.getId());
			followUp.setVersion(followUpFinal.getVersion());
			followUp.setArticle(followUpFinal.getArticle());

			res = followUp;
		}
		this.validator.validate(res, binding);
		return res;
	}

}
