/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import controller.Controller;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Person;

/**
 *
 * @author Emil
 */
@Stateless
@Path("kth.iv1201.recruitmentserv.person")
public class PersonFacadeREST {

    private Person p;
    @Inject
    private Controller cont;

    @Inject
    private SessionHandler sessionhandler;

    public PersonFacadeREST() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String fromServ(JsonObject user) {
        switch (user.getString("type")) {
            case "login":
                User newUser = new User(user.getString("username"), user.getString("password"));
                return login(newUser);

            case "register":
                Person person = new Person(user.getString("name"), user.getString("surname"), user.getString("ssn"),
                        user.getString("email"), user.getString("password"), user.getString("username"));
                return register(person);
                
            case "logout":
                logout(user.getString("uid"));
                return "";
                
            default:
                return "";
        }
    }

    private String login(User newUser) {
        Person per = cont.authenticate(newUser.getUsername());
        if (per != null && per.authenticate(newUser.getPassword())) {
            return sessionhandler.logon(per).toString();
        } else {
            return "invalid";
        }
    }

    private void logout(String id) {
        sessionhandler.logout(id);
    }

    private String register(Person person) {
        p = cont.register(person);
        if (p != null) {
            return sessionhandler.logon(p).toString();
        } else {
            return "invalid";
        }
    }

}
