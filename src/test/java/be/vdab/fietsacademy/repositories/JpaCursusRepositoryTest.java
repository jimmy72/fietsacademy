package be.vdab.fietsacademy.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

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

import be.vdab.fietsacademy.entities.Cursus;
import be.vdab.fietsacademy.entities.GroepsCursus;
import be.vdab.fietsacademy.entities.IndividueleCursus;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/insertCursus.sql")
@Import(JpaCursusRepository.class)
public class JpaCursusRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{
	private static final String CURSUSSEN = "cursussen";
	private static final LocalDate EEN_DATUM = LocalDate.of(2019, 1, 1); 
	
	@Autowired
	private JpaCursusRepository repository;
	
	private long idVanTestGroepsCursus() {
		return super.jdbcTemplate.queryForObject(
				"select id from cursussen where naam='testGroep'", Long.class);
	}
	
	private long idVanTestIndividueleCursus() {
		return super.jdbcTemplate.queryForObject(
				"select id from cursussen where naam='testIndividueel'", Long.class);
	}
	
	@Test
	public void readGroepsCursus() {
		Optional<Cursus> optionalCursus = repository.read(idVanTestGroepsCursus());
		assertEquals(((GroepsCursus) optionalCursus.get()).getNaam(), "testGroep");
	}
	
	@Test
	public void readIndividueleCursus() {
		Optional<Cursus> optionalCursus = repository.read(idVanTestIndividueleCursus());
		assertEquals(((IndividueleCursus) optionalCursus.get()).getNaam(), "testIndividueel");
	}
	
	@Test
	public void createGroepsCursus() {
		int aantalGroepsCursussen = super.countRowsInTableWhere(CURSUSSEN, "soort ='G'");
		GroepsCursus groepsCursus = new GroepsCursus("testGroep2", EEN_DATUM, EEN_DATUM);
		repository.create(groepsCursus);
		assertEquals(aantalGroepsCursussen + 1, super.countRowsInTableWhere(CURSUSSEN, "soort ='G'"));
		String soort = super.jdbcTemplate.queryForObject(
				"select soort from cursussen where naam=?", String.class, groepsCursus.getNaam());
		assertTrue(soort.equals("G"));
		assertEquals(1, super.countRowsInTableWhere(CURSUSSEN, "id=" + groepsCursus.getId()));
	}
	
	@Test
	public void createIndividueleCursus() {
		int aantalIndividueleCursussen = super.countRowsInTableWhere(CURSUSSEN, "soort ='I'");
		IndividueleCursus individueleCursus = new IndividueleCursus("testIndividueel2", 6);
		repository.create(individueleCursus);
		assertEquals(aantalIndividueleCursussen + 1, super.countRowsInTableWhere(CURSUSSEN, "soort ='I'"));
		String soort = super.jdbcTemplate.queryForObject(
				"select soort from cursussen where naam=?", String.class, individueleCursus.getNaam());
		assertTrue(soort.equals("I"));
		assertEquals(1, super.countRowsInTableWhere(CURSUSSEN, "id=" + individueleCursus.getId()));
	}
}
