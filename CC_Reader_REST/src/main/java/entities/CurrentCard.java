/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rofler
 */
@Entity
@Table(name = "current_card")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CurrentCard.findAll", query = "SELECT c FROM CurrentCard c"),
    @NamedQuery(name = "CurrentCard.findByPersonId", query = "SELECT c FROM CurrentCard c WHERE c.personId = :personId")})
public class CurrentCard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "person_id")
    private String personId;
    @JoinColumn(name = "person_id", referencedColumnName = "numBI", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Person person;

    public CurrentCard() {
    }

    public CurrentCard(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personId != null ? personId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CurrentCard)) {
            return false;
        }
        CurrentCard other = (CurrentCard) object;
        if ((this.personId == null && other.personId != null) || (this.personId != null && !this.personId.equals(other.personId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CurrentCard[ personId=" + personId + " ]";
    }
    
}
