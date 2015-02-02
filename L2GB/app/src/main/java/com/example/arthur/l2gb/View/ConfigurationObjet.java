package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arthur.l2gb.Model.Model;
import com.example.arthur.l2gb.Model.Objet_Model;
import com.example.arthur.l2gb.R;

import java.util.ArrayList;

public class ConfigurationObjet extends Activity {

    Model model;
    ArrayList<String> listeSemaine;
    Objet_Model objet;
    public static final int NEWOBJET =33;
    int idBouton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_objet);
        this.model = getIntent().getExtras().getParcelable("ListeObjet");
        this.objet = getIntent().getExtras().getParcelable("NewObjet");
        this.idBouton= getIntent().getExtras().getInt("id");
        this.model.getObjet_model().remove(this.objet);
        afficherParamettreObjet();
        choixSemaineSpinner();
    }

    public void afficherParamettreObjet(){
        EditText nom = (EditText) findViewById(R.id.edit_name_objet);
        nom.setText(objet.getName());
        EditText tempConf = (EditText) findViewById(R.id.editTextTemperatureConfort);
        tempConf.setText(objet.getTemperature_confort().toString());
        EditText tempEco = (EditText) findViewById(R.id.editTextTemperatureEco);
        tempEco.setText(objet.getTemperature_economique().toString());
        final Spinner spinnerSemaine = (Spinner) findViewById(R.id.semaineListe);
        this.listeSemaine =new ArrayList<String>();
        listeSemaine.add(this.objet.getProfilSemaine().getName());
    }

    public void choixSemaineSpinner(){
        final Spinner spinnerSemaine = (Spinner) findViewById(R.id.semaineListe);
        for(int i =0;i<this.model.getProfilSemaine_model().size();i++){
            if(!this.model.getProfilSemaine_model().get(i).getName().equals(this.objet.getProfilSemaine().getName())) {
                listeSemaine.add(this.model.getProfilSemaine_model().get(i).getName());
            }
        }

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,this.listeSemaine);
        //dataAdapterR.add(this.model.getProfilSemaine_model().get(1).getName().toString());
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemaine.setAdapter(dataAdapterR);
    }

    public void RetourObjetConfig(View v){
        finish();
    }
    public void saveObjet(View v){
        boolean nomexistant=true;
        EditText nom = (EditText) findViewById(R.id.edit_name_objet);
        if(nom.getText().length()>0) {
            for (int p = 0; p < this.model.getObjet_model().size(); p++) {
                if (nom.getText().equals(this.model.getObjet_model().get(p).getName())) {
                    nomexistant = false;
                }
            }
            if(nomexistant) {
                this.objet.setName(nom.getText().toString());
                nom = (EditText) findViewById(R.id.editTextTemperatureConfort);
                if (nom.getText().length() > 0) {
                    this.objet.setTemperature_confort(Integer.valueOf(nom.getText().toString()));
                    nom = (EditText) findViewById(R.id.editTextTemperatureEco);
                    if (nom.getText().length() > 0) {
                        Spinner listeSemaine = (Spinner) findViewById(R.id.semaineListe);
                        for(int i = 0;i<this.model.getProfilSemaine_model().size();i++){
                            if(listeSemaine.getSelectedItem().equals(this.model.getProfilSemaine_model().get(i).getName())){
                                this.objet.setProfilSemaine(this.model.getProfilSemaine_model().get(i));
                            }
                        }
                        this.objet.setTemperature_economique(Integer.valueOf(nom.getText().toString()));
                        Intent intent = new Intent();
                        intent.putExtra("Objet", this.objet);
                        intent.putExtra("id", this.idBouton);
                        setResult(NEWOBJET, intent);
                        finish();
                    } else {
                        Toast.makeText(this, "La temperature économique est pas remplit", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "La temperature confort est pas remplit", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Le nom existe déja", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this, "Le nom est pas remplit", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration_objet, menu);
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
