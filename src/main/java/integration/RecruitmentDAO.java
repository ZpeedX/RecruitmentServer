/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;


import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import model.Applications;
import model.CompetenceName;
import model.CompetenceProfile;
import model.CompetenceProfileDTO;
import model.Person;
import model.Role;
import model.SupportedLanguage;
import model.Availability;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Emil
 * @author Oscar
 * @author Evan
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class RecruitmentDAO {

    @PersistenceContext(unitName = "recruitmentPU")
    private EntityManager em;

    /**
     * This method stores a new user in the database.
     * 
     * @param newUser the new user to be stored.
     * @return Person the person stored or {@code null} if unsucessful in 
     * persisting the user.
     */
    public Person registerPerson(Person newUser) {
        try {
            if (existsUser(newUser.getUsername()) == null) {
                Role r = getRole("Applicant");
                newUser.setRoleId(r);
                em.persist(newUser);
                return newUser;
            } else {
                return null;
            }
        } catch (Exception e) { // LOG HERE
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method retrieves the person belonging to a specific username from 
     * the database.
     *
     * @param username the username of the person.
     * @return Person the person with the username or {@code null} if no such 
     * Person exists in the database.
     */
    public Person getPerson(String username) {
        try {
            return em.createNamedQuery("Person.findByUsername", Person.class)
                .setParameter("username", username).getSingleResult();
        } catch(Exception ex) {
            return null;
        }
        
    }

    private Role getRole(String name) {
        try {
            return em.createNamedQuery("Role.findByName", Role.class)
                .setParameter("name", name).getSingleResult();
        } catch(Exception ex) {
            return null;
        }
        
    }

    /**
     * This method checks in the database if there is a user with the specified
     * username.
     * 
     * @param username of the check.
     * @return Person the person with the enetered username or {@code null} if
     * no such person exists.
     */
    public Person existsUser(String username) {
        if(username == null || username.isEmpty()) { return null;}
        
        try{
            return em.createNamedQuery("Person.findByUsername", Person.class)
                    .setParameter("username", username).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This metohd returns a list with all the competences stored in the database.
     * 
     * @return List<CompetenceName> the list with all competences.
     */
    public List<CompetenceName> listCompetence() {
        return em.createNamedQuery("CompetenceName.findAll", CompetenceName.class).getResultList();
    }

    /**
     * This method retrieves all the competences in a specific language which
     * is chosen by the user.
     * 
     * @param locale language selected/specified.
     * @return List<CompetenceName> a list with competences in the specified language.
     */
    public List<CompetenceName> getAllCompetences(String locale) {
        TypedQuery<CompetenceName> query = em.createNamedQuery("CompetenceName.findAllByLang", CompetenceName.class)
                .setParameter("locale", locale);
        return query.getResultList();
    }
    
    /**
     *
     * @param locale
     * @return
     */
    public SupportedLanguage getSlId(String locale){
        TypedQuery<SupportedLanguage> p = em.createNamedQuery("SupportedLanguage.findByLocale", SupportedLanguage.class)
                .setParameter("locale", locale);
        return p.getSingleResult();
    }
    
    /**
     *
     * @return
     */
    public List<Applications> getAllApplications() {
        TypedQuery<Applications> query = em.createNamedQuery("Applications.findAll", Applications.class);
        return query.getResultList();
    }

    /**
     *
     * @param submissionDate
     * @param periodFrom
     * @param periodTo
     * @param competence
     * @param firstname
     * @param dummyDate
     * @return
     */
    public List<Applications> getApplications(Date submissionDate, Date periodFrom, Date periodTo, long competence, String firstname, Date dummyDate) {
        TypedQuery<Applications> query
                = em.createNamedQuery("Applications.findByParams", Applications.class)
                        .setParameter("firstname", firstname)
                        .setParameter("cpId", competence)
                        .setParameter("regDate", submissionDate, TemporalType.DATE)
                        .setParameter("tempDate", dummyDate, TemporalType.DATE)
                        .setParameter("datePeriodFrom", periodFrom, TemporalType.DATE)
                        .setParameter("datePeriodTo", periodTo, TemporalType.DATE);
        return query.getResultList();
    }

    /**
     * This method recieves a list with profiles belonging to a specific user
     * and persists these profiles in the database if they are valid. 
     * 
     * @param user username associated with the profiles.
     * @param profiles the profiles to be stored.
     */
    public void addCompetenceProfiles(String user, List<CompetenceProfileDTO> profiles) {
        Person person = em.createNamedQuery("Person.findByUsername", Person.class).setParameter("username", user).getSingleResult();
        System.out.println("Person is : " + person.getName() + ", email: " + person.getEmail());
        
        profiles.forEach(p -> {
            CompetenceProfile cp = new CompetenceProfile();
            cp.setCompetenceId(p.getCompetenceId());
            cp.setYearsOfExperience(p.getYearsOfExperience());
            cp.setPersonId(person);
            try {
                em.persist(cp);
            } catch (ConstraintViolationException e) { // LOG these errors
                e.getConstraintViolations().forEach(err -> System.out.println("err = " + err.toString()));
            } catch (Exception ex) {
                System.out.println("ERROR ADDING TO DB: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    /**
     * This method recieves a list with availabilities belonging to a specific
     * user and persists these in the database if they are valid. 
     * 
     * @param username the user associated with the availabilities.
     * @param availabilities the availabilities to be stored.
     */
    public void addAvailabilities(String username, List<Availability> availabilities) {
        Person person = em.createNamedQuery("Person.findByUsername", Person.class)
                .setParameter("username", username).getSingleResult();
        
        availabilities.forEach(av -> {
            try {
                av.setPersonId(person);
                em.persist(av);
            } catch(Exception ex) {
                System.out.println("ERROR ADDING AVAIL: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
    }
}
