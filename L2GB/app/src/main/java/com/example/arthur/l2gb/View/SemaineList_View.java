package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arthur.l2gb.Model.Model;
import com.example.arthur.l2gb.Model.Semaine_Model;
import com.example.arthur.l2gb.R;


public class SemaineList_View extends Activity {

    MainActivity mainActivity = null;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semaine_list__view);
        this.model = getIntent().getExtras().getParcelable("MODEL");

        TableLayout jourTSemaine = (TableLayout) findViewById(R.id.tableauSemaineList);
        TableRow tr;

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT);
        layoutParams.setMargins(2, 2, 2, 2);

        for(int i = 0 ; i < this.model.getProfilSemaine_model().size() ; i++){
            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(generateTextView(this.model.getProfilSemaine_model().get(i).getName(), layoutParams));
            jourTSemaine.addView(tr, layoutParams);
        }
    }

    public TextView generateTextView(String texte, TableRow.LayoutParams ly) {
        TextView result = new TextView(this);
        result.setBackgroundColor(Color.LTGRAY);
        result.setTextColor(Color.DKGRAY);
        result.setGravity(Gravity.CENTER);
        result.setPadding(2, 2, 2, 2);
        result.setText(texte);
        result.setTextSize(20);
        result.setVisibility(View.VISIBLE);
        result.setLayoutParams(ly);
        return result;
    }


    public void AjoutSemaine(View view){
        Intent intent = new Intent(this, SemaineConfiguration_View.class);
        intent.putExtra("MODEL",this.model);
        startActivityForResult(intent, MainActivity.CODE_RETOUR);
    }

    public void RetourSemaine(View view){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Vérification du code de retour
        if(requestCode == MainActivity.CODE_RETOUR) {

            // Vérifie que le résultat est OK
            if(resultCode == SemaineConfiguration_View.NEWSEMAINE) {
                // On récupére le paramètre "Nom" de l'intent
                Semaine_Model semaine = data.getParcelableExtra("Semaine");
                // Création de l'intent
                this.model.getProfilSemaine_model().add(semaine);
                TableLayout jourTableau = (TableLayout) findViewById(R.id.tableauSemaineList);
                TableRow tr;
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT);
                layoutParams.setMargins(2, 2, 2, 2);
                tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tr.addView(generateTextView(semaine.getName(), layoutParams));
                jourTableau.addView(tr, layoutParams);

                Intent intent = new Intent();
                intent.putExtra("Semaine", semaine);
                setResult(MainActivity.SEMAINEADD, intent);

            } else if (resultCode == RESULT_CANCELED) {
                // On affiche que l'opération est annulée
                Toast.makeText(this, "Opération annulé", Toast.LENGTH_SHORT).show();
            }

        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_semaine_list__view, menu);
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
