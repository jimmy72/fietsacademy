package be.vdab.fietsacademy.repositories;

import java.util.Optional;

import be.vdab.fietsacademy.entities.Campus;

public interface CampusRepository {
	public abstract void create(Campus campus);
	public abstract Optional<Campus> read(long id);
}
