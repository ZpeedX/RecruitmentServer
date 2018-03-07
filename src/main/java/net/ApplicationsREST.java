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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.ApplicationDetailsDTO;
import model.Applications;
import model.CompetenceDTO;
import model.RoleEnum;
import model.Secured;
import model.StatusNameDTO;

/**
 * Recievieves incoming external calls on specified paths and responds with the 
 * outcome.
 *
 * @author Evan
 */
@Stateless
@Secured({RoleEnum.Recruiter})
@Path("applications")
public class ApplicationsREST {

    @Inject
    private Controller contr;
    /**
     * Constructor
     */
    public ApplicationsREST() {
    }
    /**
     * Endpoint for application list requests
     * @return list of applications
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("listApplications")
    public JsonArray listApplications() {
        List<Applications> applications = contr.listApplications();
        return appListToJsonArray(applications);
    }
    /**
     * Endpoint for competence list requests
     * @return Respose containing list of competences
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("competence")
    public Response listCompetences() {
        return Response.ok(new GenericEntity<List<CompetenceDTO>>(contr.listCompetence()) {}).build();
    }
    /**
     * Endpoint for application search requests
     * @param entity application parameters
     * @return Reponse containing search results
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("searchApplication")
    public Response searcApplications(JsonObject entity) {
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
        
        return Response.ok(appListToJsonArray(applications)).build();
    }

    private JsonArray appListToJsonArray(List<Applications> list) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        
        list.forEach(application -> {
            builder.add(application.toJson());
        });
        
        return builder.build();
    }
    /**
     * Endpoint for application retrieval requests
     * @param applicationId id for application
     * @return application requested
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getApplicationDetails")
    public Response getApplicationDetails(@HeaderParam("applicationId") long applicationId) {
        ApplicationDetailsDTO appDetail = contr.getApplicationDetails(applicationId);

        if (appDetail == null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        
        return Response.ok(new GenericEntity<ApplicationDetailsDTO>(appDetail) {}).build();
    }
    /**
     * Endpoint for pdf requests
     * @param applicationId application to be printed as pdf
     * @param language language of pdf
     * @return pdf as object
     */
    @GET
    @Produces("application/pdf")
    @Path("getApplicationDetails/pdf/{id}")

    public Response getPdf(@PathParam("id") Long applicationId, @HeaderParam("locale") String language){
        return Response.ok((Object) contr.getPdf(applicationId, language)).build();
    }
    /**
     * Endpoint for application status changes
     * @param obj application information
     * @return Response with List of StatusNameDTO object if response is ok, else return Response.EXPECTATION_FAILED
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("changeStatus")
    public Response changeAppStatus(@NotNull JsonObject obj) {

        long applicationId = obj.getInt("applicationId");
        String appStatus = obj.getString("appStatus");

        List<StatusNameDTO> statusNames = contr.changeAppStatus(applicationId, appStatus);
        if (statusNames != null) {
            return Response.ok(new GenericEntity<List<StatusNameDTO>>(statusNames) {
            }, MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.EXPECTATION_FAILED).build();
    }

}
