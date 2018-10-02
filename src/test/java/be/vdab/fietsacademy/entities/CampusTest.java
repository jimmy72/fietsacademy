package be.vdab.fietsacademy.entities;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import be.vdab.fietsacademy.enums.Geslacht;
import be.vdab.fietsacademy.valueobjects.Adres;
import be.vdab.fietsacademy.valueobjects.TelefoonNr;
public class CampusTest {
	private Campus testCampus;
	private Campus testCampus2;
	private Docent docent1;
	private TelefoonNr testTelefoonNr1;
	private TelefoonNr testTelefoonNr2;
	@Before
	public void before() {
		testCampus = new Campus("eentestcampus", new Adres("Steenakkerstraat", "26a", "3590", "Diepenbeek"));
		testCampus2 = new Campus("test2", new Adres("test2","test2","test2","test2"));
		docent1 = new Docent("test", "test", BigDecimal.TEN, "test@fietsacademy.be", Geslacht.MAN, testCampus);
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
	
	@Test
	public void campus1IsDeCampusVanDocent1() {
		assertEquals(testCampus, docent1.getCampus());
		assertEquals(1, testCampus.getDocenten().size());
		assertTrue(testCampus.getDocenten().contains(docent1));
	}
	@Test
	public void docent1VerhuistNaarCampus2() {
		assertTrue(testCampus2.add(docent1));
		assertTrue(testCampus.getDocenten().isEmpty());
		assertEquals(1, testCampus2.getDocenten().size());
		assertTrue(testCampus2.getDocenten().contains(docent1));
		assertEquals(testCampus2, docent1.getCampus());
	}
	
}
