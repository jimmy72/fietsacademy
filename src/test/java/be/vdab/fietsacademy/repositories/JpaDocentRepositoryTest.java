package be.vdab.fietsacademy.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;

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

import be.vdab.fietsacademy.entities.Docent;
import be.vdab.fietsacademy.enums.Geslacht;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/insertDocent.sql")
@Import(JpaDocentRepository.class)
public class JpaDocentRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private JpaDocentRepository repository;
	private static final String DOCENTEN = "docenten";
	private Docent docent;
	
	@Before
	public void before() {
		docent = new Docent("test", "test", BigDecimal.TEN, "test@fietsacademy.be", Geslacht.MAN);
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
		int aantalDocenten = super.countRowsInTable(DOCENTEN);
		repository.create(docent); //docent = testdocent beschreven in @Before
		assertEquals(aantalDocenten + 1, super.countRowsInTable(DOCENTEN));
		assertNotEquals(0, docent.getId()); //@GeneratedValue(strategy = GenerationType.IDENTITY) heeft id van docent ingevuld
		assertEquals(1, super.countRowsInTableWhere(DOCENTEN, "id=" + docent.getId())); //where clausule als 2de parameter
		
	}

}