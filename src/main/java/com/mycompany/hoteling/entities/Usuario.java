/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gohug
 */
@Entity(name = "Usuario")
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByDni", query = "SELECT u FROM Usuario u WHERE u.dni = :dni"),
    @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono"),
    @NamedQuery(name = "Usuario.findByFechaNacimiento", query = "SELECT u FROM Usuario u WHERE u.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Usuario.findByCif", query = "SELECT u FROM Usuario u WHERE u.cif = :cif"),
    @NamedQuery(name = "Usuario.findByDomicilio", query = "SELECT u FROM Usuario u WHERE u.domicilio = :domicilio"),
    @NamedQuery(name = "Usuario.findByCapitalSocial", query = "SELECT u FROM Usuario u WHERE u.capitalSocial = :capitalSocial"),
    @NamedQuery(name = "Usuario.findByOtros", query = "SELECT u FROM Usuario u WHERE u.otros = :otros"),
    @NamedQuery(name = "Usuario.findByVerificado", query = "SELECT u FROM Usuario u WHERE u.verificado = :verificado"),
    @NamedQuery(name = "Usuario.delete", query = "DELETE FROM Usuario u WHERE u.email = :email")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 65)
    @Column(name = "password")
    private String password;
    @Size(max = 9)
    @Column(name = "dni")
    private String dni;
    @Size(max = 9)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 10)
    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;
    @Size(max = 9)
    @Column(name = "cif")
    private String cif;
    @Size(max = 200)
    @Column(name = "domicilio")
    private String domicilio;
    @Column(name = "capital_social")
    private BigInteger capitalSocial;
    @Size(max = 255)
    @Column(name = "otros")
    private String otros;
    @Column(name = "verificado")
    private Short verificado;

    public Usuario() {
    }

    public Usuario(String email) {
        this.email = email;
    }

    public Usuario(String email, String nombre, String password) {
        this.email = email;
        this.nombre = nombre;
        this.password = password;
    }

    public Usuario(String email, String nombre, String password, String dni, String telefono, String fecha_nac) {
        this.email = email;
        this.nombre = nombre;
        this.password = password;
        this.dni = dni;
        this.telefono = telefono;
        this.fechaNacimiento = fecha_nac;
    }

    public Usuario(String email, String nombre, String password, String cif, String domicilio, BigInteger capitalSocial, String otros) {
        this.email = email;
        this.nombre = nombre;
        this.password = password;
        this.cif = cif;
        this.domicilio = domicilio;
        this.capitalSocial = capitalSocial;
        this.otros = otros;
    }

    public Usuario(String email, String nombre, String password, String dni, String telefono, String fecha_nac, String cif, String domicilio, BigInteger capitalSocial, String otros, Short verificado) {
        this.email = email;
        this.nombre = nombre;
        this.password = password;
        this.dni = dni;
        this.telefono = telefono;
        this.fechaNacimiento = fecha_nac;
        this.cif = cif;
        this.domicilio = domicilio;
        this.capitalSocial = capitalSocial;
        this.otros = otros;
        this.verificado=verificado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public BigInteger getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(BigInteger capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public Short getVerificado() {
        return verificado;
    }

    public void setVerificado(Short verificado) {
        this.verificado = verificado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.hoteling.entities.Usuario[ email=" + email + " ]";
    }

}
