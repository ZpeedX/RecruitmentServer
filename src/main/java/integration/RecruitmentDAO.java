/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;


import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                return getPerson(newUser.getUsername());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Person getPerson(String username) {
        TypedQuery<Person> p = em.createNamedQuery("Person.findByUsername", Person.class)
                .setParameter("username", username);

        return p.getSingleResult();
    }

    public Role getRole(String name) {
        TypedQuery<Role> r = em.createNamedQuery("Role.findByName", Role.class)
                .setParameter("name", name);
        return r.getSingleResult();
    }

    //Check if a user is already in database
    public Person existsUser(String username) {
        try{
        TypedQuery<Person> p = em.createNamedQuery("Person.findByUsername", Person.class)
                .setParameter("username", username);
            return p.getSingleResult();
        } catch(Exception e){
            return null;
        }
    }
    public List<CompetenceName> listCompetence() {
        return em.createNamedQuery("CompetenceName.findAll", CompetenceName.class).getResultList();
    }
    public List<CompetenceName> getAllCompetences() {
        TypedQuery<CompetenceName> query = em.createNamedQuery("CompetenceName.findAll", CompetenceName.class);
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

    public List<Applications> getApplications(Date submissionDate, String periodFrom, String periodTo, long competence, String firstname) {
        Date tempDate = null;
        try{
                 tempDate = new SimpleDateFormat("d-MM-yyyy").parse("1-11-0000");
        }catch(ParseException e){
            
        }
                TypedQuery<Applications> query = 
                        em.createNamedQuery("Applications.findByParams", Applications.class)
                        .setParameter("firstname", firstname)
                        .setParameter("cpId", competence)
                        .setParameter("regDate", submissionDate, TemporalType.DATE)
                        .setParameter("tempDate", tempDate, TemporalType.DATE);
                
        return query.getResultList();
    }

    public void addAvailabilities(String user, List<AvailabilityDTO> availabilities) {
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
    }
	
    public void addCompetenceProfiles(String user, List<CompetenceProfileDTO> profiles) {
        Person person = em.createNamedQuery("Person.findByUsername", Person.class).setParameter("username", user).getSingleResult();
        System.out.println("Person is : " + person.getName() + ", email: " + person.getEmail());
        
        profiles.forEach(p -> {
            CompetenceProfile cp = new CompetenceProfile();
            cp.setCompetenceId(p.getCompetenceId());
            cp.setYearsOfExperience((long) p.getYearsOfExperience());
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
}
