/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import controller.Controller;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import model.AvailabilityDTO;
import model.CompetenceDTO;
import model.CompetenceProfileDTO;
import model.RoleEnum;
import model.Secured;

/**
 *
 * @author Oscar
 */
@Stateless
@Secured({RoleEnum.Applicant})
@Path("apply")
public class ApplyFacadeRest {
    @Inject private Controller controller;
    
    @Context SecurityContext securityContext;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompetenceDTO> listCompetence() {
        return controller.listCompetence();
    }

    @POST
    @Path("/competence")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerCompetence(List<CompetenceProfileDTO> profiles) {
        System.out.println("path = /competence");
        if(profiles == null || profiles.isEmpty()) { return Response.notModified().build(); }
        
        profiles.forEach(p -> {
            System.out.println("id: " + p.getCompetenceId() + ", name: " /*+ p.getName() */+ ", yoe: " + p.getYearsOfExperience());
        });
        
        controller.addCompetenceProfiles(getUserFromPrincipal(), profiles);        
        return Response.ok().build();
    }
    
    @POST
    @Path("/availability")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerAvailability(List<AvailabilityDTO> availabilities) {
        System.out.println("path = /availability");
        if (availabilities == null || availabilities.isEmpty()) {return Response.notModified().build(); }
        
        availabilities.forEach(av -> {
            System.out.println("from: " + av.getFromDate().toString() + ", to: " + av.getToDate().toString());
        });
        
        controller.addAvailabilities(getUserFromPrincipal(), availabilities);
        return Response.ok().build();
    }
    
    private String getUserFromPrincipal() {
        String user = securityContext.getUserPrincipal().getName();
        System.out.println("Logged in user is: " + user);
        return user;
    }
}
