package com.proconsi.leondecompras.actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.adapters.AdapterArticulosSubfamilia;
import com.proconsi.leondecompras.adapters.CustomAdapterCategorias;
import com.proconsi.leondecompras.comunicaciones.AsynctaskObtenerArticulosSubfamilia;
import com.proconsi.leondecompras.comunicaciones.AsynctaskObtenerCategorias;
import com.proconsi.leondecompras.comunicaciones.objetos.Articulo;
import com.proconsi.leondecompras.comunicaciones.objetos.Categoria;
import com.proconsi.leondecompras.comunicaciones.objetos.Familia;
import com.proconsi.leondecompras.comunicaciones.objetos.Subfamilia;
import com.proconsi.leondecompras.utilidades.Config;
import com.proconsi.leondecompras.views.MenuLateral;

import java.util.ArrayList;
import java.util.Random;

public class ActivityListadoCategorias extends AppCompatActivity {

    private Categoria[] categorias;
    private ArrayList<Subfamilia> listasSubFamilias;
    private DrawerLayout drawerLayout;
    private ListView listaCategorias;
    private RelativeLayout linearLayout;
    private  Articulo[] articulos;

    private  ProgressDialog cargando;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_categorias);

        init();
        setData();

    }

    private void init() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            if (Config.PCI) {
                actionBar.setSubtitle(getString(R.string.conectado_a_pci));
            }
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ((MenuLateral) findViewById(R.id.menu_lateral)).setDrawerLayout(drawerLayout);

        listaCategorias = (ListView) findViewById(R.id.listado_categorias);

        listaCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Categoria categoriaSeleccionada = categorias[i];

                Intent maleta = new Intent(ActivityListadoCategorias.this, ActivityListadoFamilias.class);
                maleta.putExtra("categoria", categoriaSeleccionada);

                startActivity(maleta);
            }
        });
        linearLayout =(RelativeLayout) findViewById(R.id.layout_fondo);


    }

    private void setData() {

        cargando = new ProgressDialog(this);


        cargando.setCancelable(false);
        cargando.setMessage(getString(R.string.Cargando));
        cargando.show();

        AsynctaskObtenerCategorias asynctaskObtenerCategorias = new AsynctaskObtenerCategorias();
        asynctaskObtenerCategorias.setOnResponse(new AsynctaskObtenerCategorias.OnResponse() {

            @Override
            public void onResponse(Categoria[] result) {
                categorias = result;
                cargarDatosCategoria(0);
            }
        });

        asynctaskObtenerCategorias.execute();

    }

    /**
     *Metodo que carga datos en una categoria determinada por la posicion en el array pasado por paramentro
     *@param posicion es la posicion en el array de categorias
     */
    private void cargarDatosCategoria(int posicion){
        final TypedArray colores = getResources().obtainTypedArray(R.array.colores); //se obtiene los colores para los fondos

        if(posicion < categorias.length) {
            categorias[posicion].setColor(colores.getColor(posicion, 0));  //se añade el color a la categoria
            categorias[posicion].setLogo(R.mipmap.ic_launcher); //se le añade una imagen a la categoria
            obtenerSubfamilias(categorias[posicion]); //se obtienen todas las subfamilias de esta categoria
            numeroArticulosPorSubFamilia(0,posicion); //se obtienen el numero de articulos de esta categoria

        }
        else
        {
            cargando.dismiss(); //se cierra el progress dialogo
            //se crea el custom adapter que se asocia al listView
            CustomAdapterCategorias adapter = new CustomAdapterCategorias(this,R.layout.custom_categorias_layout,categorias);
            listaCategorias.setAdapter(adapter);
        }


    }

    /**
     * Metodo que obtiene todas las subfamilias que tiene una categoria dada por paramentro
     * @param cat es la categoria de la que se quieren obtener todas las subfamilias
     */
    public void obtenerSubfamilias(Categoria cat)
    {
        listasSubFamilias = new ArrayList<>();
        //se obtiene las familias que tiene la categoría
        Familia[] familias = cat.getFamilias();
        //se recorre cada familia para obtener las subfamilias de cada familia
        for(Familia fam : familias)
        {
            Subfamilia[] subfamilias = fam.getSubfamilias();
            //se recorre la coleccion para añadir cada subfamilia al arraylist de tipo subfamilia
            for(Subfamilia subfam : subfamilias)
            {
                listasSubFamilias.add(subfam); //se añade la subfamilia a la lista
            }
        }
    }

    /**
     *Metodo que obtiene todos los articulos que tiene una subfamilia
     * @param id es la posicion de la subfamilia en la lista de subfamilias
     * @param pos es la posicion de la categoria en la coleccion de categorias
     */
    private void numeroArticulosPorSubFamilia(final int id, int pos)
    {
        if(id < listasSubFamilias.size()) {
            Subfamilia subfamilia = listasSubFamilias.get(id);

            AsynctaskObtenerArticulosSubfamilia asynctaskObtenerArticulosSubfamilia = new AsynctaskObtenerArticulosSubfamilia();
            final int finalPos = pos;
            asynctaskObtenerArticulosSubfamilia.setOnResponse(new AsynctaskObtenerArticulosSubfamilia.OnResponse() {

                @Override
                public void onResponse(Articulo[] result) {
                    articulos = result;

                    categorias[finalPos].setNumeroProductosTotales(categorias[finalPos].getNumArticulosTotales() + articulos.length);
                    int idAux = id;
                    //se llama el metodo de forma recursiva para obtener el numero de productos de todas las subfamilias
                    numeroArticulosPorSubFamilia(++idAux, finalPos);
                }
            });

            asynctaskObtenerArticulosSubfamilia.execute((int) subfamilia.getId());


        } else {

            //se llama el metodo de cargar datos de la siguiente categoria
            cargarDatosCategoria(++pos);
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
