/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.cliente;

import com.mycompany.hoteling.entities.Hotel;
import com.mycompany.hoteling.entities.Reserva;
import com.mycompany.hoteling.json.HotelReader;
import com.mycompany.hoteling.json.ReservaReader;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author gohug
 */
@Named
@RequestScoped
public class ReservaClientBean {

    @Inject
    ReservaBackingBean bean;
    Client client;
    WebTarget target;
    //WebTarget targetHotel;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/hoteling/webresources/com.mycompany.hoteling.entities.reserva");

        //targetHotel = client.target("http://localhost:8080/hoteling/webresources/com.mycompany.hoteling.entities.hotel");
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }

    public Reserva[] getReservas() {//Buscar por cliente??
        return target
                .request()
                .get(Reserva[].class);
    }

    public Reserva getReserva() {
        return target
                .register(ReservaReader.class)
                .path("{reservaId}")
                .resolveTemplate("reservaId", bean.getReservaId())
                .request(MediaType.APPLICATION_JSON)
                .get(Reserva.class);
    }

    /*public Hotel getHotel() {
        return targetHotel
                .register(HotelReader.class)
                .path("{hotelId}")
                .resolveTemplate("hotelId", bean.getHotelId())
                .request(MediaType.APPLICATION_JSON)
                .get(Hotel.class);
    }*/

 /*  public String getHotelById(int id) {
        Integer i = id;
        return targetHotel
                .register(HotelReader.class)
                .path("{" + i.toString() + "}")
                .resolveTemplate("id", bean.getHotelId())
                .request(MediaType.APPLICATION_JSON)
                .get(Hotel.class).getNombre();

    }*/
    public String getHotetById(int id) {
        try {
            Hotel h = em.createNamedQuery("Hotel.findById",
                    Hotel.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return h.getNombre();

        } catch (NoResultException e) {
            return null;
        }
    }
    public void deleteReserva() {
        target.path("{reservaId}")
                .resolveTemplate("reservaId", bean.getReservaId())
                .request()
                .delete();
    }
    
    public List<Reserva> getReservasUsuario(String email){
        return em.createNamedQuery("Reserva.findByCliente",Reserva.class)
                .setParameter("cliente", email)
                .getResultList();
    }
}
