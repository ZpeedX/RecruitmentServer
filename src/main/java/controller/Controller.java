/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import integration.RecruitmentDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import model.Competence;
import model.Person;
import net.User;

/**
 *
 * @author Emil
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class Controller {
    
    @EJB
    RecruitmentDAO rdao;
    
    public Person authenticate(String username) {
        return rdao.existsUser(username);
    }
    public Person register(Person newUser) {
        return rdao.registerPerson(newUser);
    }
    
    public List<Competence> getAllCompetences() {
        return rdao.getAllCompetences();
    }
    
}
