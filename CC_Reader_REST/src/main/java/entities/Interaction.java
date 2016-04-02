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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rofler
 */
@Entity
@Table(name = "interaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Interaction.findAll", query = "SELECT i FROM Interaction i"),
    @NamedQuery(name = "Interaction.findByInteraction", query = "SELECT i FROM Interaction i WHERE i.interaction = :interaction"),
    @NamedQuery(name = "Interaction.findByRoomCode", query = "SELECT i FROM Interaction i WHERE i.roomCode = :roomCode"),
    @NamedQuery(name = "Interaction.findByTime", query = "SELECT i FROM Interaction i WHERE i.time = :time")})
public class Interaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "interaction")
    private String interaction;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "roomCode")
    private String roomCode;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "time")
    private String time;
    @JoinColumn(name = "person_id", referencedColumnName = "numBI")
    @ManyToOne(optional = false)
    private Person personId;

    public Interaction() {
    }

    public Interaction(String time) {
        this.time = time;
    }

    public Interaction(String time, String interaction, String roomCode) {
        this.time = time;
        this.interaction = interaction;
        this.roomCode = roomCode;
    }

    public String getInteraction() {
        return interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (time != null ? time.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Interaction)) {
            return false;
        }
        Interaction other = (Interaction) object;
        if ((this.time == null && other.time != null) || (this.time != null && !this.time.equals(other.time))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Interaction[ time=" + time + " ]";
    }
    
}
