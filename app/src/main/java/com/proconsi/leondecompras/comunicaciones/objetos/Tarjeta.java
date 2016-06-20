package com.proconsi.leondecompras.comunicaciones.objetos;

/**
 * Created by practicassoftware3 on 05/05/2016.
 * Esta clase representa una tarjeta de crédito
 */
public class Tarjeta {

    private String numero;
    private int imagen;

    public Tarjeta(String n)
    {
        numero = n;

    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String nombre) {
        this.numero = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
