package com.insumoskeij.appaksu.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.insumoskeij.appaksu.R;
import com.insumoskeij.appaksu.ServiceHandler;
import com.insumoskeij.appaksu.adapter.CatalogoAdapter;
import com.insumoskeij.appaksu.interfaces.IcomunicaFragments;
import com.insumoskeij.appaksu.model.Catalogo;
import com.insumoskeij.appaksu.model.Marca;
import com.insumoskeij.appaksu.model.Modelo;
import com.insumoskeij.appaksu.model.Motor;
import com.insumoskeij.appaksu.model.TipoProducto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.insumoskeij.appaksu.R.layout.fragment_form_busqueda;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FormBusquedaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FormBusquedaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormBusquedaFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private OnFragmentInteractionListener mListener;

        RecyclerView recyclerCatalogo;
        ArrayList<Catalogo> catalogoList;
        EditText campoBusqueda;
        Button btnBuscar;
        private String buscar;

        Activity activity;
        IcomunicaFragments interfaceComunicaFragments;

       /****Variables pais/idioma**/

        private String idPais;
        private String idIdioma;


    /*****Combo TIPO PRODUCTO****/
        private Spinner spTipoProducto;
        private ArrayList<TipoProducto> TprodList;
        private String txtAgregarTprod="";

       /*****Combo MARCA****/
        private Spinner spMarca;
        private String txtAgregarMarca="";
        private ArrayList<Marca> MarcaList;

        /*****Combo MODELO****/
        private Spinner spModelo;
        private String txtAgregarModelo="";
        private  ArrayList<Modelo> ModeloList;

        /*****Combo MOTOR****/
        private Spinner spMotor;
        private String txtAgregarMotor="";
        private  ArrayList<Motor> MotorList;

        ProgressDialog progreso;
        RequestQueue request;
        JsonObjectRequest jsonObjectRequest;

    public FormBusquedaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormBusquedaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormBusquedaFragment newInstance(String param1, String param2) {
        FormBusquedaFragment fragment = new FormBusquedaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(fragment_form_busqueda, container, false);

        catalogoList=new ArrayList<>();
        /*****Cargar valor pais/idioma******/

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        idPais=preferences.getString("idPais","");
        idIdioma=preferences.getString("idIdioma","");


        /*****Combo Tipo Producto******/
        spTipoProducto = vista.findViewById(R.id.spTipoProducto);
        TprodList = new ArrayList<TipoProducto>();

        spTipoProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtAgregarTprod = TprodList.get(i).getIdTipoProducto();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        new GetTproducto().execute();

        /*****Combo Marca******/
        spMarca = vista.findViewById(R.id.spMarca);
        MarcaList = new ArrayList<Marca>();

        spMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtAgregarMarca = MarcaList.get(i).getId_marca();
                ModeloList.clear();
                MotorList.clear();
                if (!txtAgregarMarca.isEmpty()){
                new GetModelo().execute();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        new GetMarca().execute();

        /****COMBO MODELO***/
        spModelo = vista.findViewById(R.id.spModelo);
        ModeloList = new ArrayList<Modelo>();

        spModelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtAgregarModelo=ModeloList.get(i).getId_modelo();
                MotorList.clear();
                if (!txtAgregarModelo.isEmpty()){
                new GetMotor().execute();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /****COMBO MOTOR***/
        spMotor = vista.findViewById(R.id.spMotor);
        MotorList = new ArrayList<Motor>();

        spMotor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtAgregarMotor = MotorList.get(i).getId_motor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        recyclerCatalogo =  vista.findViewById(R.id.idRecycler);
        recyclerCatalogo.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerCatalogo.setHasFixedSize(true);


        btnBuscar =  vista.findViewById(R.id.btnBuscar);
        request= Volley.newRequestQueue(getContext());


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cargarWebService();
            }
        });

        return vista;
    }

 /**************CONSULTA DE PRODUCTOS****************/

 private void cargarWebService() {

     progreso = new ProgressDialog(getContext());
     progreso.setMessage("Consultando...");
     progreso.show();

     buscar=campoBusqueda.getText().toString();

     //String url ="http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlBusqueda.php?&pais="+idPais+"&buscar="+campoBusqueda.getText().toString();
     String url ="http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlBusqueda.php?opc="+idIdioma+"&pais="+idPais+"&keyword="+buscar+
             "&tipoProducto="+ txtAgregarTprod+"&marca="+txtAgregarMarca+"&modelo="+txtAgregarModelo+"&motor="+txtAgregarMotor;
     System.out.println("urlll "+url);
     url=url.replace(" ","%20");

     jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
     request.add(jsonObjectRequest);
 }




    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"error al consultar"+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {
        Catalogo catalogo=null;

        JSONArray json= response.optJSONArray("data");
        try {
            for (int i =0;i<json.length();i++) {
                catalogo= new Catalogo();
                JSONObject jsonObject=null;
                jsonObject = json.getJSONObject(i);

                catalogo.setTxtMarca(jsonObject.optString("d_marca"));
                catalogo.setTxtserie(jsonObject.optString("d_serie"));
                catalogo.setTxtModelo(jsonObject.optString("d_modelo"));
                catalogo.setTxtAnno(jsonObject.optString("d_desde_hasta"));
                //System.out.println("xxxxx "+catalogo.getNombreVeh());
                catalogo.setTxtCodigoProd(jsonObject.optString("d_codigo"));
                // System.out.println(catalogo.getNombreFiltro());
                catalogo.setTxtTipoProd(jsonObject.optString("d_tipo_prod"));
                catalogo.setTxtDetalle(jsonObject.optString("d_detalles"));
                // System.out.println(catalogo.getTipoProd());
                catalogo.setRutaImg((jsonObject.optString("d_imagen")));
                //System.out.println ("image"+catalogo.getRutaimg());
                catalogoList.add(catalogo);

            }
            progreso.hide();
            CatalogoAdapter adapter=new CatalogoAdapter(catalogoList, getContext());
            recyclerCatalogo.setAdapter(adapter);

            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"selecciono: "+catalogoList.get(recyclerCatalogo.getChildAdapterPosition(view)).
                            getTxtCodigoProd(),Toast.LENGTH_SHORT).show();

                    //interfaceComunicaFragments.enviarProducto(catalogoList.get(recyclerCatalogo.getChildAdapterPosition(view)));
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            progreso.hide();
            Toast.makeText(getContext(),"error de conexion "+response,Toast.LENGTH_SHORT).show();
            progreso.hide();
        }

    }


    /*********Fin CONSULTA***********/



