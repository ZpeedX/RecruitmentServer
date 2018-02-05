/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Evan
 */
@Entity
@Table(name = "COMPETENCE_PROFILE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompetenceProfile.findAll", query = "SELECT c FROM CompetenceProfile c")
    , @NamedQuery(name = "CompetenceProfile.findByCompetenceProfileId", query = "SELECT c FROM CompetenceProfile c WHERE c.competenceProfileId = :competenceProfileId")
    , @NamedQuery(name = "CompetenceProfile.findByYearsOfExperience", query = "SELECT c FROM CompetenceProfile c WHERE c.yearsOfExperience = :yearsOfExperience")})
public class CompetenceProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMPETENCE_PROFILE_ID")
    private Long competenceProfileId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "YEARS_OF_EXPERIENCE")
    private BigDecimal yearsOfExperience;
    @JoinColumn(name = "COMPETENCE_ID", referencedColumnName = "COMPETENCE_ID")
    @ManyToOne(optional = false)
    private Competence competenceId;
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
    @ManyToOne(optional = false)
    private Person personId;

    public CompetenceProfile() {
    }

    public CompetenceProfile(Long competenceProfileId) {
        this.competenceProfileId = competenceProfileId;
    }

    public CompetenceProfile(Long competenceProfileId, BigDecimal yearsOfExperience) {
        this.competenceProfileId = competenceProfileId;
        this.yearsOfExperience = yearsOfExperience;
    }

    public Long getCompetenceProfileId() {
        return competenceProfileId;
    }

    public void setCompetenceProfileId(Long competenceProfileId) {
        this.competenceProfileId = competenceProfileId;
    }

    public BigDecimal getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(BigDecimal yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Competence getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(Competence competenceId) {
        this.competenceId = competenceId;
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
        hash += (competenceProfileId != null ? competenceProfileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompetenceProfile)) {
            return false;
        }
        CompetenceProfile other = (CompetenceProfile) object;
        if ((this.competenceProfileId == null && other.competenceProfileId != null) || (this.competenceProfileId != null && !this.competenceProfileId.equals(other.competenceProfileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kth.iv1201.recruitmentserv.CompetenceProfile[ competenceProfileId=" + competenceProfileId + " ]";
    }
    
}
