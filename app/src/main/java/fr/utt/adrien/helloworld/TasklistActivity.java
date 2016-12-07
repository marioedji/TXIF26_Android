package fr.utt.adrien.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fr.utt.adrien.helloworld.model.Livraison;

/**
 * Created by qifan on 2016/11/30.
 */

public class TasklistActivity extends RequestActivity {

    private String TOKEN="";
    private List<Livraison> livraisons = new ArrayList<>();
    private ListView mLivraisonListView;
    ArrayAdapter<Livraison> mAdapter;
    private final int REQUEST_GET_MES_LIVRAISONS = 1;
    private final int REQUEST_GET_SE_DECONNECTER = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TOKEN = getIntent().getStringExtra("token");
        mLivraisonListView = (ListView) findViewById(R.id.livraisonListView);
        mAdapter = new ArrayAdapter<Livraison>(this,android.R.layout.simple_list_item_1,livraisons);
        mLivraisonListView.setAdapter(mAdapter);
        getMesLivraisons();
    }


    public void getMesLivraisons(){
        doGet(REQUEST_GET_MES_LIVRAISONS,"http://lelabo228.com/txapi/index.php/mes_livraisons/",TOKEN);
       /*
        ---> EXO
       final String url = http://lelabo228.com/txapi/index.php/mes_livraisons/"+TOKEN;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Type listType = new TypeToken<List<Livraison>>() {}.getType();
                        livraisons = new Gson().fromJson(response, listType);
                        mAdapter.clear();
                        mAdapter.addAll(livraisons);
                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response : ",""+error.networkResponse.statusCode);
                    }
                }
        );
        queue.add(getRequest);*/
    }

    @Override
    public void onOtherErrorResponse(int idRequest, int statusCode) {

    }

    @Override
    public void onSuccess(int idRequest, String response) {
        switch (idRequest){
            case REQUEST_GET_MES_LIVRAISONS:
                Type listType = new TypeToken<List<Livraison>>() {}.getType();
                livraisons = new Gson().fromJson(response, listType);
                mAdapter.clear();
                mAdapter.addAll(livraisons);
                mAdapter.notifyDataSetChanged();
                break;
            case REQUEST_GET_SE_DECONNECTER:
                doLogout();
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                doGet(REQUEST_GET_SE_DECONNECTER,"http://lelabo228.com/txapi/index.php/deconnexion/",TOKEN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackFromLogin(String token) {
        TOKEN = token;
        getMesLivraisons();
    }
}
