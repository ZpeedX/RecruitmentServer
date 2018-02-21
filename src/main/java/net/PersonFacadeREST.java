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
import javax.json.spi.JsonProvider;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    public PersonFacadeREST() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fromServ(JsonObject user) {
        if (user.getString("type").equals("login")) {
            return Response.ok(login(user)).build();
        } else {
            Person person = new Person(user.getString("name"), user.getString("surname"), user.getString("ssn"),
                    user.getString("email"), user.getString("password"), user.getString("username"));
            return Response.ok(register(person)).build();
        }
    }

    private JsonObject login(JsonObject newUser) {
        Person per = cont.authenticate(newUser.getString("username", ""));
        if (per != null && per.authenticate(newUser.getString("password", ""))) {
            String token = cont.login(per.getUsername(), per.getRoleId().getName());
            String role = cont.getRoleFromToken(token);
            return successJson(token, role);
        } else {
            return errorJson("invalid");
        }
    }

    private JsonObject register(Person person) {
        p = cont.register(person);
        if (p != null) {
            String token = cont.login(p.getUsername(), p.getRoleId().getName());
            String role = cont.getRoleFromToken(token);
            return successJson(token, role);
        } else {
            return errorJson("invalid");
        }
    }

    private JsonObject errorJson(String msg) {
        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("error", msg).build();
        
        return json;
    }
    
    private JsonObject successJson(String token, String role) {
        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("token", token).add("role", role).build();
        
        return json;
    }
}
