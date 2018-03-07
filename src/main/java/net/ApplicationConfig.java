/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * Sets the application variables and settings.
 *
 * @author Evan
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {
    /**
     * Auto generated
     * @return 
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(model.AppExceptionMapper.class);
        resources.add(model.AuthentitactionFilter.class);
        resources.add(model.AuthorizationFilter.class);
        resources.add(net.ApplicationsREST.class);
        resources.add(net.ApplyFacadeRest.class);
        resources.add(net.PersonFacadeREST.class);
    }
}
