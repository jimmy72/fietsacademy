package be.vdab.fietsacademy.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaCampusRepository.class)
@Sql("/insertCampus.sql")
public class JpaCampusRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{
	private final static String CAMPUSSEN = "campussen";
	@Autowired
	private JpaCampusRepository repository;
	private Campus campus;
	
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
	
}
