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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oscar
 */
@Entity
@Table(name = "TOKEN")
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
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ROLE")
    private String role;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TOKEN")
    private String token;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISSUED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issued;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXPIRES")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;

    public Token() {
    }

    public Token(String username) {
        this.username = username;
    }

    public Token(String username, String role, String token, Date issued, Date expires) {
        this.username = username;
        this.role = role;
        this.token = token;
        this.issued = issued;
        this.expires = expires;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getIssued() {
        return issued;
    }

    public void setIssued(Date issued) {
        this.issued = issued;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Token)) {
            return false;
        }
        Token other = (Token) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Token[ username=" + username + " ]";
    }
    
}
