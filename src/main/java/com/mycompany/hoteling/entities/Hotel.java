/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.entities;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gohug
 */
@Entity
@Table(name = "hotel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hotel.findAll", query = "SELECT h FROM Hotel h"),
    @NamedQuery(name = "Hotel.findByEmpresa", query = "SELECT h FROM Hotel h WHERE h.empresa = :empresa"),
    @NamedQuery(name = "Hotel.findById", query = "SELECT h FROM Hotel h WHERE h.id = :id"),
    @NamedQuery(name = "Hotel.findByNombre", query = "SELECT h FROM Hotel h WHERE h.nombre = :nombre"),
    @NamedQuery(name = "Hotel.findByCiudad", query = "SELECT h FROM Hotel h WHERE h.ciudad = :ciudad"),
    @NamedQuery(name = "Hotel.findByNumeroHab", query = "SELECT h FROM Hotel h WHERE h.numeroHab = :numeroHab"),
    @NamedQuery(name = "Hotel.findByServicios", query = "SELECT h FROM Hotel h WHERE h.servicios = :servicios"),
    @NamedQuery(name = "Hotel.ciudadesDistinct", query = "SELECT DISTINCT h.ciudad FROM Hotel h")})
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "empresa")
    private String empresa;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ciudad")
    private String ciudad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_hab")
    private int numeroHab;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 510)
    @Column(name = "servicios")
    private String servicios;

    public Hotel() {
    }

    public Hotel(Integer id) {
        this.id = id;
    }

    public Hotel(Integer id, String empresa, String nombre, String ciudad, int numeroHab, String servicios) {
        this.id = id;
        this.empresa = empresa;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.numeroHab = numeroHab;
        this.servicios = servicios;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getNumeroHab() {
        return numeroHab;
    }

    public void setNumeroHab(int numeroHab) {
        this.numeroHab = numeroHab;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hotel)) {
            return false;
        }
        Hotel other = (Hotel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.hoteling.entities.Hotel[ id=" + id + " ]";
    }
    
}
