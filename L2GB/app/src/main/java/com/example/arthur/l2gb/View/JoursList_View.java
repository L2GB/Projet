package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arthur.l2gb.R;

public class JoursList_View extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jours_list__view);
        Button boutonValider = (Button)findViewById(R.id.ButtonValider);
        final EditText editTextNom = (EditText)findViewById(R.id.editTextNom);
    }

    public void AjoutObjet(View view){
        Intent intent = new Intent(this, JoursConfiguration_View.class);
        startActivityForResult(intent, MainActivity.CODE_RETOUR);
    }

    public void Retour(View view){
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jours_list__view, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Vérification du code de retour
        if(requestCode == MainActivity.CODE_RETOUR) {

            // Vérifie que le résultat est OK
            if(resultCode == RESULT_OK) {
                // On récupére le paramètre "Nom" de l'intent
                String nom = data.getStringExtra("Nom");
                // Création de l'intent
                Intent intent = new Intent();
                // On rajoute le nom saisie dans l'intent
                intent.putExtra("Nom",nom);
                // On retourne le résultat avec l'intent
                setResult(RESULT_OK, intent);
                // On termine cette activité
                finish();
            } else if (resultCode == RESULT_CANCELED) {
                // On affiche que l'opération est annulée
                Toast.makeText(this, "Opération annulé", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
