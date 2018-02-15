/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import controller.Controller;
import java.util.HashMap;
import java.util.Random;
import javax.enterprise.context.ApplicationScoped;
import model.Person;

/**
 *
 * @author Emil
 */


@ApplicationScoped
public class SessionHandler {
    private HashMap<Long, Person> loggedOnUsers = new HashMap<>();
    private long generatedLong;
    
    public Long logon(Person p){
        do{
        generatedLong = new Random().nextLong();
        }while(loggedOnUsers.containsKey(generatedLong));
        loggedOnUsers.put(generatedLong, p);
        return generatedLong;
    }
    
    public void logout(String id){
        Long l = new Long(id);
        loggedOnUsers.remove(l);
    }
    
     
}
