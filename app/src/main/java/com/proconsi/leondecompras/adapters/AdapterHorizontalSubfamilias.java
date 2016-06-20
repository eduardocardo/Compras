package com.proconsi.leondecompras.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.comunicaciones.objetos.Subfamilia;
import com.proconsi.leondecompras.utilidades.Utilidades;
import com.proconsi.leondecompras.views.ImageView;
import com.proconsi.leondecompras.views.TextViewTypeface;
import com.squareup.picasso.Picasso;

/**
 * Created by rodrigo.gomez on 22/09/2015.
 */
public class AdapterHorizontalSubfamilias extends RecyclerView.Adapter<AdapterHorizontalSubfamilias.ViewHolder> {

    public IOnItemClickListener iOnItemClickListener;

    public interface IOnItemClickListener {
        void onItemClickListener(View view, int position, Object object);
    }

    public void setOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    private Subfamilia[] subfamilias;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public Context context;

        public View view;
        public TextViewTypeface nombre;
        public TextViewTypeface precio;
        public ImageView imagen;

        public ViewHolder(View itemView, Context context) {
            super(itemView);

            this.context = context;

            this.view = itemView;
            this.nombre = (TextViewTypeface) itemView.findViewById(R.id.text_nombre_articulo);
            this.precio = (TextViewTypeface) itemView.findViewById(R.id.texto_precio);
            this.imagen = (ImageView) itemView.findViewById(R.id.imagen_articulo);
        }
    }

    public AdapterHorizontalSubfamilias(Subfamilia[] subfamilias) {
        this.subfamilias = subfamilias;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_familia_subfamilia, null);

        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (iOnItemClickListener != null) {
                    iOnItemClickListener.onItemClickListener(holder.view, position, subfamilias[position]);
                }
            }
        });

        holder.nombre.setText(subfamilias[position].getNombre());
        holder.precio.setVisibility(View.GONE);

        String urlImagen = subfamilias[position].getUrlFoto();
        if (urlImagen != null && urlImagen.length() > 0) {
            int resizeW = (int) Utilidades.convertDpToPixel(120f, holder.context);
            int resizeH = (int) Utilidades.convertDpToPixel(100f, holder.context);

            Picasso.with(holder.context)
                    .load(urlImagen)
                    .resize(resizeW, resizeH)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .into(holder.imagen);
        }
    }

    @Override
    public int getItemCount() {
        return subfamilias.length;
    }
}
