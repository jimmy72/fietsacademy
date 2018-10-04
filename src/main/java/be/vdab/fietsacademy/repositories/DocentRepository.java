package be.vdab.fietsacademy.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import be.vdab.fietsacademy.entities.Docent;
import be.vdab.fietsacademy.queryresults.AantalDocentenPerWedde;
import be.vdab.fietsacademy.queryresults.IdEnEmailAdres;

public interface DocentRepository {
	public abstract Optional<Docent> read(long id);
	public abstract Optional<Docent> readWithLock(long id);
	public abstract void create(Docent docent);
	public abstract void delete(long id);
	public abstract List<Docent> findAll();
	public abstract List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot);
	public abstract List<String> findEmailAdressen();
	public abstract List<IdEnEmailAdres> findIdsEnEmailAdressen();
	public abstract BigDecimal findGrootsteWedde();
	public abstract List<AantalDocentenPerWedde> findAantalDocentenPerWedde();
	public abstract int algemeneOpslag(BigDecimal percentage);
}
