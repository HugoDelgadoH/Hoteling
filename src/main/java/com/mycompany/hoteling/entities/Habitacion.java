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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "habitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Habitacion.findAll", query = "SELECT h FROM Habitacion h"),
    @NamedQuery(name = "Habitacion.findByPrecio", query = "SELECT h FROM Habitacion h WHERE h.precio = :precio"),
    @NamedQuery(name = "Habitacion.findByTipo", query = "SELECT h FROM Habitacion h WHERE h.tipo = :tipo"),
    @NamedQuery(name = "Habitacion.findByNumero", query = "SELECT h FROM Habitacion h WHERE h.habitacionPK.numero = :numero"),
    @NamedQuery(name = "Habitacion.findByHotel", query = "SELECT h FROM Habitacion h WHERE h.habitacionPK.hotel = :hotel")})
public class Habitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HabitacionPK habitacionPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio")
    private BigInteger precio;
    @Size(max = 30)
    @Column(name = "tipo")
    private String tipo;

    public Habitacion() {
    }

    public Habitacion(HabitacionPK habitacionPK) {
        this.habitacionPK = habitacionPK;
    }

    public Habitacion(HabitacionPK habitacionPK, BigInteger precio) {
        this.habitacionPK = habitacionPK;
        this.precio = precio;
    }

    public Habitacion(int numero, int hotel) {
        this.habitacionPK = new HabitacionPK(numero, hotel);
    }

    public HabitacionPK getHabitacionPK() {
        return habitacionPK;
    }

    public void setHabitacionPK(HabitacionPK habitacionPK) {
        this.habitacionPK = habitacionPK;
    }

    public BigInteger getPrecio() {
        return precio;
    }

    public void setPrecio(BigInteger precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (habitacionPK != null ? habitacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Habitacion)) {
            return false;
        }
        Habitacion other = (Habitacion) object;
        if ((this.habitacionPK == null && other.habitacionPK != null) || (this.habitacionPK != null && !this.habitacionPK.equals(other.habitacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.hoteling.entities.Habitacion[ habitacionPK=" + habitacionPK + " ]";
    }
    
}
