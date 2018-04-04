package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NewspaperRepository;
import domain.Newspaper;

@Service
@Transactional
public class NewspaperService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private NewspaperRepository newspaperRepository;

	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public NewspaperService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Newspaper create() {
		Newspaper result;
		result = new Newspaper();
		return result;
	}

	public Collection<Newspaper> findAll() {
		Collection<Newspaper> res;
		res = this.newspaperRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Newspaper findOne(int id) {
		Assert.isTrue(id != 0);
		Newspaper res;
		res = this.newspaperRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Newspaper save(Newspaper newspaper) {
		Assert.notNull(newspaper);
		Newspaper res;
		res = this.newspaperRepository.save(newspaper);
		return res;
	}

	public void delete(Newspaper newspaper) {
		Assert.notNull(newspaper);
		Assert.isTrue(newspaper.getId() != 0);
		Assert.isTrue(this.newspaperRepository.exists(newspaper.getId()));
		this.newspaperRepository.delete(newspaper);
	}

	// Other business method --------------------------------------------------

}
