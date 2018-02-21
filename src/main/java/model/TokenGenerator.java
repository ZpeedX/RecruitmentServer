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
 *
 * @author Oscar
 */
@Stateless
public class TokenGenerator {
    private final long VALID_TOKEN_TIME = TimeUnit.HOURS.toMillis(2);
    
    private String getNewTokenString() {
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        return token;
    }
    
    public Token generateToken(String username, String role) {
        String token = getNewTokenString();
        long issuedMs = System.currentTimeMillis();
        Date issued = new Date(issuedMs);
        Date expires = new Date(issuedMs + VALID_TOKEN_TIME);
        return new Token(username, role, token, issued, expires);
    }
}
