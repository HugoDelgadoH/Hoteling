/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.json;

import com.mycompany.hoteling.entities.Usuario;
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
public class UsuarioWriter implements MessageBodyWriter<Usuario>{
            @Override
    public boolean isWriteable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return Usuario.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Usuario t, Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return -1;
    }

    @Override
    public void writeTo(Usuario t, Class<?> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, Object> mm, OutputStream out) throws IOException, WebApplicationException {
        JsonGenerator gen = Json.createGenerator(out);
        //System.out.println("---"+t.getFechaNacimiento()+"---");
        gen.writeStartObject()
                .write("email", t.getEmail())
                .write("nombre", t.getNombre())
                .write("password", t.getPassword())
                .write("dni", t.getDni())
                .write("telefono", t.getTelefono())
                .write("fecha_nacimiento", t.getFechaNacimiento()) //Null en la base de datos
                .write("cif",t.getCif())
                .write("domicilio",t.getDomicilio())
                .write("capital_social",t.getCapitalSocial())
                .write("otros",t.getOtros())
                .write("verificado", t.getVerificado())
                .writeEnd();
        gen.flush();    
    }
}
