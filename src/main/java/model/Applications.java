/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
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
                    + "FROM Applications app, CompetenceProfile cp "
                    + "WHERE (app.personId.name = :firstname OR :firstname = '')"
                    + "AND ((cp.competenceId.competenceId = :cpId AND app.personId = cp.personId) OR :cpId = 0) "
                    + "AND (app.registrationDate = :regDate OR :regDate = :tempDate)"
                  )})
public class Applications implements Serializable {

    @Column(name = "REGISTRATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date registrationDate;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "APPLICATION_ID")
    private Long applicationId;
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
    @ManyToOne(optional = false)
    private Person personId;

    public Applications() {
    }

    public Applications(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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
        hash += (applicationId != null ? applicationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Applications)) {
            return false;
        }
        Applications other = (Applications) object;
        if ((this.applicationId == null && other.applicationId != null) || (this.applicationId != null && !this.applicationId.equals(other.applicationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Applications[ applicationId=" + applicationId + " ]";
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    public JsonObject toJson() {
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("firstname", personId.getName())
                .add("surname", personId.getSurname())
                .add("email", personId.getEmail());
        return obj.build();
    }
}
