package com.proconsi.leondecompras.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.comunicaciones.objetos.Articulo;
import com.proconsi.leondecompras.utilidades.Utilidades;
import com.proconsi.leondecompras.views.ImageView;
import com.proconsi.leondecompras.views.TextViewTypeface;
import com.proconsi.leondecompras.views.Valoracion;
import com.squareup.picasso.Picasso;

/**
 * Created by rodrigo.gomez on 21/09/2015.
 */
public class AdapterArticulosSubfamilia extends ArrayAdapter<Articulo> {

    public AdapterArticulosSubfamilia(Context context, int resource, Articulo[] objects) {
        super(context, resource, objects);
    }

    class ViewHolder {
        ImageView imagen;
        TextViewTypeface nombre;
        TextViewTypeface nombreVendedor;
        TextViewTypeface precioUnidad;
        TextViewTypeface precioAntes;
        TextViewTypeface precioKilo;
        Valoracion valoracion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Articulo articulo = getItem(position);
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fila_articulo, null);
            holder = new ViewHolder();

            holder.imagen = (ImageView) convertView.findViewById(R.id.imagen_articulo);
            holder.nombre = (TextViewTypeface) convertView.findViewById(R.id.text_nombre_articulo);
            holder.nombreVendedor = (TextViewTypeface) convertView.findViewById(R.id.text_nombre_vendedor);
            holder.precioUnidad = (TextViewTypeface) convertView.findViewById(R.id.text_precio_unidad);

            TextViewTypeface precioAntes = (TextViewTypeface) convertView.findViewById(R.id.texto_precio);
            precioAntes.setPaintFlags(precioAntes.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.precioAntes = precioAntes;
            holder.precioKilo = (TextViewTypeface) convertView.findViewById(R.id.text_precio_kilo);
            holder.valoracion = (Valoracion) convertView.findViewById(R.id.valoracion);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String urlImagen = articulo.getUrlImagenPrincipal();
        if (urlImagen != null && urlImagen.length() > 0) {
            int resize = (int) Utilidades.convertDpToPixel(150f, getContext());

            Picasso.with(getContext())
                    .load(urlImagen)
                    .resize(resize, resize)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .into(holder.imagen);
        }

        holder.nombre.setText(articulo.getNombre());
        holder.nombreVendedor.setText(getContext().getString(R.string.vendedor) + ": " + "El serranillo");
        holder.precioUnidad.setText(articulo.getPrecioUnidadTratado() + " " + getContext().getString(R.string.unidad));

        String precioAntesAux = articulo.getPrecioAntesTratado();
        if (precioAntesAux != null) {
            holder.precioAntes.setText(precioAntesAux + " " + getContext().getString(R.string.unidad));
        } else {
            holder.precioAntes.setVisibility(View.GONE);
        }

        holder.precioKilo.setText(articulo.getPrecioKiloTratado() + " " + getContext().getString(R.string.unidad_medida));

        holder.valoracion.setEnabled(false);
        holder.valoracion.setValoracion(articulo.getValoracion());

        return convertView;
    }
}
