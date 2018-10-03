package be.vdab.fietsacademy.entities;

import be.vdab.fietsacademy.valueobjects.Adres;
import be.vdab.fietsacademy.valueobjects.TelefoonNr;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-10-03T21:24:16.479+0200")
@StaticMetamodel(Campus.class)
public class Campus_ {
	public static volatile SingularAttribute<Campus, Long> id;
	public static volatile SingularAttribute<Campus, String> naam;
	public static volatile SingularAttribute<Campus, Adres> adres;
	public static volatile SetAttribute<Campus, TelefoonNr> telefoonNummers;
	public static volatile SetAttribute<Campus, Docent> docenten;
}
