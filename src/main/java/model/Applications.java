/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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
@Table(name = "APPLICATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Applications.findAll", query = "SELECT a FROM Applications a")
    , @NamedQuery(name = "Applications.findByApplicationId", query = "SELECT a FROM Applications a WHERE a.applicationId = :applicationId")
    , @NamedQuery(name = "Applications.findByParams",
            query = "SELECT DISTINCT app "
            + "FROM Applications app, CompetenceProfile cp, Availability av "
            + "WHERE (LOWER(app.personId.name) = LOWER(:firstname) OR :firstname = '')"
            + "AND ((cp.competenceId = :cpId AND app.personId = cp.personId) OR :cpId = 0) "
            + "AND (app.registrationDate = :regDate OR :regDate = :tempDate) "
            + "AND ((av.fromDate >= :datePeriodFrom AND app.personId = av.personId) OR :datePeriodFrom = :tempDate) "
            + "AND ((av.toDate <= :datePeriodTo AND app.personId = av.personId ) OR :datePeriodTo = :tempDate)"
    )})
public class Applications implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "APPLICATION_ID")
    private Long applicationId;
    @Column(name = "REGISTRATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date registrationDate;
    @Column(name = "STATUS_ID")
    private BigInteger statusId;
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
    @ManyToOne
    private Person personId;

    public Applications() {
    }

    public Applications(Long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Gets the value of the applicationsId property
     *
     * @return applicationId as long value
     */
    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * Sets the applicationId property
     *
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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
     * Gets the value of the registrationDate property
     *
     * @return registrationDate as Date object
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the registrationDate property
     *
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public BigInteger getStatusId() {
        return statusId;
    }

    public void setStatusId(BigInteger statusId) {
        this.statusId = statusId;
    }

    /**
     * Adds properties in this class into json object
     *
     * @return Json Object
     */
    public JsonObject toJson() {
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("applicationId", applicationId.toString())
                .add("firstname", personId.getName())
                .add("surname", personId.getSurname())
                .add("email", personId.getEmail());
        return obj.build();
    } 
}
