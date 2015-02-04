package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.arthur.l2gb.Model.Model;
import com.example.arthur.l2gb.Model.Objet_Model;
import com.example.arthur.l2gb.R;

public class ListeObjetConnecte_View extends Activity {

    public static final int OBJETMODIF = 1;
    Model model;
    int nb0bjetConnu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_objet_connecte__view);
        this.model = getIntent().getExtras().getParcelable("MODEL");
        TableLayout jourTableau = (TableLayout) findViewById(R.id.tableauObjetConnu);
        TableRow tr;
        this.nb0bjetConnu=0;
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT);
        layoutParams.setMargins(2, 2, 2, 2);

        for(int i = 0 ; i < this.model.getObjet_model().size() ; i++){
            if(!this.model.getObjet_model().get(i).isInconnu()) {
                tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tr.addView(generateTextView(this.model.getObjet_model().get(i).getName(), layoutParams, this.model, this.model.getObjet_model().get(i).isConnecte()));
                tr.addView(generateBoutonallume(this.model.getObjet_model().get(i),layoutParams,this.model.getObjet_model().get(i).isAllume()));
                jourTableau.addView(tr, layoutParams);
                this.nb0bjetConnu++;
            }
        }
    }

    public void ajouterObjet(View view){

    }

    public void retour(View view){
        finish();
    }

    public Button generateTextView(String texte, TableRow.LayoutParams ly, final Model model,boolean connecte) {
        final Button result = new Button(this);
        if(connecte) {
            result.setTextColor(0xff339933);
        }else{
            result.setTextColor(0xffAC0000);
        }
        result.setId(this.nb0bjetConnu);
        result.setGravity(Gravity.CENTER);
        result.setText(texte);
        result.setTextSize(15);
        result.setLayoutParams(ly);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goConfigurationObjet(v, result.getText().toString());
            }
        });
        return result;
    }

    public Button generateBoutonallume(final Objet_Model objet,TableRow.LayoutParams ly,boolean allume) {
        final Button result = new Button(this);
        if(objet.isConnecte()) {
            if (allume) {
                result.setTextColor(0xffFFCC00);
                result.setText("allume");
            } else {
                result.setText("eteint");
            }
        }else{
            result.setText("-----");
        }
        result.setId(this.nb0bjetConnu);
        result.setGravity(Gravity.CENTER);
        result.setTextSize(15);
        result.setLayoutParams(ly);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(objet.isConnecte()) {
                    Intent intent = new Intent();
                    if (result.getText().equals("allume")) {
                        objet.setAllume(false);
                        result.setText("eteint");
                        result.setTextColor(0xff000000);
                    } else {
                        objet.setAllume(true);
                        result.setText("allume");
                        result.setTextColor(0xffFFCC00);
                    }
                    intent.putExtra("state", objet);
                    setResult(MainActivity.OBJECHANGE, intent);
                    finish();
                }else{
                    pasConnecte();
                }
            }
        });
        return result;
    }



    public void pasConnecte(){
        Toast.makeText(this, "Objet non connecté", Toast.LENGTH_SHORT).show();
    }

    public void goConfigurationObjet(View v, String nameBouton){
        Intent intent = new Intent(this, ConfigurationObjet.class);
        intent.putExtra("ListeObjet",this.model);
        for(int i = 0;i<this.model.getObjet_model().size();i++){
            if(this.model.getObjet_model().get(i).getName().equals(nameBouton)){
                intent.putExtra("NewObjet",this.model.getObjet_model().get(i));
                intent.putExtra("id",v.getId());
            }
        }
        startActivityForResult(intent, OBJETMODIF);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Vérification du code de retour
        if(requestCode == OBJETMODIF) {

            // Vérifie que le résultat est OK
            if(resultCode == ConfigurationObjet.NEWOBJET) {
                // On récupére le paramètre "Nom" de l'intent
                Objet_Model objet = data.getParcelableExtra("Objet");
                // Création de l'intent
                for(int p = 0; p<this.model.getObjet_model().size();p++){
                    if(objet.getDeviceId()==this.model.getObjet_model().get(p).getDeviceId() &&
                       objet.getInstanceNum()==this.model.getObjet_model().get(p).getInstanceNum() ){
                        this.model.getObjet_model().get(p).setObjet(objet);
                    }
                }

                int id = data.getIntExtra("id",0);
                Button but = (Button) findViewById(id);
                but.setText(objet.getName().toString());
                Intent intent = new Intent();
                 intent.putExtra("ObjetModif", objet);
                 setResult(MainActivity.OBJETADD, intent);
            } else if (resultCode == RESULT_CANCELED) {
                // On affiche que l'opération est annulée
                Toast.makeText(this, "Opération annulé", Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_liste_objet_connecte__view, menu);
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
