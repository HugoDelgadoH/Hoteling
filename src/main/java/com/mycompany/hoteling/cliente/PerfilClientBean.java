/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.cliente;

import com.mycompany.hoteling.entities.Usuario;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author gohug
 */
@Named
@RequestScoped
public class PerfilClientBean {

    @Inject
    UserTransaction ut;

    @Inject
    PerfilBackingBean bean;
    Client client;
    WebTarget target;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/hoteling/webresources/com.mycompany.hoteling.entities.usuario");
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }

    public Usuario getUserByEmail(String email) {
        try {
            return em.createNamedQuery("Usuario.findByEmail",
                    Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    public void deleteUser(String email) {
        target.path("email")
                .resolveTemplate("email", email)
                .request()
                .delete();
    }

    public String getUsuarioForBack(String email) {
        try {
            Usuario u = em.createNamedQuery("Usuario.findByEmail",
                    Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();

            bean.setEmail(u.getEmail());
            bean.setDni(u.getDni());
            bean.setFechaNac(u.getFechaNacimiento());
            bean.setNombre(u.getNombre());
            bean.setTelefono(u.getTelefono());
            bean.setDni(u.getDni());
            bean.setCif(u.getCif());
            bean.setCapitalSocial(u.getCapitalSocial());
            bean.setDomicilio(u.getDomicilio());
            bean.setOtros(u.getOtros());
            //bean.setVerificado(u.getVerificado());

            return u.getNombre();

        } catch (NoResultException e) {
            return null;
        }
    }

    public void modificaUser() {

    }

    public void borrarUser(String email) throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        ut.begin();
        em.joinTransaction();
        em.createNamedQuery("Usuario.delete", Usuario.class)
                .setParameter("email", email)
                .executeUpdate();
        ut.commit();
    }

    public String getNombreByEmail(String email) {
        return em.createNamedQuery("Usuario.findByEmail",
                Usuario.class)
                .setParameter("email", email)
                .getSingleResult()
                .getNombre();
    }

}
