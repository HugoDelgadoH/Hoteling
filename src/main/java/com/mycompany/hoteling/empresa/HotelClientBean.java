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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
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

    public String limpiaValores() {
        bean.setNombre("");
        bean.setCiudad("");
        bean.setNumeroHab(0);
        bean.setServicios("");

        return "/empresa/addHotel.xhtml";
    }

    public void validaHotel(ComponentSystemEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        UIInput uiInputNombre = (UIInput) components.findComponent("nombre");
        String nombre = uiInputNombre.getLocalValue() == null ? "" : uiInputNombre.getLocalValue().toString();
        UIInput uiInputCiudad = (UIInput) components.findComponent("ciudad");
        String ciudad = uiInputCiudad.getLocalValue() == null ? "" : uiInputCiudad.getLocalValue().toString();
        UIInput uiInputNumHab = (UIInput) components.findComponent("hab");
        String numHab = uiInputNumHab.getLocalValue() == null ? "" : uiInputNumHab.getLocalValue().toString();
        int numeroHab = Integer.parseInt(numHab);
        UIInput uiInputPrecio = (UIInput) components.findComponent("precio");
        String prec = uiInputPrecio.getLocalValue() == null ? "" : uiInputPrecio.getLocalValue().toString();
        double precio = Double.parseDouble(prec);

        if (nombre.length() < 4) {
            FacesMessage msg = new FacesMessage("El nombre debe contener más de cuatro caracteres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputNombre.getClientId(), msg);
            facesContext.renderResponse();
        }

        if (ciudad.length() < 4) {
            FacesMessage msg = new FacesMessage("Introduce una ciudad válida");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputCiudad.getClientId(), msg);
            facesContext.renderResponse();
        }

        if (numeroHab < 5) {
            FacesMessage msg = new FacesMessage("El número de haitaciones debe ser al menos 5");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputNumHab.getClientId(), msg);
            facesContext.renderResponse();
        }

        if (precio <= 0) {
            FacesMessage msg = new FacesMessage("El precio debe ser mayor que 0");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputPrecio.getClientId(), msg);
            facesContext.renderResponse();
        }
    }          

    public void validaHotelEdit(ComponentSystemEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        UIInput uiInputNombre = (UIInput) components.findComponent("nom");
        String nombre = uiInputNombre.getLocalValue() == null ? "" : uiInputNombre.getLocalValue().toString();
        UIInput uiInputCiudad = (UIInput) components.findComponent("ciu");
        String ciudad = uiInputCiudad.getLocalValue() == null ? "" : uiInputCiudad.getLocalValue().toString();
        UIInput uiInputNumHab = (UIInput) components.findComponent("num");
        String numHab = uiInputNumHab.getLocalValue() == null ? "" : uiInputNumHab.getLocalValue().toString();
        int numeroHab = Integer.parseInt(numHab);

        if (nombre.length() < 4) {
            FacesMessage msg = new FacesMessage("El nombre debe contener más de cuatro caracteres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputNombre.getClientId(), msg);
            facesContext.renderResponse();
        }

        if (ciudad.length() < 4) {
            FacesMessage msg = new FacesMessage("Introduce una ciudad válida");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputCiudad.getClientId(), msg);
            facesContext.renderResponse();
        }

        if (numeroHab < 5) {
            FacesMessage msg = new FacesMessage("El número de haitaciones debe ser al menos 5");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(uiInputNumHab.getClientId(), msg);
            facesContext.renderResponse();
        }
    }
}
