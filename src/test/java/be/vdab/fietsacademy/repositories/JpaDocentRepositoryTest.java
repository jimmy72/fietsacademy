package be.vdab.fietsacademy.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import be.vdab.fietsacademy.entities.Campus;
import be.vdab.fietsacademy.entities.Docent;
import be.vdab.fietsacademy.enums.Geslacht;
import be.vdab.fietsacademy.queryresults.AantalDocentenPerWedde;
import be.vdab.fietsacademy.queryresults.IdEnEmailAdres;
import be.vdab.fietsacademy.valueobjects.Adres;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/insertCampus.sql")
@Sql("/insertDocent.sql")
@Import(JpaDocentRepository.class)
public class JpaDocentRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	
	private static final String DOCENTEN = "docenten";
	private Docent docent;
	private Campus campus;
	@Autowired
	private JpaDocentRepository repository;
	@Autowired
	private EntityManager manager;
	
	@Before
	public void before() {
		campus = new Campus("testcampus", new Adres("straat", "huisnr", "postcode", "gemeente")); 
		docent = new Docent("test", "test", BigDecimal.TEN, "test@fietsacademy.be", Geslacht.MAN, campus);
	}
	
	private long idVanTestMan() {
		return super.jdbcTemplate.queryForObject("select id from docenten where voornaam = 'testM'", Long.class);
	}
	
	private long idVanTestVrouw() {
		return super.jdbcTemplate.queryForObject("select id from docenten where voornaam = 'testV'", Long.class);
	}
	@Test
	public void read() {
		docent = repository.read(idVanTestMan()).get();
		assertEquals("testM", docent.getVoornaam());
		docent = repository.read(idVanTestVrouw()).get();
		assertEquals("testV", docent.getVoornaam());
	}
	@Test
	public void readOnbestaandeDocent() {
		assertFalse(repository.read(-1).isPresent());
	}
	
	@Test
	public void man() {
		assertEquals(Geslacht.MAN, repository.read(idVanTestMan()).get().getGeslacht());
	}
	
	@Test
	public void vrouw() {
		assertEquals(Geslacht.VROUW, repository.read(idVanTestVrouw()).get().getGeslacht());
	}
	
	@Test
	public void create() {
		manager.persist(this.campus);
		int aantalDocenten = super.countRowsInTable(DOCENTEN);
		repository.create(this.docent); //docent = testdocent beschreven in @Before
		assertEquals(aantalDocenten + 1, super.countRowsInTable(DOCENTEN));
		assertNotEquals(0, docent.getId()); //@GeneratedValue(strategy = GenerationType.IDENTITY) heeft id van docent ingevuld
		assertEquals(1, super.countRowsInTableWhere(DOCENTEN, "id=" + docent.getId())); //where clausule als 2de parameter
		assertEquals(campus.getId(), super.jdbcTemplate.queryForObject(
				"select campusid from docenten where id=?", Long.class, docent.getId()).longValue());
	}
	
	@Test
	public void delete() {
		long id = idVanTestMan();
		int aantalDocenten = super.countRowsInTable(DOCENTEN);
		repository.delete(id);
		manager.flush();
		assertEquals(aantalDocenten - 1, super.countRowsInTable(DOCENTEN));
		assertEquals(0, super.countRowsInTableWhere(DOCENTEN, "id=" + id));
	}
	
	@Test
	public void findAll() {
		List<Docent> docenten = repository.findAll();
		assertEquals(super.countRowsInTable(DOCENTEN), docenten.size());
		BigDecimal vorigeWedde = BigDecimal.ZERO;
		for (Docent docent : docenten) {
			assertTrue(docent.getWedde().compareTo(vorigeWedde) >= 0);
			vorigeWedde = docent.getWedde();
		}
	}
	
	@Test
	public void findByWeddeBetween() {
		BigDecimal duizend = BigDecimal.valueOf(1_000);
		BigDecimal tweeduizend=BigDecimal.valueOf(2_000);
		List<Docent> docenten = repository.findByWeddeBetween(duizend, tweeduizend);
		long aantalDocenten = super.countRowsInTableWhere(DOCENTEN, "wedde between 1000 and 2000");
		assertEquals(aantalDocenten, docenten.size());
		docenten.forEach(docent -> {
			assertTrue(docent.getWedde().compareTo(duizend) >= 0);
			assertTrue(docent.getWedde().compareTo(tweeduizend) <= 0);
		});
	}
	
	@Test
	public void findEmailAdressen() {
		List<String> emailAdressen = repository.findEmailAdressen();
		long aantal = super.jdbcTemplate.queryForObject("select count(distinct emailadres) from docenten", Long.class);
		assertEquals(emailAdressen.size(), aantal);
		emailAdressen.forEach(e ->
				assertTrue(e.contains("@")));
	}
	
	@Test
	public void findIdsEnEmailAdressen() {
		List<IdEnEmailAdres> idsEnEmailAdressen = repository.findIdsEnEmailAdressen();
		assertEquals(super.countRowsInTable(DOCENTEN), idsEnEmailAdressen.size());
	}
	
	@Test
	public void findGrootsteWedde() {
		BigDecimal grootste = repository.findGrootsteWedde();
		BigDecimal grootste2 = super.jdbcTemplate.queryForObject(
				"select max(wedde) from docenten", BigDecimal.class);
		assertEquals(0, grootste.compareTo(grootste2));
	}
	
	@Test
	public void aantalDocentenPerWedde() {
		List<AantalDocentenPerWedde> aantalDocentenPerWedde = repository.findAantalDocentenPerWedde();
		long aantalUniekeWeddes = super.jdbcTemplate.queryForObject(
				"select count(distinct wedde) from docenten", Long.class);
		assertEquals(aantalDocentenPerWedde.size(), aantalUniekeWeddes);
		long aantalDocentenMetWedde1000 = super.countRowsInTableWhere(DOCENTEN, "wedde = 1000");
		aantalDocentenPerWedde.stream()
				.filter(aantalPerWedde -> aantalPerWedde.getWedde().compareTo(BigDecimal.valueOf(1_000)) == 0)
				.forEach(aantalPerWedde -> assertEquals(aantalDocentenMetWedde1000, aantalPerWedde.getAantalDocenten()));
	}
	
	@Test
	public void algemeneOpslag() {
		int aantalGewijzigdeRecords = repository.algemeneOpslag(BigDecimal.valueOf(10));
		assertEquals(super.countRowsInTable(DOCENTEN), aantalGewijzigdeRecords);
		BigDecimal nieuweWedde = super.jdbcTemplate.queryForObject(
				"select wedde from docenten where id=?", BigDecimal.class, idVanTestMan());
		assertEquals(0, BigDecimal.valueOf(1_100).compareTo(nieuweWedde));
				
	}
	
	@Test
	public void bijnamenLezen() {
		docent = repository.read(idVanTestMan()).get();
		assertTrue(docent.getBijnamen().contains("test"));
	}
	@Test
	public void bijnaamToevoegen() {
		manager.persist(this.campus);
		repository.create(this.docent);
		docent.addBijnaam("test");
		manager.flush();
		assertEquals("test", super.jdbcTemplate.queryForObject(
				"select bijnaam from docentenbijnamen where docentid=?", String.class, docent.getId()));
	}

}
