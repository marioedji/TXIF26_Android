package fr.utt.adrien.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                boolean authen = connextionTest();

                if (authen) {
                    goToTasklistActivity();
                    Log.d("testLogin", "réussi à se connecter");
                } else {
                    Toast.makeText(LoginActivity.this, " échec de connexion", Toast.LENGTH_LONG).show();
                    Log.d("testLogin", "échouer à se connecter");
                }
            }
        });
    }

    private void goToTasklistActivity() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, TasklistActivity.class);
        startActivity(intent);
    }

    private boolean connextionTest() {
        String nom_user = etIdentifiant.getText().toString();
        String mdp = etMdp.getText().toString();
        if (nom_user.equals("admin") && mdp.equals("123")) {
            return true;
        } else {
            return false;
        }

    }

}
