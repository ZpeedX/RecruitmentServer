/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity for a competence
 * @author Emil
 * @author Oscar
 */
@Entity
@Table(name = "COMPETENCE_NAME", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"COMPETENCE_ID", "NAME", "SUPPORTED_LANGUAGE_ID"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompetenceName.findAll", query = "SELECT c FROM CompetenceName c")
    , @NamedQuery(name = "CompetenceName.findByCompetenceNameId", query = "SELECT c FROM CompetenceName c WHERE c.competenceNameId = :competenceNameId")
    , @NamedQuery(name = "CompetenceName.findByName", query = "SELECT c FROM CompetenceName c WHERE c.name = :name")
    , @NamedQuery(name = "CompetenceName.findByCompetenceId", query = "SELECT c FROM CompetenceName c WHERE c.competenceId = :competenceId")
    , @NamedQuery(name = "CompetenceName.findAllByLang", query = "SELECT c FROM CompetenceName c WHERE c.supportedLanguageId.locale = :locale")
})
public class CompetenceName implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COMPETENCE_NAME_ID", nullable = false)
    private Long competenceNameId;
    @NotNull
    @JoinColumn(name = "SUPPORTED_LANGUAGE_ID", referencedColumnName = "SUPPORTED_LANGUAGE_ID", nullable = false)
    @ManyToOne(optional = false)
    private SupportedLanguage supportedLanguageId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMPETENCE_ID", nullable = false)
    private long competenceId;
    
    /**
     * Class constructor
     */
    public CompetenceName() {
    }

    /**
     * Class Constructor
     *
     * @param competenceNameId sets the competenceNameId property
     */
    public CompetenceName(Long competenceNameId) {
        this.competenceNameId = competenceNameId;
    }

    /**
     * Class Constructor
     *
     * @param competenceNameId sets the competenceNameId property
     * @param name sets the name property
     * @param competenceId sets the competenceId property
     */
    public CompetenceName(Long competenceNameId, String name, long competenceId) {
        this.competenceNameId = competenceNameId;
        this.name = name;
        this.competenceId = competenceId;
    }

    /**
     * Gets the value of the competenceNameId property
     *
     * @return competenceNameId as Long object
     */
    public Long getCompetenceNameId() {
        return competenceNameId;
    }

    /**
     * Sets the competenceNameId property
     *
     * @param competenceNameId the competenceNameId to set
     */
    public void setCompetenceNameId(Long competenceNameId) {
        this.competenceNameId = competenceNameId;
    }

    /**
     * Gets the value of the supportedLanguageId property
     *
     * @return supportedLanguageId as SupportedLanguage object
     */
    public SupportedLanguage getSupportedLanguageId() {
        return supportedLanguageId;
    }

    /**
     * Sets the supportedLanguageId property
     *
     * @param supportedLanguageId the supportedLanguageId to set
     */
    public void setSupportedLanguageId(SupportedLanguage supportedLanguageId) {
        this.supportedLanguageId = supportedLanguageId;
    }

    /**
     * Gets the value of the name property
     *
     * @return name as String object
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name property
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets the value of the competenceId property
     *
     * @return competenceId as long
     */    
    public long getCompetenceId() {
        return competenceId;
    }

    /**
     * Sets the competenceId property
     *
     * @param competenceId the competenceId to set
     */
    public void setCompetenceId(long competenceId) {
        this.competenceId = competenceId;
    }
}
