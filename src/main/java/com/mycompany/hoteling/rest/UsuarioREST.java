/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.rest;

import com.mycompany.hoteling.entities.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author gohug
 */
@Named
@Stateless
@Path("com.mycompany.hoteling.entities.usuario")
public class UsuarioREST {

    @PersistenceContext(unitName = "com.mycompany_hoteling_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public UsuarioREST() {
    }

    @DELETE
    @Path("{email}")
    public void remove(@PathParam("email") String email) {
        
        try {
            em.remove(em.merge(find(email)));
        } catch(IllegalArgumentException m){
            //System.out.println(em.contains(Usuario.class));
            System.out.println("Error2");
        }

    }

    @GET
    @Path("{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Usuario find(@PathParam("email") String email) {
        return em.find(Usuario.class, email);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Usuario> findAll() {
        return em.createNamedQuery("Usuario.findAll", Usuario.class)
                .getResultList();
    }
}
