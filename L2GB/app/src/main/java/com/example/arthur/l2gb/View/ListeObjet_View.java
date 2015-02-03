package com.example.arthur.l2gb.View;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.arthur.l2gb.Model.Model;
import com.example.arthur.l2gb.R;

public class ListeObjet_View extends TabActivity {

    MainActivity mainActivity = null;
    Model model = null;
    int hrs,min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Model model;

        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_liste_objet__view);
       // Intent intent = getIntent();
      //  this.model = getIntent().getExtras().getParcelable("MODEL");
        //generateTableauObjet(this.model.getObjet_model());
        this.model = getIntent().getExtras().getParcelable("MODEL");
        TabHost mTabHost = getTabHost();
        Intent intentConnu = new Intent(this  ,ListeObjetConnecte_View.class );
        intentConnu.putExtra("MODEL",this.model);
        Intent intentInConnu = new Intent(this  ,ListeObjetInconnu_View.class );
        intentInConnu.putExtra("MODEL",this.model);
        mTabHost.addTab(mTabHost.newTabSpec("Objet connu").setIndicator("Objet connu").setContent(intentConnu));
        mTabHost.addTab(mTabHost.newTabSpec("Objet inconnu").setIndicator("Objet inconnu").setContent(intentInConnu));
        mTabHost.setCurrentTab(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_liste_objet__view, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Vérification du code de retour
        if(requestCode == ListeObjetConnecte_View.OBJETMODIF) {

            // Vérifie que le résultat est OK
            if(resultCode == MainActivity.OBJETADD) {
                // On récupére le paramètre "Nom" de l'intent
                Model newModel = data.getParcelableExtra("Model");
                Intent intent = new Intent();
                intent.putExtra("Model", newModel);
                setResult(MainActivity.OBJETADD, intent);
                Toast.makeText(this, "Objet modifier", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // On affiche que l'opération est annulée
                Toast.makeText(this, "Opération annulé", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
