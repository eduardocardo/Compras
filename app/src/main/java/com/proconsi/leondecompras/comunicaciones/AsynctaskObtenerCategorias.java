package com.proconsi.leondecompras.comunicaciones;

import android.os.AsyncTask;

import com.proconsi.leondecompras.comunicaciones.objetos.Categoria;
import com.proconsi.leondecompras.utilidades.Config;
import com.proconsi.leondecompras.utilidades.Utilidades;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigo.gomez on 21/09/2015.
 */
public class AsynctaskObtenerCategorias extends AsyncTask<Void, Void, Categoria[]> {

    public interface OnResponse {
        void onResponse(Categoria[] result);
    }

    public OnResponse onResponse;

    public void setOnResponse(OnResponse onResponse) {
        this.onResponse = onResponse;
    }

    // ************************************************************************

    private Categoria[] tratarRespueta(JSONArray jsonArray) {
        Categoria[] categorias = new Categoria[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject categoriaJson = jsonArray.optJSONObject(i);

            if (categoriaJson != null) {
                categorias[i] = new Categoria(categoriaJson);
            }
        }

        return categorias;
    }

    @Override
    protected Categoria[] doInBackground(Void... voids) {
        String url = Config.URL_API_CATEGORIAS + "?todo=true";

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(Config.TIME_OUT_COMUNICACIONES, TimeUnit.MILLISECONDS);
        client.setReadTimeout(Config.TIME_OUT_COMUNICACIONES, TimeUnit.MILLISECONDS);
        client.setWriteTimeout(Config.TIME_OUT_COMUNICACIONES, TimeUnit.MILLISECONDS);

        if (isCancelled()) {
            return new Categoria[0];
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        if (isCancelled()) {
            return new Categoria[0];
        }

        try {
            long initMillis = System.currentTimeMillis();
            Call call = client.newCall(request);
            Response response = call.execute();

            if (isCancelled()) {
                return new Categoria[0];
            }
            int ese = response.code();
            switch (response.code()) {
                case 200: {
                    String respuesta = response.body().string();

                    if (isCancelled()) {
                        return new Categoria[0];
                    }

                    Utilidades.mostrarLog("OBTENER_CATEGORIAS", "Url: " + url);
                    Utilidades.mostrarLog("OBTENER_CATEGORIAS", "(" + ((System.currentTimeMillis() - initMillis) / 1000.0) + " segs) Respuesta: " + respuesta);

                    JSONArray jsonObject = new JSONArray(respuesta);




                    Categoria[] articuloses = tratarRespueta(jsonObject);

                    if (isCancelled()) {
                        return new Categoria[0];
                    }

                    return articuloses;
                }

                case 401: {
                    if (!Utilidades.loginExpirado()) {
                        return new Categoria[0];
                    }

                    Utilidades.refrescarTokens();

                    if (isCancelled()) {
                        return new Categoria[0];
                    }

                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Categoria[0];
    }

    @Override
    protected void onPostExecute(Categoria[] result) {
        super.onPostExecute(result);

        if (result == null && Config.accessToken != null) {
            AsynctaskObtenerCategorias asynctaskObtenerCategorias = new AsynctaskObtenerCategorias();
            asynctaskObtenerCategorias.setOnResponse(onResponse);

            asynctaskObtenerCategorias.execute();
        } else if (onResponse != null) {
            onResponse.onResponse(result);
        }
    }
}
