package com.proconsi.leondecompras.comunicaciones.objetos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rodrigo.gomez on 27/10/2015.
 */
public class Familia implements Serializable {

    private long id;
    private long idCategoria;
    private String nombre;
    private String urlFoto;
    private Subfamilia[] subfamilias;

    public Familia(JSONObject familiaJson) {
        this.id = familiaJson.optInt("id");
        this.idCategoria = familiaJson.optInt("categoria_id");
        this.nombre = familiaJson.optString("nombre");
        this.urlFoto = familiaJson.optString("url_foto");

        JSONArray jsonArray = familiaJson.optJSONArray("subfamilias");
        if (jsonArray != null) {
            this.subfamilias = new Subfamilia[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subfamiliaJson = jsonArray.optJSONObject(i);

                if (subfamiliaJson != null) {
                    this.subfamilias[i] = new Subfamilia(subfamiliaJson);
                }
            }
        }
    }

    public long getId() {
        return id;
    }

    public long getIdCategoria() {
        return idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public Subfamilia[] getSubfamilias() {
        return subfamilias;
    }
}
