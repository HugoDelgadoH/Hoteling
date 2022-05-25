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
import java.util.List;
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
public class HotelesReader implements MessageBodyReader<List<Hotel>> {

    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return Hotel.class.isAssignableFrom(type);
    }

    @Override
    public List<Hotel> readFrom(Class<List<Hotel>> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
        List<Hotel> hoteles = null;
        Hotel h = new Hotel();
        JsonParser parser = Json.createParser(in);
        while (parser.hasNext()) {
            switch (parser.next()) {
                case START_ARRAY:
                    switch (parser.next()) {
                        case START_OBJECT:
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
                           hoteles.add(h);
                            break;
                        default:
                            break;   
                        
                    }
            }
        }
        return hoteles;
    }

}
