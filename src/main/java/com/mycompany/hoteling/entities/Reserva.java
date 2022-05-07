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
@Table(name = "reserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r"),
    @NamedQuery(name = "Reserva.findById", query = "SELECT r FROM Reserva r WHERE r.id = :id"),
    @NamedQuery(name = "Reserva.findByHotel", query = "SELECT r FROM Reserva r WHERE r.hotel = :hotel"),
    @NamedQuery(name = "Reserva.findByHabitacion", query = "SELECT r FROM Reserva r WHERE r.habitacion = :habitacion"),
    @NamedQuery(name = "Reserva.findByCliente", query = "SELECT r FROM Reserva r WHERE r.cliente = :cliente"),
    @NamedQuery(name = "Reserva.findByFechaIni", query = "SELECT r FROM Reserva r WHERE r.fechaIni = :fechaIni"),
    @NamedQuery(name = "Reserva.findByFechaFin", query = "SELECT r FROM Reserva r WHERE r.fechaFin = :fechaFin"),
    @NamedQuery(name = "Reserva.findByTarjetaCredito", query = "SELECT r FROM Reserva r WHERE r.tarjetaCredito = :tarjetaCredito"),
    @NamedQuery(name = "Reserva.findByFechaTarjeta", query = "SELECT r FROM Reserva r WHERE r.fechaTarjeta = :fechaTarjeta")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hotel")
    private int hotel;
    @Column(name = "habitacion")
    private Integer habitacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "cliente")
    private String cliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "fecha_ini")
    private String fechaIni;
    @Size(max = 10)
    @Column(name = "fecha_fin")
    private String fechaFin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 19)
    @Column(name = "tarjeta_credito")
    private String tarjetaCredito;
    @Size(max = 7)
    @Column(name = "fecha_tarjeta")
    private String fechaTarjeta;

    public Reserva() {
    }

    public Reserva(Integer id) {
        this.id = id;
    }

    public Reserva(Integer id, int hotel, String cliente, String fechaIni, String tarjetaCredito) {
        this.id = id;
        this.hotel = hotel;
        this.cliente = cliente;
        this.fechaIni = fechaIni;
        this.tarjetaCredito = tarjetaCredito;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getHotel() {
        return hotel;
    }

    public void setHotel(int hotel) {
        this.hotel = hotel;
    }

    public Integer getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Integer habitacion) {
        this.habitacion = habitacion;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(String tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    public String getFechaTarjeta() {
        return fechaTarjeta;
    }

    public void setFechaTarjeta(String fechaTarjeta) {
        this.fechaTarjeta = fechaTarjeta;
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
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.hoteling.entities.Reserva[ id=" + id + " ]";
    }
    
}
