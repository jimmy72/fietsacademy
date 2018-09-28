package be.vdab.fietsacademy.entities;

import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("G")
public class GroepsCursus extends Cursus {

	private static final long serialVersionUID = 1L;
	private LocalDate van;
	private LocalDate tot;
	
	protected GroepsCursus() {
		super();
	}

	public GroepsCursus(String naam, LocalDate van, LocalDate tot) {
		super(naam);
		this.van = van;
		this.tot = tot;
	}

	public LocalDate getVan() {
		return van;
	}

	public LocalDate getTot() {
		return tot;
	}
	
}
