package com.proconsi.leondecompras.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.utilidades.Utilidades;

/**
 * Created by rodrigo.gomez on 23/09/2015.
 */
public class ViewLeerMas extends LinearLayout {

    private final int MAX_LINEAS_VER_MAS = 3;

    private boolean postRealizado;
    private String texto;
    private int numLineasIniciales;
    private int altoInicial;

    private TextViewTypeface textTexto;
    private TextViewTypeface leerMas;

    public ViewLeerMas(Context context) {
        super(context);
        init();
    }

    public ViewLeerMas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewLeerMas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);

        post(new Runnable() {

            @Override
            public void run() {
                postRealizado = true;

                if (texto != null && texto.length() > 0) {
                    setTexto(texto);
                }
            }
        });

        textTexto = new TextViewTypeface(getContext());
        textTexto.setEllipsize(TextUtils.TruncateAt.END);

        int padding = (int) Utilidades.convertDpToPixel(10f, getContext());
        leerMas = new TextViewTypeface(getContext());
        leerMas.setPadding(0, padding, 0, 0);
        leerMas.setTextColor(getResources().getColor(R.color.color_primary));
        leerMas.setGravity(Gravity.CENTER);
        leerMas.setText(R.string.leer_mas);
        leerMas.setVisibility(GONE);

        leerMas.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                if (textTexto.getLineCount() == MAX_LINEAS_VER_MAS) {
                    textTexto.setMaxLines(numLineasIniciales);
                    leerMas.setText(R.string.leer_menos);
                } else {
                    textTexto.setMaxLines(MAX_LINEAS_VER_MAS);
                    leerMas.setText(R.string.leer_mas);
                }
            }
        });

        if (isInEditMode()) {
            textTexto.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
            leerMas.setVisibility(VISIBLE);
        }

        addView(textTexto);
        addView(leerMas);
    }

    public void setTexto(String texto) {
        this.texto = texto;

        if (textTexto != null && postRealizado) {
            textTexto.setText(texto);

            // Si se hace el 'setText' una vez pasado el post no funciona porque da un 'altoInicial' mal calculado.
            numLineasIniciales = textTexto.getLineCount();
            altoInicial = textTexto.getHeight();

            if (numLineasIniciales > MAX_LINEAS_VER_MAS) {
                textTexto.setMaxLines(MAX_LINEAS_VER_MAS);
                leerMas.setVisibility(VISIBLE);
            }
        }
    }
}
