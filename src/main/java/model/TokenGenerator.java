/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.ejb.Stateless;

/**
 * Generator for the random tokens given to users when they log in.
 *
 * @author Oscar
 */
@Stateless
public class TokenGenerator {
    private final long VALID_TOKEN_TIME = TimeUnit.HOURS.toMillis(2);
    
    /**
     * Creates a random 32 character long string
     * @return rndom 32 character long string
     */
    private String getNewTokenString() {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        return token;
    }
    
    /**
     * This method generates a new Token object for a specified user belonging 
     * to a specific role. The token belongs to the {@code Token} class.
     * 
     * @param username user to issue token to.
     * @param role the role the user belongs to.
     * @return Token for the user.
     */
    public Token generateToken(String username, String role) {
        String token = getNewTokenString();
        long issuedMs = System.currentTimeMillis();
        Date issued = new Date(issuedMs);
        Date expires = new Date(issuedMs + VALID_TOKEN_TIME);
        return new Token(username, role, token, issued, expires);
    }
}
