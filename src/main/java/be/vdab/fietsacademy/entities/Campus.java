package be.vdab.fietsacademy.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import be.vdab.fietsacademy.valueobjects.Adres;
import be.vdab.fietsacademy.valueobjects.TelefoonNr;

@Entity
@Table(name="campussen")
public class Campus implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String naam;
	@Embedded
	private Adres adres;
	@ElementCollection 
	@CollectionTable(name = "campussentelefoonnrs", joinColumns = @JoinColumn(name = "campusId")) 
	@Column(name = "nummer")
	private Set<TelefoonNr> telefoonNummers;
	
	protected Campus() {
	}
	
	public Campus(String naam, Adres adres) {
		this.naam = naam;
		this.adres = adres;
		this.telefoonNummers = new LinkedHashSet<>();
	}

	public Set<TelefoonNr> getTelefoonNummers() {
		return Collections.unmodifiableSet(this.telefoonNummers);
	}
	
	public boolean addTelefoonNr(TelefoonNr telefoonNr) {
		if(telefoonNr == null) {
			throw new NullPointerException();
		}
		return this.telefoonNummers.add(telefoonNr);
	}
	
	public boolean removeTelefoonNr(TelefoonNr telefoonNr) {
		return this.telefoonNummers.remove(telefoonNr);
	}

	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public Adres getAdres() {
		return adres;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public void setAdres(Adres adres) {
		this.adres = adres;
	}
	
}
