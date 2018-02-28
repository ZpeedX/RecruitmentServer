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
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import model.Person;
import model.Secured;

/**
 *
 * @author Emil
 * @author Oscar
 */
@Stateless
@Path("auth")
public class PersonFacadeREST {

    @Inject
    private Controller cont;
    
    @Context SecurityContext securityContext;

    public PersonFacadeREST() {
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@NotNull JsonObject user) {
        try {
            return Response.ok(loginUser(user)).build();
        } catch(Exception ex) { // MAYBE LOG HERE
            return Response.status(422).entity(Entity.json("{\"error\":\"invalid input\"}")).build();
        }
    }
    
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Person person) {
        try {
            return Response.ok(registerUser(person)).build();
        } catch(Exception ex) { // MAYBE LOG HERE
            return Response.status(422).entity(Entity.json("{\"error\":\"invalid input\"}")).build();
        }
    }
    
    @GET
    @Secured
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout() {
        String username = securityContext.getUserPrincipal().getName();
        cont.logout(username);
        return Response.ok().build();
    }

    private JsonObject loginUser(JsonObject newUser) {
        try {
            Person per = cont.authenticate(newUser.getString("username", ""));

            if(per != null && !per.getUsername().isEmpty() && per.authenticate(newUser.getString("password", ""))) {
                String token = cont.login(per.getUsername(), per.getRoleId().getName());
                String role = cont.getRoleFromToken(token);
                return successJson(token, role, per.getUsername());
            } else {
                return errorJson("invalid");
            }
        } catch(Exception ex) {
            return errorJson("invalid");
        }
        
    }

    private JsonObject registerUser(Person newPerson) {
        Person person = cont.register(newPerson);
        
        if(person != null) {
            String token = cont.login(person.getUsername(), person.getRoleId().getName());
            String role = cont.getRoleFromToken(token);
            return successJson(token, role, person.getUsername());
        } else {
            return errorJson("invalid");
        }
    }

    private JsonObject errorJson(String msg) {
        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("error", msg).build();
        
        return json;
    }
    
    private JsonObject successJson(String token, String role, String username) {
        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("token", token).add("role", role).add("username", username).build();
        
        return json;
    }
}
