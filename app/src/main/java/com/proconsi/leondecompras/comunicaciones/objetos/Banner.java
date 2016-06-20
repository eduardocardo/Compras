package com.proconsi.leondecompras.comunicaciones.objetos;

import org.json.JSONObject;

import java.util.Random;

/**
 * Created by rodrigo.gomez on 27/10/2015.
 */
public class Banner {

    private String nombre;
    private String urlImagen;

    public Banner(int posicion) {
        this.nombre = generarNombreAleatorio();
        this.urlImagen = generarUrlImagenAleatoria("");
    }

    public Banner(JSONObject jsonObject) {
        this.nombre = jsonObject.optString("nombre");
        this.urlImagen = jsonObject.optString("url_imagen");
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    // ***************** GENERADOR DATOS FALSOS *****************

    private String generarNombreAleatorio() {
        Random r = new Random();
        int i1 = r.nextInt(100);

        String nombre = "";
        if (i1 < 10) {
            nombre = "Patatas fritas";
        } else if (i1 >= 10 && i1 < 20) {
            nombre = "Chorizo de cantimpalo";
        } else if (i1 >= 20 && i1 < 30) {
            nombre = "Macarrones con tomate";
        } else if (i1 >= 30 && i1 < 40) {
            nombre = "Pizza";
        } else if (i1 >= 40 && i1 < 50) {
            nombre = "Ensalada de huevos de codorniz adornados con especias de azerot";
        } else if (i1 >= 50 && i1 < 60) {
            nombre = "Agüita mineral";
        } else if (i1 >= 60 && i1 < 70) {
            nombre = "Cocido maragato";
        } else if (i1 >= 70 && i1 < 80) {
            nombre = "Hamburguesa";
        } else if (i1 >= 80 && i1 < 90) {
            nombre = "Filete de ternera";
        } else if (i1 >= 90) {
            nombre = "Arroz con albóndigas";
        }

        return nombre;
    }

    private String generarUrlImagenAleatoria(String tipoImagen) {
        Random r = new Random();
        int i1 = r.nextInt(500 - 400) + 400;

        r = new Random();
        int i2 = r.nextInt(500 - 400) + 400;

        return "http://lorempixel.com/" + i1 + "/" + i2 + "/" + tipoImagen + "/";
    }
}
