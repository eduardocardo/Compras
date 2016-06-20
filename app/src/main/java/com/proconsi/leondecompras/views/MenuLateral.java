package com.proconsi.leondecompras.views;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.actividades.ActivityCestaCompra;
import com.proconsi.leondecompras.actividades.ActivityListadoCategorias;
import com.proconsi.leondecompras.utilidades.Config;

/**
 * Created by rodrigo.gomez on 21/09/2015.
 */
public class MenuLateral extends LinearLayout {

    private DrawerLayout drawerLayout;
    private View view;
    private TextViewTypeface verCategorias;
    private TextViewTypeface verCestaCompra;

    public MenuLateral(Context context) {
        super(context);
        init();
    }

    public MenuLateral(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MenuLateral(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    private void init() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.menu_lateral, null);
        view.post(new Runnable() {

            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            }
        });

        verCategorias = (TextViewTypeface) view.findViewById(R.id.ver_categorias);
        verCestaCompra=(TextViewTypeface)view.findViewById(R.id.ver_cesta_compra);

        setListeners();
        setColors();

        addView(view);
    }

    private void setListeners() {
        verCategorias.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                cerrarMenu();

                if (comprobarClaseActual(ActivityListadoCategorias.class.getSimpleName())) {
                    return;
                }

                getContext().startActivity(new Intent(getContext(), ActivityListadoCategorias.class));
            }
        });

        verCestaCompra.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                cerrarMenu();

                if (comprobarClaseActual(ActivityCestaCompra.class.getSimpleName())) {
                    return;
                }

                getContext().startActivity(new Intent(getContext(), ActivityCestaCompra.class));
            }
        });
    }

    private void setColors() {
        if (comprobarClaseActual(ActivityListadoCategorias.class.getSimpleName())) {
            verCategorias.setBackgroundColor(getResources().getColor(R.color.color_primary_dark));
        }
        if (comprobarClaseActual(ActivityCestaCompra.class.getSimpleName())) {
            verCestaCompra.setBackgroundColor(getResources().getColor(R.color.color_primary_dark));
        }

        // TODO Meter mas casos segun se van añadiendo botones.
    }

    // *****************************************************************************

    private void cerrarMenu() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawers();
        }
    }

    private boolean comprobarClaseActual(String nombreClase) {
        return getContext().getClass().getSimpleName().equals(nombreClase);
    }
}
