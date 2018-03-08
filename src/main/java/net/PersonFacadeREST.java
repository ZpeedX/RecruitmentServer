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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import model.AppRuntimeException;
import model.ErrorMessageEnum;
import model.Person;
import model.Secured;

/**
 * Recievieves incoming external calls on specified paths and responds with the 
 * outcome.
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
    /**
     * Endpoint recieving user information from user to log in
     * @param user user credentials
     * @return response on success/fail
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@NotNull JsonObject user) {
        return Response.ok(loginUser(user)).build();
    }
    /**
     * Endpoint recieving registration information from user to register
     * @param person user credentials
     * @return response on success/fail
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Person person) {
        return Response.ok(registerUser(person)).build();
    }
    /**
     * Endpoint recieving registration information from user to register
     * @return empty response
     */
    @GET
    @Secured
    @Path("/logout")
    public Response logout() {
        String username = securityContext.getUserPrincipal().getName();
        cont.logout(username);
        return Response.noContent().build();
    }
    
    /**
     * Endpoint recieving validation information from user to register
     * 
     * @param json object with the password
     * @return response that everything checks out or throws an exception with an
     * apporpriate status.
     */
    @POST
    @Secured
    @Path("/validate")
    public Response validateChoice(@NotNull JsonObject json) {
        return validate(json);
    }
    
    private Response validate(JsonObject json) {
        String pass = json.getString("password", "");
        String username = securityContext.getUserPrincipal().getName();
        Person person = cont.authenticate(username);
        
        if(person == null || !person.authenticate(pass)) {
            throw new AppRuntimeException(ErrorMessageEnum.INVALID_CONTENT.toString());
        } else {
            return Response.noContent().build();
        }
    }

    private JsonObject loginUser(JsonObject newUser) {
        Person person = cont.authenticate(newUser.getString("username", ""));
        
        if(person == null || !person.authenticate(newUser.getString("password", ""))) {
            throw new AppRuntimeException(ErrorMessageEnum.INVALID_CONTENT.toString());
        }
        
        String token = cont.login(person.getUsername(), person.getRoleId().getName());
        String role = cont.getRoleFromToken(token);
        
        return successJson(token, role, person.getUsername());
        
    }

    private JsonObject registerUser(Person newPerson) {
        Person person = cont.register(newPerson);
        
        String token = cont.login(person.getUsername(), person.getRoleId().getName());
        String role = cont.getRoleFromToken(token);
        
        return successJson(token, role, person.getUsername());
    }
    
    private JsonObject successJson(String token, String role, String username) {
        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("token", token).add("role", role).add("username", username).build();
        
        return json;
    }
}
