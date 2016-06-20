package com.proconsi.leondecompras.comunicaciones.objetos;

import com.proconsi.leondecompras.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by rodrigo.gomez on 27/10/2015.
 */
public class Categoria implements Serializable {

    private long id;
    private String nombre;
    private Familia[] familias;
    private int logo;
    private String descripcion;
    private int numArticulosTotales;
    private int colorFondo;

    public static final String[] DESCRIPCIONES ={"Mejores productos de León","Productos de gran calidad","La mejor morcilla del  mundo mundial"};


    public Categoria(JSONObject categoriaJson) {
        this.id = categoriaJson.optInt("id");
        this.nombre = categoriaJson.optString("nombre");

        JSONArray jsonArray = categoriaJson.optJSONArray("familias");
        if (jsonArray != null) {
            this.familias = new Familia[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject familiaJson = jsonArray.optJSONObject(i);

                if (familiaJson != null) {
                    this.familias[i] = new Familia(familiaJson);
                }
            }
        }
        Random rnd = new Random();
        descripcion = DESCRIPCIONES[rnd.nextInt(DESCRIPCIONES.length - 1)];


    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Familia[] getFamilias() {
        return familias;
    }

    public int getLogo()
    {
        return logo;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public int getNumArticulosTotales()
    {
        return numArticulosTotales;
    }

    public void setNumeroProductosTotales(int num)
    {
        numArticulosTotales = num;
    }

    public void setLogo(int log)
    {
        logo = log;
    }
    public void setColor(int col)
    {
        colorFondo = col;
    }
    public int getColor()
    {
        return colorFondo;
    }
}
