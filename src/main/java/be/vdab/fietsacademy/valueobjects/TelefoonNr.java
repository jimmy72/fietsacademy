package be.vdab.fietsacademy.valueobjects;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class TelefoonNr implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nummer;
	private boolean fax;
	private String opmerking;
	
	protected TelefoonNr() {
	}
	
	public TelefoonNr(String nummer, boolean fax, String opmerking) {
		if(nummer.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.nummer = nummer;
		this.fax = fax;
		this.opmerking = opmerking;
	}

	public String getNummer() {
		return nummer;
	}

	public boolean isFax() {
		return fax;
	}

	public String getOpmerking() {
		return opmerking;
	}

	

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 71 * hash + this.nummer.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TelefoonNr)) {
			return false;
		}
		TelefoonNr tel2 = (TelefoonNr) obj;
		return this.getNummer().equalsIgnoreCase(tel2.getNummer());
	}
		
}
