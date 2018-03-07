
package model;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity for competence for user
 * @author Evan
 * @author Oscar
 */
@Entity
@Table(name = "COMPETENCE_PROFILE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompetenceProfile.findAll", query = "SELECT c FROM CompetenceProfile c")
    , @NamedQuery(name = "CompetenceProfile.findByCompetenceProfileId", query = "SELECT c FROM CompetenceProfile c WHERE c.competenceProfileId = :competenceProfileId")
    , @NamedQuery(name = "CompetenceProfile.findByYearsOfExperience", query = "SELECT c FROM CompetenceProfile c WHERE c.yearsOfExperience = :yearsOfExperience")
    , @NamedQuery(name = "CompetenceProfile.findByCompetenceId", query = "SELECT c FROM CompetenceProfile c WHERE c.competenceId = :competenceId")
    , @NamedQuery(name = "CompetenceProfile.findByPersonObject", query = "SELECT c FROM CompetenceProfile c WHERE c.personId = :person")})
public class CompetenceProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COMPETENCE_PROFILE_ID", nullable = false)
    private Long competenceProfileId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "YEARS_OF_EXPERIENCE", precision = 4, scale = 2, nullable = false)
    private double yearsOfExperience;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMPETENCE_ID", nullable = false)
    private Long competenceId;
    @NotNull
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID", nullable = false)
    @ManyToOne(optional = false)
    private Person personId;

    /**
     * Class Constructor
     */
    public CompetenceProfile() {
    }

    /**
     * Class Constructor
     *
     * @param competenceProfileId sets the competenceProfileId property
     */
    public CompetenceProfile(Long competenceProfileId) {
        this.competenceProfileId = competenceProfileId;
    }

    /**
     * Gets the value of the competenceProfileId property
     *
     * @return competenceProfileId as Long object
     */
    public Long getCompetenceProfileId() {
        return competenceProfileId;
    }

    /**
     * Sets the competenceProfileId property
     *
     * @param competenceProfileId the competenceProfileId to set
     */
    public void setCompetenceProfileId(Long competenceProfileId) {
        this.competenceProfileId = competenceProfileId;
    }

    /**
     * Gets the value of the getYearsOfExperience property
     *
     * @return getYearsOfExperience as double
     */
    public double getYearsOfExperience() {
        return yearsOfExperience;
    }

    /**
     * Sets the yearsOfExperience property
     *
     * @param yearsOfExperience the yearsOfExperience to set
     */
    public void setYearsOfExperience(double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    /**
     * Gets the value of the competenceId property
     *
     * @return competenceId as Long object
     */
    public Long getCompetenceId() {
        return competenceId;
    }

    /**
     * Sets the competenceId property
     *
     * @param competenceId the competenceId to set
     */
    public void setCompetenceId(Long competenceId) {
        this.competenceId = competenceId;
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
}
