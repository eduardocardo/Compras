package com.proconsi.leondecompras.views;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.adapters.AdapterHorizontalBanner;
import com.proconsi.leondecompras.comunicaciones.objetos.Banner;
import com.squareup.okhttp.Call;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rodrigo.gomez on 21/09/2015.
 */
public class ViewBannerPortada extends LinearLayout {

    private Banner[] banners;

    private Activity activity;

    private RecyclerView recyclerView;
    private View productosNoCargados;
    private View contenedorCargandoProductos;

    private AsynctaskObtenerBanner asynctaskObtenerBanner;
    private AsynctaskAutoScoll asynctaskAutoScoll;

    public ViewBannerPortada(Context context) {
        super(context);
        init();
    }

    public ViewBannerPortada(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewBannerPortada(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_horizontal, null);

        TextViewTypeface titulo = (TextViewTypeface) view.findViewById(R.id.text_titulo);
        titulo.setVisibility(GONE);

        TextViewTypeface cargando = (TextViewTypeface) view.findViewById(R.id.text_cargando);
        cargando.setText(R.string.cargando);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        productosNoCargados = view.findViewById(R.id.text_no_cargado);
        contenedorCargandoProductos = view.findViewById(R.id.contenedor_cargando);

        if (!isInEditMode()) {
            asynctaskObtenerBanner = new AsynctaskObtenerBanner();
            asynctaskAutoScoll = new AsynctaskAutoScoll();
        }

        addView(view);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void cargarDatos() {
        if (asynctaskObtenerBanner != null) {
            asynctaskObtenerBanner.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        if (asynctaskAutoScoll != null) {
            //asynctaskAutoScoll.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (asynctaskObtenerBanner != null) {
            asynctaskObtenerBanner.cancelar();
        }

        if (asynctaskAutoScoll != null) {
            asynctaskAutoScoll.cancelar();
        }

        super.onDetachedFromWindow();
    }

    private class AsynctaskObtenerBanner extends AsyncTask<Void, Void, Banner[]> {

        private Call call;

        private Banner[] tratarRespueta(String respuesta) {
            try {
                Banner[] banners = null;
                JSONObject jsonObject = new JSONObject(respuesta);

                return banners;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new Banner[0];
        }

        public void cancelar() {
            cancel(true);
        }

        @Override
        protected Banner[] doInBackground(Void... voids) {
            Banner[] banners = new Banner[100];
            for (int i = 0; i < banners.length; i++) {
                banners[i] = new Banner(i + 1);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return banners;

//            if (Config.accessToken == null || Config.accessToken.length() == 0) {
//                if (!Utilidades.generarTokens()) {
//                    return new Producto[0];
//                }
//            }
//
//            String url = "";
//
//            OkHttpClient client = new OkHttpClient();
//            client.setConnectTimeout(Config.TIME_OUT_COMUNICACIONES, TimeUnit.MILLISECONDS);
//            client.setReadTimeout(Config.TIME_OUT_COMUNICACIONES, TimeUnit.MILLISECONDS);
//            client.setWriteTimeout(Config.TIME_OUT_COMUNICACIONES, TimeUnit.MILLISECONDS);
//
//            RequestBody formBody = new FormEncodingBuilder()
//                    .add("access_token", Config.accessToken)
//                    .build();
//
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(formBody)
//                    .build();
//
//            try {
//                long initMillis = System.currentTimeMillis();
//                call = client.newCall(request);
//                Response response = call.execute();
//
//                switch (response.code()) {
//                    case 200: {
//                        String respuesta = response.body().string();
//
//                        Utilidades.mostrarLog("COMPLEMENTARIOS", "Url: " + url);
//                        Utilidades.mostrarLog("COMPLEMENTARIOS", "(" + ((System.currentTimeMillis() - initMillis) / 1000.0) + " segs) Respuesta: " + respuesta);
//
//                        return tratarRespueta(respuesta);
//                    }
//
//                    case 401: {
//                        Utilidades.refrescarTokens();
//                        return null;
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return new Producto[0];
        }

        @Override
        protected void onPostExecute(Banner[] result) {
            super.onPostExecute(result);

            banners = result;

            if (banners != null && banners.length > 0) {
                AdapterHorizontalBanner adapterHorizontalBanner = new AdapterHorizontalBanner(banners);
                adapterHorizontalBanner.setOnItemClickListener(new AdapterHorizontalBanner.IOnItemClickListener() {

                    @Override
                    public void onItemClickListener(View view, int position, Object object) {
                        recyclerView.smoothScrollToPosition(banners.length - 1);
                    }
                });

                recyclerView.setAdapter(adapterHorizontalBanner);
                recyclerView.scrollToPosition(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % banners.length);

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

    private class AsynctaskAutoScoll extends AsyncTask<Void, Integer, Void> {

        private int count = 0;
        private boolean bloquear;

        public void cancelar() {
            cancel(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            while (!isCancelled()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isCancelled()) {
                    continue;
                }

                if (bloquear) {
                    bloquear = false;
                    continue;
                }

                if (banners != null && banners.length > 0 && activity != null) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            onProgressUpdate(count++);
                        }
                    });
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... integers) {
            super.onProgressUpdate(integers);
            int posicionInicial = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % banners.length + integers[0];

            recyclerView.scrollToPosition(posicionInicial);
            recyclerView.smoothScrollToPosition(posicionInicial + 1);
        }
    }
}
