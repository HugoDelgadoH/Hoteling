/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.jaas;

import com.mycompany.hoteling.entities.GrupoUsuario;
import com.mycompany.hoteling.entities.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author gohug
 */
@Stateless
public class UserEJB {

    @PersistenceContext
    private EntityManager em;

    public Usuario createUser(Usuario user) {
        try {
            user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        GrupoUsuario group = new GrupoUsuario();
        group.setEmail(user.getEmail());
        group.setNombre("cliente");//cliente admin o empresa
        em.persist(user);
        em.persist(group);
        return user;
    }
        public Usuario createEmpresa(Usuario user) {
        try {
            user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        GrupoUsuario group = new GrupoUsuario();
        group.setEmail(user.getEmail());
        group.setNombre("empresa");//cliente admin o empresa
        em.persist(user);
        em.persist(group);
        return user;
    }

    public Usuario findByEmail(String email) {
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findByEmail",
                Usuario.class);
        query.setParameter("email", email);
        Usuario user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
        }
        return user;
    }
}
