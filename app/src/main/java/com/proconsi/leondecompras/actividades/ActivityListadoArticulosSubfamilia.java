package com.proconsi.leondecompras.actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.adapters.AdapterArticulosSubfamilia;
import com.proconsi.leondecompras.comunicaciones.AsynctaskObtenerArticulosSubfamilia;
import com.proconsi.leondecompras.comunicaciones.objetos.Articulo;
import com.proconsi.leondecompras.comunicaciones.objetos.Subfamilia;
import com.proconsi.leondecompras.utilidades.Config;
import com.proconsi.leondecompras.views.MenuLateral;

/**
 * Created by rodrigo.gomez on 21/09/2015.
 */
public class ActivityListadoArticulosSubfamilia extends AppCompatActivity {

    private Articulo[] articulos;
    private Subfamilia subfamilia;

    private ProgressDialog progressDialog;

    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listaArticulosSubfamilia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_articulos_subfamilia);

        init();
        setData(true);
        setListeners();
    }

    private void init() {
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            if (Config.PCI) {
                actionBar.setSubtitle(getString(R.string.conectado_a_pci));
            }
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.obteniendo_articulos));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ((MenuLateral) findViewById(R.id.menu_lateral)).setDrawerLayout(drawerLayout);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary), getResources().getColor(R.color.color_primary_dark));

        listaArticulosSubfamilia = (ListView) findViewById(R.id.lista_articulos_subfamilia);
    }

    private void setData(boolean mostrarDialogo) {
        Intent intent = getIntent();
        if (intent != null) {
            subfamilia = (Subfamilia) intent.getSerializableExtra("subfamilia");

            if (subfamilia != null) {
                actionBar.setTitle(subfamilia.getNombre());
            }
        }

        AsynctaskObtenerArticulosSubfamilia asynctaskObtenerArticulosSubfamilia = new AsynctaskObtenerArticulosSubfamilia();
        asynctaskObtenerArticulosSubfamilia.setOnResponse(new AsynctaskObtenerArticulosSubfamilia.OnResponse() {

            @Override
            public void onResponse(Articulo[] result) {
                swipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();
                articulos = result;

                if (result != null) {
                    AdapterArticulosSubfamilia adapterArticulosSubfamilia = new AdapterArticulosSubfamilia(ActivityListadoArticulosSubfamilia.this, 0, articulos);
                    listaArticulosSubfamilia.setAdapter(adapterArticulosSubfamilia);
                }
            }
        });

        if (mostrarDialogo) {
            progressDialog.show();
        }

        asynctaskObtenerArticulosSubfamilia.execute(subfamilia != null ? (int) subfamilia.getId() : 1);
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                setData(false);
            }
        });

        listaArticulosSubfamilia.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent maleta = new Intent(ActivityListadoArticulosSubfamilia.this, ActivityVerArticulo.class);
                maleta.putExtra("articulo", articulos[i]);

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(ActivityListadoArticulosSubfamilia.this,
//                            new Pair<View, String>(view.findViewById(R.id.imagen_producto), getString(R.string.anim_imagen_producto)));
//
//                    startActivity(maleta, transitionActivityOptions.toBundle());
                    startActivity(maleta);
                } else {
                    startActivity(maleta);
                }
            }
        });
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

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawers();
            return;
        }

        super.onBackPressed();
    }
}