/*************CARGANDO COMBO TIPO PRODUCTO**************************************************************************************/
    private void cargarTproducto() {
            List<String> lables = new ArrayList<String>();
            //txtAgregarTprod.setText("");
            for (int i = 0; i < TprodList.size(); i++) {
                lables.add(TprodList.get(i).getTipoProduto());
            }
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, lables);
            spinnerAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTipoProducto.setAdapter(spinnerAdapter);
        }
    private class GetTproducto extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  progreso = new ProgressDialog(getContext());
            progreso.setMessage("Cargando compbos..");
            progreso.setCancelable(false);
            progreso.show();*/
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            //String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMTipoProducto.php?opc="+idIdioma, ServiceHandler.GET);
            String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMTipoProducto.php?opc=1", ServiceHandler.GET);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray frutas = jsonObj.getJSONArray("data");

                        for (int i = 0; i < frutas.length(); i++) {
                            JSONObject catObj = (JSONObject) frutas.get(i);
                            //System.out.println(catObj);
                            TipoProducto cat = new TipoProducto(catObj.getString("id"), catObj.getString("desc"));

                            TprodList.add(cat);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            /*if (progreso.isShowing())
                progreso.dismiss();*/
            cargarTproducto();
        }
    }
/**************FIN CARGA DE COMBO TIPO PRODUCTO***********************************************************************************/

    /*************CARGANDO COMBO MARCA**************************************************************************************/
    private void cargarComboMarca() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < MarcaList.size(); i++) {
            lables.add(MarcaList.get(i).getNombre_marca());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(spinnerAdapter);
    }
    private class GetMarca extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  progreso = new ProgressDialog(getContext());
            progreso.setMessage("Cargando compbos..");
            progreso.setCancelable(false);
            progreso.show();*/
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            //String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMMarca.php?opc="+idIdioma+"&pais="+idPais+"", ServiceHandler.GET);
            String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMMarca.php?opc=1&pais=1", ServiceHandler.GET);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray frutas = jsonObj.getJSONArray("data");

                        for (int i = 0; i < frutas.length(); i++) {
                            JSONObject catObj = (JSONObject) frutas.get(i);
                            //System.out.println(catObj);
                            Marca cat = new Marca(catObj.getString("id"), catObj.getString("desc"));
                            //System.out.println(cat);
                            MarcaList.add(cat);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            /*if (progreso.isShowing())
                progreso.dismiss();*/
            cargarComboMarca();
        }
    }
    /**************FIN CARGA DE MARCA***********************************************************************************/

    /*************CARGANDO COMBO MODELO**************************************************************************************/
    private void cargarComboModelo() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < ModeloList.size(); i++) {
            lables.add(ModeloList.get(i).getNombre_modelo());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spModelo.setAdapter(spinnerAdapter);
    }
    private class GetModelo extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  progreso = new ProgressDialog(getContext());
            progreso.setMessage("Cargando compbos..");
            progreso.setCancelable(false);
            progreso.show();*/
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMModelo.php?opc="+idIdioma+"&marca="+txtAgregarMarca+"&serie=%22%22", ServiceHandler.GET);
            System.out.println("urlllll"+"http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMModelo.php?opc="+idIdioma+"&marca="+txtAgregarMarca+"&serie=%22%22");
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray frutas = jsonObj.getJSONArray("data");

                        for (int i = 0; i < frutas.length(); i++) {
                            JSONObject catObj = (JSONObject) frutas.get(i);
                            //System.out.println(catObj);
                            Modelo cat = new Modelo(catObj.getString("id"), catObj.getString("desc"));
                            //System.out.println(cat);
                            ModeloList.add(cat);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            /*if (progreso.isShowing())
                progreso.dismiss();*/
            cargarComboModelo();
        }
    }
    /**************FIN CARGA DE MODELO***********************************************************************************/

    /*************CARGANDO COMBO MOTOR**************************************************************************************/
    private void cargarComboMotor() {
        List<String> lables = new ArrayList<String>();
        for (int i = 0; i < MotorList.size(); i++) {
            lables.add(MotorList.get(i).getNombre_motor());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMotor.setAdapter(spinnerAdapter);
    }
    private class GetMotor extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  progreso = new ProgressDialog(getContext());
            progreso.setMessage("Cargando compbos..");
            progreso.setCancelable(false);
            progreso.show();*/
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall("http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlMMotor.php?opc="+idIdioma+"&modelo="+txtAgregarModelo, ServiceHandler.GET);
            Log.e("Response: ", "> " + json);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray frutas = jsonObj.getJSONArray("data");

                        for (int i = 0; i < frutas.length(); i++) {
                            JSONObject catObj = (JSONObject) frutas.get(i);
                            //System.out.println(catObj);
                            Motor cat = new Motor(catObj.getString("id"), catObj.getString("desc"));
                            //System.out.println(cat);
                            MotorList.add(cat);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            /*if (progreso.isShowing())
                progreso.dismiss();*/
            cargarComboMotor();
        }
    }
    /**************FIN CARGA DE MOTOR***********************************************************************************/



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.activity =(Activity) context;
            interfaceComunicaFragments=(IcomunicaFragments) this.activity;

        }

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
