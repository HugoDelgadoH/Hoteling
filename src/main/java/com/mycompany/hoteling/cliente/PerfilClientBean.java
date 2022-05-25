/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.cliente;

import com.mycompany.hoteling.entities.Usuario;
import com.mycompany.hoteling.jaas.EmailValidator;
import com.mycompany.hoteling.json.UsuarioReader;
import com.mycompany.hoteling.json.UsuarioWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

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

    public Usuario getUserByEmail(String email) {//rest, falta fecha nac
        /*try {
            return em.createNamedQuery("Usuario.findByEmail",
                    Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();

        } catch (NoResultException | NullPointerException e) {
            return null;
        }*/
        return target.path("email")
                .register(UsuarioReader.class)
                .path("{email}")
                .resolveTemplate("email", email)
                .request(MediaType.APPLICATION_JSON)
                .get(Usuario.class);

    }

    public void deleteUser(String email) {
        target.path("email")
                .resolveTemplate("email", email)
                .request()
                .delete();
    }

    public String getUsuarioForBack(String email) throws ParseException {
        try {
            Usuario u = em.createNamedQuery("Usuario.findByEmail",
                    Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();

            bean.setEmail(u.getEmail());
            bean.setDni(u.getDni());
            bean.setPassword(u.getPassword());
            bean.setFechaNacString(u.getFechaNacimiento());
            bean.setNombre(u.getNombre());
            bean.setTelefono(u.getTelefono());
            bean.setDni(u.getDni());
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

    public String modificaUser(String email) {
        Usuario h = new Usuario(bean.getEmail(), bean.getNombre(), bean.getPassword(), bean.getDni(), bean.getTelefono(), bean.getFechaNacString(), bean.getCif() == null ? "" : bean.getCif(), bean.getDomicilio() == null ? "" : bean.getDomicilio(), bean.getCapitalSocial() == null ? BigInteger.ZERO : bean.getCapitalSocial(), bean.getOtros() == null ? "" : bean.getOtros(), bean.getVerificado() == 0 ? Short.parseShort("0") : Short.parseShort("1"));
        target.path("{email}")
                .register(UsuarioWriter.class)
                .resolveTemplate("email", email)
                .request()
                .put(Entity.entity(h, MediaType.APPLICATION_JSON));

        return "index";
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
        try {
            return em.createNamedQuery("Usuario.findByEmail",
                    Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult()
                    .getNombre();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public void validaPerfilCliente(ComponentSystemEvent event) {
        EmailValidator e = new EmailValidator();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        UIInput uiInputNombre = (UIInput) components.findComponent("nombre");
        String nombre = uiInputNombre.getLocalValue() == null ? "" : uiInputNombre.getLocalValue().toString();
        UIInput uiInputEmail = (UIInput) components.findComponent("email");
        String email = uiInputEmail.getLocalValue() == null ? "" : uiInputEmail.getLocalValue().toString();
        UIInput uiInputFecha = (UIInput) components.findComponent("fecha");
        Date fecha = null;
        try {
            fecha = (Date) (uiInputFecha.getLocalValue() == null ? "" : uiInputFecha.getLocalValue());
        } catch (ClassCastException ex) {
        }
        UIInput uiInputTelefono = (UIInput) components.findComponent("tlf");
        String telefono = uiInputTelefono.getLocalValue() == null ? "" : uiInputTelefono.getLocalValue().toString();
        UIInput uiInputDni = (UIInput) components.findComponent("dni");
        String dni = uiInputDni.getLocalValue() == null ? "" : uiInputDni.getLocalValue().toString();

        if (nombre.length() <= 4) {//Nombre
            FacesMessage msg = new FacesMessage("El nombre debe contener más de cuatro caracteres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputNombre.getClientId(), msg);
            facesContext.renderResponse();
        }

        if (getNombreByEmail(email) != null && getNombreByEmail(email).equals(bean.getEmail())) {//email repetido y distinto al del propio usuario
            FacesMessage msg = new FacesMessage("El email introducido ya existe");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputEmail.getClientId(), msg);
            facesContext.renderResponse();
        }
        try {//email valido
            e.validate(facesContext, uiInputEmail, email);
        } catch (ValidatorException ex) {
            FacesMessage msg = new FacesMessage("El email introducido no es válido");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputEmail.getClientId(), msg);
            facesContext.renderResponse();
        }

        if (telefono.length() < 9 || telefono.length() > 9 || !isNumeric(telefono)) { //tlf tiene que tener 9 digitos
            FacesMessage msg = new FacesMessage("El teléfono introducido no es válido");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputTelefono.getClientId(), msg);
            facesContext.renderResponse();
        }

        if (!dniCorrecto(dni)) { //dni valido
            FacesMessage msg = new FacesMessage("El DNI introducido no es válido");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputDni.getClientId(), msg);
            facesContext.renderResponse();
        }

        if (edad(fecha) < 18) {
            FacesMessage msg = new FacesMessage("Debe ser mayor de edad");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputFecha.getClientId(), msg);
            facesContext.renderResponse();
        }

    }

    public boolean isNumeric(String s) {
        if (s == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public boolean dniCorrecto(String dni) {
        ValidadorDNI v = new ValidadorDNI(dni);

        return v.validar();
    }

    public int edad(Date fechaNac) {
        return CalculadoraEdad.edad(fechaNac, LocalDate.now());
    }

}
