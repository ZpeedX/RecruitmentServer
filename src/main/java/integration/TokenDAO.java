/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import java.util.Date;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Token;

/**
 *
 * @author Oscar
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class TokenDAO {
    
    @PersistenceContext(unitName = "tokenPU")
    private EntityManager em;
    
    public boolean addToken(Token token) {
        boolean success = false;
        
        try{
            em.merge(token);
            success = true;
        } catch(Exception ex) {
            System.out.println("ERROR ADDING TOKEN: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        return success;
    }
    
    public Token getTokenFromUsername(String username) {
        return em.find(Token.class, username);
    }
    
    public String getUsernameFromToken(String token) {
        try {
            return em.createNamedQuery("Token.findByToken", Token.class)
                .setParameter("token", token).getSingleResult().getUsername();
        } catch(NullPointerException nex) {
            System.out.println("ERROR GETTING USER: " + nex.getMessage());
            nex.printStackTrace();
            return null;
        }
    }
    
    public boolean isUserInRole(String username, String role) {
        Token token = em.find(Token.class, username);
        
        return token == null ? false : role.equals(token.getRole());
    }
    
    public boolean isValidToken(String issuedToken) {
        try {
            Token token = em.createNamedQuery("Token.findByToken", Token.class)
                .setParameter("token", issuedToken).getSingleResult();
        
            return validateToken(token);
        } catch(Exception ex) {
            return false;
        }
    }
    
    private boolean validateToken(Token token) {
        Date now = new Date(System.currentTimeMillis());
        
        return token == null ? false : now.before(token.getExpires());
    }

    public String getRoleFromToken(String token) {
        Token tok = em.createNamedQuery("Token.findByToken", Token.class)
                .setParameter("token", token).getSingleResult();
        
        return tok == null ? null : tok.getRole();
    }

    public void logout(String username) {
        Token token = em.find(Token.class, username);
        token.setExpires(new Date(0));
        em.merge(token);
    }
    
}
