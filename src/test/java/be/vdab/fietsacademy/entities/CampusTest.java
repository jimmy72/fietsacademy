package be.vdab.fietsacademy.entities;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import be.vdab.fietsacademy.valueobjects.Adres;
import be.vdab.fietsacademy.valueobjects.TelefoonNr;
public class CampusTest {
	private Campus testCampus;
	private TelefoonNr testTelefoonNr1;
	private TelefoonNr testTelefoonNr2;
	@Before
	public void before() {
		testCampus = new Campus("eentestcampus", new Adres("Steenakkerstraat", "26a", "3590", "Diepenbeek"));
		testTelefoonNr1 = new TelefoonNr("979204", false, "Een standaardopmerking");
		testTelefoonNr2 = new TelefoonNr("111111", true, "Nog een standaardopmerking");
	}
	
	@Test
	public void eenNieuwAangemaakteCampusHeeftGeenTelefoonNummers() {
		assertTrue(testCampus.getTelefoonNummers().size() == 0);
	}
	
	@Test
	public void campusTelefoonNummerToevoegenLukt() {
		assertTrue(testCampus.addTelefoonNr(new TelefoonNr("888", false, "opmerking")));
		assertEquals(1, testCampus.getTelefoonNummers().size());
		assertTrue(testCampus.getTelefoonNummers().contains(new TelefoonNr("888", true, "")));
	}
	@Test
	public void tweeKeerHetzelfdeTelefoonNrToevoegenKanNiet() {
		testCampus.addTelefoonNr(new TelefoonNr("888", false, "opmerking"));
		testCampus.addTelefoonNr(new TelefoonNr("888", false, "opmerking"));
		assertNotEquals(2, testCampus.getTelefoonNummers().size());
		assertEquals(1, testCampus.getTelefoonNummers().size());
	}
	@Test(expected = NullPointerException.class)
	public void nullAlsTelefoonNrToevoegenKanNiet() {
		testCampus.addTelefoonNr(null);
	}
	@Test(expected = NullPointerException.class)
	public void eenTelefoonNrMetNullAlsNummerToevoegenKanNiet() {
		testCampus.addTelefoonNr(new TelefoonNr(null, true, "opmerking"));
	}
	@Test(expected = IllegalArgumentException.class)
	public void eenTelefoonNrMetEenLegeStringAlsNummerToevoegenKanNiet() {
		testCampus.addTelefoonNr(new TelefoonNr("", true, "opmerking"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void eenTelefoonNrMetEenSpatieAlsNummerToevoegenKanNiet() {
		testCampus.addTelefoonNr(new TelefoonNr(" ", true, "opmerking"));
	}
	@Test
	public void telefoonNummerVerwijderenLukt() {
		testCampus.addTelefoonNr(testTelefoonNr1);
		assertTrue(testCampus.removeTelefoonNr(testTelefoonNr1));
		assertTrue(testCampus.getTelefoonNummers().isEmpty());
	}
	@Test
	public void eenTelefoonNrVerwijderenDatJeNietToevoegdeKanNiet() {
		testCampus.addTelefoonNr(testTelefoonNr1);
		assertFalse(testCampus.removeTelefoonNr(testTelefoonNr2));
		assertEquals(1, testCampus.getTelefoonNummers().size());
		assertTrue(testCampus.getTelefoonNummers().contains(testTelefoonNr1));
	}
	
}
