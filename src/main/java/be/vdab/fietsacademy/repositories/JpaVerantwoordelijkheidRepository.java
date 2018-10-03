package be.vdab.fietsacademy.repositories;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Validation;
import org.springframework.stereotype.Repository;
import org.springframework.validation.Validator;

import be.vdab.fietsacademy.entities.Verantwoordelijkheid;

@Repository
class JpaVerantwoordelijkheidRepository implements VerantwoordelijkheidRepository{

	private EntityManager manager;

	public JpaVerantwoordelijkheidRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public Optional<Verantwoordelijkheid> read(long id) {
		return Optional.ofNullable(manager.find(Verantwoordelijkheid.class, id));
	}

	@Override
	public void create(Verantwoordelijkheid verantwoordelijkheid)  {
		manager.persist(verantwoordelijkheid);
	}

}
