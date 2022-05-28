/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hoteling.general;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author gohug
 */
public class FotosHoteles {

    ArrayList<String> fotos;

    public FotosHoteles() {
        fotos=new ArrayList<>();
        fotos.add("https://y.cdrst.com/foto/hotel-sf/8683/granderesp/hotel-barcelo-punta-umbria-beach-resort-servicios-857e6da.jpg");
        fotos.add("https://imgcy.trivago.com/c_limit,d_dummy.jpeg,f_auto,h_1300,q_auto,w_2000/itemimages/13/42/13425668.jpeg");
        fotos.add("https://www.tourinews.es/uploads/s1/30/77/24/marriott-se-hace-con-el-100-de-la-espanola-ac-hotels.jpeg");
        fotos.add("https://media-cdn.tripadvisor.com/media/photo-s/16/1a/ea/54/hotel-presidente-4s.jpg");
        fotos.add("https://media-cdn.tripadvisor.com/media/photo-s/22/aa/62/f8/hotel-exterior.jpg");
        fotos.add("https://content.r9cdn.net/himg/de/8b/f5/leonardo-1122025-FUENTE_ATARDECER_O-852834.jpg");
        fotos.add("https://d500.epimg.net/cincodias/imagenes/2021/12/22/companias/1640168545_359840_1640179309_noticia_normal.jpg");
        fotos.add("https://static.barcelo.com/content/dam/bhg/master/es/hoteles/guatemala/guatemala-city/barcelo-guatemala-city/main-photos/hotel/BGUA_VIEW_01.jpg");
        fotos.add("https://casadelpoeta.es/wp-content/uploads/2019/09/Hoteles-con-encanto-en-Espa%C3%B1a.jpg");
        fotos.add("https://e00-expansion.uecdn.es/assets/multimedia/imagenes/2021/10/13/16341217436727.jpg");
        fotos.add("https://www.hotelescenter.es/wp-content/blogs.dir/1601/files/home//home-corporativa-cordoba-hotel-corboba-center-1.jpg");
        fotos.add("https://media-cdn.tripadvisor.com/media/photo-s/21/91/1a/75/exterior.jpg");
        fotos.add("https://www.momondo.es/himg/69/12/b5/arbisoftimages-158935-piscina2-961686.jpg");
        fotos.add("https://www.marinador.com/sites/default/files/hotel-gran-duque-4.jpg");
    }

    public String getRandomFoto() {

        Random rand = new Random();
        
        return fotos.get(rand.nextInt(fotos.size()));
    }
}
