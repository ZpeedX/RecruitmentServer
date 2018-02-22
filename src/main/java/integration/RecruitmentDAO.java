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
import model.AvailabilityDTO;

import javax.validation.ConstraintViolationException;
/**
 *
 * @author Emil
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class RecruitmentDAO {

    @PersistenceContext(unitName = "recruitmentPU")
    private EntityManager em;

    //Store a person in the database
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

    //Check if a user is already in database
    public Person existsUser(String username) {
        if(username == null || username.isEmpty()) { return null;}
        
        try{
            return em.createNamedQuery("Person.findByUsername", Person.class)
                    .setParameter("username", username).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    public List<CompetenceName> listCompetence() {
        return em.createNamedQuery("CompetenceName.findAll", CompetenceName.class).getResultList();
    }
    public List<CompetenceName> getAllCompetences(String locale) {
        TypedQuery<CompetenceName> query = em.createNamedQuery("CompetenceName.findAllByLang", CompetenceName.class)
                .setParameter("locale", locale);
        return query.getResultList();
    }
    
    public SupportedLanguage getSlId(String locale){
        TypedQuery<SupportedLanguage> p = em.createNamedQuery("SupportedLanguage.findByLocale", SupportedLanguage.class)
                .setParameter("locale", locale);
        return p.getSingleResult();
    }
    
    public List<Applications> getAllApplications() {
        TypedQuery<Applications> query = em.createNamedQuery("Applications.findAll", Applications.class);
        return query.getResultList();
    }

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

    /*public void addAvailabilities(String user, List<AvailabilityDTO> availabilities) {
        Person person = em.createNamedQuery("Person.findByUsername", Person.class).setParameter("username", user).getSingleResult();
        System.out.println("Person is : " + person.getName() + ", email: " + person.getEmail());
        
        availabilities.forEach(a -> {
            Availability availability = new Availability();
            availability.setPersonId(person);
            availability.setFromDate(a.getFromDate());
            availability.setToDate(a.getToDate());
            try {
                em.persist(availability);
            } catch (ConstraintViolationException e) { // LOG these errors
                e.getConstraintViolations().forEach(err -> System.out.println("err = " + err.toString()));
            } catch (Exception ex) {
                System.out.println("ERROR ADDING TO DB: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }*/
	
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
