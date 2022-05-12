/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.cliente;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author gohug
 */
@Named
@RequestScoped
public class Utiles {

    private boolean disabled = true;
    

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean _bol) {
        this.disabled = _bol;
    }

    public void action() {
        disabled = false;
    }
}
