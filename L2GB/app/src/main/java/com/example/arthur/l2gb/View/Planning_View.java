package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.arthur.l2gb.Model.Model;
import com.example.arthur.l2gb.R;

import java.util.ArrayList;

public class Planning_View extends Activity {

    final String NAME = "user_login";
    private TableRow tr;
    private TableRow.LayoutParams layoutParams;
    ArrayList<String> listeJour = null;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.layoutParams= new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        this.layoutParams.setMargins(2, 2, 2, 2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning__view);

        Intent intent = getIntent();
        this.model = getIntent().getExtras().getParcelable("MODEL");
        ajouterJour(0);
    }


    private void ajouterJour(int Jour){
        int debutMin;
        int debutHeure;
        int FintMin;
        int FinHeure;
        boolean autorisation;
        TableRow tr;
        TableLayout tableauPlaning = (TableLayout) findViewById(R.id.talbeauPlanning);
        for(Integer i = 0 ; i < model.getProfilSemaine_model().size() ; i++) {
            tr = new TableRow(this);
            tr.addView(generateTextView30("Jour : " + model.getProfilSemaine_model().get(i).getProfilJourList().get(Jour).getName() + "  (Semaine : "
                    + model.getProfilSemaine_model().get(i).getName() + ")", layoutParams));
            tableauPlaning.addView(tr, layoutParams);
            for(int objet=0;objet<model.getObjet_model().size();objet++) {
                if(model.getObjet_model().get(objet).getProfilSemaine().getName().equals(model.getProfilSemaine_model().get(i).getName())) {
                    tr = new TableRow(this);
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tr.addView(generateTextView20(" - "+model.getObjet_model().get(objet).getName(), layoutParams));
                    tableauPlaning.addView(tr, layoutParams);
                }

            }
            //affiche les crenau
            for(int crenau = 0; crenau< model.getProfilSemaine_model().get(i).getProfilJourList().get(Jour).getCreneauList().size(); crenau++){
                debutHeure = model.getProfilSemaine_model().get(i).getProfilJourList().get(Jour).getCreneauList().get(crenau).gethDebut();
                debutMin =  model.getProfilSemaine_model().get(i).getProfilJourList().get(Jour).getCreneauList().get(crenau).getmDebut();
                FinHeure =  model.getProfilSemaine_model().get(i).getProfilJourList().get(Jour).getCreneauList().get(crenau).gethFin();
                FintMin =  model.getProfilSemaine_model().get(i).getProfilJourList().get(Jour).getCreneauList().get(crenau).getmFin();
                autorisation =  model.getProfilSemaine_model().get(i).getProfilJourList().get(Jour).getCreneauList().get(crenau).getAutorisation();

                if(autorisation){
                    tr = new TableRow(this);
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tr.addView(generateTextView20("creneau n°"+(crenau+1)+" : debut "+debutHeure+":"+debutMin+", fin "+FinHeure+":"+FintMin+". Modifiable", layoutParams));
                    tableauPlaning.addView(tr, layoutParams);
                }else{
                    tr = new TableRow(this);
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tr.addView(generateTextView20("creneau n°"+(crenau+1)+" : debut "+debutHeure+":"+debutMin+", fin "+FinHeure+":"+FintMin+". Non modifiable", layoutParams));
                    tableauPlaning.addView(tr, layoutParams);
                }
            }

        }
    }

    public void Lundi(View view){
        TableLayout tableauPlaning = (TableLayout) findViewById(R.id.talbeauPlanning);
        tableauPlaning.removeAllViews();
        ajouterJour(0);
    }
    public void Mardi(View view){
        TableLayout tableauPlaning = (TableLayout) findViewById(R.id.talbeauPlanning);
        tableauPlaning.removeAllViews();
        ajouterJour(1);
    }
    public void Mercredi(View view){
        TableLayout tableauPlaning = (TableLayout) findViewById(R.id.talbeauPlanning);
        tableauPlaning.removeAllViews();
        ajouterJour(2);
    }
    public void Jeudi(View view){
        TableLayout tableauPlaning = (TableLayout) findViewById(R.id.talbeauPlanning);
        tableauPlaning.removeAllViews();
        ajouterJour(3);
    }
    public void Vendredi(View view){
        TableLayout tableauPlaning = (TableLayout) findViewById(R.id.talbeauPlanning);
        tableauPlaning.removeAllViews();
        ajouterJour(4);
    }
    public void Samedi(View view){
        TableLayout tableauPlaning = (TableLayout) findViewById(R.id.talbeauPlanning);
        tableauPlaning.removeAllViews();
        ajouterJour(5);
    }
    public void Dimanche(View view){
        TableLayout tableauPlaning = (TableLayout) findViewById(R.id.talbeauPlanning);
        tableauPlaning.removeAllViews();
        ajouterJour(6);
    }

    public TextView generateTextView30(String texte, TableRow.LayoutParams ly) {
        TextView result = new TextView(this);
        result.setTextColor(Color.DKGRAY);
        result.setPadding(2, 2, 2, 2);
        result.setText(texte);
        result.setTextSize(20);
        result.setVisibility(View.VISIBLE);
        result.setLayoutParams(ly);
        return result;
    }
    public TextView generateTextView20(String texte, TableRow.LayoutParams ly) {
        TextView result = new TextView(this);
        result.setPadding(2, 2, 2, 2);
        result.setText(texte);
        result.setTextSize(15);
        result.setVisibility(View.VISIBLE);
        result.setLayoutParams(ly);
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_planning__view, menu);
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
