/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.general;

import com.mycompany.hoteling.entities.Hotel;
import com.mycompany.hoteling.rest.HotelFacadeREST;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author gohug
 */
@Named
@ViewScoped
public class DataGridView implements Serializable {

    private Hotel selectedHotel;
    private int rating;

    @PersistenceContext
    EntityManager em;

    public DataGridView() {
        this.rating = 4;
    }

    @PostConstruct
    public void init() {
    }

    public Hotel getSelectedHotel() {
        return selectedHotel;
    }

    public void setSelectedHotel(Hotel selectedHotel) {
        this.selectedHotel = selectedHotel;
    }

   /* public void clearMultiViewState() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        //PrimeFaces.current().multiViewState().clearAll(viewId, true, this::showMessage);
    }

    private void showMessage(String clientId) {
        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, clientId + " multiview state has been cleared out", null));
    }*/

    public String redirectReserva(String email) {
        if (email == null) {
            return "login";
        }
        return "reserva";
    }

    public int getRating() {
        return rating;
    }
    
    
}
