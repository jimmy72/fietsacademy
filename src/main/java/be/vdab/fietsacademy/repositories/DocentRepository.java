package be.vdab.fietsacademy.repositories;

import java.util.Optional;

import be.vdab.fietsacademy.entities.Docent;

public interface DocentRepository {
	public abstract Optional<Docent> read(long id);
}
