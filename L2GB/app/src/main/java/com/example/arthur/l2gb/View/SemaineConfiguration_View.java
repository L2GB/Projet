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
import com.example.arthur.l2gb.Model.Semaine_Model;
import com.example.arthur.l2gb.R;

import java.util.ArrayList;

public class SemaineConfiguration_View extends Activity {

    MainActivity mainActivity = null;
    Model model;
    int vaChanger=0;
    Semaine_Model nouvelSemaine;
    public static final int OKJOUR = 12;
    public static final int NEWSEMAINE = 11;
    ArrayList<Boolean> listBoolean;

    /**
     * Permet de sauvegarder la semaine si toute les condition sont remplit
     * @param view sur appuis sur un bouton
     */
    public void sauvegarder(View view){
        EditText editText = (EditText) findViewById(R.id.edit_name_semaine);
        String name = editText.getText().toString();
        if(semaineExistePas(name)){
            if(crenauxEstRemplit()){
                Intent intent = new Intent();
                this.nouvelSemaine.setName(name);
                ajouterJour();
                intent.putExtra("Semaine",this.nouvelSemaine);
                setResult(NEWSEMAINE, intent);
                finish();
            }
        }
    }

    /**
     * Permet la création des spinner avec les jours de la liste.
     */
    private void ajouterJour(){
        Spinner listeSemaine = (Spinner) findViewById(R.id.spinnerLundi);
        for(int i = 0;i<this.model.getJours_Model().size();i++){
            if(listeSemaine.getSelectedItem().equals(this.model.getJours_Model().get(i).getName())){
                this.nouvelSemaine.getProfilJourList().add(this.model.getJours_Model().get(i));
            }
        }
        listeSemaine = (Spinner) findViewById(R.id.spinnerMardi);
        for(int i = 0;i<this.model.getJours_Model().size();i++){
            if(listeSemaine.getSelectedItem().equals(this.model.getJours_Model().get(i).getName())){
                this.nouvelSemaine.getProfilJourList().add(this.model.getJours_Model().get(i));
            }
        }
        listeSemaine = (Spinner) findViewById(R.id.spinnerMercredi);
        for(int i = 0;i<this.model.getJours_Model().size();i++){
            if(listeSemaine.getSelectedItem().equals(this.model.getJours_Model().get(i).getName())){
                this.nouvelSemaine.getProfilJourList().add(this.model.getJours_Model().get(i));
            }
        }
        listeSemaine = (Spinner) findViewById(R.id.spinnerJeudi);
        for(int i = 0;i<this.model.getJours_Model().size();i++){
            if(listeSemaine.getSelectedItem().equals(this.model.getJours_Model().get(i).getName())){
                this.nouvelSemaine.getProfilJourList().add(this.model.getJours_Model().get(i));
            }
        }
        listeSemaine = (Spinner) findViewById(R.id.spinnerVendredi);
        for(int i = 0;i<this.model.getJours_Model().size();i++){
            if(listeSemaine.getSelectedItem().equals(this.model.getJours_Model().get(i).getName())){
                this.nouvelSemaine.getProfilJourList().add(this.model.getJours_Model().get(i));
            }
        }
        listeSemaine = (Spinner) findViewById(R.id.spinnerSamedi);
        for(int i = 0;i<this.model.getJours_Model().size();i++){
            if(listeSemaine.getSelectedItem().equals(this.model.getJours_Model().get(i).getName())){
                this.nouvelSemaine.getProfilJourList().add(this.model.getJours_Model().get(i));
            }
        }
        listeSemaine = (Spinner) findViewById(R.id.spinnerDimanche);
        for(int i = 0;i<this.model.getJours_Model().size();i++){
            if(listeSemaine.getSelectedItem().equals(this.model.getJours_Model().get(i).getName())){
                this.nouvelSemaine.getProfilJourList().add(this.model.getJours_Model().get(i));
            }
        }
    }

