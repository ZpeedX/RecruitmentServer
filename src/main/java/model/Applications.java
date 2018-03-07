/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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
 * Entity for an application
 * @author Evan
 * @author Oscar
 */
@Entity
@Table(name = "APPLICATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Applications.findAll", query = "SELECT a FROM Applications a")
    , @NamedQuery(name = "Applications.findByPersonObject", query = "SELECT a FROM Applications a WHERE a.personId = :person")
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "APPLICATION_ID", nullable = false)
    private Long applicationId;
    @NotNull
    @Column(name = "REGISTRATION_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date registrationDate;
    @NotNull
    @JoinColumn(name = "STATUS_NAME", referencedColumnName = "STATUS_NAME_ID", nullable = false)
    @ManyToOne
    private StatusName statusId;
    @NotNull
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID", nullable = false)
    @ManyToOne
    private Person personId;
    
    /**
     * Constructor
     */
    public Applications() {
    }
    /**
     * Constuct application from id
     * @param applicationId 
     */
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
        return new Date(registrationDate.getTime());
    }

    /**
     * Sets the registrationDate property
     *
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = new Date(registrationDate.getTime());
    }
    /**
     * Returns status id of application
     * @return status id
     */
    public StatusName getStatusId() {
        return statusId;
    }
    /**
     * Sets the status id of the application
     * @param statusId the status id
     */
    public void setStatusId(StatusName statusId) {
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
