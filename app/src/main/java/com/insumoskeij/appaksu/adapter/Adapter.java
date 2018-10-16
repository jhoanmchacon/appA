package com.insumoskeij.appaksu.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.insumoskeij.appaksu.R;
import com.insumoskeij.appaksu.model.Catalogo;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Adapter extends BaseAdapter {
    //private Activity activity;
    Context context;
    private LayoutInflater inflater;
    private List<Catalogo> item;
    RequestQueue request;

    public Adapter(Context context, List<Catalogo> item) {
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
        TextView txt_modelo =  convertView.findViewById(R.id.txtModelo);
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

        return convertView;
    }


}