package be.vdab.fietsacademy.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import be.vdab.fietsacademy.entities.Campus;
import be.vdab.fietsacademy.entities.Docent;
import be.vdab.fietsacademy.enums.Geslacht;
import be.vdab.fietsacademy.exceptions.DocentNietGevondenException;
import be.vdab.fietsacademy.repositories.DocentRepository;
import be.vdab.fietsacademy.valueobjects.Adres;

@RunWith(MockitoJUnitRunner.class)
public class DefaultDocentServiceTest {
	private DefaultDocentService service;
	@Mock
	private DocentRepository repository;
	private Docent docent;
	private Campus campus;
	
	@Before
	public void before() {
		campus = new Campus("testcampus", new Adres("straat", "huisnr", "postcode", "gemeente"));
		docent = new Docent("test", "test", BigDecimal.valueOf(100), "test@fietsacademy.be", Geslacht.MAN, campus);
		when(repository.read(1)).thenReturn(Optional.of(docent));
		when(repository.read(-1)).thenReturn(Optional.empty());
		service = new DefaultDocentService(repository);
	}
	
	@Test
	public void opslag() {
		service.opslag(1, BigDecimal.TEN);
		assertEquals(0, BigDecimal.valueOf(110).compareTo(docent.getWedde()));
		verify(repository).read(1);
	}
	
	@Test(expected = DocentNietGevondenException.class)
	public void opslagVoorOnbestaandeDocent() {
		service.opslag(-1, BigDecimal.TEN);
		verify(repository).read(-1);
	}
	
}
