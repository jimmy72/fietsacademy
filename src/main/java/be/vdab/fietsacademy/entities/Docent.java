package be.vdab.fietsacademy.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

import be.vdab.fietsacademy.enums.Geslacht;

@Entity
@Table(name = "docenten")
@NamedEntityGraph(name = Docent.MET_CAMPUS, attributeNodes = @NamedAttributeNode("campus")) //leest bijbehorende campus via join
public class Docent implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final String MET_CAMPUS = "Docent.metCampus";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String voornaam;
	private String familienaam;
	private BigDecimal wedde;
	private String emailAdres;
	@Enumerated(EnumType.STRING)
	private Geslacht geslacht;
	@ElementCollection 
	@CollectionTable(name = "docentenbijnamen", joinColumns = @JoinColumn(name = "docentid") ) 
	@Column(name = "bijnaam")
	private Set<String> bijnamen;
	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "campusid") 
	private Campus campus;
	@ManyToMany(mappedBy = "docenten") 
	private Set<Verantwoordelijkheid> verantwoordelijkheden = new LinkedHashSet<>();
	
	protected Docent() {
		//Je maakt de default constructor protected in plaats van public als je liever hebt dat zo weinig mogelijk
		//classes deze constructor kunnen gebruiken
	}
	
	public Docent(String voornaam, String familienaam, BigDecimal wedde, String emailAdres, Geslacht geslacht, Campus campus) {
		this.voornaam = voornaam;
		this.familienaam = familienaam;
		this.wedde = wedde;
		this.emailAdres = emailAdres;
		this.geslacht = geslacht;
		this.bijnamen = new LinkedHashSet<>();
		this.setCampus(campus);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getVoornaam() {
		return voornaam;
	}
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}
	public String getFamilienaam() {
		return familienaam;
	}
	public void setFamilienaam(String familienaam) {
		this.familienaam = familienaam;
	}
	public BigDecimal getWedde() {
		return wedde;
	}
	public void setWedde(BigDecimal wedde) {
		this.wedde = wedde;
	}
	public String getEmailAdres() {
		return emailAdres;
	}
	public void setEmailAdres(String emailAdres) {
		this.emailAdres = emailAdres;
	}

	public Geslacht getGeslacht() {
		return geslacht;
	}

	public void setGeslacht(Geslacht geslacht) {
		this.geslacht = geslacht;
	}
	
	public Set<String> getBijnamen() {
		return Collections.unmodifiableSet(bijnamen);
	}
	
	public boolean addBijnaam(String bijnaam) {
		if(bijnaam.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		return bijnamen.add(bijnaam);
	}
	
	public boolean removeBijnaam(String bijnaam) {
		return bijnamen.remove(bijnaam);
	}
	
	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		if(campus == null) {
			throw new NullPointerException();
		}
		if(!campus.getDocenten().contains(this)) {
			campus.add(this);
		}
		this.campus = campus;
	}

	public void opslag(BigDecimal percentage) {
		if(percentage.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
		this.setWedde(this.getWedde().multiply(factor, new MathContext(2, RoundingMode.HALF_UP)));
	}
	
	public boolean add(Verantwoordelijkheid verantwoordelijkheid) {
		boolean toegevoegd = verantwoordelijkheden.add(verantwoordelijkheid);
		if ( ! verantwoordelijkheid.getDocenten().contains(this)) {
			verantwoordelijkheid.add(this);
		}
		return toegevoegd;
	}
	
	public boolean remove(Verantwoordelijkheid verantwoordelijkheid) {
		boolean verwijderd = verantwoordelijkheden.remove(verantwoordelijkheid);
		if (verantwoordelijkheid.getDocenten().contains(this)) {
			verantwoordelijkheid.remove(this);
		}
		return verwijderd;
	}
		
	public Set<Verantwoordelijkheid> getVerantwoordelijkheden() {
		return Collections.unmodifiableSet(verantwoordelijkheden);
	}
	
	@Override
	public int hashCode() {
		return this.emailAdres == null ? 0 : this.emailAdres.toLowerCase().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Docent)) {
			return false;
		}
		if(this.emailAdres == null) {
			return false;
		}
		
		return this.getEmailAdres().equalsIgnoreCase(((Docent) obj).getEmailAdres());
	}
	
}
