package be.vdab.fietsacademy.repositories;

import java.util.Optional;

import be.vdab.fietsacademy.entities.Cursus;

public interface CursusRepository {
	public abstract Optional<Cursus> read(long id);
	public abstract void create(Cursus cursus);
}
