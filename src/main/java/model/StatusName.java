/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigInteger;
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
 *
 * @author Evan
 * @author Oscar
 */
@Entity
@Table(name = "STATUS_NAME", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"STATUS_ID", "NAME", "SUPPORTED_LANGUAGE_ID"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatusName.findAll", query = "SELECT s FROM StatusName s")
    , @NamedQuery(name = "StatusName.findByStatusNameId", query = "SELECT s FROM StatusName s WHERE s.statusNameId = :statusNameId")
    , @NamedQuery(name = "StatusName.findByStatusId", query = "SELECT s FROM StatusName s WHERE s.statusId = :statusId")
    , @NamedQuery(name = "StatusName.findByName", query = "SELECT s FROM StatusName s WHERE s.name = :name")})
public class StatusName implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STATUS_NAME_ID", nullable = false)
    private Long statusNameId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS_ID", nullable = false)
    private BigInteger statusId;
    @Basic(optional = false)
    @NotNull
    @Size(max = 255)
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;
    @NotNull
    @JoinColumn(name = "SUPPORTED_LANGUAGE_ID", referencedColumnName = "SUPPORTED_LANGUAGE_ID", nullable = false)
    @ManyToOne(optional = false)
    private SupportedLanguage supportedLanguageId;

    public StatusName() {
    }

    public StatusName(Long statusNameId) {
        this.statusNameId = statusNameId;
    }

    public Long getStatusNameId() {
        return statusNameId;
    }

    public void setStatusNameId(Long statusNameId) {
        this.statusNameId = statusNameId;
    }

    public BigInteger getStatusId() {
        return statusId;
    }

    public void setStatusId(BigInteger statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupportedLanguage getSupportedLanguageId() {
        return supportedLanguageId;
    }

    public void setSupportedLanguageId(SupportedLanguage supportedLanguageId) {
        this.supportedLanguageId = supportedLanguageId;
    }

}
