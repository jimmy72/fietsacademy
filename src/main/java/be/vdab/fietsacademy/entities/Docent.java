package be.vdab.fietsacademy.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "docenten")
public class Docent implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	private String voornaam;
	private String familienaam;
	private BigDecimal wedde;
	private String emailAdres;
	
	protected Docent() {
		//Je maakt de default constructor protected in plaats van public als je liever hebt dat zo weinig mogelijk
		//classes deze constructor kunnen gebruiken
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
	
	
	
	
}
