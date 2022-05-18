/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.cliente;

import com.mycompany.hoteling.entities.Hotel;
import com.mycompany.hoteling.entities.Reserva;
import com.mycompany.hoteling.entities.Tarjeta;
import com.mycompany.hoteling.json.ReservaReader;
import com.mycompany.hoteling.json.ReservaWriter;
import com.mycompany.hoteling.json.TarjetaReader;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
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
@FlowScoped("reserva")
public class Reservas implements Serializable {

    private int id;
    private String ciudad;
    private int hotelId;
    private Date fechaInicio = new Date();
    private Date fechaFin = new Date();
    private Date fechaTarjeta = new Date();
    private String tarjeta = "";
    private String hotel = "";
    private String fechaInicioString = "";
    private String fechaFinString = "";
    private String fechaTarjetaString = "";
    private SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    Client client;
    WebTarget target;
    WebTarget targetValdavia;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/hoteling/webresources/com.mycompany.hoteling.entities.reserva");
        targetValdavia = client.target("http://valdavia.infor.uva.es:8080/hoteling/webresources/tarjetas");
    }

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

    public Date getFechaInicio() throws ParseException {
        return fechaInicio;
    }

    public String getFechaInicioString() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String d = formatter.format(fechaInicio);
        return d;
    }

    public String getFechaFinString() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String d = formatter.format(fechaFin);
        return d;
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
    
    public String getTarjetaValdavia(){
        return this.tarjeta.replace("-", "");
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Date getFechaTarjeta() {
        return fechaTarjeta;
    }

    public String getFechaTarjetaString() {
        DateFormat formatter = new SimpleDateFormat("MM/yyyy");
        String d = formatter.format(fechaTarjeta);
        return d;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addReserva(String email) {
        Reserva h = new Reserva();
        h.setCliente(email);
        h.setHotel(this.hotelId);
        h.setFechaIni(getFechaInicioString());
        h.setFechaFin(getFechaFinString());
        h.setTarjetaCredito(this.tarjeta);
        h.setFechaTarjeta(getFechaTarjetaString());
        target.register(ReservaWriter.class)
                .request()
                .post(Entity.entity(h, MediaType.APPLICATION_JSON));
    }

    public Reserva getReserva() {
        return target
                .register(ReservaReader.class)
                .path("{id}")
                .resolveTemplate("id", this.id) //De donde sale el nombre de este id
                .request(MediaType.APPLICATION_JSON)
                .get(Reserva.class);
    }

    public boolean checkTarjeta() {
        Tarjeta t;
        t = targetValdavia.register(TarjetaReader.class)
                .path("{id}")
                .resolveTemplate("id", getTarjetaValdavia())
                .request(MediaType.APPLICATION_JSON)
                .get(Tarjeta.class);

        return !(t==null || t.getAutorizada().equals("no"));
    }

}
