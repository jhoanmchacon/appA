package com.insumoskeij.appaksu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class DetalleActivity extends AppCompatActivity {

    TextView txtMotor, txtMarca, txtModelo, txtAnno,
            txtTipoProd, txtCodProd, txtCodProd_2,
            txtOMarcas, tOMarcas,
            tCodBarra, txtDetalleCodBarra,
            txtDetalleMedida, tDetalleMedida,
            txtDetallePeso, tDetallePeso;
    ImageView imgProd;
    RequestQueue request;
    Context context;
    String rutaImgProd, tprod, cprod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        imgProd = findViewById(R.id.imgProd);
        txtMarca = findViewById(R.id.txtMarca);
        txtModelo = findViewById(R.id.txtModelo);
        txtAnno = findViewById(R.id.txtAnno);
        txtTipoProd = findViewById(R.id.txtTipoProd);
        txtCodProd = findViewById(R.id.txtCodProd);
        txtCodProd_2 = findViewById(R.id.txtNombreProd_2);
        txtMotor = findViewById(R.id.txtMotor);
        txtOMarcas = findViewById(R.id.txtDetalles);
        tOMarcas = findViewById(R.id.tOMarcas);
        txtDetalleCodBarra = findViewById(R.id.txtDetallesCodBarra);
        tCodBarra = findViewById(R.id.tCodBarra);
        txtDetalleMedida = findViewById(R.id.txtDetallesMedida);
        tDetalleMedida = findViewById(R.id.tMedida);
        txtDetallePeso = findViewById(R.id.txtDetallesPeso);
        tDetallePeso = findViewById(R.id.tPeso);


        request = Volley.newRequestQueue(getApplicationContext());


        //GET INTENT
        Intent i = this.getIntent();

        //RECEIVE DATA

        String marca = i.getExtras().getString("MARCA");
        String modelo = i.getExtras().getString("MODELO");
        String anno = i.getExtras().getString("ANNO");
        String motor = i.getExtras().getString("MOTOR");
        String detalle = i.getExtras().getString("DETALLE");
        String detalleCodBarra = i.getExtras().getString("DETALLECODBARRA");
        String detalleMedida = i.getExtras().getString("DETALLEMEDIDA");
        String detallePeso = i.getExtras().getString("DETALLEPESO");
        tprod = i.getExtras().getString("TPROD");
        cprod = i.getExtras().getString("CPROD");
        rutaImgProd = i.getExtras().getString("RutaImgProd");

        if (!motor.equals("-")) {
            txtMotor.setVisibility(View.VISIBLE);
        }

        if (!anno.equals("0-0")) {
            txtAnno.setVisibility(View.VISIBLE);
        }

        if (detalle.trim().length() > 0) {
            tOMarcas.setVisibility(View.VISIBLE);
            txtOMarcas.setVisibility(View.VISIBLE);
        }

        if (detalleCodBarra.trim().length() > 0) {
            tCodBarra.setVisibility(View.VISIBLE);
            txtDetalleCodBarra.setVisibility(View.VISIBLE);
        }

        if (detalleMedida.trim().length() > 0) {
            tDetalleMedida.setVisibility(View.VISIBLE);
            txtDetalleMedida.setVisibility(View.VISIBLE);
        }

        if (detallePeso.trim().length() > 0) {
            tDetallePeso.setVisibility(View.VISIBLE);
            txtDetallePeso.setVisibility(View.VISIBLE);
        }


        //BIND DATA
        txtMotor.setText(motor);
        txtOMarcas.setText(Html.fromHtml(detalle));
        txtDetalleCodBarra.setText(detalleCodBarra);
        txtDetalleMedida.setText(detalleMedida);
        txtDetallePeso.setText(detallePeso);
        txtMarca.setText(marca);
        txtModelo.setText(modelo);
        txtAnno.setText(anno);
        txtCodProd.setText(cprod);
        txtCodProd_2.setText(cprod);
        txtTipoProd.setText(tprod);


        ImageRequest imageRequest = new ImageRequest(rutaImgProd,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imgProd.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.add(imageRequest);

        imgProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDetailActivity(v);


            }
        });


    }

    private void openDetailActivity(View view) {

        //Toast.makeText(this,"sssssssssss "+ rutaImgProd, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(DetalleActivity.this, ImagenDetalle.class);
        //System.out.println("qqq2 "+rutaImgProd);
        intent.putExtra("CPROD", cprod);
        intent.putExtra("TPROD", tprod);
        intent.putExtra("IMG", rutaImgProd);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptionsCompat activityOptions =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            this,
                            new Pair<View, String>(view.findViewById(R.id.imgProd),
                                    ImagenDetalle.VIEW_NAME_HEADER_IMAGE)
                    );

            ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
        } else
            startActivity(intent);


    }
}
