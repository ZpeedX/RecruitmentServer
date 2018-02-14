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
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Competence;

/**
 *
 * @author Evan
 */
@Stateless
@Path("applications")
public class ApplicationsREST  {
    
    @Inject
    private Controller contr;

    public ApplicationsREST() {    
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray fromClient(String entity) {
        System.out.println("hello");
        List<Competence> comps = contr.getAllCompetences();    
        return listToJsonArray(comps);
    }
    
    public JsonArray listToJsonArray(List<Competence> list) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Competence l : list) {
            builder.add(l.toJson().build());
        }
        return builder.build();
    }
}
