/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity for user
 *
 * @author Evan
 */
@Entity
@Table(name = "PERSON", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"USERNAME"})
    , @UniqueConstraint(columnNames = {"SSN"})})
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
    , @NamedQuery(name = "Person.findByPersonId", query = "SELECT p FROM Person p WHERE p.personId = :personId")
    , @NamedQuery(name = "Person.findByName", query = "SELECT p FROM Person p WHERE p.name = :name")
    , @NamedQuery(name = "Person.findBySurname", query = "SELECT p FROM Person p WHERE p.surname = :surname")
    , @NamedQuery(name = "Person.findBySsn", query = "SELECT p FROM Person p WHERE p.ssn = :ssn")
    , @NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email = :email")
    , @NamedQuery(name = "Person.findByPassword", query = "SELECT p FROM Person p WHERE p.password = :password")
    , @NamedQuery(name = "Person.findByUsername", query = "SELECT p FROM Person p WHERE p.username = :username")})
public class Person implements Serializable {

    @OneToMany(mappedBy = "personId")
    private List<CompetenceProfile> competenceProfileList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId")
    private List<Applications> applicationsList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERSON_ID", nullable = false)
    private long personId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SURNAME", nullable = false, length = 255)
    private String surname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SSN", nullable = false, length = 255)
    private String ssn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "USERNAME", nullable = false, length = 255)
    private String username;
    @NotNull
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID", nullable = false)
    @ManyToOne(optional = false)
    private Role roleId;

    /**
     * Class Constructor
     */
    public Person() {
    }

    /**
     * Class Constructor
     *
     * @param personId sets the personId property
     */
    public Person(Long personId) {
        this.personId = personId;
    }

    /**
     * Class Constructor
     *
     * @param name sets the name property
     * @param surname sets the surname property
     * @param ssn sets the ssn property
     * @param email sets the email property
     * @param password sets the password property
     * @param username sets the username property
     */
    public Person(String name, String surname, String ssn, String email, String password, String username) {
        this.name = name;
        this.surname = surname;
        this.ssn = ssn;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    /**
     * Gets the value of the personId property
     *
     * @return personId as long
     */
    public long getPersonId() {
        return personId;
    }

    /**
     * Sets the personId property
     *
     * @param personId the personId to set
     */
    public void setPersonId(long personId) {
        this.personId = personId;
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
     * Gets the value of the surname property
     *
     * @return surname as String object
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname property
     *
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the value of the snn property
     *
     * @return ssn as String object
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Sets the ssn property
     *
     * @param ssn the ssn to set
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * Gets the value of the email property
     *
     * @return email as String object
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email property
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the value of the password property
     *
     * @return password as String object
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password property
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
     * Gets the value of the roleId property
     *
     * @return roleId as Role object
     */
    public Role getRoleId() {
        return roleId;
    }

    /**
     * Sets the roleId property
     *
     * @param roleId the roleId to set
     */
    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    /**
     * Checks if provided password matches the password property in the Person
     * object
     *
     * @param pass provided password
     * @return Boolean True if they match, else False
     */
    public boolean authenticate(String pass) {
        return pass.equals(password);
    }

    /**
     * @return returns Object of this class as string
     */
    @Override
    public String toString() {
        return "Person{" + "competenceProfileList=" + competenceProfileList + ", applicationsList=" + applicationsList + ", personId=" + personId + ", name=" + name + ", surname=" + surname + ", ssn=" + ssn + ", email=" + email + ", password=" + password + ", username=" + username + ", roleId=" + roleId + '}';
    }

}
