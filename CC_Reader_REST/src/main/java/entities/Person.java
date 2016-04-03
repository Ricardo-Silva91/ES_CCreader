/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rofler
 */
@Entity
@Table(name = "person")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
    @NamedQuery(name = "Person.findByCountry", query = "SELECT p FROM Person p WHERE p.country = :country"),
    @NamedQuery(name = "Person.findByFirstname", query = "SELECT p FROM Person p WHERE p.firstname = :firstname"),
    @NamedQuery(name = "Person.findByNotes", query = "SELECT p FROM Person p WHERE p.notes = :notes"),
    @NamedQuery(name = "Person.findByDocumentType", query = "SELECT p FROM Person p WHERE p.documentType = :documentType"),
    @NamedQuery(name = "Person.findByCardVersion", query = "SELECT p FROM Person p WHERE p.cardVersion = :cardVersion"),
    @NamedQuery(name = "Person.findByNumBI", query = "SELECT p FROM Person p WHERE p.numBI = :numBI"),
    @NamedQuery(name = "Person.findByLastnameFather", query = "SELECT p FROM Person p WHERE p.lastnameFather = :lastnameFather"),
    @NamedQuery(name = "Person.findByLastnameMother", query = "SELECT p FROM Person p WHERE p.lastnameMother = :lastnameMother"),
    @NamedQuery(name = "Person.findByNumSNS", query = "SELECT p FROM Person p WHERE p.numSNS = :numSNS"),
    @NamedQuery(name = "Person.findByFirstnameMother", query = "SELECT p FROM Person p WHERE p.firstnameMother = :firstnameMother"),
    @NamedQuery(name = "Person.findByLocale", query = "SELECT p FROM Person p WHERE p.locale = :locale"),
    @NamedQuery(name = "Person.findByDeliveryDate", query = "SELECT p FROM Person p WHERE p.deliveryDate = :deliveryDate"),
    @NamedQuery(name = "Person.findByHeight", query = "SELECT p FROM Person p WHERE p.height = :height"),
    @NamedQuery(name = "Person.findByNumSS", query = "SELECT p FROM Person p WHERE p.numSS = :numSS"),
    @NamedQuery(name = "Person.findBySex", query = "SELECT p FROM Person p WHERE p.sex = :sex"),
    @NamedQuery(name = "Person.findByCardNumberPAN", query = "SELECT p FROM Person p WHERE p.cardNumberPAN = :cardNumberPAN"),
    @NamedQuery(name = "Person.findByFirstnameFather", query = "SELECT p FROM Person p WHERE p.firstnameFather = :firstnameFather"),
    @NamedQuery(name = "Person.findByBirthDate", query = "SELECT p FROM Person p WHERE p.birthDate = :birthDate"),
    @NamedQuery(name = "Person.findByMrz3", query = "SELECT p FROM Person p WHERE p.mrz3 = :mrz3"),
    @NamedQuery(name = "Person.findByMrz2", query = "SELECT p FROM Person p WHERE p.mrz2 = :mrz2"),
    @NamedQuery(name = "Person.findByLastname", query = "SELECT p FROM Person p WHERE p.lastname = :lastname"),
    @NamedQuery(name = "Person.findByMrz1", query = "SELECT p FROM Person p WHERE p.mrz1 = :mrz1"),
    @NamedQuery(name = "Person.findByNationality", query = "SELECT p FROM Person p WHERE p.nationality = :nationality"),
    @NamedQuery(name = "Person.findByNumNIF", query = "SELECT p FROM Person p WHERE p.numNIF = :numNIF"),
    @NamedQuery(name = "Person.findByCardNumber", query = "SELECT p FROM Person p WHERE p.cardNumber = :cardNumber"),
    @NamedQuery(name = "Person.findByDeliveryEntity", query = "SELECT p FROM Person p WHERE p.deliveryEntity = :deliveryEntity"),
    @NamedQuery(name = "Person.findByAuthentication", query = "SELECT p FROM Person p WHERE p.authentication = :authentication")})
