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
import com.proconsi.leondecompras.actividades.ActivityListadoSubfamilias;
import com.proconsi.leondecompras.adapters.AdapterHorizontalFamilias;
import com.proconsi.leondecompras.comunicaciones.objetos.Familia;

/**
 * Created by rodrigo.gomez on 21/09/2015.
 */
public class ViewFamilias extends LinearLayout {

    private RecyclerView recyclerView;
    private View familiasNoCargadas;
    private View contenedorCargandoFamilias;

    public ViewFamilias(Context context) {
        super(context);
        init();
    }

    public ViewFamilias(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewFamilias(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_horizontal, null);

        TextViewTypeface titulo = (TextViewTypeface) view.findViewById(R.id.text_titulo);
        titulo.setText(R.string.familias);

        TextViewTypeface cargando = (TextViewTypeface) view.findViewById(R.id.text_cargando);
        cargando.setText(R.string.cargando);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        familiasNoCargadas = view.findViewById(R.id.text_no_cargado);
        contenedorCargandoFamilias = view.findViewById(R.id.contenedor_cargando);

        addView(view);
    }

    public void cargarDatos(final Familia[] familias) {
        if (familias != null && familias.length > 0) {
            AdapterHorizontalFamilias adapterHorizontalFamilias = new AdapterHorizontalFamilias(familias);
            adapterHorizontalFamilias.setOnItemClickListener(new AdapterHorizontalFamilias.IOnItemClickListener() {

                @Override
                public void onItemClickListener(View view, int position, Object object) {
                    Intent maleta = new Intent(getContext(), ActivityListadoSubfamilias.class);
                    maleta.putExtra("familia", familias[position]);

                    getContext().startActivity(maleta);
                }
            });

            recyclerView.setAdapter(adapterHorizontalFamilias);

            recyclerView.animate().alpha(1).setDuration(500);
            familiasNoCargadas.animate().alpha(0).setDuration(500);
            contenedorCargandoFamilias.animate().alpha(0).setDuration(500);
        } else {
            recyclerView.animate().alpha(0).setDuration(500);
            familiasNoCargadas.animate().alpha(1).setDuration(500);
            contenedorCargandoFamilias.animate().alpha(0).setDuration(500);
        }
    }
}
