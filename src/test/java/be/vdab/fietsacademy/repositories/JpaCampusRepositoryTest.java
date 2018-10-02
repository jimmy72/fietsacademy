package be.vdab.fietsacademy.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

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
import be.vdab.fietsacademy.valueobjects.Adres;
import be.vdab.fietsacademy.valueobjects.TelefoonNr;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaCampusRepository.class)
@Sql("/insertCampus.sql")
@Sql("/insertDocent.sql")
public class JpaCampusRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{
	private final static String CAMPUSSEN = "campussen";
	@Autowired
	private JpaCampusRepository repository;
	private Campus campus;
	@Autowired
	private EntityManager manager;
	
	@Before
	public void before() {
		campus = new Campus("testcampus", new Adres("test","test","test","test"));
	}
	
	private long idVanTestCampus() {
		return super.jdbcTemplate.queryForObject("select id from campussen where naam='test'", Long.class);
	}
	
	@Test
	public void create() {
		//controleren of er 1 record werd toegevoegd
		int aantalCampussen = super.countRowsInTable(CAMPUSSEN);
		repository.create(campus);
		assertEquals(aantalCampussen + 1, super.countRowsInTable(CAMPUSSEN));
		//controleren aan de hand van de naam of toegevoegde record effectief aanwezig is
		long id = this.campus.getId();
		String naam = super.jdbcTemplate.queryForObject("select naam from campussen where id=?", String.class, id);
		assertTrue(naam.equalsIgnoreCase("testcampus"));
	}
	
	@Test
	public void read() {
		Campus c = repository.read(idVanTestCampus()).get();//uit sql file
		//repository.create(this.campus);
		//Campus c = repository.read(this.campus.getId()).get();//uit member campus
		//assertEquals("testcampus", c.getNaam());
		assertEquals("test", c.getNaam());
		assertEquals("test", c.getAdres().getGemeente());
	}
	
	@Test
	public void telefoonNrLezen() {
		Campus testCampus = repository.read(idVanTestCampus()).get();
		assertTrue(testCampus.getTelefoonNummers().contains(new TelefoonNr("111", false, "eenopmerking")));//via insertCampus.sql werd nummer 111 toegevoegd
		assertTrue(testCampus.getTelefoonNummers().size() == 1);
		for(Iterator<TelefoonNr> i = testCampus.getTelefoonNummers().iterator(); i.hasNext(); ) {
			TelefoonNr tn = i.next();
			assertEquals("eenopmerking", tn.getOpmerking());
		}
	}
	
	@Test
	public void telefoonNrToevoegen() {
		repository.create(campus);
		campus.addTelefoonNr(new TelefoonNr("999", true, "eriseenfax"));
		manager.flush();
		assertEquals("999", super.jdbcTemplate.queryForObject(
				"select nummer from campussentelefoonnrs where campusId=?", String.class, campus.getId()));
	}
	
	@Test
	public void docentenLazyLoaded() {
		Campus campus = repository.read(idVanTestCampus()).get(); 
		assertEquals(2, campus.getDocenten().size()); 
		assertEquals("testM", campus.getDocenten().stream().findFirst().get().getVoornaam());
	}
	
}
