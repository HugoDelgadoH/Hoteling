/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.jaas;

import com.mycompany.hoteling.cliente.CalculadoraEdad;
import com.mycompany.hoteling.cliente.ValidadorDNI;
import com.mycompany.hoteling.entities.Usuario;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author gohug
 */
@Named
@SessionScoped
public class RegisterView implements Serializable {

    @Inject
    private UserEJB userEJB;

    private String nombre;
    private String email;
    private String password;
    private String confirmPassword;
    private Date fecha_nac;
    private String dni;
    private String telefono;
    private String cif;
    private String domicilio;
    private BigInteger capital_social;
    private String otros;

    /*public void validatePassword(ComponentSystemEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmpassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }
        if (!password.equals(confirmPassword)) {
            FacesMessage msg = new FacesMessage("Las contraseñas no coinciden");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputPassword.getClientId(), msg);
            facesContext.renderResponse();
        }
        UIInput uiInputEmail = (UIInput) components.findComponent("email");
        String email = uiInputEmail.getLocalValue() == null ? "" : uiInputEmail.getLocalValue().toString();
        if (userEJB.findByEmail(email) != null) {
            FacesMessage msg = new FacesMessage("Ya existe un usuario con ese email");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputPassword.getClientId(), msg);
            facesContext.renderResponse();
        }
    }*/
    public void validaRegistro(ComponentSystemEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        UIInput uiInputNombre = (UIInput) components.findComponent("name");
        String nombre = uiInputNombre.getLocalValue() == null ? "" : uiInputNombre.getLocalValue().toString();
        UIInput uiInputDNI = (UIInput) components.findComponent("dni");
        String dni = uiInputDNI.getLocalValue() == null ? "" : uiInputDNI.getLocalValue().toString();
        UIInput uiInputFecha = (UIInput) components.findComponent("fecha");
        Date fecha = null;
        try {
            fecha = (Date) (uiInputFecha.getLocalValue() == null ? "" : uiInputFecha.getLocalValue());
        } catch (ClassCastException e) {
        }
        UIInput uiInputTelefono = (UIInput) components.findComponent("tlf");
        String telefono = uiInputTelefono.getLocalValue() == null ? "" : uiInputTelefono.getLocalValue().toString();

        if (nombre.length() <= 4) {//Nombre
            FacesMessage msg = new FacesMessage("El nombre debe contener más de cuatro caracteres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputNombre.getClientId(), msg);
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
            facesContext.addMessage(uiInputDNI.getClientId(), msg);
            facesContext.renderResponse();
        }

        if (edad(fecha) < 18) {
            FacesMessage msg = new FacesMessage("Debe ser mayor de edad");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputFecha.getClientId(), msg);
            facesContext.renderResponse();
        }

        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmpassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }
        if (!password.equals(confirmPassword)) {
            FacesMessage msg = new FacesMessage("Las contraseñas no coinciden");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputPassword.getClientId(), msg);
            facesContext.renderResponse();
        }
        UIInput uiInputEmail = (UIInput) components.findComponent("email");
        String email = uiInputEmail.getLocalValue() == null ? "" : uiInputEmail.getLocalValue().toString();
        if (userEJB.findByEmail(email) != null) {
            FacesMessage msg = new FacesMessage("Ya existe un usuario con ese email");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputPassword.getClientId(), msg);
            facesContext.renderResponse();
        }
    }

    public void validaRegistroEmpresa(ComponentSystemEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        UIInput uiInputNombre = (UIInput) components.findComponent("name");
        String nombre = uiInputNombre.getLocalValue() == null ? "" : uiInputNombre.getLocalValue().toString();
        UIInput uiInputCif = (UIInput) components.findComponent("cif");
        String cif = uiInputCif.getLocalValue() == null ? "" : uiInputCif.getLocalValue().toString();
        /*UIInput uiInputDom = (UIInput) components.findComponent("domic");
        String domicilio = uiInputDom.getLocalValue() == null ? "" : uiInputDom.getLocalValue().toString();*/
        UIInput uiInputCap = (UIInput) components.findComponent("cap");
        String capital = uiInputCap.getLocalValue() == null ? "" : uiInputCap.getLocalValue().toString();

        if (nombre.length() <= 4) {//Nombre
            FacesMessage msg = new FacesMessage("El nombre debe contener más de cuatro caracteres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputNombre.getClientId(), msg);
            facesContext.renderResponse();
        }
        if (cif.length() != 9) {
            FacesMessage msg = new FacesMessage("Introduce un CIF correcto");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputCif.getClientId(), msg);
            facesContext.renderResponse();
        }
        if (!isNumeric(capital)) {
            FacesMessage msg = new FacesMessage("Introduce un capital correcto");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputCif.getClientId(), msg);
            facesContext.renderResponse();
        }

        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmpassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }
        if (!password.equals(confirmPassword)) {
            FacesMessage msg = new FacesMessage("Las contraseñas no coinciden");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputPassword.getClientId(), msg);
            facesContext.renderResponse();
        }
        UIInput uiInputEmail = (UIInput) components.findComponent("email");
        String email = uiInputEmail.getLocalValue() == null ? "" : uiInputEmail.getLocalValue().toString();
        if (userEJB.findByEmail(email) != null) {
            FacesMessage msg = new FacesMessage("Ya existe un usuario con ese email");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputPassword.getClientId(), msg);
            facesContext.renderResponse();
        }
    }

    public boolean dniCorrecto(String dni) {
        ValidadorDNI v = new ValidadorDNI(dni);

        return v.validar();
    }

    public int edad(Date fechaNac) {
        return CalculadoraEdad.edad(fechaNac, LocalDate.now());
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

    public String register() {
        Usuario user = new Usuario(email, nombre, password, dni, telefono, getFecha_nacString());
        userEJB.createUser(user);
        //System.out.println("Nuevo usuario creado con e-mail: " + email + " y nombre:" + nombre);
        return "registerOk";
    }

    public String registerEmpresa() {
        Usuario user = new Usuario(email, nombre, password, cif, domicilio, capital_social, otros);
        userEJB.createEmpresa(user);
        return "registerOk";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFecha_nacString() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String d = formatter.format(fecha_nac);
        return d;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public BigInteger getCapital_social() {
        return capital_social;
    }

    public void setCapital_social(BigInteger capital_social) {
        this.capital_social = capital_social;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

}
