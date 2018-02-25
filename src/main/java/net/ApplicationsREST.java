/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import controller.Controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.ApplicationDetailsDTO;
import model.Applications;
import model.CompetenceDTO;
import model.RoleEnum;
import model.Secured;

/**
 *
 * @author Evan
 */
@Stateless
//@Secured({RoleEnum.Recruiter})
@Path("applications")
public class ApplicationsREST {

    @Inject
    private Controller contr;

    public ApplicationsREST() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("listApplications")
    public JsonArray listApplications() {
        List<Applications> applications = contr.listApplications();
        return appListToJsonArray(applications);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("competence")
    public Response listCompetences() {
        return Response.ok(new GenericEntity<List<CompetenceDTO>>(contr.listCompetence()) {
        }, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("searchApplication")
    public JsonArray searcApplications(JsonObject entity) {
        Date regDate, periodFrom, periodTo, dummyDate;
        SimpleDateFormat formatDate = new SimpleDateFormat("d-MM-yyyy");

        try {
            dummyDate = formatDate.parse("0-00-0000");
        } catch (ParseException e) {
            return null;
        }

        try {
            regDate = formatDate.parse(entity.getString("subDate"));
        } catch (ParseException e) {
            regDate = dummyDate;
        }
        try {
            periodFrom = formatDate.parse(entity.getString("periodFrom"));
        } catch (ParseException e) {
            periodFrom = dummyDate;
        }
        try {
            periodTo = formatDate.parse(entity.getString("periodTo"));
        } catch (ParseException e) {
            periodTo = dummyDate;
        }

        List<Applications> applications
                = contr.getApplications(
                        regDate,
                        periodFrom,
                        periodTo,
                        Long.parseLong(entity.getString("competence")),
                        entity.getString("name"),
                        dummyDate);
        return appListToJsonArray(applications);

    }

    public JsonArray appListToJsonArray(List<Applications> list) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Applications l : list) {
            builder.add(l.toJson());
        }
        return builder.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getApplicationDetails")
    public Response getApplicationDetails(@HeaderParam("applicationId") long applicationId) {
        ApplicationDetailsDTO appDetail = contr.getApplicationDetails(applicationId);
        if(appDetail == null){
           return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(new GenericEntity<ApplicationDetailsDTO>(appDetail) {
        }, MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("changeStatus")
    public Response changeAppStatus(@NotNull JsonObject obj){
        
        long applicationId = obj.getInt("applicationId");
        long appStatus = obj.getInt("appStatus");
        
        if(contr.changeAppStatus(applicationId, appStatus)){
            return Response.ok().build();
        }
        return Response.status(Response.Status.EXPECTATION_FAILED).build();
    }
}
