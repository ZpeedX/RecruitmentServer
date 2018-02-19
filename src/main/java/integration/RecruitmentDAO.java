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
import model.Person;
import model.Role;

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

    public List<CompetenceName> getAllCompetences() {
        TypedQuery<CompetenceName> query = em.createNamedQuery("Competence.findAll", CompetenceName.class);
        return query.getResultList();
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
}
