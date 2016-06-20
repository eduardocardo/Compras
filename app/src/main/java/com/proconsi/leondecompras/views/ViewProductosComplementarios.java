package com.proconsi.leondecompras.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.actividades.ActivityVerArticulo;
import com.proconsi.leondecompras.adapters.AdapterHorizontalArticulos;
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

import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigo.gomez on 21/09/2015.
 */
public class ViewProductosComplementarios extends LinearLayout {

    private Activity activity;

    private RecyclerView recyclerView;
    private View productosNoCargados;
    private View contenedorCargandoProductos;

    private AsynctaskObtenerProductosComplementarios asynctaskObtenerProductosComplementarios;

    public ViewProductosComplementarios(Context context) {
        super(context);
        init();
    }

    public ViewProductosComplementarios(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewProductosComplementarios(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_horizontal, null);

        TextViewTypeface titulo = (TextViewTypeface) view.findViewById(R.id.text_titulo);
        titulo.setText(R.string.articulos_complementarios);

        TextViewTypeface cargando = (TextViewTypeface) view.findViewById(R.id.text_cargando);
        cargando.setText(R.string.cargando_articulos_complementarios);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        productosNoCargados = view.findViewById(R.id.text_no_cargado);
        contenedorCargandoProductos = view.findViewById(R.id.contenedor_cargando);

        if (!isInEditMode()) {
            asynctaskObtenerProductosComplementarios = new AsynctaskObtenerProductosComplementarios();
        }

        addView(view);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void cargarDatos() {
        if (asynctaskObtenerProductosComplementarios != null) {
            asynctaskObtenerProductosComplementarios.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (asynctaskObtenerProductosComplementarios != null) {
            asynctaskObtenerProductosComplementarios.cancelar();
        }

        super.onDetachedFromWindow();
    }

    private class AsynctaskObtenerProductosComplementarios extends AsyncTask<Void, Void, Articulo[]> {

        private Call call;

        private Articulo[] tratarRespueta(JSONArray respuesta) {
            Articulo[] articulos = new Articulo[respuesta.length()];
            for (int i = 0; i < articulos.length; i++) {
                JSONObject articulo = respuesta.optJSONObject(i);

                if (articulo != null) {
                    articulos[i] = new Articulo(articulo);
                }
            }

            return articulos;
        }

        public void cancelar() {
            cancel(true);
        }

        @Override
        protected Articulo[] doInBackground(Void... voids) {
//            if (Config.accessToken == null || Config.accessToken.length() == 0) {
//                if (!Utilidades.generarTokens()) {
//                    return new Articulo[0];
//                }
//            }
            //se generan artículos con datos obtenidos de forma aleatoria,no hay conexion con servidor
            Articulo[] articuloses = new Articulo[10];
            for (int i = 0; i < articuloses.length; i++) {
                articuloses[i] = new Articulo(i + 1);
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (articuloses != null) {
                return articuloses;
            }

            String url = Config.URL_API_ARTICULOS + "?random=true";

            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(Config.TIME_OUT_COMUNICACIONES, TimeUnit.MILLISECONDS);
            client.setReadTimeout(Config.TIME_OUT_COMUNICACIONES, TimeUnit.MILLISECONDS);
            client.setWriteTimeout(Config.TIME_OUT_COMUNICACIONES, TimeUnit.MILLISECONDS);

//            RequestBody formBody = new FormEncodingBuilder()
//                    .add("access_token", Config.accessToken)
//                    .build();

            Request request = new Request.Builder()
                    .url(url)
//                    .post(formBody)
                    .build();

            try {
                long initMillis = System.currentTimeMillis();
                call = client.newCall(request);
                Response response = call.execute();

                int c = response.code();
                switch (response.code()) {
                    case 200: {
                        String respuesta = response.body().string();

                        Utilidades.mostrarLog("COMPLEMENTARIOS", "Url: " + url);
                        Utilidades.mostrarLog("COMPLEMENTARIOS", "(" + ((System.currentTimeMillis() - initMillis) / 1000.0) + " segs) Respuesta: " + respuesta);

                        //JSONObject jsonObject = new JSONObject(respuesta);
                        JSONArray jsonArray = new JSONArray(respuesta);

                      /*  int resultCode = jsonObject.optInt("resultCode", -1);
                        if (resultCode != 0) {
                            return new Articulo[0];
                        }*/

                        //return tratarRespueta(jsonObject.optJSONArray("resultValues"));
                        return tratarRespueta(jsonArray);
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
        protected void onPostExecute(final Articulo[] articulos) {
            super.onPostExecute(articulos);

            if (articulos == null && Config.accessToken != null) {
                AsynctaskObtenerProductosComplementarios asynctaskObtenerProductosComplementarios = new AsynctaskObtenerProductosComplementarios();
                asynctaskObtenerProductosComplementarios.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else if (articulos != null && articulos.length > 0) {
                AdapterHorizontalArticulos adapterHorizontalArticulos = new AdapterHorizontalArticulos(articulos);
                adapterHorizontalArticulos.setOnItemClickListener(new AdapterHorizontalArticulos.IOnItemClickListener() {

                    @Override
                    public void onItemClickListener(View view, int position, Object object) {
                        Intent maleta = new Intent(getContext(), ActivityVerArticulo.class);
                        maleta.putExtra("articulo", articulos[position]);

                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && activity != null) {
//                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity,
//                            new Pair<View, String>(view.findViewById(R.id.imagen_producto), getContext().getString(R.string.anim_imagen_producto)));
//
//                    getContext().startActivity(maleta, transitionActivityOptio.toBundle());
                            getContext().startActivity(maleta);
                        } else {
                            getContext().startActivity(maleta);
                        }
                    }
                });

                recyclerView.setAdapter(adapterHorizontalArticulos);

                recyclerView.animate().alpha(1).setDuration(500);
                productosNoCargados.animate().alpha(0).setDuration(500);
                contenedorCargandoProductos.animate().alpha(0).setDuration(500);
            } else {
                recyclerView.animate().alpha(0).setDuration(500);
                productosNoCargados.animate().alpha(1).setDuration(500);
                contenedorCargandoProductos.animate().alpha(0).setDuration(500);
            }
        }
    }
}
