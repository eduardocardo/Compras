package com.proconsi.leondecompras.comunicaciones;

import android.os.AsyncTask;
import android.util.Log;

import com.proconsi.leondecompras.comunicaciones.objetos.Articulo;
import com.proconsi.leondecompras.utilidades.Config;
import com.proconsi.leondecompras.utilidades.Utilidades;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigo.gomez on 24/09/2015.
 */
public class AsynctaskObtenerArticulosSubfamilia extends AsyncTask<Integer, Void, Articulo[]> {

    private Integer[] integers;

    public interface OnResponse {
        void onResponse(Articulo[] result);
    }

    public OnResponse onResponse;

    public void setOnResponse(OnResponse onResponse) {
        this.onResponse = onResponse;
    }

    // ************************************************************************

    private Articulo[] tratarRespueta(JSONArray jsonArray) {
        Articulo[] articulos = new Articulo[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject articulo = jsonArray.optJSONObject(i);

            if (articulo != null) {
                articulos[i] = new Articulo(articulo);
            }
        }

        return articulos;
    }

    @Override
    protected Articulo[] doInBackground(Integer... integers) {
//        if (integers != null) {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            Articulo[] articuloses = new Articulo[10];
//            for (int i = 0; i < articuloses.length; i++) {
//                articuloses[i] = new Articulo(i + 1);
//            }
//
//            return articuloses;
//        }

        if (integers == null || integers.length != 1) {
            return new Articulo[0];
        }

//        if (Config.accessToken == null || Config.accessToken.length() == 0) {
//            if (!Utilidades.generarTokens()) {
//                return new Articulo[0];
//            }
//        }

        if (isCancelled()) {
            return new Articulo[0];
        }

        this.integers = integers;

        String url = Config.URL_API_ARTICULOS + "?subfamilia=" + integers[0];


        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(Config.TIME_OUT_COMUNICACIONES, TimeUnit.MILLISECONDS);
        client.setReadTimeout(Config.TIME_OUT_COMUNICACIONES, TimeUnit.MILLISECONDS);
        client.setWriteTimeout(Config.TIME_OUT_COMUNICACIONES, TimeUnit.MILLISECONDS);

        if (isCancelled()) {
            return new Articulo[0];
        }

//        RequestBody formBody = new FormEncodingBuilder()
//                .add("access_token", Config.accessToken)
//                .build();

        Request request = new Request.Builder()
                .url(url)
//                .post(formBody)
                .build();

        if (isCancelled()) {
            return new Articulo[0];
        }

        try {
            long initMillis = System.currentTimeMillis();
            Call call = client.newCall(request);
            Response response = call.execute();

            if (isCancelled()) {
                return new Articulo[0];
            }

            switch (response.code()) {
                case 200: {
                    String respuesta = response.body().string();

                    if (isCancelled()) {
                        return new Articulo[0];
                    }

                    Utilidades.mostrarLog("OBTENER_ARTICULOS", "Url: " + url);
                    Utilidades.mostrarLog("OBTENER_ARTICULOS", "(" + ((System.currentTimeMillis() - initMillis) / 1000.0) + " segs) Respuesta: " + respuesta);

                    JSONArray jsonObject = new JSONArray(respuesta);



                    return tratarRespueta(jsonObject);
                }

                case 401: {
                    if (!Utilidades.loginExpirado()) {
                        return new Articulo[0];
                    }

                    Utilidades.refrescarTokens();

                    if (isCancelled()) {
                        return new Articulo[0];
                    }

                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Articulo[0];
    }

    @Override
    protected void onPostExecute(Articulo[] result) {
        super.onPostExecute(result);

        if (result == null && Config.accessToken != null) {
            AsynctaskObtenerArticulosSubfamilia asynctaskObtenerArticulosSubfamilia = new AsynctaskObtenerArticulosSubfamilia();
            asynctaskObtenerArticulosSubfamilia.setOnResponse(onResponse);

            asynctaskObtenerArticulosSubfamilia.execute(integers);
        } else if (onResponse != null) {
            onResponse.onResponse(result);
        }
    }
}
