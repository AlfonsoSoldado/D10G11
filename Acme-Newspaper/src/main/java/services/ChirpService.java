package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import domain.Chirp;

@Service
@Transactional
public class ChirpService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ChirpRepository chirpRepository;

	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public ChirpService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Chirp create() {
		Chirp result;
		result = new Chirp();
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

}
