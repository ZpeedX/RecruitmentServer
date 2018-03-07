/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A unique token for logged on users
 * @author Oscar
 */
@Entity
@Table(name = "TOKEN", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"TOKEN"})
    , @UniqueConstraint(columnNames = {"USERNAME"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Token.findAll", query = "SELECT t FROM Token t")
    , @NamedQuery(name = "Token.findByUsername", query = "SELECT t FROM Token t WHERE t.username = :username")
    , @NamedQuery(name = "Token.findByRole", query = "SELECT t FROM Token t WHERE t.role = :role")
    , @NamedQuery(name = "Token.findByToken", query = "SELECT t FROM Token t WHERE t.token = :token")
    , @NamedQuery(name = "Token.findByIssued", query = "SELECT t FROM Token t WHERE t.issued = :issued")
    , @NamedQuery(name = "Token.findByExpires", query = "SELECT t FROM Token t WHERE t.expires = :expires")})
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "USERNAME", nullable = false, length = 255)
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ROLE", nullable = false, length = 255)
    private String role;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TOKEN", nullable = false, length = 255)
    private String token;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISSUED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date issued;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXPIRES", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;

    /**
     * Class Constructor
     */
    public Token() {
    }

    /**
     * Class Constructor
     *
     * @param username sets the username property
     */
    public Token(String username) {
        this.username = username;
    }

    /**
     * Class Constructor
     *
     * @param username sets the username property
     * @param role sets the role property
     * @param token sets the token property
     * @param issued sets the issued property
     * @param expires sets the expires property
     */
    public Token(String username, String role, String token, Date issued, Date expires) {
        this.username = username;
        this.role = role;
        this.token = token;
        this.issued = new Date(issued.getTime());
        this.expires = new Date(expires.getTime());
    }

    /**
     * Gets the value of the username property
     *
     * @return username as String object
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username property
     *
     * @param username the password to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the value of the role property
     *
     * @return role as String object
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role property
     *
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the value of the token property
     *
     * @return token as String object
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token property
     *
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the value of the issued property
     *
     * @return issued as Date object
     */
    public Date getIssued() {
        return new Date(issued.getTime());
    }

    /**
     * Sets the issued property
     *
     * @param issued the token to set
     */
    public void setIssued(Date issued) {
        this.issued = new Date(issued.getTime());
    }

    /**
     * Gets the value of the expires property
     *
     * @return expires as Date object
     */
    public Date getExpires() {
        return new Date(expires.getTime());
    }

    /**
     * Sets the expires property
     *
     * @param expires the expires to set
     */
    public void setExpires(Date expires) {
        this.expires = new Date(expires.getTime());
    }
}
