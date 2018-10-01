package be.vdab.fietsacademy.valueobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class TelefoonNrTest {

	private TelefoonNr tel1;
	private TelefoonNr tel1bis;
	private TelefoonNr tel2;
		
	@Before
	public void before() {
		tel1 = new TelefoonNr("0476273233", false, "GSM Jimmy");
		tel1bis = new TelefoonNr("0476273233", false, "GSM Jimmy");
		tel2 = new TelefoonNr("0476271957", false, "GSM Sonja");
	}

	@Test (expected = NullPointerException.class)
	public void telefoonNrMetNulAlsNummerMislukt() {
		new TelefoonNr(null, false, "test");
	}
	
	@Test
	public void tweeTelefoonNrObjectenZijnGelijkAlsNummersGelijkZijn() {
		assertEquals(tel1, tel1bis);
	}
	
	@Test
	public void tweeTelefoonNrObjectenZijnVerschillendAlsNummersVerschillendZijn() {
		assertNotEquals(tel1, tel2);
	}
	
	@Test
	public void eenTelefoonNrVerschiltVanNull() {
		assertNotEquals(tel1, null);
	}
	@Test
	public void eenTelefoonNrVerschiltVanEenAnderTypeObject() {
		assertNotEquals(tel1, "");
	}
	@Test
	public void gelijkeTelefoonNrsGevenDezelfdeHashCode() {
		assertEquals(tel1.hashCode(), tel1bis.hashCode());
	}

}
