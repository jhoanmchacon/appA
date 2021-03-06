package com.insumoskeij.appaksu.adapter;



import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.insumoskeij.appaksu.R;
import com.insumoskeij.appaksu.model.Producto;

import java.util.List;

public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.CatalogoHolder> implements View.OnClickListener {

    List<Producto> productoList;
    private View.OnClickListener listener;
    RequestQueue request;
    Context context;

    public CatalogoAdapter(List<Producto> productoList, Context context){
        this.productoList = productoList;
        this.context=context;
        request= Volley.newRequestQueue(context);
    }


    @Override
    public CatalogoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalogo_list,parent,false);
        RecyclerView.LayoutParams layoutParams =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        vista.setOnClickListener(this);
        return new CatalogoHolder(vista);
    }

    public  void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;

    }

    @Override
    public void onBindViewHolder(CatalogoHolder holder, int position) {
        holder.txtMarca.setText(productoList.get(position).getTxtMarca().toString());
        holder.txtModelo.setText(productoList.get(position).getTxtModelo().toString());
        holder.txtAnno.setText(productoList.get(position).getTxtAnno().toString());
        holder.txtFiltro.setText(productoList.get(position).getTxtCodigoProd().toString());
        holder.txtTipoProd.setText(productoList.get(position).getTxtTipoProd().toString());
        if (productoList.get(position).getRutaImg()!=null){
            cargarImagenWebService(productoList.get(position).getRutaImg(),holder);
        }else {
            holder.imgProd.setImageResource(R.drawable.img_base);
        }


    }

    private void cargarImagenWebService(String rutaimg, final CatalogoHolder holder) {
        String urlImagen = rutaimg;
        urlImagen = urlImagen.replace(" ", "%20");
        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.imgProd.setImageBitmap(response);

            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "error al cargar imagen ", Toast.LENGTH_SHORT).show();
                holder.imgProd.setImageResource(R.drawable.img_base);
            }
        });
        request.add(imageRequest);
    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    @Override
    public void onClick(View view) {
    if (listener!=null){
        listener.onClick(view);
    }
    }


    public class CatalogoHolder extends RecyclerView.ViewHolder {

        TextView txtMarca, txtModelo, txtAnno,txtFiltro, txtTipoProd;
        ImageView imgProd;

        public CatalogoHolder(View itemView) {
            super(itemView);
            txtMarca=itemView.findViewById(R.id.txtMarca);
            txtModelo=itemView.findViewById(R.id.txtModelo);
            txtAnno=itemView.findViewById(R.id.txtAnno);
            txtFiltro= itemView.findViewById(R.id.txtNombreProd);
            txtTipoProd=itemView.findViewById(R.id.txtTipoProd);
            imgProd=itemView.findViewById(R.id.imgProd);

        }
    }
}
