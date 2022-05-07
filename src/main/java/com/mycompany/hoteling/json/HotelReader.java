/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.json;

import com.mycompany.hoteling.entities.Hotel;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author gohug
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class HotelReader implements MessageBodyReader<Hotel> {

    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return Hotel.class.isAssignableFrom(type);
    }

    @Override
    public Hotel readFrom(Class<Hotel> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
        Hotel h = new Hotel();
        JsonParser parser = Json.createParser(in);
        while (parser.hasNext()) {
            switch (parser.next()) {
                case KEY_NAME:
                    String key = parser.getString();
                    parser.next();
                    switch (key) {
                        case "empresa":
                            h.setEmpresa(parser.getString());
                            break;
                        case "id":
                            h.setId(parser.getInt());
                            break;
                        case "nombre":
                            h.setNombre(parser.getString());
                            break;
                        case "ciudad":
                            h.setCiudad(parser.getString());
                            break;
                        case "numero_hab":
                            h.setNumeroHab(parser.getInt());
                            break;
                        case "servicios":
                            h.setServicios(parser.getString());
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        return h;
    }

}
