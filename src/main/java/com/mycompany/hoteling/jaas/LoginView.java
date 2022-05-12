/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.jaas;

import com.mycompany.hoteling.entities.Usuario;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gohug
 */
@Named
@SessionScoped
public class LoginView implements Serializable {

    private String email;
    private String password;

    @Inject
    private UserEJB userEJB;

    private Usuario user;

    public Usuario getAuthenticatedUser() {
        return user;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(email, password);
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login incorrecto!", null));
            return "login";
        }
        this.user = userEJB.findByEmail(request.getUserPrincipal().getName());
        if (request.isUserInRole("cliente")) {
            return "/cliente/index?faces-redirect=true";
        } else if (request.isUserInRole("empresa")) {
            return "/empresa/index?faces-redirect=true";
        } else if (request.isUserInRole("admin")) {
            return "/admin/index?faces-redirect=true";
        } else {
            return "login";
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            this.user = null;
            request.logout();
            ((HttpSession) context.getExternalContext().getSession(false)).invalidate();
        } catch (ServletException e) {
            System.out.println("Fallo durante el proceso de logout!");
        }
        return "/index?faces-redirect=true";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
