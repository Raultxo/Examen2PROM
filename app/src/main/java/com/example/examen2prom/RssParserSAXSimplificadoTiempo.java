package com.example.examen2prom;

import android.sax.Element;
import android.sax.RootElement;
import android.util.Xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class RssParserSAXSimplificadoTiempo {

    private final URL rssUrl;
    private Tiempo tiempoActual;

    // Constructor que recibe la URL
    public RssParserSAXSimplificadoTiempo(String url) {
        try {
            this.rssUrl = new URL (url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo parse que devuelve un objeto Tiempo
    public Tiempo parse(){
        // Coge el elemento root
        RootElement root = new RootElement("data");

        // Cuando empieza el elemento root, crea el objeto Tiempo
        root.setStartElementListener(attributes -> tiempoActual = new Tiempo());

        // Coge los datos en referencia al dia
        Element day1 = root.getChild("day1");
        day1.getChild("date").setEndTextElementListener(body -> tiempoActual.setDia(body));
        day1.getChild("temperature_max").setEndTextElementListener(body -> tiempoActual.setTemperatura_max(body));
        day1.getChild("temperature_min").setEndTextElementListener(body -> tiempoActual.setTemperatura_min(body));
        day1.getChild("text").setEndTextElementListener(body -> tiempoActual.setEstado(body));

        // Coge los datos en referencia a la hora actual
        Element hour_hour = root.getChild("hour_hour");
        Element hour1 = hour_hour.getChild("hour1");
        hour1.getChild("hour_data").setEndTextElementListener(body -> tiempoActual.setHora(body));
        hour1.getChild("temperature").setEndTextElementListener(body -> tiempoActual.setTemperatura(body));
        hour1.getChild("icon").setEndTextElementListener(body -> tiempoActual.setIcono(body));

        try {
            Xml.parse(this.getInputStream(),
                    Xml.Encoding.ISO_8859_1,
                    root.getContentHandler());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return tiempoActual;
    }

    private InputStream getInputStream() {
        try {
            return rssUrl.openConnection().getInputStream();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
