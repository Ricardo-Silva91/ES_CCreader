package entities;

import entities.CurrentCard;
import entities.Interaction;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-03T20:47:15")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile SingularAttribute<Person, String> country;
    public static volatile SingularAttribute<Person, String> firstname;
    public static volatile SingularAttribute<Person, String> notes;
    public static volatile SingularAttribute<Person, String> documentType;
    public static volatile SingularAttribute<Person, String> cardVersion;
    public static volatile SingularAttribute<Person, String> numBI;
    public static volatile SingularAttribute<Person, String> lastnameFather;
    public static volatile SingularAttribute<Person, String> lastnameMother;
    public static volatile SingularAttribute<Person, String> numSNS;
    public static volatile SingularAttribute<Person, String> firstnameMother;
    public static volatile SingularAttribute<Person, String> locale;
    public static volatile CollectionAttribute<Person, Interaction> interactionCollection;
    public static volatile SingularAttribute<Person, String> deliveryDate;
    public static volatile SingularAttribute<Person, String> height;
    public static volatile SingularAttribute<Person, String> authentication;
    public static volatile SingularAttribute<Person, String> numSS;
    public static volatile SingularAttribute<Person, CurrentCard> currentCard;
    public static volatile SingularAttribute<Person, String> sex;
    public static volatile SingularAttribute<Person, String> cardNumberPAN;
    public static volatile SingularAttribute<Person, String> firstnameFather;
    public static volatile SingularAttribute<Person, String> birthDate;
    public static volatile SingularAttribute<Person, String> mrz3;
    public static volatile SingularAttribute<Person, String> mrz2;
    public static volatile SingularAttribute<Person, String> lastname;
    public static volatile SingularAttribute<Person, String> mrz1;
    public static volatile SingularAttribute<Person, String> nationality;
    public static volatile SingularAttribute<Person, String> numNIF;
    public static volatile SingularAttribute<Person, String> cardNumber;
    public static volatile SingularAttribute<Person, String> deliveryEntity;

}