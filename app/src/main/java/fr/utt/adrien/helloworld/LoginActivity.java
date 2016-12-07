package fr.utt.adrien.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import fr.utt.adrien.helloworld.model.Chauffeur;

/**
 * Voici le main activity, ici s'affichera le Hello World
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * The Tx.
     */
    Button btnLogin;
    EditText etIdentifiant;
    EditText etMdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        btnLogin = (Button) findViewById(R.id.btn_sign_in);
        etIdentifiant = (EditText) findViewById(R.id.login_user_name);
        etMdp = (EditText) findViewById(R.id.login_user_pwd);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            connextionTest(etIdentifiant.getText().toString(),etMdp.getText().toString());
            }
        });
    }

   /*
      -->   EXO
    private void goToTasklistActivity(String token) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, TasklistActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
    }
    */

    /**
     * Permet de définir l'activité idéal de destination après la connexion
     *
     *
     * @param token
     */
    public void goToAppropriateActivity(String token){
        Intent intent = new Intent();
        intent.putExtra("token",token);
        setResult(RESULT_OK, intent);
        if(this.getIntent().getBooleanExtra("isLoggedOut",false)){
            //Redirection sur une activité par défaut si l'on revient d'une deconnexion
            intent.setClass(this, TasklistActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        finish();

    }

    /**
     * Fait appel à un web
     */
    private void connextionTest(final String login, final String passwd) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://lelabo228.com/txapi/index.php/connexion";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                        Gson gson = new Gson();
                        String token =  gson.fromJson(response, Chauffeur[].class)[0].getToken();
                        // goToTasklistActivity(token); --> EXO
                        goToAppropriateActivity(token);

                        Log.d("testLogin", "réussi à se connecter");
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(LoginActivity.this, " échec de connexion", Toast.LENGTH_LONG).show();
                        Log.d("testLogin", "échouer à se connecter ");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("login", login);
                params.put("passwd", hashToSHA512(passwd,""));
                return params;
            }
        };
        queue.add(postRequest);

    }

    /**
     * Permet de hasher un mot de passe en SHA512
     * @param passwordToHash le mot de passe à hasher
     * @param salt le sel
     * @return
     */
    public String hashToSHA512(String passwordToHash, String   salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            try {
                md.update(salt.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte[] bytes = new byte[0];
            try {
                bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }

}
