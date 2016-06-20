package com.proconsi.leondecompras.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.comunicaciones.objetos.Banner;
import com.proconsi.leondecompras.utilidades.Utilidades;
import com.proconsi.leondecompras.views.ImageView;
import com.proconsi.leondecompras.views.TextViewTypeface;
import com.squareup.picasso.Picasso;

/**
 * Created by rodrigo.gomez on 22/09/2015.
 */
public class AdapterHorizontalBanner extends RecyclerView.Adapter<AdapterHorizontalBanner.ViewHolder> {

    public IOnItemClickListener iOnItemClickListener;

    public interface IOnItemClickListener {
        void onItemClickListener(View view, int position, Object object);
    }

    public void setOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    private Banner[] banners;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Context context;

        public View view;
        public TextViewTypeface nombre;
        public ImageView imagen;

        public ViewHolder(View itemView, Context context) {
            super(itemView);

            this.context = context;

            this.view = itemView;
            this.nombre = (TextViewTypeface) itemView.findViewById(R.id.text_nombre_banner);
            this.imagen = (ImageView) itemView.findViewById(R.id.imagen_banner);
        }
    }

    public AdapterHorizontalBanner(Banner[] banners) {
        this.banners = banners;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_banner, null);

        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int pos) {
        final int position = pos % banners.length;
        final int resizeW = Utilidades.screenWidth(holder.context);

        holder.view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (iOnItemClickListener != null) {
                    iOnItemClickListener.onItemClickListener(holder.view, position, banners[position]);
                }
            }
        });

        holder.view.post(new Runnable() {

            @Override
            public void run() {
                int resizeH = (int) Utilidades.convertDpToPixel(140f, holder.context);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.view.getLayoutParams();
                params.width = resizeW;
                params.height = resizeH;

                holder.view.setLayoutParams(params);
            }
        });

        holder.nombre.setText(banners[position].getNombre());

        String urlImagen = banners[position].getUrlImagen();
        if (urlImagen != null && urlImagen.length() > 0) {
            int resizeH = (int) Utilidades.convertDpToPixel(100f, holder.context);

            Picasso.with(holder.context)
                    .load(urlImagen)
                    .resize(resizeW - (int) Utilidades.convertDpToPixel(10f, holder.context), resizeH)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .into(holder.imagen);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
