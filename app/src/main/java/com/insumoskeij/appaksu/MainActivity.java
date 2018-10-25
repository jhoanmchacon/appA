package com.insumoskeij.appaksu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.insumoskeij.appaksu.adapter.Adapter;
import com.insumoskeij.appaksu.app.AppController;
import com.insumoskeij.appaksu.fragment.FormBusquedaFragment;
import com.insumoskeij.appaksu.interfaces.IcomunicaFragments;
import com.insumoskeij.appaksu.model.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener,
        IcomunicaFragments,
        FormBusquedaFragment.OnFragmentInteractionListener{

    ProgressDialog pDialog;
    List<Producto> listData = new ArrayList<Producto>();
    Adapter adapter;
    SwipeRefreshLayout swipe;
    ListView list_view;

    boolean fragmentSelecionado=false;

    public static final String url_data = "http://aksuglobal.com/catalogo_aksu/aksuapp/prueba/datos.php";
    public static final String url_cari = "http://aksuglobal.com/catalogo_aksu/aksuapp/controlador_app/controlBusqueda.php?opc=1&pais=1";

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String TAG_MARCA = "d_marca";
    public static final String TAG_MODELO = "d_modelo";
    public static final String TAG_MOTOR = "d_motor";
    public static final String TAG_KW = "d_kw_poten";
    public static final String TAG_ANNO = "d_desde_hasta";
    public static final String TAG_CODIGO = "d_codigo";
    public static final String TAG_TIPO_PROD = "d_tipo_prod";
    public static final String TAG_RUTA_IMG = "d_imagen";
    public static final String TAG_RUTA_IMG_2 = "d_imagen_2";
    public static final String TAG_DETALLES = "d_detalles";
    public static final String TAG_DETALLESCODBARRA = "d_detallesCodBarra";
    public static final String TAG_DETALLESMEDIDA = "d_detallesMedida";
    public static final String TAG_DETALLESPESO = "d_detallesPeso";
    public static final String TAG_RESULTS = "data";
    public static final String TAG_MESSAGE = "d_tipo_prod";
    public static final String TAG_VALUE = "value";


    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

       /* Fragment fragment= new FormBusquedaFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_main,fragment).commit();*/


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //swipe = findViewById(R.id.swipe_refresh);
        list_view = findViewById(R.id.list_view_2);

        adapter = new Adapter(this,listData);
        list_view.setAdapter(adapter);

        /*swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           callData();
                       }
                   }
        );*/

    }

    private void callData() {
        listData.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(url_data, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        Producto item = new Producto();

                        item.setTxtMarca(obj.getString(TAG_MARCA));
                        item.setTxtModelo(obj.getString(TAG_MODELO));
                        item.setTxtMotor(obj.getString(TAG_MOTOR));
                        item.setTxtKwPotencia(obj.getString(TAG_KW));
                        item.setTxtAnno(obj.getString(TAG_ANNO));
                        item.setTxtTipoProd(obj.getString(TAG_TIPO_PROD));
                        item.setTxtCodigoProd(obj.getString(TAG_CODIGO));
                        item.setRutaImg(obj.getString(TAG_RUTA_IMG));
                        item.setTxtDetalle(obj.getString(TAG_DETALLES));

                        listData.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifying list adapter about data changes
                // so that it renders the list view with updated data
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    @Override
    public void onRefresh() {
        //callData();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        cariData(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.Buscar));
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

   private void cariData(final String keyword) {
       if (fragmentSelecionado==true) {
           getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.content_main)).commit();
       }

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, url_cari, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    int value = jObj.getInt(TAG_VALUE);

                    if (value == 1) {
                        listData.clear();
                        adapter.notifyDataSetChanged();

                        String getObject = jObj.getString(TAG_RESULTS);
                        JSONArray jsonArray = new JSONArray(getObject);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Producto data = new Producto();

                            data.setTxtMarca(obj.getString(TAG_MARCA));
                            data.setTxtModelo(obj.getString(TAG_MODELO));
                            data.setTxtMotor(obj.getString(TAG_MOTOR));
                            data.setTxtKwPotencia(obj.getString(TAG_KW));
                            data.setTxtAnno(obj.getString(TAG_ANNO));
                            data.setTxtTipoProd(obj.getString(TAG_TIPO_PROD));
                            data.setTxtCodigoProd(obj.getString(TAG_CODIGO));
                            data.setRutaImg(obj.getString(TAG_RUTA_IMG));
                            data.setRutaImg_2(obj.getString(TAG_RUTA_IMG_2));
                            data.setTxtDetalle(obj.getString(TAG_DETALLES));
                            data.setTxtDetalleCodBarra(obj.getString(TAG_DETALLESCODBARRA));
                            data.setTxtDetalleMedida(obj.getString(TAG_DETALLESMEDIDA));
                            data.setTxtDetallePeso(obj.getString(TAG_DETALLESPESO));

                            listData.add(data);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("keyword", keyword);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment miFragment=null;


        if (id == R.id.nav_camera) {
            /*Intent i = new Intent(this, ItemListActivity.class);
            startActivity(i);*/

            listData.clear();
            adapter.notifyDataSetChanged();
            miFragment = new FormBusquedaFragment();
            fragmentSelecionado=true;

        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);

        } else if (id == R.id.Instagram) {
            Uri uri = Uri.parse("https://instagram.com/aksuglobal");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (id == R.id.Twitter) {
            Uri uri = Uri.parse("https://twitter.com/aksuglobal");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (id == R.id.Facebook) {
            Uri uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/aksuglobal/?ref=br_rs");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (id == R.id.Youtube) {
            Uri uri = Uri.parse("https://www.youtube.com/user/AKSUVZLA");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        if (fragmentSelecionado==true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,miFragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void enviarProducto(Producto producto) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