    /**
     * Permet de verifier si tous les crenau jour sont remplit
     * @return oui si tous les crenau sont remplit
     */
    private boolean crenauxEstRemplit(){
        Spinner listeJour = (Spinner) findViewById(R.id.spinnerLundi);
        if(listeJour.getSelectedItem().equals("Choix jour")){
            Toast.makeText(this, "Tous les creneaux ne sont pas rempli", Toast.LENGTH_LONG).show();
            return false;
        }
        listeJour = (Spinner) findViewById(R.id.spinnerMardi);
        if(listeJour.getSelectedItem().equals("Choix jour")){
            Toast.makeText(this, "Tous les creneaux ne sont pas rempli", Toast.LENGTH_LONG).show();
            return false;
        }
        listeJour = (Spinner) findViewById(R.id.spinnerMercredi);
        if(listeJour.getSelectedItem().equals("Choix jour")){
            Toast.makeText(this, "Tous les creneaux ne sont pas rempli", Toast.LENGTH_LONG).show();
            return false;
        }
        listeJour = (Spinner) findViewById(R.id.spinnerJeudi);
        if(listeJour.getSelectedItem().equals("Choix jour")){
            Toast.makeText(this, "Tous les creneaux ne sont pas rempli", Toast.LENGTH_LONG).show();
            return false;
        }
        listeJour = (Spinner) findViewById(R.id.spinnerVendredi);
        if(listeJour.getSelectedItem().equals("Choix jour")){
            Toast.makeText(this, "Tous les creneaux ne sont pas rempli", Toast.LENGTH_LONG).show();
            return false;
        }
        listeJour = (Spinner) findViewById(R.id.spinnerSamedi);
        if(listeJour.getSelectedItem().equals("Choix jour")){
            Toast.makeText(this, "Tous les creneaux ne sont pas rempli", Toast.LENGTH_LONG).show();
            return false;
        }
        listeJour = (Spinner) findViewById(R.id.spinnerDimanche);
        if(listeJour.getSelectedItem().equals("Choix jour")){
            Toast.makeText(this, "Tous les creneaux ne sont pas rempli", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * Permet de savoir si le nom de semaine est vide ou déja existant
     * @param name nom à verifier
     * @return oui si les condition sont remplit
     */
    private boolean semaineExistePas(String name){
        if(name.length()>0) {
            for(int p = 0;p<this.model.getProfilSemaine_model().size();p++){
                if(this.model.getProfilSemaine_model().get(p).getName().equals(name)){
                    Toast.makeText(this, "Le nom existe déja", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }else{
            Toast.makeText(this, "Le champ nom est vide", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * affiche tous les choix des spinner
     */
    private void afficheSemaine(){
        choixSemaineSpinnerLundi();
        choixSemaineSpinnerMardi();
        choixSemaineSpinnerMercredi();
        choixSemaineSpinnerJeudi();
        choixSemaineSpinnerVendredi();
        choixSemaineSpinnerSamedi();
        choixSemaineSpinnerDimanche();
    }

    /**
     * Affiche le spinner Lundi.
     */
    private void choixSemaineSpinnerLundi(){
        ArrayList<String> listeJours = new ArrayList<String>();
        listeJours.add("Choix jour");
        final Spinner spinnerSemaine = (Spinner) findViewById(R.id.spinnerLundi);
        for(int i =0;i<this.model.getJours_Model().size();i++){
            listeJours.add(this.model.getJours_Model().get(i).getName());

        }
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listeJours);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemaine.setAdapter(dataAdapterR);
    }

    /**
     * Affiche le spinner Mardi.
     */
    private void choixSemaineSpinnerMardi(){
        ArrayList<String> listeJours = new ArrayList<String>();
        listeJours.add("Choix jour");
        final Spinner spinnerSemaine = (Spinner) findViewById(R.id.spinnerMardi);
        for(int i =0;i<this.model.getJours_Model().size();i++){
            listeJours.add(this.model.getJours_Model().get(i).getName());

        }
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listeJours);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemaine.setAdapter(dataAdapterR);
    }

    /**
     * Affiche le spinner Mercredi.
     */
    private void choixSemaineSpinnerMercredi(){
        ArrayList<String> listeJours = new ArrayList<String>();
        listeJours.add("Choix jour");
        final Spinner spinnerSemaine = (Spinner) findViewById(R.id.spinnerMercredi);
        for(int i =0;i<this.model.getJours_Model().size();i++){
            listeJours.add(this.model.getJours_Model().get(i).getName());

        }
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listeJours);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemaine.setAdapter(dataAdapterR);
    }

    /**
     * Affiche le spinner Jeudi.
     */
    private void choixSemaineSpinnerJeudi(){
        ArrayList<String> listeJours = new ArrayList<String>();
        listeJours.add("Choix jour");
        final Spinner spinnerSemaine = (Spinner) findViewById(R.id.spinnerJeudi);
        for(int i =0;i<this.model.getJours_Model().size();i++){
            listeJours.add(this.model.getJours_Model().get(i).getName());

        }
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listeJours);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemaine.setAdapter(dataAdapterR);
    }

    /**
     * Affiche le spinner Vendredi.
     */
    private void choixSemaineSpinnerVendredi(){
        ArrayList<String> listeJours = new ArrayList<String>();
        listeJours.add("Choix jour");
        final Spinner spinnerSemaine = (Spinner) findViewById(R.id.spinnerVendredi);
        for(int i =0;i<this.model.getJours_Model().size();i++){
            listeJours.add(this.model.getJours_Model().get(i).getName());

        }
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listeJours);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemaine.setAdapter(dataAdapterR);
    }

    /**
     * Affiche le spinner Samedi.
     */
    private void choixSemaineSpinnerSamedi(){
        ArrayList<String> listeJours = new ArrayList<String>();
        listeJours.add("Choix jour");
        final Spinner spinnerSemaine = (Spinner) findViewById(R.id.spinnerSamedi);
        for(int i =0;i<this.model.getJours_Model().size();i++){
            listeJours.add(this.model.getJours_Model().get(i).getName());

        }
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listeJours);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemaine.setAdapter(dataAdapterR);
    }

    /**
     * Affiche le spinner Dimanche.
     */
    private void choixSemaineSpinnerDimanche(){
        ArrayList<String> listeJours = new ArrayList<String>();
        listeJours.add("Choix jour");
        final Spinner spinnerSemaine = (Spinner) findViewById(R.id.spinnerDimanche);
        for(int i =0;i<this.model.getJours_Model().size();i++){
            listeJours.add(this.model.getJours_Model().get(i).getName());

        }
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listeJours);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemaine.setAdapter(dataAdapterR);
    }

    /**
     * Retour à la vue précédente.
     * @param view sur appuis sur un bouton
     */
    public void annuler(View view){finish();}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semaine_configuration__view);
        this.model = getIntent().getExtras().getParcelable("MODEL");
        this.nouvelSemaine = new Semaine_Model();
        listBoolean = new ArrayList<Boolean>();
        for(int top = 0; top<7;top++){
            this.listBoolean.add(false);
        }
        afficheSemaine();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_semaine_configuration__view, menu);
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
