package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arthur.l2gb.Model.Creneaux_Model;
import com.example.arthur.l2gb.Model.Jours_Model;
import com.example.arthur.l2gb.Model.Model;
import com.example.arthur.l2gb.R;

import java.util.ArrayList;

public class JoursConfiguration_View extends Activity {

    public static final int HEUREDEBUT = 1;
    public static final int HEUREFIN = 2;
    public static final int NEWJOURS = 11;
    private Integer crenauIndice=0;
    private TableRow.LayoutParams layoutParams;
    private TableRow tr;
    private Jours_Model jour;
    private Button saveBoutton;
    private EditText edit_name_jour;
    private int i=0;
    private Integer change = 0;
    Model model;
    public ArrayList<Creneaux_Model> listCreneaux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jours_configuration__view);
        this.model = getIntent().getExtras().getParcelable("MODEL");

        listCreneaux =new ArrayList<Creneaux_Model>();

        this.layoutParams= new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        this.layoutParams.setMargins(2, 2, 2, 2);

        this.jour = new Jours_Model();

        i=1;
    }

    /** Called when the user clicks the Add button */
    public void addCreneau(View view) {
        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


        /*** Affiche le numéro du créneau dans le tableau ***/

        Creneaux_Model newCrenau = new  Creneaux_Model();
        newCrenau.sethFin(25);
        newCrenau.sethDebut(25);
        this.jour.getCreneauList().add(newCrenau);
        Integer size = (Integer) this.jour.getCreneauList().size();
        tr.addView(generateTextView(size.toString(), layoutParams));

        /*** Affiche le boutton de choix du début de créneau ***/
        tr.addView(generateButtonDebut(layoutParams,this.jour));
        /*** Affiche le boutton de choix de fin de créneau ***/
        tr.addView(generateButtonDFin(layoutParams,this.jour));

        tr.addView(generateSwitchAutorisation(layoutParams,this.jour));

        //tr.addView(generateButtonSupprimer(layoutParams));

        tl.addView(tr, layoutParams);
        this.listCreneaux.add(new Creneaux_Model());

    }

    public TextView generateTextView(String texte, TableRow.LayoutParams ly) {
        TextView result = new TextView(this);
        result.setTextColor(Color.DKGRAY);
        result.setGravity(Gravity.CENTER);
        result.setPadding(10, 2, 10, 2);
        result.setText(texte);
        result.setTextSize(20);
        result.setVisibility(View.VISIBLE);
        result.setLayoutParams(ly);
        return result;
    }

    public Button generateButtonDebut(TableRow.LayoutParams ly,final Jours_Model jour){
        final Button boutton = new Button(this);
        boutton.setGravity(Gravity.CENTER);
        boutton.setPadding(10, 2, 10, 2);
        boutton.setText("debut");
        boutton.setTextSize(20);
        boutton.setVisibility(View.VISIBLE);
        boutton.setLayoutParams(ly);
        boutton.setId(i);
        i++;
        boutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heureDebutConfig(jour.getCreneauList().size()-1, boutton.getId());
            }
        });
        return boutton;
    }
    public void heureDebutConfig(int crenaux, int id){
        Intent intent = new Intent(this, PopUp_View.class);
        intent.putExtra("crenau",crenaux);
        intent.putExtra("id",id);
        startActivityForResult(intent, HEUREDEBUT);
    }

    public Button generateButtonDFin(TableRow.LayoutParams ly,final Jours_Model jour){
        final Button boutton = new Button(this);
        boutton.setGravity(Gravity.CENTER);
        boutton.setPadding(10, 2, 10, 2);
        boutton.setText("fin");
        boutton.setTextSize(20);
        boutton.setVisibility(View.VISIBLE);
        boutton.setLayoutParams(ly);
        boutton.setId(i);
        i++;
        boutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heureFinConfig(jour.getCreneauList().size() - 1, boutton.getId());
            }
        });
        return boutton;
    }
    public void heureFinConfig(int crenaux, int id){
        Intent intent = new Intent(this, PopUp_View.class);
        intent.putExtra("crenau",crenaux);
        intent.putExtra("id",id);
        startActivityForResult(intent, HEUREFIN);
    }

    public Button generateSwitchAutorisation(TableRow.LayoutParams ly,final Jours_Model jour){
        final Button boutton = new Button(this);
        boutton.setGravity(Gravity.CENTER);
        boutton.setPadding(10, 2, 10, 2);
        if(jour.getCreneauList().get(jour.getCreneauList().size() - 1).getAutorisation()) {
            boutton.setText("oui");
        }else{
            boutton.setText("non");
        }
        boutton.setTextSize(20);
        boutton.setVisibility(View.VISIBLE);
        boutton.setLayoutParams(ly);
        boutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boutton.getText().equals("oui")){
                    boutton.setText("non");
                    crenauAutorisation(false,jour.getCreneauList().size()-1);
                }else{
                    boutton.setText("oui");
                    crenauAutorisation(true,jour.getCreneauList().size()-1);
                }
            }
        });
        return boutton;
    }

    public void crenauAutorisation(boolean autorisation,int crenau){
        this.jour.getCreneauList().get(crenau).setAutorisation(autorisation);
    }

    public void supprimerLigne(View view){
        if(this.jour.getCreneauList().size()>0) {
            TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
            tl.removeViewAt(this.jour.getCreneauList().size() - 1);
            this.jour.getCreneauList().remove(this.jour.getCreneauList().size() - 1);
        }else {
            Toast.makeText(this, "Pas de crenaux à supprimer", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Vérification du code de retour
        if(requestCode == HEUREDEBUT) {
            // Vérifie que le résultat est OK
            if(resultCode == RESULT_OK) {
                int heure = data.getIntExtra("heure", 0);
                int min = data.getIntExtra("minute",0);
                int id = data.getIntExtra("id",0);
                int crenau = data.getIntExtra("crenau",0);
                Button but = (Button) findViewById(id);
                but.setText(heure +":" + min );
                this.jour.getCreneauList().get(crenau).sethDebut(heure);
                this.jour.getCreneauList().get(crenau).setmDebut(min);
            }
        }else if(requestCode == HEUREFIN){
            if(resultCode == RESULT_OK) {
                int heure = data.getIntExtra("heure", 0);
                int min = data.getIntExtra("minute", 0);
                int id = data.getIntExtra("id", 0);
                int crenau = data.getIntExtra("crenau", 0);
                Button but = (Button) findViewById(id);
                but.setText(heure + ":" + min);
                this.jour.getCreneauList().get(crenau).sethFin(heure);
                this.jour.getCreneauList().get(crenau).setmFin(min);
            }
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

    public void saveDisplay(View view){
        EditText editText = (EditText) findViewById(R.id.edit_name_jour);
        String name = editText.getText().toString();
        if(nomJournéeInexistant(name)){
            this.jour.setName(name);
            if(this.jour.getCreneauList().size()==0) {
                Toast.makeText(this, "Jour ajouté sans  crenaux", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("Jour",this.jour);
                setResult(NEWJOURS, intent);
                finish();
            }else{
                if(crenauxEstRemplit()) {
                    Intent intent = new Intent();
                    intent.putExtra("Jour",this.jour);
                    setResult(NEWJOURS, intent);
                    finish();
                }
            }
        }
    }

    private boolean crenauxEstRemplit(){
        for(int t=0; t<this.jour.getCreneauList().size();t++){
            if(this.jour.getCreneauList().get(t).gethDebut() == 25 || this.jour.getCreneauList().get(t).gethFin() == 25) {
                Toast.makeText(this, "Tous les creneaux ne sont pas rempli", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private boolean nomJournéeInexistant(String name){
        if(name.length()>0) {
            for (int p = 0; p < this.model.getJours_Model().size(); p++) {
                if (name.equals(this.model.getJours_Model().get(p).getName())) {
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

    public void RetourJourConfig(View view){
        finish();
    }
}
