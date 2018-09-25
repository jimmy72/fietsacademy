package be.vdab.fietsacademy.entities;

import static org.junit.Assert.assertEquals;

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
	
}
