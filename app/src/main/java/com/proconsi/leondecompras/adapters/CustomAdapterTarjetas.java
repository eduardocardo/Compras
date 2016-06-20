package com.proconsi.leondecompras.adapters;

import android.app.Activity;
import android.provider.ContactsContract;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.comunicaciones.objetos.Tarjeta;
import com.proconsi.leondecompras.views.ImageView;
import com.proconsi.leondecompras.views.TextViewTypeface;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by practicassoftware3 on 05/05/2016.
 */
public class CustomAdapterTarjetas extends ArrayAdapter<Tarjeta> {

    private ArrayList<Tarjeta> tarjetas;
    private int viewResourceId;


    public CustomAdapterTarjetas(Activity context, int textViewResourceId,ArrayList<Tarjeta> tarje)
    {
        super(context,textViewResourceId,tarje);
        viewResourceId = textViewResourceId;
        tarjetas = tarje;
    }



    static class ViewHolder{
        TextViewTypeface numberCondifi;
        TextViewTypeface numberNoCodifi;


    }

    // @Override
    public View getView(int position, View view, ViewGroup parent) {

        View item = view; // el View de una fila
        ViewHolder holder; // recipiente de Views

        if (view == null) {
            // Creamos un elemento inflater
            LayoutInflater inflater = ((Activity) this.getContext()).getLayoutInflater();
            // Creamos un objeto View que sea el resultado de inflar el layout de nuestra fila
            item = inflater.inflate(viewResourceId, null); //si sale
            //se crea un recipiente de views
            holder = new ViewHolder();
            holder.numberCondifi =(TextViewTypeface)item.findViewById(R.id.tv_numeroTarjeta);
            holder.numberNoCodifi =(TextViewTypeface)item.findViewById(R.id.tv_numeroNoCodifi);
            item.setTag(holder);
        }
        else
        {
            holder =(ViewHolder)item.getTag();
        }


          String numeroCompleto = this.getItem(position).getNumero();//se obtiene el numero completo(15 caracteres)
          String numeroNoVisible = numeroCompleto.substring(0,11); //se obtiene la secuencia no visible del numero
          String numeroVisible = numeroCompleto.substring(11);  //se obtiene la cadena visible del numero(los ultimos 4 numeros)

           holder.numberCondifi.setText(numeroNoVisible);
           holder.numberNoCodifi.setText(numeroVisible);

        /*item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "CLICK");

            }
        });*/

        return item;
    }


}
