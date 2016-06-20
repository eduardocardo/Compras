package com.proconsi.leondecompras.comunicaciones.objetos;

import com.proconsi.leondecompras.utilidades.Utilidades;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by rodrigo.gomez on 21/09/2015.
 */
public class Articulo implements Serializable {

    private int numProducto;
    private String nombre;
    private String descripcion;
    private String fecha;
    private String[] urlsImagenes;
    private int stock;
    private double precioUnidad;
    private double precioKilo;
    private double precioAntes;
    private double valoracion;
    public static final String PRECIO_KILO ="€/Kg";

    // Constructor datos falsos.
    public Articulo(int posicion) {
        this.numProducto = generarNumProductoAleatorio();
        this.nombre = generarNombreAleatorio();
        this.descripcion = generarDescripcionLoremIpsumAleatoria();
        this.fecha = generarFechaAleatoria();
        this.urlsImagenes = generarUrlsImagenesAleatorias("food");
        this.stock = posicion;
        this.precioUnidad = posicion * Double.parseDouble("10." + new Random().nextInt(10));
        this.precioKilo = posicion * Double.parseDouble("11." + new Random().nextInt(10));
        this.precioAntes = posicion * Double.parseDouble("15." + new Random().nextInt(10));
        this.valoracion = generarValoracionAleatoria();
    }

    public Articulo(JSONObject jsonObject) {
        this.numProducto = jsonObject.optInt("id", -1);
        this.nombre = jsonObject.optString("nombre");
        this.descripcion = jsonObject.optString("descripcion");
        this.fecha = jsonObject.optString("updated_at");

        JSONArray fotos = jsonObject.optJSONArray("urls_fotos");
        if (fotos != null) {
            this.urlsImagenes = new String[fotos.length()];

            for (int i = 0; i < fotos.length(); i++) {
                String urlFoto = fotos.optString(i);

                if (urlFoto != null) {
                    this.urlsImagenes[i] = urlFoto;
                }
            }
        }

        this.stock = jsonObject.optInt("stock", -1);

        JSONObject oferta = jsonObject.optJSONObject("oferta");
        if (oferta != null) {
            this.precioUnidad = oferta.optDouble("pvp", -1);
            this.precioKilo = oferta.optDouble("pvp", -1);
            this.precioAntes = jsonObject.optDouble("precio", -1);
        } else {
            this.precioUnidad = jsonObject.optDouble("precio", -1);
            this.precioKilo = jsonObject.optDouble("precioKilo", -1);
            this.precioAntes = -1;
        }

        this.valoracion = jsonObject.optDouble("valoracion", -1);
    }

    public int getNumProducto() {
        return numProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getUrlImagenPrincipal() {
        if (urlsImagenes != null && urlsImagenes.length > 0) {
            return urlsImagenes[0];
        } else {
            return null;
        }
    }

    public String[] getUrlsImagenes() {
        return urlsImagenes;
    }

    public int getStock() {
        return stock;
    }

    public double getPrecioUnidad() {
        return precioUnidad;
    }

    public String getPrecioUnidadTratado() {
        return Utilidades.tratarPrecio(precioUnidad);
    }

    public double getPrecioKilo() {
        return precioKilo;
    }

    public String getPrecioKiloTratado() {
        return Utilidades.tratarPrecio(precioKilo);
    }

    public double getPrecioAntes() {
        return precioAntes;
    }

    public String getPrecioAntesTratado() {
        if (precioAntes == -1) {
            return null;
        }

        return Utilidades.tratarPrecio(precioAntes);
    }

    public double getValoracion() {
        return valoracion;
    }

    // ***************** GENERADOR DATOS FALSOS *****************

    private int generarNumProductoAleatorio() {
        Random r = new Random();
        return r.nextInt(999999999 - 1) + 1;
    }

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

    private String generarDescripcionLoremIpsumAleatoria() {
        String loremImpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?";

        Random r = new Random();
        int i1 = r.nextInt(loremImpsum.length() - 20) + 20;

        return loremImpsum.substring(0, i1);
    }

    private String generarFechaAleatoria() {
        Random r = new Random();
        int i1 = r.nextInt(28 - 1) + 1;

        r = new Random();
        int i2 = r.nextInt(12 - 1) + 1;

        return i1 + "/" + i2 + "/2015";
    }

    private String[] generarUrlsImagenesAleatorias(String tipoImagen) {
        Random r = new Random();
        int numImagenes = r.nextInt(10 - 1) + 1;

        String[] urlsImagenes = new String[numImagenes];
        for (int i = 0; i < numImagenes; i++) {
            int i1 = r.nextInt(500 - 400) + 400;

            r = new Random();
            int i2 = r.nextInt(500 - 400) + 400;

            urlsImagenes[i] = "http://lorempixel.com/" + i1 + "/" + i2 + "/" + tipoImagen + "/";
        }

        return urlsImagenes;
    }

    private double generarValoracionAleatoria() {
        Random r = new Random();
        int i1 = r.nextInt(500);

        return i1 / 100.0;
    }
}
