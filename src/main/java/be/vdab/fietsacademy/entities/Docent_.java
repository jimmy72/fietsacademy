package be.vdab.fietsacademy.entities;

import be.vdab.fietsacademy.enums.Geslacht;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-10-03T21:24:33.193+0200")
@StaticMetamodel(Docent.class)
public class Docent_ {
	public static volatile SingularAttribute<Docent, Long> id;
	public static volatile SingularAttribute<Docent, String> voornaam;
	public static volatile SingularAttribute<Docent, String> familienaam;
	public static volatile SingularAttribute<Docent, BigDecimal> wedde;
	public static volatile SingularAttribute<Docent, String> emailAdres;
	public static volatile SingularAttribute<Docent, Geslacht> geslacht;
	public static volatile SetAttribute<Docent, String> bijnamen;
	public static volatile SingularAttribute<Docent, Campus> campus;
	public static volatile SetAttribute<Docent, Verantwoordelijkheid> verantwoordelijkheden;
}
