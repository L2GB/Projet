package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.arthur.l2gb.R;

public class JoursConfiguration_View extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jours_configuration__view);
    }

    public void envoiDuNom(View view){
        final EditText editTextNom = (EditText)findViewById(R.id.editTextNom);
        // On vérifie que la taille de la chaine de retour est supèrieur à 0
        if(editTextNom.getText().length() > 0) {
            // Création de l'intent
            Intent intent = new Intent();
            // On rajoute le nom saisie dans l'intent
            intent.putExtra("Nom", editTextNom.getText().toString());
            // On retourne le résultat avec l'intent
            setResult(RESULT_OK, intent);
            // On termine cette activité
            finish();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jours_configuration__view, menu);
        return true;
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
