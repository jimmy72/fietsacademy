package be.vdab.fietsacademy.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import be.vdab.fietsacademy.enums.Geslacht;

public class DocentTest {

	private static final BigDecimal NORMALE_WEDDE = BigDecimal.valueOf(200);
	private Docent docent1;
	
	@Before
	public void before() {
		docent1 = new Docent("Jimmy", "Godin", NORMALE_WEDDE, "jimmy.godin@hotmail.com", Geslacht.MAN);
	}

	@Test
	public void opslagMet10ProcentLukt() {
		docent1.opslag(BigDecimal.valueOf(10));
		assertEquals(0, BigDecimal.valueOf(220).compareTo(docent1.getWedde()));
	}
	
	@Test(expected = NullPointerException.class)
	public void opslagMetNullKanNiet() {
		docent1.opslag(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void opslagMet0KanNiet() {
		docent1.opslag(BigDecimal.ZERO);
		assertEquals(0, NORMALE_WEDDE.compareTo(docent1.getWedde()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void opslagMetNegatiefPercentageKanNiet() {
		docent1.opslag(BigDecimal.valueOf(-1));
		assertEquals(0, NORMALE_WEDDE.compareTo(docent1.getWedde()));
	}
	
	@Test
	public void eenNieuweDocentHeeftGeenBijnamen() {
		assertTrue(docent1.getBijnamen().isEmpty());
	}
	
	@Test
	public void bijnaamToevoegen() {
		boolean isToegevoegd = docent1.addBijnaam("test");
		assertTrue(isToegevoegd);
		assertEquals(1, docent1.getBijnamen().size());
		assertTrue(docent1.getBijnamen().contains("test"));
	}
	
	@Test
	public void tweeKeerDezelfdeBijnaamToevoegenKanNiet() {
		docent1.addBijnaam("test");
		assertFalse(docent1.addBijnaam("test"));
		assertEquals(1, docent1.getBijnamen().size());
	}
	
	@Test(expected = NullPointerException.class)
	public void nullAlsBijnaamToevoegenKanNiet() {
		docent1.addBijnaam(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
		public void eenLegeBijnaamToevoegenKanNiet() {
		docent1.addBijnaam("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void eenBijnaamMetEnkelSpatiesToevoegenKanNiet() {
		docent1.addBijnaam(" ");
	}
	
	@Test
	public void bijnaamVerwijderen() {
		docent1.addBijnaam("test");
		assertTrue(docent1.removeBijnaam("test"));
		assertTrue(docent1.getBijnamen().isEmpty());
	}
	
	@Test
	public void eenBijnaamVerwijderenDieJeNietToevoegdeKanNiet() {
		docent1.addBijnaam("test");
		assertFalse(docent1.removeBijnaam("test2"));
		assertEquals(1, docent1.getBijnamen().size());
		assertTrue(docent1.getBijnamen().contains("test"));
	}
	
}
