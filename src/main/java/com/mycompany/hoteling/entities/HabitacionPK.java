/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author gohug
 */
@Embeddable
public class HabitacionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "numero")
    private int numero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hotel")
    private int hotel;

    public HabitacionPK() {
    }

    public HabitacionPK(int numero, int hotel) {
        this.numero = numero;
        this.hotel = hotel;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getHotel() {
        return hotel;
    }

    public void setHotel(int hotel) {
        this.hotel = hotel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) numero;
        hash += (int) hotel;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HabitacionPK)) {
            return false;
        }
        HabitacionPK other = (HabitacionPK) object;
        if (this.numero != other.numero) {
            return false;
        }
        if (this.hotel != other.hotel) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.hoteling.entities.HabitacionPK[ numero=" + numero + ", hotel=" + hotel + " ]";
    }
    
}
