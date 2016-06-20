package com.proconsi.leondecompras.comunicaciones.objetos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rodrigo.gomez on 27/10/2015.
 */
public class Subfamilia implements Serializable {

    private long id;
    private long idFamilia;
    private String nombre;
    private String urlFoto;
    private Articulo[] articulos;

    public Subfamilia(JSONObject subfamiliaJson) {
        this.id = subfamiliaJson.optInt("id");
        this.idFamilia = subfamiliaJson.optInt("familia_id");
        this.nombre = subfamiliaJson.optString("nombre");
        this.urlFoto = subfamiliaJson.optString("url_foto");

        JSONArray jsonArray = subfamiliaJson.optJSONArray("articulos");
        if (jsonArray != null) {
            this.articulos = new Articulo[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject artic = jsonArray.optJSONObject(i);

                if (artic != null) {
                    this.articulos[i] = new Articulo(artic);
                }
            }
        }
    }

    public long getId() {
        return id;
    }

    public long getIdFamilia() {
        return idFamilia;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public Articulo[] getArticulos()
    {
        return articulos;
    }
}
