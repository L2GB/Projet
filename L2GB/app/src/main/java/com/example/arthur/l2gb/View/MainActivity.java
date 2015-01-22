package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.arthur.l2gb.Model.Model;
import com.example.arthur.l2gb.R;

import java.util.ArrayList;


public class MainActivity extends Activity {

    public final static String  NAME = "user_login";
    public static final int CODE_RETOUR = 0;
    private String test;
    Model model = null;
    int menu = 1;
    ArrayList<String> listeJour = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = 1;
        Button Retour= (Button) this.findViewById(R.id.button7);
        Button Jour= (Button) this.findViewById(R.id.button5);
        Button Semaine= (Button) this.findViewById(R.id.button6);
        Retour.setVisibility(View.GONE);
        Jour.setVisibility(View.GONE);
        Semaine.setVisibility(View.GONE);
        this.listeJour = new ArrayList<String>();
        this.test = "Rien";
        this.creerMVC();

    }

        private void creerMVC(){
         this.model = new Model();
        this.listeJour.add("1er jours");
        this.listeJour.add("2er jours");
        this.listeJour.add("3er jours");
        this.listeJour.add("4er jours");
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Vérification du code de retour
        if(requestCode == CODE_RETOUR) {

            // Vérifie que le résultat est OK
            if(resultCode == RESULT_OK) {

                // On récupére le paramètre "Nom" de l'intent
                String nom = data.getStringExtra("Nom");
                this.test = nom;
                // On affiche le résultat
                Toast.makeText(this, "Votre nom est : " + nom, Toast.LENGTH_LONG).show();
                // Si l'activité est annulé
            } else if (resultCode == RESULT_CANCELED) {

                // On affiche que l'opération est annulée
                Toast.makeText(this, "Opération annulé", Toast.LENGTH_SHORT).show();

            }

        }
        Button Retour= (Button) this.findViewById(R.id.button7);
        Button Jour= (Button) this.findViewById(R.id.button5);
        Button Semaine= (Button) this.findViewById(R.id.button6);
        Retour.setVisibility(View.GONE);
        Jour.setVisibility(View.GONE);
        Semaine.setVisibility(View.GONE);

        Button Scenario= (Button) this.findViewById(R.id.button);
        Button Objet= (Button) this.findViewById(R.id.button3);
        Button Planning= (Button) this.findViewById(R.id.button4);
        Button Consommation= (Button) this.findViewById(R.id.button2);
        Scenario.setVisibility(View.VISIBLE);
        Objet.setVisibility(View.VISIBLE);
        Planning.setVisibility(View.VISIBLE);
        Consommation.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void goConsommation(View view){
        Intent intent = new Intent(this, Consommation_View.class);
        startActivity(intent);
    }

    public void goJours(View view){
        Intent intent = new Intent(this, JoursList_View.class);
        startActivityForResult(intent, CODE_RETOUR);
    }

    public void goSemaine(View view){
        Intent intent = new Intent(this, Scenario_View.class);
        startActivity(intent);
    }

    public void goScenario(View view){
        Button Scenario= (Button) this.findViewById(R.id.button);
        Button Objet= (Button) this.findViewById(R.id.button3);
        Button Planning= (Button) this.findViewById(R.id.button4);
        Button Consommation= (Button) this.findViewById(R.id.button2);
        Scenario.setVisibility(View.GONE);
        Objet.setVisibility(View.GONE);
        Planning.setVisibility(View.GONE);
        Consommation.setVisibility(View.GONE);

        Button Retour= (Button) this.findViewById(R.id.button7);
        Button Jour= (Button) this.findViewById(R.id.button5);
        Button Semaine= (Button) this.findViewById(R.id.button6);
        Retour.setVisibility(View.VISIBLE);
        Jour.setVisibility(View.VISIBLE);
        Semaine.setVisibility(View.VISIBLE);
    }

    public void goObjets(View view){
        Intent intent = new Intent(this, ListeObjet_View.class);
        startActivity(intent);
    }

    public void goPlanning(View view){
        Intent intent = new Intent(this, Planning_View.class);
        String message = test.toString();
        intent.putStringArrayListExtra(NAME, this.listeJour);
        startActivity(intent);
    }

    public void goMenu(View view){
        Button Retour= (Button) this.findViewById(R.id.button7);
        Button Jour= (Button) this.findViewById(R.id.button5);
        Button Semaine= (Button) this.findViewById(R.id.button6);
        Retour.setVisibility(View.GONE);
        Jour.setVisibility(View.GONE);
        Semaine.setVisibility(View.GONE);

        Button Scenario= (Button) this.findViewById(R.id.button);
        Button Objet= (Button) this.findViewById(R.id.button3);
        Button Planning= (Button) this.findViewById(R.id.button4);
        Button Consommation= (Button) this.findViewById(R.id.button2);
        Scenario.setVisibility(View.VISIBLE);
        Objet.setVisibility(View.VISIBLE);
        Planning.setVisibility(View.VISIBLE);
        Consommation.setVisibility(View.VISIBLE);
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
