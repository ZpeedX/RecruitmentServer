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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import model.Token;

/**
 * Handles the communication with the recruitment_tokens database.
 *
 * @author Oscar
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class TokenDAO {
    
    @PersistenceContext(unitName = "tokenPU")
    private EntityManager em;
    
    /**
     * This method tries to store the token issued to a user to the database.
     * 
     * @param token the token to be stored
     * @return boolean true if successfully added to the database, else false
     */
    public boolean addToken(Token token) {
        boolean success = false;
        
        try{
            em.merge(token);
            success = true;
        } catch(Exception ex) {
            System.out.println("ERROR ADDING TOKEN: " + ex.getMessage());
            

        }
        
        return success;
    }
    
    /**
     * This method retrieves the token issued a specific user from the database.
     * 
     * @param username the username of the user
     * @return Token the token the user has been issued or null if none
     */
    public Token getTokenFromUsername(String username) {
        return em.find(Token.class, username);
    }
    
    /**
     * This method retrieves the username from the database for whom 
     * the token has been issued.
     * 
     * @param token the string value the token has
     * @return String the username of the token holder
     */
    public String getUsernameFromToken(String token) {
        try {
            return em.createNamedQuery("Token.findByToken", Token.class)
                .setParameter("token", token).getSingleResult().getUsername();
        } catch(NullPointerException | NoResultException nex) {
            System.out.println("ERROR GETTING USER: " + nex.getMessage());
            return null;
        }
    }
    
    /**
     * This method checks in the database if a user belongs to the role entered.
     * 
     * @param username the username of signed in user
     * @param role the role the user has been assigned
     * @return boolean true is the user belongs to the role, else false
     */
    public boolean isUserInRole(String username, String role) {
        Token token = em.find(Token.class, username);
        
        return token == null ? false : role.equals(token.getRole());
    }
    
    /**
     * This method checks if a user has a valid token in the database.
     * 
     * @param issuedToken the token the user has been issued or uses
     * @return boolean true if the token is valid, else false
     */
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

    /**
     * This method retrives the name of the role associated with the token of the 
     * signed in user from the database.
     * 
     * @param token token genereted for signed in users
     * @return String the name of the role the user belongs to
     */
    public String getRoleFromToken(String token) {
        Token tok = em.createNamedQuery("Token.findByToken", Token.class)
                .setParameter("token", token).getSingleResult();
        
        return tok == null ? null : tok.getRole();
    }

    /**
     * This method logs out a signed in user by using the entered username and
     * invalidating the token in the database.
     * 
     * @param username logged in user
     */
    public void logout(String username) {
        Token token = em.find(Token.class, username);
        token.setExpires(new Date(0));
        em.merge(token);
    }
    
}
