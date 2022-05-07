/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.json;

import com.mycompany.hoteling.entities.Hotel;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author gohug
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class HotelWriter implements MessageBodyWriter<Hotel>{
        @Override
    public boolean isWriteable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return Hotel.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Hotel t, Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return -1;
    }

    @Override
    public void writeTo(Hotel t, Class<?> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, Object> mm, OutputStream out) throws IOException, WebApplicationException {
        JsonGenerator gen = Json.createGenerator(out);
        gen.writeStartObject()
                .write("empresa", t.getEmpresa())
                .write("id", t.getId())
                .write("nombre", t.getNombre())
                .write("ciudad", t.getCiudad())
                .write("numero_hab", t.getNumeroHab())
                .write("servicios", t.getServicios())
                .writeEnd();
        gen.flush();    
    }    
}
