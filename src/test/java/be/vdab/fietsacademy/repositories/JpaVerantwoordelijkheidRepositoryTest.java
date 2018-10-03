package be.vdab.fietsacademy.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

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
import be.vdab.fietsacademy.entities.Verantwoordelijkheid;
import be.vdab.fietsacademy.enums.Geslacht;
import be.vdab.fietsacademy.valueobjects.Adres;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/insertCampus.sql")
@Sql("/insertVerantwoordelijkheid.sql")
@Sql("/insertDocent.sql")
@Sql("/insertDocentVerantwoordelijkheid.sql")
@Import(JpaVerantwoordelijkheidRepository.class)
public class JpaVerantwoordelijkheidRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{

	private static final String VERANTWOORDELIJKHEDEN = "verantwoordelijkheden";
	@Autowired
	private JpaVerantwoordelijkheidRepository repository;
	@Autowired
	private EntityManager manager;
	private Verantwoordelijkheid verantwoordelijkheid;
	private Campus campus;
	private Docent docent;
	
	
	@Before
	public void before() {
		verantwoordelijkheid = new Verantwoordelijkheid("ICT");
		campus = new Campus("testcampus", new Adres("straat", "huisnr", "postcode", "gemeente")); 
		docent = new Docent("test", "test", BigDecimal.TEN, "test@fietsacademy.be", Geslacht.MAN, campus);
	}
	
	private long idVanTestVerantwoordelijkheid() {
		return super.jdbcTemplate.queryForObject(
			"select id from verantwoordelijkheden where naam='test'",
			Long.class		
		);
	}
	@Test
	public void testRead() {
		Verantwoordelijkheid v = repository.read(idVanTestVerantwoordelijkheid()).get();
		assertEquals("test", v.getNaam());
	}
	
	@Test
	public void testCreate() {
		repository.create(verantwoordelijkheid);
		assertEquals(1, super.countRowsInTableWhere(VERANTWOORDELIJKHEDEN,"naam='ICT'"));
	}
	
	@Test
	public void docentenLezen() {
		Verantwoordelijkheid v = repository.read(idVanTestVerantwoordelijkheid()).get();
		assertEquals(1, v.getDocenten().size());
		assertTrue(v.getDocenten().contains(new Docent("jim","god",BigDecimal.TEN,"testM@fietsacademy.be",Geslacht.MAN, new Campus("t",new Adres("g","g","h","d")))));
	}
	@Test
	public void docentToevoegen() {
		manager.persist(campus);
		manager.persist(docent);
		repository.create(this.verantwoordelijkheid);
		this.verantwoordelijkheid.add(docent);
		manager.flush();
		assertEquals(docent.getId(),
			super.jdbcTemplate.queryForObject(
				"select docentid from docentenverantwoordelijkheden where verantwoordelijkheidid=?", 
				Long.class, 
				verantwoordelijkheid.getId()
			).longValue()
		);
	}

}
