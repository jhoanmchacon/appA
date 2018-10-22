package com.insumoskeij.appaksu.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.insumoskeij.appaksu.DetailActivity;
import com.insumoskeij.appaksu.R;
import com.insumoskeij.appaksu.model.Producto;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Adapter extends BaseAdapter  {
    //private Activity activity;
    Context context;
    private LayoutInflater inflater;
    private List<Producto> item;
    RequestQueue request;

    public Adapter(Context context, List<Producto> item) {
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {



        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        Picasso.with(this.context).load(item.get(position).getRutaImg())
                .error(R.drawable.img_base)
                .fit()
                .centerInside()
                .into(imgProd);

        final Producto producto = (Producto) this.getItem(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailActivity();

            }
        });


        return convertView;
    }

    private  void openDetailActivity (){



        Toast.makeText(context,"sssssssssss",Toast.LENGTH_SHORT).show();

       /* Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("MOTOR",)*/
    }
}