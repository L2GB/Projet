package com.l2gb.applipfe;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class Consommation_affichage extends ActionBarActivity  {

    private ListeObject listeObject;
    private Integer crenauIndice=0;
    private TableRow.LayoutParams layoutParams;
    private TableRow tr;
    int x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consommation);
        this.layoutParams= new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        this.layoutParams.setMargins(2, 2, 2, 2);
        int i;
        for(i=0;i<5;i++){
            this.crenauIndice++;
            TableLayout tl = (TableLayout) findViewById(R.id.tablelayoutId);
            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            /*** Affiche le numéro du créneau dans le tableau ***/
            tr.addView(generateTextView(String.valueOf(crenauIndice), layoutParams));
/***        Affiche le boutton de choix du début de créneau ***/
            tr.addView(generateTextView("Lampe",layoutParams));
            /*** Affiche le boutton de choix du début de créneau ***/
            tr.addView(generateTextView("Prise",layoutParams));
            /*** Affiche le boutton de choix de fin de créneau ***/
            tr.addView(generateTextView("ahahah",layoutParams));

            tl.addView(tr, layoutParams);
        }
        Intent intent = getIntent();

    }

    public void ajouterListeObjet(ListeObject listeObject){
        this.listeObject=listeObject;
    }

    public void ObjetAchanger(){

    }

    private TextView generateTextView(String texte, TableRow.LayoutParams ly) {
        TextView result = new TextView(this);
        result.setBackgroundColor(Color.WHITE);
        result.setTextColor(Color.DKGRAY);
        result.setGravity(Gravity.CENTER);
        result.setPadding(2, 2, 2, 2);
        result.setText(texte);
        result.setTextSize(20);
        result.setVisibility(View.VISIBLE);
        result.setLayoutParams(ly);
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_consommation, menu);
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
