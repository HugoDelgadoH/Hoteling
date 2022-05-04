/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.modelo;

import com.mycompany.hoteling.entities.Usuario;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gohug
 */
@Named(value = "user")
@ApplicationScoped
public class User {

    private String user;
    private String password;
    private String tipo;
    private boolean valid;

    @PersistenceContext
    EntityManager em;

    public User() {
        this.valid=false;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String User) {
        this.user = User;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String Password) {
        this.password = Password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }   

    //retorna true si la contraseña y el email coinciden y false si no
    public boolean validUser() {
            System.out.println("----------------");
        if (user.equals(em.createNamedQuery("Usuario.pwByUser", Usuario.class)
                .setParameter("email", user)
                .setParameter("password", password)
                .getSingleResult()
                .getEmail())) {
            
            valid=true;
           // tipo=em.createNamedQuery(user)
            return true;
        } else {
            valid=false;
            return false;
        }
    }

}
