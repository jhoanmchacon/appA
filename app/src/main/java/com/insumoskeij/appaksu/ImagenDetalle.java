package com.insumoskeij.appaksu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.insumoskeij.appaksu.model.Producto;
import com.squareup.picasso.Picasso;

public class ImagenDetalle extends AppCompatActivity {


    private ImageView imagenExtendida;
    //GET INTENT




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_detalle);
        Intent i = this.getIntent();
        //usarToolbar();

        // Obtener el coche con el identificador establecido en la actividad principal
        //itemDetallado = Producto.getItem(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));


        String rutaImgProd = i.getExtras().getString("IMG");
        imagenExtendida =  findViewById(R.id.imagen_extendida);

         System.out.println("qqq "+rutaImgProd);
        Picasso.with(this).load(rutaImgProd)
                .error(R.drawable.img_base)
                .fit()
                .centerInside()
                .into(imagenExtendida);
    }

    private void cargarImagenExtendida() {

    }

    private void usarToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}