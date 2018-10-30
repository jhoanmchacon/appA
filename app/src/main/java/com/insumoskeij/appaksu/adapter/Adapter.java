package com.insumoskeij.appaksu.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.insumoskeij.appaksu.DetalleActivity;
import com.insumoskeij.appaksu.R;
import com.insumoskeij.appaksu.model.Producto;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Adapter extends BaseAdapter  {
    private Activity activity;
    //Context context;
    private LayoutInflater inflater;
    private List<Producto> item;

    public Adapter(Activity activity, List<Producto> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item,parent, false);


        TextView txt_marca =  convertView.findViewById(R.id.txtMarca);
        final TextView txt_modelo =  convertView.findViewById(R.id.txtModelo);
        TextView txt_anno =  convertView.findViewById(R.id.txtAnno);
        TextView txt_tipoProd =  convertView.findViewById(R.id.txtTipoProd);
        TextView txt_nombreProd =  convertView.findViewById(R.id.txtNombreProd);
        ImageView imgProd =  convertView.findViewById(R.id.imgProd);

        txt_marca.setText(item.get(position).getTxtMarca());
        txt_modelo.setText(item.get(position).getTxtModelo());
        txt_anno.setText(item.get(position).getTxtAnno());
        txt_tipoProd.setText(item.get(position).getTxtTipoProd());
        txt_nombreProd.setText(item.get(position).getTxtCodigoProd());
        Picasso.with(this.activity).load(item.get(position).getRutaImg())
                .error(R.drawable.img_base)
                .fit()
                .centerInside()
                .into(imgProd);

        if (!(item.get(position).getTxtAnno()).equals("0-0")){
            txt_anno.setVisibility(View.VISIBLE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailActivity(position);

            }
        });


        return convertView;
    }

    private  void openDetailActivity (int position){



        //Toast.makeText(context,"sssssssssss",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(activity, DetalleActivity.class);
        intent.putExtra("MOTOR",(item.get(position).getTxtMotor()));
        intent.putExtra("DETALLE",(item.get(position).getTxtDetalle()));
        intent.putExtra("DETALLECODBARRA",(item.get(position).getTxtDetalleCodBarra()));
        intent.putExtra("DETALLEMEDIDA",(item.get(position).getTxtDetalleMedida()));
        intent.putExtra("DETALLEPESO",(item.get(position).getTxtDetallePeso()));
        intent.putExtra("RutaImgProd",(item.get(position).getRutaImg_2()));
        intent.putExtra("MARCA",item.get(position).getTxtMarca());
        intent.putExtra("MODELO",item.get(position).getTxtModelo());
        intent.putExtra("ANNO",item.get(position).getTxtAnno());
        intent.putExtra("TPROD",item.get(position).getTxtTipoProd());
        intent.putExtra("CPROD",item.get(position).getTxtCodigoProd());


        activity.startActivity(intent);
    }
}