package fr.utt.adrien.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Voici le main activity, ici s'affichera le Hello World
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * The Tx.
     */
    Button btnLogin;
    EditText tvIdentifiant;
    EditText tvMdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        btnLogin = (Button) findViewById(R.id.btn_sign_in);
        tvIdentifiant= (EditText) findViewById(R.id.login_user_name);
        tvMdp= (EditText) findViewById(R.id.login_user_pwd);
        final String nom_user=tvIdentifiant.getText().toString();
        final String mdp=tvMdp.getText().toString();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean authen=connextionTest(nom_user,mdp);

                if (authen){
                    goToTasklistActivity();
                    Log.d("testLogin","réussi à se connecter");
                }else{
                    Toast.makeText(LoginActivity.this," échec de connexion",Toast.LENGTH_LONG).show();
                    Log.d("testLogin","échouer à se connecter");
                }
            }
        });
    }

    private void goToTasklistActivity() {
        Intent intent= new Intent();
        intent.setClass(LoginActivity.this,TasklistActivity.class);
        startActivity(intent);
    }

    private boolean connextionTest(String nom_user,String mdp){
//        if (nom_user.equals("admin")&&mdp.equals("123")){
//            return true;
//        }else {
//            return false;
//        }
        return true;
    }

}
