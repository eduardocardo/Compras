package com.proconsi.leondecompras.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.comunicaciones.objetos.Categoria;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by practicassoftware3 on 22/04/2016.
 */
public class CustomAdapterCategorias extends ArrayAdapter<Categoria> {

    private Categoria[] categorias;
    private int viewResourceId;




    public CustomAdapterCategorias(Activity context, int textViewResourceId, Categoria[] categ)
    {
        super(context,textViewResourceId,categ);
        viewResourceId = textViewResourceId;
        categorias = categ;
    }

    static class ViewHolder{
        TextView numArticu;
        TextView titulo;
        TextView descripcion;
        ImageView imagen;
        ImageView imag_super;
        TextView letra;
        RelativeLayout linearLayout;
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
            holder.numArticu =(TextView)item.findViewById(R.id.tv_numArticulos);
            holder.titulo=(TextView)item.findViewById(R.id.tv_titulo);
            holder.descripcion=(TextView)item.findViewById(R.id.tv_descripcion);
            holder.imagen=(ImageView)item.findViewById(R.id.im_logoCategoria);
            holder.imag_super=(ImageView)item.findViewById(R.id.iv_imagenSuperpuesta);
            holder.letra =(TextView) item.findViewById(R.id.tv_letraTitulo);
            holder.linearLayout=(RelativeLayout) item.findViewById(R.id.layout_fondo);
            item.setTag(holder);
        }
        else
        {
            holder =(ViewHolder)item.getTag();
        }
        Random rnd = new Random();
        holder.titulo.setText(this.getItem(position).getNombre());
        holder.imagen.setImageResource(this.getItem(position).getLogo());
        holder.descripcion.setText(this.getItem(position).getDescripcion());
        holder.numArticu.setText(this.getItem(position).getNumArticulosTotales() + " Productos");
        holder.imag_super.setImageResource(R.drawable.cup);
        holder.letra.setText(this.getItem(position).getNombre().substring(0,1));
        holder.linearLayout.setBackgroundColor(this.getItem(position).getColor());


        return item;
    }


}
