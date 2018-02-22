/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
    , @NamedQuery(name = "CompetenceName.findByName", query = "SELECT c FROM CompetenceName c WHERE c.name = :name")
    , @NamedQuery(name = "CompetenceName.findByCompetenceId", query = "SELECT c FROM CompetenceName c WHERE c.competenceId = :competenceId")
    , @NamedQuery(name = "CompetenceName.findAllByLang", query = "SELECT c FROM CompetenceName c WHERE c.supportedLanguageId.locale = :locale")
})
public class CompetenceName implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMPETENCE_NAME_ID")
    private Long competenceNameId;
    @JoinColumn(name = "SUPPORTED_LANGUAGE_ID", referencedColumnName = "SUPPORTED_LANGUAGE_ID")
    @ManyToOne(optional = false)
    private SupportedLanguage supportedLanguageId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMPETENCE_ID")
    private long competenceId;

    public CompetenceName() {
    }

    public CompetenceName(Long competenceNameId) {
        this.competenceNameId = competenceNameId;
    }

    public CompetenceName(Long competenceNameId, String name, long competenceId) {
        this.competenceNameId = competenceNameId;
        this.name = name;
        this.competenceId = competenceId;
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

    public long getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(long competenceId) {
        this.competenceId = competenceId;
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
    
    public JsonObject toJson() {
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("name", name)
                .add("id", competenceNameId);
        return obj.build();
    }
    
}
