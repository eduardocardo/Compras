package com.proconsi.leondecompras.actividades;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.comunicaciones.objetos.Articulo;
import com.proconsi.leondecompras.utilidades.Config;
import com.proconsi.leondecompras.utilidades.JavaBlurProcess;
import com.proconsi.leondecompras.utilidades.Utilidades;
import com.proconsi.leondecompras.views.ButtonTypeface;
import com.proconsi.leondecompras.views.CustomScrollView;
import com.proconsi.leondecompras.views.ImageView;
import com.proconsi.leondecompras.views.MenuLateral;
import com.proconsi.leondecompras.views.TextViewTypeface;
import com.proconsi.leondecompras.views.Valoracion;
import com.proconsi.leondecompras.views.ViewLeerMas;
import com.proconsi.leondecompras.views.ViewProductosComplementarios;
import com.proconsi.leondecompras.views.ViewProductosRelacionados;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ActivityVerArticulo extends AppCompatActivity {

    private Articulo articulo;
    private int unidades = 1;

    private ActionBar actionBar;
    private ColorDrawable fondoActionBar;
    private DrawerLayout drawerLayout;

    private ImageView imagenCabecera;
    private ImageView imagenCabeceraBlur;
    private Bitmap imagenBitmap;
    private TextViewTypeface precioAntes;

    private CustomScrollView scroll;

    private View cabeceraCompraFalsa;
    private LinearLayout contenedorSeleccionarUnidadesFalso;
    private TextViewTypeface precioUnidadFalso;
    private TextViewTypeface precioKiloFalso;
    private TextViewTypeface textoUnidadesFalso;
    private TextViewTypeface precioCalculadoFalso;
    private LinearLayout comprarFalso;

    private View cabeceraCompra;
    private LinearLayout contenedorSeleccionarUnidades;
    private TextViewTypeface precioUnidad;
    private TextViewTypeface precioKilo;
    private TextViewTypeface textoUnidades;
    private TextViewTypeface precioCalculado;
    private LinearLayout comprar;

    private TextViewTypeface nombre;
    private TextViewTypeface numProducto;
    private TextViewTypeface fechaProducto;
    private ViewLeerMas viewLeerMas;

    private Valoracion valoracion;
    private ImageView imagenPieFavorito;
    private ImageView imagenPieCompartir;

    private boolean favorito;
    private boolean siguiendoVendedor;
    private boolean siguiendoProducto;

    private View.OnClickListener onClickUnidades = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            showNumberPickerDialog();
        }
    };

    private View.OnClickListener onClickComprar = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            startActivity(new Intent(ActivityVerArticulo.this, ActivityLogin.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_articulo);

        if (cargarBundle()) {
            init();
            setData();
            setListener();
        } else {
            onBackPressed();
        }
    }

    private boolean cargarBundle() {
        if (getIntent() == null) {
            return false;
        }

        articulo = (Articulo) getIntent().getSerializableExtra("articulo");

        return articulo != null;
    }

    private void init() {
        actionBar = getSupportActionBar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ((MenuLateral) findViewById(R.id.menu_lateral)).setDrawerLayout(drawerLayout);

        imagenCabecera = (ImageView) findViewById(R.id.imagen_ver_producto_cabecera);
        imagenCabeceraBlur = (ImageView) findViewById(R.id.imagen_ver_producto_cabecera_blur);
        imagenCabeceraBlur.setAlpha(0f);

        precioAntes = (TextViewTypeface) findViewById(R.id.texto_precio_antes);
        precioAntes.setPaintFlags(precioAntes.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        scroll = (CustomScrollView) findViewById(R.id.scroll_ver_producto);

        cabeceraCompraFalsa = findViewById(R.id.cabecera_compra_producto_falsa);
        precioUnidadFalso = (TextViewTypeface) cabeceraCompraFalsa.findViewById(R.id.texto_precio_unidad);
        precioKiloFalso = (TextViewTypeface) cabeceraCompraFalsa.findViewById(R.id.texto_precio_kilo);
        contenedorSeleccionarUnidadesFalso = (LinearLayout) cabeceraCompraFalsa.findViewById(R.id.contenedor_seleccionar_unidades);
        textoUnidadesFalso = (TextViewTypeface) cabeceraCompraFalsa.findViewById(R.id.text_unidades);
        precioCalculadoFalso = (TextViewTypeface) cabeceraCompraFalsa.findViewById(R.id.text_precio_calculado);
        comprarFalso = (LinearLayout) cabeceraCompraFalsa.findViewById(R.id.contenedor_comprar);

        cabeceraCompra = findViewById(R.id.cabecera_compra_producto);
        precioUnidad = (TextViewTypeface) cabeceraCompra.findViewById(R.id.texto_precio_unidad);
        precioKilo = (TextViewTypeface) cabeceraCompra.findViewById(R.id.texto_precio_kilo);
        contenedorSeleccionarUnidades = (LinearLayout) cabeceraCompra.findViewById(R.id.contenedor_seleccionar_unidades);
        textoUnidades = (TextViewTypeface) cabeceraCompra.findViewById(R.id.text_unidades);
        precioCalculado = (TextViewTypeface) cabeceraCompra.findViewById(R.id.text_precio_calculado);
        comprar = (LinearLayout) cabeceraCompra.findViewById(R.id.contenedor_comprar);

        nombre = (TextViewTypeface) findViewById(R.id.text_nombre_articulo);
        numProducto = (TextViewTypeface) findViewById(R.id.text_num_producto);
        fechaProducto = (TextViewTypeface) findViewById(R.id.fecha_producto);
        viewLeerMas = (ViewLeerMas) findViewById(R.id.view_leer_mas);

        valoracion = (Valoracion) findViewById(R.id.valoracion);
        imagenPieFavorito = (ImageView) findViewById(R.id.imagen_favorito);
        imagenPieCompartir = (ImageView) findViewById(R.id.imagen_compartir);
    }

    private void setData() {
        if (actionBar != null) {
            actionBar.setTitle(articulo.getNombre());
            actionBar.setDisplayHomeAsUpEnabled(true);

            if (Config.PCI) {
                actionBar.setSubtitle(getString(R.string.conectado_a_pci));
            }

//            fondoActionBar = new ColorDrawable(getResources().getColor(R.color.color_primary));
//            actionBar.setBackgroundDrawable(fondoActionBar);
//            fondoActionBar.setAlpha(0);
        }

        String urlImagen = articulo.getUrlImagenPrincipal();
        if (urlImagen != null && urlImagen.length() > 0) {
            int resize = (int) getResources().getDimension(R.dimen.alto_imagen_cabecera);

            Picasso.with(this)
                    .load(urlImagen)
                    .resize(Utilidades.screenWidth(this), resize)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .into(imagenCabecera, new Callback() {

                        @Override
                        public void onSuccess() {
                            if (imagenBitmap == null) {
                                imagenBitmap = ((BitmapDrawable) imagenCabecera.getDrawable()).getBitmap();

                                imagenBitmap = JavaBlurProcess.blur(imagenBitmap, 100f);
                                imagenCabeceraBlur.setImageBitmap(imagenBitmap);
                            }
                        }

                        @Override public void onError() { }
                    });
        }

        String precioAntesAux = articulo.getPrecioAntesTratado();
        if (precioAntesAux != null) {
            precioAntes.setText(precioAntesAux + " " + getString(R.string.unidad));
        } else {
            precioAntes.setVisibility(View.GONE);
        }

        precioUnidadFalso.setText(articulo.getPrecioUnidadTratado() + " " + getString(R.string.unidad));
        precioUnidad.setText(articulo.getPrecioUnidadTratado() + " " + getString(R.string.unidad));

        if (articulo.getPrecioKilo() > 0) {
            precioKiloFalso.setText(articulo.getPrecioKiloTratado() + " " + getString(R.string.unidad_medida));
            precioKilo.setText(articulo.getPrecioKiloTratado() + " " + getString(R.string.unidad_medida));
        } else {
            precioKiloFalso.setVisibility(View.INVISIBLE);
            precioKilo.setVisibility(View.INVISIBLE);
        }

        fechaProducto.setText(articulo.getFecha());

        setPrecioCalculado();

        nombre.setText(articulo.getNombre());
        numProducto.setText(articulo.getNumProducto() + "");
        viewLeerMas.setTexto(articulo.getDescripcion());

        ViewProductosComplementarios viewProductosComplementarios = (ViewProductosComplementarios) findViewById(R.id.view_productos_complementarios);
        viewProductosComplementarios.setActivity(this);
        viewProductosComplementarios.cargarDatos();

        ViewProductosRelacionados viewProductosRelacionados = (ViewProductosRelacionados) findViewById(R.id.view_productos_relacionados);
        viewProductosRelacionados.setActivity(this);
        viewProductosRelacionados.cargarDatos();
    }

    private void setListener() {
        final int tamanioImagenCabecera = (int) getResources().getDimension(R.dimen.alto_imagen_cabecera);
        scroll.setOnScrollViewListener(new CustomScrollView.OnScrollViewListener() {

            @Override
            public void onScrollChanged(CustomScrollView v, int l, int t, int oldl, int oldt) {
                imagenCabecera.setTranslationY(-(t / 3));
                imagenCabeceraBlur.setTranslationY(-(t / 3));
                imagenCabeceraBlur.setAlpha((t / (float) tamanioImagenCabecera) * 2);

                precioAntes.setTranslationY(-(t / 2));

                if (fondoActionBar != null) {
                    fondoActionBar.setAlpha(Utilidades.getAlphaForView(v.getScrollY()));
                }

                if (t >= tamanioImagenCabecera) {
                    cabeceraCompraFalsa.setVisibility(View.VISIBLE);
                    cabeceraCompra.setVisibility(View.INVISIBLE);
                } else {
                    cabeceraCompraFalsa.setVisibility(View.GONE);
                    cabeceraCompra.setVisibility(View.VISIBLE);
                }
            }
        });

        if (articulo.getStock() > 0) {
            contenedorSeleccionarUnidadesFalso.setOnClickListener(onClickUnidades);
            contenedorSeleccionarUnidades.setOnClickListener(onClickUnidades);
        } else {
            textoUnidadesFalso.setVisibility(View.GONE);
            textoUnidades.setVisibility(View.GONE);
        }

        comprarFalso.setOnClickListener(onClickComprar);
        comprar.setOnClickListener(onClickComprar);

        findViewById(R.id.contenedor_datos_vendedor).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.text_mas_del_vendedor).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.text_terminos_de_venta).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });

        final ButtonTypeface botonSeguirVendedor = (ButtonTypeface) findViewById(R.id.button_seguir_vendedor);
        botonSeguirVendedor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                botonSeguirVendedor.setBackgroundResource(siguiendoVendedor ? R.drawable.fondo_redondo_blanco_grisclaro : R.drawable.fondo_redondo_colorprimary);
                botonSeguirVendedor.setTextColor(getResources().getColor(siguiendoVendedor ? R.color.gris_claro : R.color.blanco_perfecto));
                siguiendoVendedor = !siguiendoVendedor;
            }
        });

        final ButtonTypeface botonSeguirProducto = (ButtonTypeface) findViewById(R.id.button_seguir_producto);
        botonSeguirProducto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                botonSeguirProducto.setBackgroundResource(siguiendoProducto ? R.drawable.fondo_redondo_blanco_grisclaro : R.drawable.fondo_redondo_colorprimary);
                botonSeguirProducto.setTextColor(getResources().getColor(siguiendoProducto ? R.color.gris_claro : R.color.blanco_perfecto));
                siguiendoProducto = !siguiendoProducto;
            }
        });

        imagenPieFavorito.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                imagenPieFavorito.setImageResource(favorito ? R.drawable.ic_no_favorito : R.drawable.ic_favorito);
                favorito = !favorito;
            }
        });

        imagenPieCompartir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Utilidades.compartir(ActivityVerArticulo.this, articulo.getNombre(), articulo.getDescripcion());
            }
        });
    }

    private void setPrecioCalculado() {
        textoUnidadesFalso.setText(unidades + " unidades");
        textoUnidades.setText(unidades + " unidades");

        precioCalculadoFalso.setText(Utilidades.tratarPrecio(articulo.getPrecioUnidad() * unidades) + " " + getString(R.string.unidad));
        precioCalculado.setText(Utilidades.tratarPrecio(articulo.getPrecioUnidad() * unidades) + " " + getString(R.string.unidad));
    }

    private void showNumberPickerDialog() {
        RelativeLayout linearLayout = new RelativeLayout(this);
        final NumberPicker aNumberPicker = new NumberPicker(this);
        aNumberPicker.setMaxValue(articulo.getStock());
        aNumberPicker.setMinValue(1);
        aNumberPicker.setValue(unidades);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
        RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        linearLayout.setLayoutParams(params);
        linearLayout.addView(aNumberPicker, numPicerParams);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.seleccione_unidades);
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                unidades = aNumberPicker.getValue();
                setPrecioCalculado();
            }
        });

        alertDialogBuilder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ver_producto, menu);

        final MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Esto es llamado cuando se le da a buscar.
                searchView.setIconified(true);
                searchView.clearFocus();
                searchItem.collapseActionView();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Esto es llamado cada vez que se cambia el texto de busqueda.
                return false;
            }
        });

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
        if (drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawers();
            return;
        }

        super.onBackPressed();
    }
}
