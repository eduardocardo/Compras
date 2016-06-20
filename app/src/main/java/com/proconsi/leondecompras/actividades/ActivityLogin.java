package com.proconsi.leondecompras.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.views.ButtonTypeface;
import com.proconsi.leondecompras.views.EditTextTypeface;
import com.proconsi.leondecompras.views.TextViewTypeface;

/**
 * Created by rodrigo.gomez on 21/09/2015.
 */
public class ActivityLogin extends Activity {

    private TextViewTypeface accesoRegistro;
    private EditTextTypeface loginUser;
    private EditTextTypeface loginPass;
    private View contenedorMantenermeConectado;
    private CheckBox checkboxMantenermeConectado;
    private ButtonTypeface acceder;
    private TextViewTypeface olvidasteUserPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setData();
        setListeners();
    }

    private void init() {
        accesoRegistro = (TextViewTypeface) findViewById(R.id.text_acceso_o_registro);
        loginUser = (EditTextTypeface) findViewById(R.id.edit_login_user);
        loginPass = (EditTextTypeface) findViewById(R.id.edit_login_pass);
        contenedorMantenermeConectado = findViewById(R.id.contenedor_mantenerme_conectado);
        checkboxMantenermeConectado = (CheckBox) findViewById(R.id.checkbox_mantenerme_conectado);
        acceder = (ButtonTypeface) findViewById(R.id.button_acceder);
        olvidasteUserPass = (TextViewTypeface) findViewById(R.id.text_olvidaste_user_o_pass);
    }

    private void setData() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.gris_oscuro));
        }

        String textoAccesoRegistro = accesoRegistro.getText().toString();

        Spannable wordtoSpan = new SpannableString(textoAccesoRegistro);
        wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.amarillo)), 0, textoAccesoRegistro.indexOf(" "), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        accesoRegistro.setText(wordtoSpan);
    }

    private void setListeners() {
        acceder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLogin.this, ActivityListadoArticulosSubfamilia.class));
            }
        });
    }
}
