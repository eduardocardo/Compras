package com.proconsi.leondecompras.comunicaciones.objetos;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by practicassoftware3 on 03/05/2016.
 */
public class Vendedor implements Serializable {

    private String nombre;
    private long telefono;
    private String direccion;
    private double latitud;
    private double longitud;


    public  Vendedor(String n,long t,String d,double lati,double longi)
    {
        nombre = n;
        telefono = t;
        direccion = d;
        latitud = lati;
        longitud = longi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
