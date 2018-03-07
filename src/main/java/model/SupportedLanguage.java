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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity for supported languages
 * @author Emil
 */
@Entity
@Table(name = "SUPPORTED_LANGUAGE", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"LOCALE"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupportedLanguage.findAll", query = "SELECT s FROM SupportedLanguage s")
    , @NamedQuery(name = "SupportedLanguage.findBySupportedLanguageId", query = "SELECT s FROM SupportedLanguage s WHERE s.supportedLanguageId = :supportedLanguageId")
    , @NamedQuery(name = "SupportedLanguage.findByLocale", query = "SELECT s FROM SupportedLanguage s WHERE s.locale = :locale")})
public class SupportedLanguage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SUPPORTED_LANGUAGE_ID", nullable = false)
    private Long supportedLanguageId;
    @Basic(optional = false)
    @NotNull
    @Size(max = 255)
    @Column(name = "LOCALE", nullable = false, length = 255)
    private String locale;
    
    /**
     * Class Constructor
     */
    public SupportedLanguage() {
    }

    /**
     * Class Constructor
     *
     * @param supportedLanguageId sets the supportedLanguageId property
     * 
     */
    public SupportedLanguage(Long supportedLanguageId) {
        this.supportedLanguageId = supportedLanguageId;
    }
    
    /**
     * Gets the value of the supportedLanguageId property
     *
     * @return supportedLanguageId as Long object
     */
    public Long getSupportedLanguageId() {
        return supportedLanguageId;
    }

    /**
     * Sets the supportedLanguageId property
     *
     * @param supportedLanguageId the supportedLanguageId to set
     */  
    public void setSupportedLanguageId(Long supportedLanguageId) {
        this.supportedLanguageId = supportedLanguageId;
    }

    
    /**
     * Gets the value of the locale property
     *
     * @return locale as String object
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the locale property
     *
     * @param locale the locale to set
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }
}
