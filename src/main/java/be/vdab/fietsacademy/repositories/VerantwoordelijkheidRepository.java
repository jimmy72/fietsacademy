package be.vdab.fietsacademy.repositories;

import java.util.Optional;

import be.vdab.fietsacademy.entities.Verantwoordelijkheid;

public interface VerantwoordelijkheidRepository {
	public abstract Optional<Verantwoordelijkheid> read(long id);
	public abstract void create(Verantwoordelijkheid verantwoordelijkheid);
}
