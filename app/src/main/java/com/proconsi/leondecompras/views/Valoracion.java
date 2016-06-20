package com.proconsi.leondecompras.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.proconsi.leondecompras.R;

/**
 * Created by rodrigo.gomez on 20/07/2015.
 */
public class Valoracion extends LinearLayout {

    private View view;
    private int valoracion;

    private ImageView votacion1;
    private ImageView votacion2;
    private ImageView votacion3;
    private ImageView votacion4;
    private ImageView votacion5;

    public Valoracion(Context context) {
        super(context);
        cargarLayout();
    }

    public Valoracion(Context context, AttributeSet attrs) {
        super(context, attrs);
        cargarLayout();
    }

    public Valoracion(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cargarLayout();
    }

    private void cargarLayout() {
        view = inflate(getContext(), R.layout.valoracion, null);
        view.post(new Runnable() {

            @Override
            public void run() {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            }
        });

        votacion1 = (ImageView) view.findViewById(R.id.votacion_1);
        votacion2 = (ImageView) view.findViewById(R.id.votacion_2);
        votacion3 = (ImageView) view.findViewById(R.id.votacion_3);
        votacion4 = (ImageView) view.findViewById(R.id.votacion_4);
        votacion5 = (ImageView) view.findViewById(R.id.votacion_5);

        votacion1.setOnClickListener(clickValoracion1);
        votacion2.setOnClickListener(clickValoracion2);
        votacion3.setOnClickListener(clickValoracion3);
        votacion4.setOnClickListener(clickValoracion4);
        votacion5.setOnClickListener(clickValoracion5);

        addView(view);
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(double valoracion) {
        int valoracionInt = (int) valoracion;
        if ((valoracion - valoracionInt) > 0.5) {
            valoracionInt++;
        }

        switch (valoracionInt) {
            case 1:
                clickValoracion1.onClick(this);
                break;
            case 2:
                clickValoracion2.onClick(this);
                break;
            case 3:
                clickValoracion3.onClick(this);
                break;
            case 4:
                clickValoracion4.onClick(this);
                break;
            case 5:
                clickValoracion5.onClick(this);
                break;
        }
    }

    public void setEnabled(boolean enabled) {
        votacion1.setOnClickListener(enabled ? clickValoracion1 : null);
        votacion2.setOnClickListener(enabled ? clickValoracion2 : null);
        votacion3.setOnClickListener(enabled ? clickValoracion3 : null);
        votacion4.setOnClickListener(enabled ? clickValoracion4 : null);
        votacion5.setOnClickListener(enabled ? clickValoracion5 : null);
    }

    // ********************** LISTENERS **********************

    private OnClickListener clickValoracion1 = new OnClickListener() {

        @Override
        public void onClick(View view) {
            valoracion = 1;

            votacion1.setImageResource(R.drawable.ic_valoracion_ok);
            votacion2.setImageResource(R.drawable.ic_valoracion_ko);
            votacion3.setImageResource(R.drawable.ic_valoracion_ko);
            votacion4.setImageResource(R.drawable.ic_valoracion_ko);
            votacion5.setImageResource(R.drawable.ic_valoracion_ko);
        }
    };

    private OnClickListener clickValoracion2 = new OnClickListener() {

        @Override
        public void onClick(View view) {
            valoracion = 2;

            votacion1.setImageResource(R.drawable.ic_valoracion_ok);
            votacion2.setImageResource(R.drawable.ic_valoracion_ok);
            votacion3.setImageResource(R.drawable.ic_valoracion_ko);
            votacion4.setImageResource(R.drawable.ic_valoracion_ko);
            votacion5.setImageResource(R.drawable.ic_valoracion_ko);
        }
    };

    private OnClickListener clickValoracion3 = new OnClickListener() {

        @Override
        public void onClick(View view) {
            valoracion = 3;

            votacion1.setImageResource(R.drawable.ic_valoracion_ok);
            votacion2.setImageResource(R.drawable.ic_valoracion_ok);
            votacion3.setImageResource(R.drawable.ic_valoracion_ok);
            votacion4.setImageResource(R.drawable.ic_valoracion_ko);
            votacion5.setImageResource(R.drawable.ic_valoracion_ko);
        }
    };

    private OnClickListener clickValoracion4 = new OnClickListener() {

        @Override
        public void onClick(View view) {
            valoracion = 4;

            votacion1.setImageResource(R.drawable.ic_valoracion_ok);
            votacion2.setImageResource(R.drawable.ic_valoracion_ok);
            votacion3.setImageResource(R.drawable.ic_valoracion_ok);
            votacion4.setImageResource(R.drawable.ic_valoracion_ok);
            votacion5.setImageResource(R.drawable.ic_valoracion_ko);
        }
    };

    private OnClickListener clickValoracion5 = new OnClickListener() {

        @Override
        public void onClick(View view) {
            valoracion = 5;

            votacion1.setImageResource(R.drawable.ic_valoracion_ok);
            votacion2.setImageResource(R.drawable.ic_valoracion_ok);
            votacion3.setImageResource(R.drawable.ic_valoracion_ok);
            votacion4.setImageResource(R.drawable.ic_valoracion_ok);
            votacion5.setImageResource(R.drawable.ic_valoracion_ok);
        }
    };
}
