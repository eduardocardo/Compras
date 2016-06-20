package com.proconsi.leondecompras.actividades;


import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.comunicaciones.objetos.Vendedor;
import com.proconsi.leondecompras.views.MenuLateral;

public class ActivityMap extends AppCompatActivity implements OnMapReadyCallback {

    private ActionBar actionBar;
  private DrawerLayout drawerLayout;
    private SupportMapFragment map;
    private GoogleMap mapa;
    private double latitud;
    private  double longitud;
    private Vendedor vendedor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_map);
       drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ((MenuLateral) findViewById(R.id.menu_lateral)).setDrawerLayout(drawerLayout);
        vendedor =(Vendedor)getIntent().getSerializableExtra("vendedor");
        //se obtiene la latitud y longitud de la posicion del vendedor
        latitud = vendedor.getLatitud();
        longitud= vendedor.getLongitud();
        map = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);

        map.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
       actionBar = getSupportActionBar();

        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng latLng = new LatLng(latitud, longitud);
       mapa.addMarker(new MarkerOptions().position(latLng). //se añade la nueva marca
                icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                .title(vendedor.getNombre()));
       mapa.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latitud, longitud)));

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
        if (drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawers();
            return;
        }

        super.onBackPressed();
    }
}
