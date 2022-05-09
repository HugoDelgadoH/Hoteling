/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.empresa;

import com.mycompany.hoteling.entities.Hotel;
import com.mycompany.hoteling.json.HotelReader;
import com.mycompany.hoteling.json.HotelWriter;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
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

    Hotel hotel;

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

    public void addHotel() {
        Hotel h = new Hotel();
        h.setEmpresa("Empresa logueada");//cambiar cuando sepamos el usuario
        h.setId(1);
        h.setNombre(bean.getNombre());
        h.setCiudad(bean.getCiudad());
        h.setNumeroHab(bean.getNumeroHab());
        h.setServicios(bean.getServicios());
        target.register(HotelWriter.class)
                .request()
                .post(Entity.entity(h, MediaType.APPLICATION_JSON));
    }

    public List<Hotel> getHotelesEmpresa() {
        try {
            return em.createNamedQuery("Hotel.findByEmpresa",
                    Hotel.class)
                    .setParameter("empresa", "Empresa logueada")//cambiar por empresa
                    .getResultList();

        } catch (NoResultException e) {
            return null;
        }
    }

    public void deleteHotel() {
        target.path("{id}")
                .resolveTemplate("id", bean.getId())
                .request()
                .delete();
    }

    public Hotel getHotel() {
        return target
                .register(HotelReader.class)
                .path("{hotelId}")
                .resolveTemplate("hotelId", bean.getId())
                .request(MediaType.APPLICATION_JSON)
                .get(Hotel.class);
    }
}
