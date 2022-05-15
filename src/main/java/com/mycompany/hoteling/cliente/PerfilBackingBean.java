/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.cliente;

import java.io.Serializable;
import java.math.BigInteger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author gohug
 */
@Named
@SessionScoped
public class PerfilBackingBean implements Serializable {

    String nombre;
    String email;
    String password;
    String NEWpassword;
    String OLDpassword;
    String dni;
    String telefono;
    String fechaNac;
    String cif;
    String domicilio;
    BigInteger capitalSocial;
    String otros;
    int verificado;

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

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public int getVerificado() {
        return verificado;
    }

    public void setVerificado(int verificado) {
        this.verificado = verificado;
    }

    public String getNEWpassword() {
        return NEWpassword;
    }

    public void setNEWpassword(String NEWpassword) {
        this.NEWpassword = NEWpassword;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public BigInteger getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(BigInteger capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public String getOLDpassword() {
        return OLDpassword;
    }

    public void setOLDpassword(String OLDpassword) {
        this.OLDpassword = OLDpassword;
    }

}
