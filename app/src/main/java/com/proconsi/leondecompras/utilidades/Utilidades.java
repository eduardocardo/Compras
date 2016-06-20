package com.proconsi.leondecompras.utilidades;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;

import com.proconsi.leondecompras.R;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rodrigo.gomez on 15/07/2015.
 */
public class Utilidades {

    public static void mostrarLog(String titulo, String mensaje) {
        Log.e(titulo, mensaje);
    }

    public static int screenWidth(Context c) {
        return c.getResources().getDisplayMetrics().widthPixels;
    }

    public static int screenHeigtht(Context c) {
        return c.getResources().getDisplayMetrics().heightPixels;
    }

    public static int statusBarHeigtht(Activity c) {
        return c.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    public static int actionBarHeight(Context c) {
        final TypedArray styledAttributes = c.getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return mActionBarSize;
    }

    public static void mostrarDialogoInformativo(Context context, String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);

        if (titulo != null && titulo.length() > 0) {
            builder.setTitle(titulo);
        }

        if (mensaje != null && mensaje.length() > 0) {
            builder.setMessage(mensaje);
        }

        builder.setPositiveButton(context.getString(R.string.aceptar), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    public static void mostrarDialogoOpciones(Context context, String titulo, String mensaje, DialogInterface.OnClickListener accionAceptar, DialogInterface.OnClickListener accionCancelar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);

        if (titulo != null && titulo.length() > 0) {
            builder.setTitle(titulo);
        }

        if (mensaje != null && mensaje.length() > 0) {
            builder.setMessage(mensaje);
        }

        builder.setPositiveButton(context.getString(R.string.aceptar), accionAceptar);
        builder.setNegativeButton(context.getString(R.string.cancelar), accionCancelar);

        builder.show();
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        return dp * (metrics.densityDpi / 160f);
    }

    public static void compartir(Context context, String titulo, String cuerpo) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, titulo);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, cuerpo);

        context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.compartir)));
    }

    public static int getAlphaForView(int scrollY) {
        int minDist = 0, maxDist = 255;
        if (scrollY > maxDist){
            return 255;
        } else if (scrollY < minDist){
            return 0;
        } else {
            return (int)  ((255.0 / maxDist) * scrollY);
        }
    }

    public static String tratarPrecio(double precio) {
        int precioInt = (int) precio;
        if (precioInt == precio) {
            return precioInt + "";
        }

        return String.format("%.02f", precio);
    }

    public static boolean generarTokens() {
        OkHttpClient clientOk = new OkHttpClient();

        RequestBody formBody = new FormEncodingBuilder()
                .add("grant_type", "password")
                .add("client_id", Config.CLIENT_ID)
                .add("client_secret", Config.CLIENT_SECRET)
                .add("username", Config.USERNAME)
                .add("password", Config.PASSWORD)
                .build();

        Request request = new Request.Builder()
                .url(Config.URL_API_GENERAR_TOKEN)
                .post(formBody)
                .build();

        try {
            Response responseOk = clientOk.newCall(request).execute();

            if (responseOk.isSuccessful()) {
                String respuesta = responseOk.body().string();

                int secondsTokenExpire = new JSONObject(respuesta).optInt("expires_in", -1);
                if (secondsTokenExpire != -1) {
                    Config.accessToken = new JSONObject(respuesta).getString("access_token");
                    Config.refreshToken = new JSONObject(respuesta).getString("refresh_token");

                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.SECOND, secondsTokenExpire);

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Config.tokenExpireAt = formatter.format(calendar.getTime());

                    return true;
                }
            }
        } catch (Exception ignored) { }

        Config.accessToken = "";
        Config.refreshToken = "";

        return false;
    }

    public static boolean refrescarTokens() {
        OkHttpClient clientOk = new OkHttpClient();

        RequestBody formBody = new FormEncodingBuilder()
                .add("grant_type", "refresh_token")
                .add("refresh_token", Config.refreshToken)
                .add("client_id", Config.CLIENT_ID)
                .add("client_secret", Config.CLIENT_SECRET)
                .add("username", Config.USERNAME)
                .add("password", Config.PASSWORD)
                .build();

        Request request = new Request.Builder()
                .url(Config.URL_API_REFRESCAR_TOKEN)
                .post(formBody)
                .build();

        try {
            Response responseOk = clientOk.newCall(request).execute();

            if (responseOk.isSuccessful()) {
                String respuesta = responseOk.body().string();

                int secondsTokenExpire = new JSONObject(respuesta).optInt("expires_in", -1);
                if (secondsTokenExpire != -1) {
                    Config.accessToken = new JSONObject(respuesta).getString("access_token");
                    Config.refreshToken = new JSONObject(respuesta).getString("refresh_token");

                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.SECOND, secondsTokenExpire);

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Config.tokenExpireAt = formatter.format(calendar.getTime());

                    return true;
                }
            }
        } catch (Exception ignored) { }

        Config.accessToken = null;
        Config.refreshToken = null;

        return false;
    }

    public static boolean loginExpirado() {
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, -60); // Restamos 60 segundos para evitar desfases de tiempos de comunicaciones y demas fallos que pueda haber.

            String fechaActual = formatter.format(calendar.getTime());

            Date dateActual = formatter.parse(fechaActual);
            Date dateExpireAt = formatter.parse(Config.tokenExpireAt);

            // La fecha 'dateActual' es mayor o igual 'dateExpireAt'.
            return dateActual.compareTo(dateExpireAt) >= 0;
        } catch (ParseException ignored) { }

        return true;
    }
}
