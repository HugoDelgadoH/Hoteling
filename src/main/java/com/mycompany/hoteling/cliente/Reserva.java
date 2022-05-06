/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.cliente;

import com.mycompany.hoteling.entities.Hotel;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gohug
 */
@Named
@FlowScoped("reserva")
public class Reserva implements Serializable {

    private String ciudad;
    private int hotelId;
    private Date fechaInicio = new Date();
    private Date fechaFin = new Date();
    private Date fechaTarjeta = new Date();
    private String tarjeta = "";
    private String hotel = "";

    @PersistenceContext
    EntityManager em;

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<Hotel> getHotelesByCiudad() {
        try {
            List<Hotel> list = em.createNamedQuery("Hotel.findByCiudad",
                    Hotel.class)
                    .setParameter("ciudad", ciudad)
                    .getResultList();
            if (list.isEmpty()) {
                return em.createNamedQuery("Hotel.findAll",
                        Hotel.class)
                        .getResultList();
            }
            return list;
        } catch (NoResultException e) {
            return em.createNamedQuery("Hotel.findAll",
                    Hotel.class)
                    .getResultList();
        }
    }

    public List<Hotel> getCiudadesDistinct() {
        try {
            List<Hotel> list = em.createNamedQuery("Hotel.ciudadesDistinct",
                    Hotel.class)
                    .getResultList();
            if (list.isEmpty()) {
                return null;
            }
            return list;
        } catch (NoResultException e) {
            return null;
        }
    }

    public String getHotetById() {
        try {
            Hotel h = em.createNamedQuery("Hotel.findById",
                    Hotel.class)
                    .setParameter("id", hotelId)
                    .getSingleResult();

            this.ciudad = h.getCiudad();
            if (this.hotel.equals("")) {
                this.hotel = h.getNombre();
            }

            return h.getNombre();

        } catch (NoResultException e) {
            return null;
        }
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public Date getFechaInicio() {
        //SimpleDateFormat f=new SimpleDateFormat("dd-MM-yyyy");
          
       // return f.format(fechaInicio);
       return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Date getFechaTarjeta() {
        return fechaTarjeta;
    }

    public void setFechaTarjeta(Date fechaTarjeta) {
        this.fechaTarjeta = fechaTarjeta;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

}
