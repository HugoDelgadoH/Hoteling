/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.empresa;

import com.mycompany.hoteling.entities.Hotel;
import com.mycompany.hoteling.general.FotosHoteles;
import com.mycompany.hoteling.json.HotelReader;
import com.mycompany.hoteling.json.HotelWriter;
import com.mycompany.hoteling.json.HotelesReader;
import java.util.List;
import java.util.Random;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author gohug
 */
@Named
@RequestScoped
public class HotelClientBean {

    @Inject
    HotelBackingBean bean;
    Client client;
    WebTarget target;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/hoteling/webresources/com.mycompany.hoteling.entities.hotel");
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }

    public void addHotel(String email) {
        Hotel h = new Hotel();
        h.setEmpresa(email);
        h.setId(1);
        h.setNombre(bean.getNombre());
        h.setCiudad(bean.getCiudad());
        h.setNumeroHab(bean.getNumeroHab());
        h.setServicios(bean.getServicios());
        target.register(HotelWriter.class)
                .request()
                .post(Entity.entity(h, MediaType.APPLICATION_JSON));

        bean.setCiudad("");
        bean.setEmpresa("");
        bean.setId(-1);
        bean.setNombre("");
        bean.setNumeroHab(0);
        bean.setPrecio(0);
        bean.setServicios("");
    }

    public List<Hotel> getHotelesEmpresa(String email) {
        /*try {
            return em.createNamedQuery("Hotel.findByEmpresa",
                    Hotel.class)
                    .setParameter("empresa", email)
                    .getResultList();

        } catch (NoResultException e) {
            return null;
        }*/
        List<Hotel> h = target
                .register(HotelesReader.class)
                .path("byEmpresa/{email}")
                .resolveTemplate("email", email)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Hotel>>() {
                });

        return h;
    }

    public void deleteHotel() {
        target.path("{id}")
                .resolveTemplate("id", bean.getId())
                .request()
                .delete();
    }

    public Hotel getHotel() {
        Hotel h = target
                .register(HotelReader.class)
                .path("{hotelId}")
                .resolveTemplate("hotelId", bean.getId())
                .request(MediaType.APPLICATION_JSON)
                .get(Hotel.class);

        bean.setEmpresa(h.getEmpresa());
        bean.setCiudad(h.getCiudad());
        bean.setNombre(h.getNombre());
        bean.setNumeroHab(h.getNumeroHab());
        bean.setServicios(h.getServicios());
        bean.setId(h.getId());

        return h;
    }

    public String editHotel() {
        Hotel h = new Hotel(bean.getId(), bean.getEmpresa(), bean.getNombre(), bean.getCiudad(), bean.getNumeroHab(), bean.getServicios());
        target.path("{id}")
                .register(HotelWriter.class)
                .resolveTemplate("id", bean.getId())
                .request()
                .put(Entity.entity(h, MediaType.APPLICATION_JSON));

        return "verHoteles";
    }

    public int getRating() {
        Random rand = new Random();

        return rand.nextInt(5) + 1;//random entre 1 y  5 incluidos
    }

    public String getRandFoto() {
        FotosHoteles f = new FotosHoteles();

        return f.getRandomFoto();
    }
}
