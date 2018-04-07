package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import domain.Chirp;
import domain.User;

@Service
@Transactional
public class ChirpService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ChirpRepository chirpRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private UserService userService;

	// Constructor ------------------------------------------------------------

	public ChirpService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Chirp create() {
		this.userService.checkAuthority();
		User user;
		Chirp result;
		Date moment;
		
		result = new Chirp();
		moment = new Date(System.currentTimeMillis() - 1000);
		user = userService.findByPrincipal();
		result.setMoment(moment);
		result.setUser(user);
		
		return result;
	}

	public Collection<Chirp> findAll() {
		Collection<Chirp> res;
		res = this.chirpRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Chirp findOne(int id) {
		Assert.isTrue(id != 0);
		Chirp res;
		res = this.chirpRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Chirp save(Chirp chirp) {
		Assert.notNull(chirp);
		Chirp res;
		res = this.chirpRepository.save(chirp);
		return res;
	}

	public void delete(Chirp chirp) {
		Assert.notNull(chirp);
		Assert.isTrue(chirp.getId() != 0);
		Assert.isTrue(this.chirpRepository.exists(chirp.getId()));
		this.chirpRepository.delete(chirp);
	}

	// Other business method --------------------------------------------------
	
	public Collection<Chirp> findChirpByUser(int id) {
		Collection<Chirp> res;
		res = this.chirpRepository.findChirpByUser(id);
		return res;
	}

}
