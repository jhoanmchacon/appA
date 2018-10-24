package com.insumoskeij.appaksu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class DetailActivity extends AppCompatActivity {

    TextView TxtMotor,TxtKwPotencia, TxtDetalle,txtMarca,txtModelo,txtAnno,txtTipoProd,txtCodProd;
    ImageView ImgProd;
    RequestQueue request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImgProd = findViewById(R.id.imgProd);
        txtMarca = findViewById(R.id.txtMarca);
        txtModelo = findViewById(R.id.txtModelo);
        txtAnno =  findViewById(R.id.txtAnno);
        txtTipoProd = findViewById(R.id.txtTipoProd);
        txtCodProd = findViewById(R.id.txtCodProd);
        TxtMotor = findViewById(R.id.txtMotor);
        //TxtKwPotencia=  findViewById(R.id.txtKwPotencia);
        TxtDetalle = findViewById(R.id.txtDetalles);


        request = Volley.newRequestQueue(getApplicationContext());


        //GET INTENT
        Intent i=this.getIntent();

        //RECEIVE DATA

        String marca=i.getExtras().getString("MARCA");
        String modelo=i.getExtras().getString("MODELO");
        String anno=i.getExtras().getString("ANNO");
        String tprod=i.getExtras().getString("TPROD");
        String cprod=i.getExtras().getString("CPROD");
        String motor=i.getExtras().getString("MOTOR");
        //String kw=i.getExtras().getString("Kw");
        String detalle=i.getExtras().getString("DETALLE");
        String rutaImgProd=i.getExtras().getString("RutaImgProd");
        System.out.println("imagennn" + rutaImgProd);



        //BIND DATA
        TxtMotor.setText(motor);
        //TxtKwPotencia.setText(kw);
        TxtDetalle.setText(detalle);
        txtMarca.setText(marca);
        txtModelo.setText(modelo);
        txtAnno.setText(anno);
        txtCodProd.setText(cprod);
        txtTipoProd.setText(tprod);


        ImageRequest imageRequest= new ImageRequest(rutaImgProd,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ImgProd.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.add(imageRequest);



    }
}
