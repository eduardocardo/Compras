package com.proconsi.leondecompras.actividades;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.utilidades.Config;
import com.proconsi.leondecompras.views.MenuLateral;
import com.proconsi.leondecompras.views.ViewBannerPortada;
import com.proconsi.leondecompras.views.ViewProductosComplementarios;
import com.proconsi.leondecompras.views.ViewProductosRelacionados;

public class ActivityPortada extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);

        init();
        setData();
    }

    private void init() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null && Config.PCI) {
            actionBar.setSubtitle(getString(R.string.conectado_a_pci));
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ((MenuLateral) findViewById(R.id.menu_lateral)).setDrawerLayout(drawerLayout);

        ViewBannerPortada viewBannerPortada = (ViewBannerPortada) findViewById(R.id.view_banner);
        viewBannerPortada.setActivity(this);
        viewBannerPortada.cargarDatos();

        ViewProductosComplementarios viewProductosComplementarios = (ViewProductosComplementarios) findViewById(R.id.view_productos_complementarios);
        viewProductosComplementarios.setActivity(this);
        viewProductosComplementarios.cargarDatos();

        ViewProductosRelacionados viewProductosRelacionados = (ViewProductosRelacionados) findViewById(R.id.view_productos_relacionados);
        viewProductosRelacionados.setActivity(this);
        viewProductosRelacionados.cargarDatos();

        ViewProductosRelacionados viewProductosRelacionados2 = (ViewProductosRelacionados) findViewById(R.id.view_productos_relacionados2);
        viewProductosRelacionados2.setActivity(this);
        viewProductosRelacionados2.cargarDatos();

        ViewProductosRelacionados viewProductosRelacionados3 = (ViewProductosRelacionados) findViewById(R.id.view_productos_relacionados3);
        viewProductosRelacionados3.setActivity(this);
        viewProductosRelacionados3.cargarDatos();

        ViewProductosRelacionados viewProductosRelacionados4 = (ViewProductosRelacionados) findViewById(R.id.view_productos_relacionados4);
        viewProductosRelacionados4.setActivity(this);
        viewProductosRelacionados4.cargarDatos();
    }

    private void setData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_abrir_menu_lateral:
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
