package be.vdab.fietsacademy.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-10-03T20:53:40.884+0200")
@StaticMetamodel(Verantwoordelijkheid.class)
public class Verantwoordelijkheid_ {
	public static volatile SingularAttribute<Verantwoordelijkheid, Long> id;
	public static volatile SingularAttribute<Verantwoordelijkheid, String> naam;
	public static volatile SetAttribute<Verantwoordelijkheid, Docent> docenten;
}
