/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import integration.RecruitmentDAO;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import net.User;

/**
 *
 * @author Emil
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class Controller {
    
    @EJB
    RecruitmentDAO edao;
    
    public boolean authenticate(User newUser) {
        return edao.authenticateUser(newUser);
    }
    
}
