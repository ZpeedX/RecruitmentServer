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
import model.Person;
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
    
    //Store a user in the database
    public boolean storeUser(Person newUser) {
        try{
        
        if (!existsUser(newUser)) {
            em.persist(newUser);
            return true;
        } else {
            return false;
        }
        }catch(Exception e){
            return false;
        }
    }
    //Check if a user is already in database
    public boolean existsUser(Person user) {
        Person u = em.find(Person.class, user.getUsername());
        if (u != null) {
            return true;
        } else {
            return false;
        }
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
