/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Evan
 */
@Entity
@Table(name = "AVAILABILITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Availability.findAll", query = "SELECT a FROM Availability a")
    , @NamedQuery(name = "Availability.findByAvailabilityId", query = "SELECT a FROM Availability a WHERE a.availabilityId = :availabilityId")
    , @NamedQuery(name = "Availability.findByFromDate", query = "SELECT a FROM Availability a WHERE a.fromDate = :fromDate")
    , @NamedQuery(name = "Availability.findByToDate", query = "SELECT a FROM Availability a WHERE a.toDate = :toDate")})
public class Availability implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "AVAILABILITY_ID")
    private Long availabilityId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FROM_DATE")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TO_DATE")
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
    @ManyToOne(optional = false)
    private Person personId;

    public Availability() {
    }

    public Availability(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    public Availability(Long availabilityId, Date fromDate, Date toDate) {
        this.availabilityId = availabilityId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Long getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
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
        hash += (availabilityId != null ? availabilityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Availability)) {
            return false;
        }
        Availability other = (Availability) object;
        if ((this.availabilityId == null && other.availabilityId != null) || (this.availabilityId != null && !this.availabilityId.equals(other.availabilityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kth.iv1201.recruitmentserv.Availability[ availabilityId=" + availabilityId + " ]";
    }
    
}
