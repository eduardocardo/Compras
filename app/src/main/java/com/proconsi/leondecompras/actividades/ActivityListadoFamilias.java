package com.proconsi.leondecompras.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.adapters.AdapterArticulosSubfamilia;
import com.proconsi.leondecompras.comunicaciones.objetos.Articulo;
import com.proconsi.leondecompras.comunicaciones.objetos.Categoria;
import com.proconsi.leondecompras.utilidades.Config;
import com.proconsi.leondecompras.views.MenuLateral;
import com.proconsi.leondecompras.views.ViewFamilias;

public class ActivityListadoFamilias extends AppCompatActivity {

    private Categoria categoria;

    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private ViewFamilias listaFamilias;
    private RecyclerView listaArticulos;
    private RecyclerView.LayoutManager lManager;
    private AdapterArticulosSubfamilia adapter;
    public Articulo[] articulos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_familias);

        init();
        setData();
    }

    private void init() {
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            if (Config.PCI) {
                actionBar.setSubtitle(getString(R.string.conectado_a_pci));
            }
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ((MenuLateral) findViewById(R.id.menu_lateral)).setDrawerLayout(drawerLayout);

        listaFamilias = (ViewFamilias) findViewById(R.id.listado_familias);



      ;

    }

    private void setData() {
        Intent intent = getIntent();
        if (intent != null) {
            categoria = (Categoria) intent.getSerializableExtra("categoria");
        }

        if (categoria != null) {
            if (actionBar != null) {
                actionBar.setTitle(categoria.getNombre());
            }

            listaFamilias.cargarDatos(categoria.getFamilias());
        }
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
