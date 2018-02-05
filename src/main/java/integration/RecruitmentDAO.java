/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Person;
import model.Role;
import net.User;

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
    public boolean registerPerson(Person newUser) {
        try{
        if (!existsUser(newUser)) {
            Role r = getRole("Applicant");
            newUser.setRoleId(r);
            em.persist(newUser);
            return true;
        } else {
            return false;
        }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public Role getRole(String name){
        TypedQuery<Role> r = em.createNamedQuery("Role.findByName", Role.class)
                .setParameter("name", name);
        return r.getSingleResult();
    }
    
    //Check if a user is already in database
    public boolean existsUser(Person user) {
        TypedQuery<Person> p = em.createNamedQuery("Person.findByUsername", Person.class)
                .setParameter("username", user.getUsername());
        
        return !p.getResultList().isEmpty();
    }
    
    public boolean authenticateUser(User user) {
        Query q = em.createNativeQuery(
                "SELECT a.username FROM PERSON a WHERE"
                + " (a.username = ? AND a.password = ?)");
        q.setParameter(1, user.getUsername());
        q.setParameter(2, user.getPassword());
        List<User> ud = q.getResultList();
        if (ud.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
