package com.insumoskeij.appaksu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView TxtMotor,TxtKwPotencia, TxtDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TxtMotor = findViewById(R.id.txtMotor);
        TxtKwPotencia=  findViewById(R.id.txtKwpotencia);
        TxtDetalle = findViewById(R.id.txtDetalles);

        //GET INTENT
        Intent i=this.getIntent();

        //RECEIVE DATA
        String name=i.getExtras().getString("MOTOR");
        String email=i.getExtras().getString("Kw");
        String username=i.getExtras().getString("DETALLE");

        //BIND DATA
        TxtMotor.setText(name);
        TxtKwPotencia.setText(email);
        TxtDetalle.setText(username);
    }
}
