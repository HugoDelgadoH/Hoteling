/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.admin;

import com.mycompany.hoteling.cliente.PerfilBackingBean;
import com.mycompany.hoteling.entities.Usuario;
import java.text.ParseException;
import java.util.List;
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

/**
 *
 * @author gohug
 */
@Named
@RequestScoped
public class ValidarClientBean {

    @Inject
    UserTransaction ut;

    @Inject
    PerfilBackingBean bean;

    @PersistenceContext
    EntityManager em;

    public List<Usuario> getEmpresasNoVerif() {
        return em.createNamedQuery("Usuario.findByVerificado", Usuario.class)
                .setParameter("verificado", Short.parseShort("0"))
                .getResultList();
    }

    public String getUsuarioForBack(String email) {
        try {
            Usuario u = em.createNamedQuery("Usuario.findByEmail",
                    Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();

            bean.setEmail(u.getEmail());
            bean.setDni(u.getDni());
            bean.setPassword(u.getPassword());
            bean.setNombre(u.getNombre());
            bean.setCif(u.getCif());
            bean.setCapitalSocial(u.getCapitalSocial());
            bean.setDomicilio(u.getDomicilio());
            bean.setOtros(u.getOtros());
            bean.setVerificado(u.getVerificado() == null ? 0 : u.getVerificado());

            return u.getNombre();

        } catch (NoResultException e) {
            return null;
        }
    }

    public String validar(String email) throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        ut.begin();
        em.joinTransaction();
        em.createNamedQuery("Usuario.validar", Usuario.class)
                .setParameter("email", email)
                .executeUpdate();
        ut.commit();
        return "validarEmpresa";
    }
}
