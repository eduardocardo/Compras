package com.proconsi.leondecompras.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.actividades.ActivityListadoArticulosSubfamilia;
import com.proconsi.leondecompras.adapters.AdapterHorizontalSubfamilias;
import com.proconsi.leondecompras.comunicaciones.objetos.Subfamilia;

/**
 * Created by rodrigo.gomez on 21/09/2015.
 */
public class ViewSubfamilias extends LinearLayout {

    private RecyclerView recyclerView;
    private View subfamiliasNoCargadas;
    private View contenedorCargandoSubfamilias;

    public ViewSubfamilias(Context context) {
        super(context);
        init();
    }

    public ViewSubfamilias(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewSubfamilias(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_horizontal, null);

        TextViewTypeface titulo = (TextViewTypeface) view.findViewById(R.id.text_titulo);
        titulo.setText(R.string.subfamilias);

        TextViewTypeface cargando = (TextViewTypeface) view.findViewById(R.id.text_cargando);
        cargando.setText(R.string.cargando);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        subfamiliasNoCargadas = view.findViewById(R.id.text_no_cargado);
        contenedorCargandoSubfamilias = view.findViewById(R.id.contenedor_cargando);

        addView(view);
    }

    public void cargarDatos(final Subfamilia[] subfamilias) {
        if (subfamilias != null && subfamilias.length > 0) {
            AdapterHorizontalSubfamilias adapterHorizontalSubfamilias = new AdapterHorizontalSubfamilias(subfamilias);
            adapterHorizontalSubfamilias.setOnItemClickListener(new AdapterHorizontalSubfamilias.IOnItemClickListener() {

                @Override
                public void onItemClickListener(View view, int position, Object object) {
                    Intent maleta = new Intent(getContext(), ActivityListadoArticulosSubfamilia.class);
                    maleta.putExtra("subfamilia", subfamilias[position]);

                    getContext().startActivity(maleta);
                }
            });

            recyclerView.setAdapter(adapterHorizontalSubfamilias);

            recyclerView.animate().alpha(1).setDuration(500);
            subfamiliasNoCargadas.animate().alpha(0).setDuration(500);
            contenedorCargandoSubfamilias.animate().alpha(0).setDuration(500);
        } else {
            recyclerView.animate().alpha(0).setDuration(500);
            subfamiliasNoCargadas.animate().alpha(1).setDuration(500);
            contenedorCargandoSubfamilias.animate().alpha(0).setDuration(500);
        }
    }
}
