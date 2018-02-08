/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public Person registerPerson(Person newUser) {
        try {
            if (!existsUser(newUser.getUsername())) {
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
    public boolean existsUser(String username) {
        TypedQuery<Person> p = em.createNamedQuery("Person.findByUsername", Person.class)
                .setParameter("username", username);

        return !p.getResultList().isEmpty();
    }
    
    public Person authenticateUser(User user) {
        if (existsUser(user.getUsername())) {
            Person p = getPerson(user.getUsername());

            if (p != null && p.getPassword().equals(user.getPassword())) {
                return p;
            }
        }
        return null;
    }
}
