package fr.utt.adrien.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mario on 06/12/2016.
 *
 * Classe dont peut étendre toute activité qui fait appel à des WebServices
 * Contient des methodes de call back en cas de succès de l'appel ou d'une erreur indéfinie.
 */

public abstract class RequestActivity extends AppCompatActivity {
    public static int REQUEST_LOGIN_TOKEN = 401;

    /**
     * Utilisé pour faire des requêtes GET
     * @param idRequest id de la requête GET (utilisé dans les callBack pour définir le traitement approprié à chaque requête)
     * @param url l'url de la requête GET
     * @param token le token d'authentification
     * @param params les params de la requête sous la forme "{param1}/{param2}/..."
     */
    public void doGet(final int idRequest,String url, String token, String params) {
        url += token + "/" + params;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onSuccess(idRequest, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onError(idRequest,error);
                    }
                }
        );
        queue.add(getRequest);
    }

    /**
     * Pour des requêtes GET sans params
     * @param idRequest
     * @param url
     * @param token
     */
    public void doGet(int idRequest, String url, String token) {
        doGet(idRequest, url,token,"");
    }

    /**
     * Utilisé pour faire des requêtes POST
     * @param idRequest id de la requête POST (utilisé dans les callBack pour définir le traitement approprié à chaque requête)
     * @param url l'url de la requête POST
     * @param token le token d'authentification
     * @param params les params de la requête sous la forme de map liste de "clef/val"
     */
    private void doPost(final int idRequest, String url, String token, final Map<String, String> params) {
        RequestQueue queue = Volley.newRequestQueue(this);
        url += token;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        onSuccess(idRequest,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        onError(idRequest,error);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        queue.add(postRequest);

    }

    /**
     * Utilisée pour la gestion des erreurs des requêtes
     * @param idRequest l'Id de la requête
     * @param error l'erreur
     */

    private void onError(int idRequest,VolleyError error) {
        if(error!=null && error.networkResponse != null) {
            if (error.networkResponse.statusCode == 401 || error.networkResponse.statusCode == 500) {
                on401Response();
            } else if (error.networkResponse.statusCode != 200
                    && error.networkResponse.statusCode != 201
                    && error.networkResponse.statusCode != 202
                    && error.networkResponse.statusCode != 203) {

                Toast.makeText(RequestActivity.this, "Server returned : " + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();

            } else {
                onOtherErrorResponse(idRequest, error.networkResponse.statusCode);
            }
        }else{
            Toast.makeText(RequestActivity.this, "Unable to connect to web service ", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Traitement par défaut pour les erreurs de type 401
     */
    public void on401Response() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivityForResult(intent,REQUEST_LOGIN_TOKEN);
    }


    /**
     * Traitement des codes non prise en charge
     * @param idRequest l'id de la requête
     * @param statusCode le code d'erreur
     */
    public abstract void onOtherErrorResponse(int idRequest, int statusCode);

    public abstract void onSuccess(int idRequest, String response);

    public abstract void onBackFromLogin(String token);
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_LOGIN_TOKEN){
            if(resultCode == RESULT_OK){
                onBackFromLogin(data.getStringExtra("token"));
            }
        }
    }

    /**
     * Méthode utilisée pour se deconnecter
     */
    public void doLogout(){
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        intent.putExtra("isLoggedOut",true);
        startActivity(intent);
        finish();
    }
}
