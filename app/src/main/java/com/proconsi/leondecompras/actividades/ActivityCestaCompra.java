package com.proconsi.leondecompras.actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ExpandedMenuView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.proconsi.leondecompras.R;
import com.proconsi.leondecompras.adapters.CustomAdapterTarjetas;
import com.proconsi.leondecompras.comunicaciones.objetos.Articulo;
import com.proconsi.leondecompras.comunicaciones.objetos.Tarjeta;
import com.proconsi.leondecompras.comunicaciones.objetos.Vendedor;
import com.proconsi.leondecompras.utilidades.Config;

import com.proconsi.leondecompras.utilidades.Utilidades;
import com.proconsi.leondecompras.views.MenuLateral;
import com.proconsi.leondecompras.views.TextViewTypeface;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ActivityCestaCompra extends AppCompatActivity {

    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private ViewFlipper viewFlipper;
    private Button comprar,contactar,confirmarCompra,continuarComprando;
    public LinearLayout mapa;
    public LinearLayout seleccionarUnidades,layoutPaypal,layoutTarjeta,layoutTransferencia;
    public int actualView;
    private int unidades;
    private TextViewTypeface uni;
    private ImageView imagenProducto,imagenCookies,imagenCondicionesVenta,imagenPaypal,imagenTarjeta,imagenTransferencia;
    private TextView nombrePro, descripPro,nombreVendedor,cantidadPagar,precioCompra,totalPagar;
    private TextView nombreProductoComprado,descripProductoComprado,direccionVendedor;
    private Articulo articulo;
    private Vendedor vendedor;
    private float initY;
    //entorno de pruebas de paypal
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    private static final String CONFIG_CLIENT_ID = "AdpQHfqBlmeHK9ATvjCvRLaO3cDM4BeBHv96alq56NqiqubXh6eIJHa0VRKlP6GCnE5PVGS20aCfIx_k";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);

    private PayPalPayment thingToBuy;
    //indica el metodo de pago seleccionado por el usuario
    private int metodoPagoSeleccionado;
    private static final int PAGO_PAYPAL = 1;
    private static final int PAGO_TARJETA = 2;
    private static final int PAGO_TRANSFERENCIA = 3;


    private static int save = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta_compra);
        init();
        setData();
        setListener();
    }

    private void init() {
        actionBar = getSupportActionBar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ((MenuLateral) findViewById(R.id.menu_lateral)).setDrawerLayout(drawerLayout);
        viewFlipper = (ViewFlipper) findViewById(R.id.details);
        comprar=(Button)findViewById(R.id.bt_comprar);
        imagenProducto = (ImageView) findViewById(R.id.iv_producto);
        nombrePro = (TextView) findViewById(R.id.tv_nombreProducto);
        descripPro = (TextView) findViewById(R.id.tv_descripcionProducto);
        nombreVendedor=(TextView)findViewById(R.id.tv_nombreVendedor);
        mapa=(LinearLayout)findViewById(R.id.layout_mostraMapa);
        seleccionarUnidades=(LinearLayout)findViewById(R.id.contenedor_seleccionar_unidades);
        uni =(TextViewTypeface)findViewById(R.id.tvt_unidades);
        cantidadPagar=(TextView)findViewById(R.id.tv_cantidadPagar);
        precioCompra=(TextView)findViewById(R.id.tv_precioCompra);
        contactar =(Button)findViewById(R.id.bt_contactar);
        confirmarCompra=(Button)findViewById(R.id.bt_confirmarCompra);
        imagenCookies=(ImageView)findViewById(R.id.iv_politicaCookies);
        imagenCondicionesVenta=(ImageView)findViewById(R.id.iv_condicionesVenta);
        totalPagar =(TextView)findViewById(R.id.tv_totalPagar);
        nombreProductoComprado =(TextView)findViewById(R.id.tv_productoComprado);
        descripProductoComprado =(TextView)findViewById(R.id.tv_descripProdComprado);
        direccionVendedor =(TextView)findViewById(R.id.tv_direccionVendedor);
        layoutPaypal =(LinearLayout)findViewById(R.id.layout_paypal);
        layoutTarjeta =(LinearLayout)findViewById(R.id.layout_tarjetaCredito);
        layoutTransferencia =(LinearLayout)findViewById(R.id.layout_transferenciaBancaria);
        imagenPaypal =(ImageView)findViewById(R.id.iv_paypal);
        imagenTarjeta =(ImageView)findViewById(R.id.iv_tarjetaCredito);
        imagenTransferencia =(ImageView)findViewById(R.id.iv_transferenciaBancaria);
        continuarComprando =(Button)findViewById(R.id.bt_continuarCompra);
        articulo = new Articulo(5);  //articulo creado con datos obtenidos de forma aleatoria
        vendedor = new Vendedor("La cilla de Feito",555555555," Plaza San Martín, 1, 24003 León",42.5735672 ,-5.5671588); //se inicializa un vendedor con datos falsos

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);
    }



    private void setData() {
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.cestaCompra));
            actionBar.setDisplayHomeAsUpEnabled(true);

            if (Config.PCI) {
                actionBar.setSubtitle(getString(R.string.conectado_a_pci));
            }
        }

        actualView = 1; //se encuentra en el primer view
        unidades = 1;  //numero de unidades iniciales
        imagenProducto.setImageResource(R.drawable.gato);
        nombrePro.setText(articulo.getNombre());
        descripPro.setText("Esta muy rico");
        nombreVendedor.setText(vendedor.getNombre());
        nombreProductoComprado.setText(articulo.getNombre());
        //descripProductoComprado.setText(articulo.getDescripcion());
        descripProductoComprado.setText("Esta muy rico");
        direccionVendedor.setText(vendedor.getDireccion());
        imagenProducto.post(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) imagenProducto.getLayoutParams();

                params1.height = Utilidades.screenWidth(ActivityCestaCompra.this)/3;


                Log.d("TAGANCHOIMAGEN", String.valueOf(params1.height));
                imagenProducto.setLayoutParams(params1);
                imagenProducto.requestLayout();
            }
        });
        precioCompra.setText(articulo.getPrecioKiloTratado() + " " + Articulo.PRECIO_KILO);
        metodoPagoSeleccionado = PAGO_PAYPAL; //metodo de pago Paypal seleccioando por defecto
        setPrecioCalculado();




    }

    public void setListener()
    {
        //evento que lanza la activity del mapa con las coordenadas del vendedor
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityCestaCompra.this,ActivityMap.class);
                intent.putExtra("vendedor",vendedor);
                startActivity(intent);
            }
        });
        //evento que avanza a la pantalla resumen de compra
        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSiguientePantalla();
            }
        });
        //evento que confirma la venta
        confirmarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double precio =  (articulo.getPrecioKilo() * unidades);
                switch (metodoPagoSeleccionado)  //se comprueba el metodo de pago seleccionado
                {
                    case PAGO_PAYPAL :
                        thingToBuy = new PayPalPayment(new BigDecimal(String.valueOf(precio)), "EUR",
                                "HeadSet", PayPalPayment.PAYMENT_INTENT_SALE);
                        Intent intent = new Intent(ActivityCestaCompra.this,
                                PaymentActivity.class);

                        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

                        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                        break;
                    case PAGO_TARJETA :
                        showDialogo();
                        break;
                    case PAGO_TRANSFERENCIA :
                        mostrarSiguientePantalla();
                        break;
                }


            }
        });
        //evento que lanza una llamada de telefono al vendedor del producto
        contactar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero =String.valueOf(vendedor.getTelefono());
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + numero));
                startActivity(intent);
            }
        });
        //evento ontouch para modificar el numero de unidades a comprar
        seleccionarUnidades.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        initY = event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float finalY = event.getY();
                        float distance = initY - finalY; //distancia en pixeles recorrida al deslizar
                        if(distance < -20)
                        {
                            if(Integer.parseInt(uni.getText().toString()) > 1) {
                                uni.setText(String.valueOf((int) (Float.parseFloat(uni.getText().toString()) - 1)));

                            }
                        }
                        else if(distance > 20)
                        {
                            if(Integer.parseInt(uni.getText().toString()) != articulo.getStock()) {
                                uni.setText(String.valueOf((int) (Float.parseFloat(uni.getText().toString()) + 1)));
                            }
                        }
                        initY = finalY;
                        unidades = Integer.parseInt(uni.getText().toString());
                        setPrecioCalculado();
                        break;

                }

                return true;
            }
        });
        //evento que lanza un cuadro de diálogo que muestra la política de cookies
        imagenCookies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityCestaCompra.this);
                alertDialogBuilder.setTitle(R.string.politicaCookies)
                                   .setMessage("GALLETASS")
                                   .setCancelable(false)
                                   .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        });
        //evento que lanza un cuadro de diálogo que muestra las condiciones de venta
        imagenCondicionesVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityCestaCompra.this);
                alertDialogBuilder.setTitle(getResources().getString(R.string.codicionesVenta))
                        .setMessage("COMPRA COMPRA COMPRA")
                        .setCancelable(false)
                        .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //evento que marca checkbox de paypal y desmarca los otros dos
        layoutPaypal.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imagenPaypal.setImageResource(R.drawable.checkboxseleccionado);
            imagenTarjeta.setImageResource(R.drawable.checkboxnoseleccionado);
            imagenTransferencia.setImageResource(R.drawable.checkboxnoseleccionado);
            metodoPagoSeleccionado = PAGO_PAYPAL;
        }
    });
        //evento que marca checkbox de tarjeta credito y desmarca los otros dos
        layoutTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenPaypal.setImageResource(R.drawable.checkboxnoseleccionado);
                imagenTarjeta.setImageResource(R.drawable.checkboxseleccionado);
                imagenTransferencia.setImageResource(R.drawable.checkboxnoseleccionado);
                metodoPagoSeleccionado = PAGO_TARJETA;
            }
        });
        //evento que marca checkbox de transferencia bancaria y desmarca los otros dos
        layoutTransferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagenPaypal.setImageResource(R.drawable.checkboxnoseleccionado);
                imagenTarjeta.setImageResource(R.drawable.checkboxnoseleccionado);
                imagenTransferencia.setImageResource(R.drawable.checkboxseleccionado);
                metodoPagoSeleccionado = PAGO_TRANSFERENCIA;
            }
        });

        //evento que vuelve a la pantalla de portada
        continuarComprando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityCestaCompra.this,ActivityPortada.class);
                startActivity(intent);
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

        if(drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.RIGHT))
        {
            drawerLayout.closeDrawers();
            return;
        }
        else
        {
            //solo permite moverse a la pantalla anterior de la cesta de la compra cuando se encuentra en confirmar compra
            if(actualView == 2)
            {
                mostrarPantallaAnterior();
                return;
            }
        }

        super.onBackPressed();

    }


    /**
     * Metodo que muestra la siguiente pantalla incluida en el viewflipper
     */
    private void mostrarSiguientePantalla() {
        actualView++;
        viewFlipper.setInAnimation(ActivityCestaCompra.this, R.anim.push_right_in);
        viewFlipper.setOutAnimation(ActivityCestaCompra.this, R.anim.push_right_out);
        viewFlipper.showNext();
    }

    /**
     * Metodo que muestra la pantalla anterior incluida en el viewflipper
     */
    private void mostrarPantallaAnterior() {
        actualView--;
        viewFlipper.setInAnimation(ActivityCestaCompra.this, R.anim.push_left_in);
        viewFlipper.setOutAnimation(ActivityCestaCompra.this, R.anim.push_left_out);
        viewFlipper.showPrevious();
    }


    /**
     * Metodo que calcula el precio del artículo a comprar y muestra ese precio
     */
    public void setPrecioCalculado()
    {
        cantidadPagar.setText(Utilidades.tratarPrecio(articulo.getPrecioKilo() * unidades));
        comprar.setText(String.format(getResources().getString(R.string.botonComprar),cantidadPagar.getText().toString()));
        totalPagar.setText(cantidadPagar.getText().toString());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject()
                                .toString(4));
                        //tras obtener la confirmacion se avanza a la siguiente pantalla
                        mostrarSiguientePantalla();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject()
                                .toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(),
                                "Future Payment code received from PayPal",
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample",
                                "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void showDialogo()
    {
        final Dialog dialog = new Dialog(this);
        View view =getLayoutInflater().inflate(R.layout.custom_tarjetas_layout, null);


        final ListView lista =(ListView)view.findViewById(R.id.lv_listaTarjetas);

        Button aceptar =(Button)view.findViewById(R.id.bt_aceptarTarjeta);
        Button cancelar =(Button)view.findViewById(R.id.bt_cancelarTarjeta);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                ProgressDialog pagando = new ProgressDialog(ActivityCestaCompra.this);
                pagando.setCancelable(false);
                pagando.setMessage(getResources().getString(R.string.pagando));
                new MyTask(ActivityCestaCompra.this,pagando).execute();


            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //lista con tarjetas con datos falsos
        ArrayList<Tarjeta> tarjetas = new ArrayList<>();
        tarjetas.add(new Tarjeta("123456789012345"));
        tarjetas.add(new Tarjeta("123456789054321"));
        tarjetas.add(new Tarjeta("123456789015234"));
        tarjetas.add(new Tarjeta("123456789012345"));
        tarjetas.add(new Tarjeta("123456789012345"));
        tarjetas.add(new Tarjeta("123456789054321"));
        tarjetas.add(new Tarjeta("123456789015234"));
        tarjetas.add(new Tarjeta("123456789054321"));
        tarjetas.add(new Tarjeta("123456789015234"));

        CustomAdapterTarjetas adapterTarjetas = new CustomAdapterTarjetas(this, R.layout.list_row_tarjeta,tarjetas);

        lista.setAdapter(adapterTarjetas);



        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*ImageView image =(ImageView)view.findViewById(R.id.iv_imagenListTarjeta);
                image.setImageResource(R.drawable.tarjeta_blanca);
                TextViewTypeface tvt =(TextViewTypeface)view.findViewById(R.id.tv_numeroNoCodifi);
                TextViewTypeface tvt2 =(TextViewTypeface)view.findViewById(R.id.tv_numeroTarjeta);
                tvt.setTextColor(getResources().getColor(R.color.blanco_perfecto));
                tvt2.setTextColor(getResources().getColor(R.color.blanco_perfecto));
                view.setBackgroundColor(getResources().getColor(R.color.color_primary));*/
               // Tarjeta listItem = (Tarjeta) lista.getItemAtPosition(position);
                onListItemClick(lista,view,position,id);

               // Toast.makeText(ActivityCestaCompra.this, listItem.getNumero(), Toast.LENGTH_SHORT).show();


            }
        });

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();
    }

    /**
     * Clase que muestra un dialogo de progreso
     */
    public class MyTask extends AsyncTask<Void,Void,Void>{

        Activity activity;
        ProgressDialog progressDialog;

        public MyTask(Activity act,ProgressDialog pro)
        {
            activity = act;
            progressDialog = pro;
        }

        public void onPreExecute() {
            progressDialog.show();

        }

        public void onPostExecute(Void unused) {

            progressDialog.dismiss();
            mostrarSiguientePantalla();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }




    public void onListItemClick(ListView parent, View v, int position, long id) {

        parent.getChildAt(position).setBackgroundColor(Color.BLUE);

        if (save != -1 && save != position){
            parent.getChildAt(save).setBackgroundColor(Color.BLACK);
        }

        save = position;

    }
}
