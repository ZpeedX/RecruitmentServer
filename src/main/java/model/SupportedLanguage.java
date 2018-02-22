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
@Table(name = "SUPPORTED_LANGUAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupportedLanguage.findAll", query = "SELECT s FROM SupportedLanguage s")
    , @NamedQuery(name = "SupportedLanguage.findBySupportedLanguageId", query = "SELECT s FROM SupportedLanguage s WHERE s.supportedLanguageId = :supportedLanguageId")
    , @NamedQuery(name = "SupportedLanguage.findByLocale", query = "SELECT s FROM SupportedLanguage s WHERE s.locale = :locale")})
public class SupportedLanguage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SUPPORTED_LANGUAGE_ID")
    private Long supportedLanguageId;
    @NotNull
    @Size(max = 255)
    @Column(name = "LOCALE")
    private String locale;

    public SupportedLanguage() {
    }

    public SupportedLanguage(Long supportedLanguageId) {
        this.supportedLanguageId = supportedLanguageId;
    }

    public Long getSupportedLanguageId() {
        return supportedLanguageId;
    }

    public void setSupportedLanguageId(Long supportedLanguageId) {
        this.supportedLanguageId = supportedLanguageId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supportedLanguageId != null ? supportedLanguageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupportedLanguage)) {
            return false;
        }
        SupportedLanguage other = (SupportedLanguage) object;
        if ((this.supportedLanguageId == null && other.supportedLanguageId != null) || (this.supportedLanguageId != null && !this.supportedLanguageId.equals(other.supportedLanguageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.SupportedLanguage[ SupportedLanguageId=" + supportedLanguageId + " ]";
    }
    
}