public class Person implements Serializable {

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
    private CurrentCard currentCard;

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "country")
    private String country;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "firstname")
    private String firstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "notes")
    private String notes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "documentType")
    private String documentType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "cardVersion")
    private String cardVersion;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "numBI")
    private String numBI;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "lastnameFather")
    private String lastnameFather;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "lastnameMother")
    private String lastnameMother;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "numSNS")
    private String numSNS;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "firstnameMother")
    private String firstnameMother;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "locale")
    private String locale;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "deliveryDate")
    private String deliveryDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "height")
    private String height;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "numSS")
    private String numSS;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "sex")
    private String sex;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "cardNumberPAN")
    private String cardNumberPAN;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "firstnameFather")
    private String firstnameFather;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "birthDate")
    private String birthDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "mrz3")
    private String mrz3;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "mrz2")
    private String mrz2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "lastname")
    private String lastname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "mrz1")
    private String mrz1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "nationality")
    private String nationality;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "numNIF")
    private String numNIF;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "cardNumber")
    private String cardNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "deliveryEntity")
    private String deliveryEntity;
    @Size(max = 10)
    @Column(name = "Authentication")
    private String authentication;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId")
    private Collection<Interaction> interactionCollection;

    public Person() {
    }

    public Person(String numBI) {
        this.numBI = numBI;
    }

    public Person(String numBI, String country, String firstname, String notes, String documentType, String cardVersion, String lastnameFather, String lastnameMother, String numSNS, String firstnameMother, String locale, String deliveryDate, String height, String numSS, String sex, String cardNumberPAN, String firstnameFather, String birthDate, String mrz3, String mrz2, String lastname, String mrz1, String nationality, String numNIF, String cardNumber, String deliveryEntity) {
        this.numBI = numBI;
        this.country = country;
        this.firstname = firstname;
        this.notes = notes;
        this.documentType = documentType;
        this.cardVersion = cardVersion;
        this.lastnameFather = lastnameFather;
        this.lastnameMother = lastnameMother;
        this.numSNS = numSNS;
        this.firstnameMother = firstnameMother;
        this.locale = locale;
        this.deliveryDate = deliveryDate;
        this.height = height;
        this.numSS = numSS;
        this.sex = sex;
        this.cardNumberPAN = cardNumberPAN;
        this.firstnameFather = firstnameFather;
        this.birthDate = birthDate;
        this.mrz3 = mrz3;
        this.mrz2 = mrz2;
        this.lastname = lastname;
        this.mrz1 = mrz1;
        this.nationality = nationality;
        this.numNIF = numNIF;
        this.cardNumber = cardNumber;
        this.deliveryEntity = deliveryEntity;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getCardVersion() {
        return cardVersion;
    }

    public void setCardVersion(String cardVersion) {
        this.cardVersion = cardVersion;
    }

    public String getNumBI() {
        return numBI;
    }

    public void setNumBI(String numBI) {
        this.numBI = numBI;
    }

    public String getLastnameFather() {
        return lastnameFather;
    }

    public void setLastnameFather(String lastnameFather) {
        this.lastnameFather = lastnameFather;
    }

    public String getLastnameMother() {
        return lastnameMother;
    }

    public void setLastnameMother(String lastnameMother) {
        this.lastnameMother = lastnameMother;
    }

    public String getNumSNS() {
        return numSNS;
    }

    public void setNumSNS(String numSNS) {
        this.numSNS = numSNS;
    }

    public String getFirstnameMother() {
        return firstnameMother;
    }

    public void setFirstnameMother(String firstnameMother) {
        this.firstnameMother = firstnameMother;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getNumSS() {
        return numSS;
    }

    public void setNumSS(String numSS) {
        this.numSS = numSS;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCardNumberPAN() {
        return cardNumberPAN;
    }

    public void setCardNumberPAN(String cardNumberPAN) {
        this.cardNumberPAN = cardNumberPAN;
    }

    public String getFirstnameFather() {
        return firstnameFather;
    }

    public void setFirstnameFather(String firstnameFather) {
        this.firstnameFather = firstnameFather;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getMrz3() {
        return mrz3;
    }

    public void setMrz3(String mrz3) {
        this.mrz3 = mrz3;
    }

    public String getMrz2() {
        return mrz2;
    }

    public void setMrz2(String mrz2) {
        this.mrz2 = mrz2;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMrz1() {
        return mrz1;
    }

    public void setMrz1(String mrz1) {
        this.mrz1 = mrz1;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNumNIF() {
        return numNIF;
    }

    public void setNumNIF(String numNIF) {
        this.numNIF = numNIF;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDeliveryEntity() {
        return deliveryEntity;
    }

    public void setDeliveryEntity(String deliveryEntity) {
        this.deliveryEntity = deliveryEntity;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    @XmlTransient
    public Collection<Interaction> getInteractionCollection() {
        return interactionCollection;
    }

    public void setInteractionCollection(Collection<Interaction> interactionCollection) {
        this.interactionCollection = interactionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numBI != null ? numBI.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.numBI == null && other.numBI != null) || (this.numBI != null && !this.numBI.equals(other.numBI))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Person[ numBI=" + numBI + " ]";
    }

    public CurrentCard getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(CurrentCard currentCard) {
        this.currentCard = currentCard;
    }
    
}
