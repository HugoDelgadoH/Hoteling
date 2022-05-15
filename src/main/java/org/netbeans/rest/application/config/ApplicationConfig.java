/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author gohug
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

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
        resources.add(com.mycompany.hoteling.json.HotelReader.class);
        resources.add(com.mycompany.hoteling.json.HotelWriter.class);
        resources.add(com.mycompany.hoteling.json.ReservaReader.class);
        resources.add(com.mycompany.hoteling.json.ReservaWriter.class);
        resources.add(com.mycompany.hoteling.rest.GrupoUsuarioFacadeREST.class);
        resources.add(com.mycompany.hoteling.rest.HabitacionFacadeREST.class);
        resources.add(com.mycompany.hoteling.rest.HotelFacadeREST.class);
        resources.add(com.mycompany.hoteling.rest.ReservaFacadeREST.class);
        resources.add(com.mycompany.hoteling.rest.UsuarioREST.class);
    }
    
}
