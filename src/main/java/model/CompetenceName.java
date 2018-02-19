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
 * @author Emil
 */
@Entity
@Table(name = "COMPETENCE_NAME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompetenceName.findAll", query = "SELECT c FROM CompetenceName c")
    , @NamedQuery(name = "CompetenceName.findByCompetenceNameId", query = "SELECT c FROM CompetenceName c WHERE c.competenceNameId = :competenceNameId")
    , @NamedQuery(name = "CompetenceName.findBySupportedLanguageId", query = "SELECT c FROM CompetenceName c WHERE c.supportedLanguageId = :supportedLanguageId")
    , @NamedQuery(name = "CompetenceName.findByName", query = "SELECT c FROM CompetenceName c WHERE c.name = :name")})
public class CompetenceName implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMPETENCE_NAME_ID")
    private Long competenceNameId;
    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "SUPPORTED_LANGUAGE_ID", referencedColumnName = "SUPPORTED_LANGUAGE_ID")
    @ManyToOne(optional = false)
    private SupportedLanguage supportedLanguageId;
    @Column(name = "NAME")
    private String name;

    public CompetenceName() {
    }

    public CompetenceName(Long competenceNameId) {
        this.competenceNameId = competenceNameId;
    }

    public CompetenceName(Long competenceNameId, String name) {
        this.competenceNameId = competenceNameId;
        this.name = name;
    }

    public Long getCompetenceNameId() {
        return competenceNameId;
    }

    public void setCompetenceNameId(Long competenceNameId) {
        this.competenceNameId = competenceNameId;
    }

    public SupportedLanguage getSupportedLanguageId() {
        return supportedLanguageId;
    }

    public void setSupportedLanguageId(SupportedLanguage supportedLanguageId) {
        this.supportedLanguageId = supportedLanguageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (competenceNameId != null ? competenceNameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompetenceName)) {
            return false;
        }
        CompetenceName other = (CompetenceName) object;
        if ((this.competenceNameId == null && other.competenceNameId != null) || (this.competenceNameId != null && !this.competenceNameId.equals(other.competenceNameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.CompetenceName[ competenceNameId=" + competenceNameId + " ]";
    }
    
}
