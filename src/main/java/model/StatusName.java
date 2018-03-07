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
 * Entity for a status
 *
 * @author Emil
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

    /**
     * Constructor
     */
    public StatusName() {
    }

    /**
     * Creates status by id
     *
     * @param statusNameId
     */
    public StatusName(Long statusNameId) {
        this.statusNameId = statusNameId;
    }

    /**
     * Returns the status name id
     *
     * @return status name id
     */
    public Long getStatusNameId() {
        return statusNameId;
    }

    /**
     * Set the id of the status name
     *
     * @param statusNameId id of status
     */
    public void setStatusNameId(Long statusNameId) {
        this.statusNameId = statusNameId;
    }

    /**
     * Returns the status name id
     *
     * @return status name id
     */
    public BigInteger getStatusId() {
        return statusId;
    }

    /**
     * Set the id of the status
     *
     * @param statusId id of status
     */
    public void setStatusId(BigInteger statusId) {
        this.statusId = statusId;
    }

    /**
     * Returns the name of the status
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the status
     *
     * @param name status name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the language id of the status
     *
     * @return language id of status
     */
    public SupportedLanguage getSupportedLanguageId() {
        return supportedLanguageId;
    }

    /**
     * Set the language id of the status
     *
     * @param supportedLanguageId status language id
     */
    public void setSupportedLanguageId(SupportedLanguage supportedLanguageId) {
        this.supportedLanguageId = supportedLanguageId;
    }

}
