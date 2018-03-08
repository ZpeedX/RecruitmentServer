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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * Entity for availability
 * @author Evan
 * @author Oscar
 */
@Entity
@Table(name = "AVAILABILITY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Availability.findAll", query = "SELECT a FROM Availability a")
    , @NamedQuery(name = "Availability.findByAvailabilityId", query = "SELECT a FROM Availability a WHERE a.availabilityId = :availabilityId")
    , @NamedQuery(name = "Availability.findByFromDate", query = "SELECT a FROM Availability a WHERE a.fromDate = :fromDate")
    , @NamedQuery(name = "Availability.findByToDate", query = "SELECT a FROM Availability a WHERE a.toDate = :toDate")
    , @NamedQuery(name = "Availability.findByPersonObject", query = "SELECT a FROM Availability a WHERE a.personId = :person")})

public class Availability implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AVAILABILITY_ID", nullable = false)
    private Long availabilityId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FROM_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TO_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @NotNull
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID", nullable = false)
    @ManyToOne(optional = false)
    private Person personId;
    
    /**
     * Constructor
     */
    public Availability() {
    }

    /**
     * Class Constructor
     *
     * @param availabilityId sets the availabilityId property
     */
    public Availability(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    /**
     * Class Constructor
     *
     * @param availabilityId sets the availabilityId property
     * @param fromDate sets the fromDate property
     * @param toDate sets the toDAte property
     */
    public Availability(Long availabilityId, Date fromDate, Date toDate) {
        this.availabilityId = availabilityId;
        this.fromDate = new Date(fromDate.getTime());
        this.toDate = new Date(toDate.getTime());
    }

    /**
     * Gets the value of the availabilityId property
     *
     * @return availabilityId as Long object
     */
    public Long getAvailabilityId() {
        return availabilityId;
    }

    /**
     * Sets the availabilityId property
     *
     * @param availabilityId the availabilityId to set
     */
    public void setAvailabilityId(Long availabilityId) {
        this.availabilityId = availabilityId;
    }

    /**
     * Gets the value of the fromDate property
     *
     * @return fromDate as Date object
     */
    public Date getFromDate() {
        return fromDate != null ? new Date(fromDate.getTime()) : null;
    }

    /**
     * Sets the fromDate property
     *
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate != null ? new Date(fromDate.getTime()) : null;
    }

    /**
     * Gets the value of the toDate property
     *
     * @return toDate as Date object
     */
    public Date getToDate() {
        return toDate != null ? new Date(toDate.getTime()) : null;
    }

    /**
     * Sets the toDate property
     *
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate != null ? new Date(toDate.getTime()) : null;
    }

    /**
     * Gets the value of the personId property
     *
     * @return personId as Person object
     */
    public Person getPersonId() {
        return personId;
    }

    /**
     * Sets the personId property
     *
     * @param personId the personId to set
     */
    public void setPersonId(Person personId) {
        this.personId = personId;
    }
    
    
    /**
     * @return returns Object of this class as string 
     */
    @Override
    public String toString() {
        return "Availability{" + "availabilityId=" + availabilityId + ", fromDate=" + fromDate + ", toDate=" + toDate + ", personId=" + personId + '}';
    }
    
}
