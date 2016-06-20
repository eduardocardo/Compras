package com.proconsi.leondecompras.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.comunicaciones.objetos.Articulo;
import com.proconsi.leondecompras.utilidades.Utilidades;
import com.proconsi.leondecompras.views.ImageView;
import com.proconsi.leondecompras.views.TextViewTypeface;
import com.squareup.picasso.Picasso;

/**
 * Created by rodrigo.gomez on 22/09/2015.
 */
public class AdapterHorizontalArticulos extends RecyclerView.Adapter<AdapterHorizontalArticulos.ViewHolder> {

    public IOnItemClickListener iOnItemClickListener;

    public interface IOnItemClickListener {
        void onItemClickListener(View view, int position, Object object);
    }

    public void setOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    private Articulo[] articuloses;

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

    public AdapterHorizontalArticulos(Articulo[] articuloses) {
        this.articuloses = articuloses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_articulo, null);

        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (iOnItemClickListener != null) {
                    iOnItemClickListener.onItemClickListener(holder.view, position, articuloses[position]);
                }
            }
        });

        holder.nombre.setText(articuloses[position].getNombre());
        holder.precio.setText(articuloses[position].getPrecioUnidadTratado() + " " + holder.context.getString(R.string.unidad));

        String urlImagen = articuloses[position].getUrlImagenPrincipal();
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
        return articuloses.length;
    }
}
