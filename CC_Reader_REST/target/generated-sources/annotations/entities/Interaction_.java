package entities;

import entities.Person;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-05T20:27:02")
@StaticMetamodel(Interaction.class)
public class Interaction_ { 

    public static volatile SingularAttribute<Interaction, String> interaction;
    public static volatile SingularAttribute<Interaction, Person> personId;
    public static volatile SingularAttribute<Interaction, String> roomCode;
    public static volatile SingularAttribute<Interaction, String> time;

}