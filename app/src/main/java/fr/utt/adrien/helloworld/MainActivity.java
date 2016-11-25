package fr.utt.adrien.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Voici le main activity, ici s'affichera le Hello World
 */
public class MainActivity extends AppCompatActivity {
    /**
     * The Tx.
     */
    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx= (TextView) findViewById(R.id.name);
        tx.setText("Bonjour Adrien");

    }
}
