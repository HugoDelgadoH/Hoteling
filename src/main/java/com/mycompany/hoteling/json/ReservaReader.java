/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.json;

import com.mycompany.hoteling.entities.Reserva;
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
public class ReservaReader implements MessageBodyReader<Reserva> {

    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return Reserva.class.isAssignableFrom(type);
    }

    @Override
    public Reserva readFrom(Class<Reserva> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
        Reserva r = new Reserva();
        JsonParser parser = Json.createParser(in);
        while (parser.hasNext()) {
            switch (parser.next()) {
                case KEY_NAME:
                    String key = parser.getString();
                    parser.next();
                    switch (key) {
                        case "cliente":
                            r.setCliente(parser.getString());
                            break;
                        case "hotel":
                            r.setHotel(parser.getInt());
                            break;
                        case "fecha_ini":
                            r.setFechaIni(parser.getString());
                            break;
                        case "fecha_fin":
                            r.setFechaFin(parser.getString());
                            break;
                        case "tarjeta_credito":
                            r.setTarjetaCredito(parser.getString());
                            break;
                        case "fecha_tarjeta":
                            r.setFechaTarjeta(parser.getString());
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        return r;
    }

}
