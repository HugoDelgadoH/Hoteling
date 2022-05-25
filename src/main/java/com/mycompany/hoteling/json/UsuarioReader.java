/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.json;

import com.mycompany.hoteling.entities.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.math.BigInteger;
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
public class UsuarioReader implements MessageBodyReader<Usuario> {

    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return Usuario.class.isAssignableFrom(type);
    }

    @Override
    public Usuario readFrom(Class<Usuario> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
        Usuario r = new Usuario();
        JsonParser parser = Json.createParser(in);
        while (parser.hasNext()) {
            switch (parser.next()) {
                case KEY_NAME:
                    String key = parser.getString();
                    parser.next();
                    switch (key) {
                        case "email":
                            r.setEmail(parser.getString());
                            break;
                        case "nombre":
                            r.setNombre(parser.getString());
                            break;
                        case "password":
                            r.setPassword(parser.getString());
                            break;
                        case "dni":
                            r.setDni(parser.getString());
                            break;
                        case "telefono":
                            r.setTelefono(parser.getString());
                            break;
                        case "fecha_nacimiento":
                            r.setFechaNacimiento(parser.getString());
                            break;
                        case "cif":
                            r.setCif(parser.getString());
                            break;
                        case "domicilio":
                            r.setDomicilio(parser.getString());
                            break;
                        case "capital_social":
                            r.setCapitalSocial(BigInteger.valueOf(parser.getInt()));
                            break;
                        case "otros":
                            r.setOtros(parser.getString());
                            break;
                        case "verificado":
                            r.setVerificado(new Short(parser.getString()));
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
