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
import java.util.Scanner;

public class JoursConfiguration_View extends Activity {

    public static final int HEURE = 1;
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

        Button saveBoutton = (Button) findViewById(R.id.save);
        edit_name_jour = (EditText) findViewById(R.id.edit_name_jour);
        i=1;
    }

    /** Called when the user clicks the Add button */
    public void addCreneau(View view) {
        this.crenauIndice++;
        TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
        tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        /*** Affiche le numéro du créneau dans le tableau ***/
        tr.addView(generateTextView(String.valueOf((this.i - 1) / 3), layoutParams));

        /*** Affiche le boutton de choix du début de créneau ***/
        tr.addView(generateButtonDebut(layoutParams));
        /*** Affiche le boutton de choix de fin de créneau ***/
        tr.addView(generateButtonDFin(layoutParams));

        tr.addView(generateSwitchAutorisation(layoutParams));

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

    public Button generateButtonDebut(TableRow.LayoutParams ly){
        Button boutton = new Button(this);
        boutton.setId(this.i);
        this.i++;
        boutton.setTextColor(Color.DKGRAY);
        boutton.setGravity(Gravity.CENTER);
        boutton.setPadding(10, 2, 10, 2);
        boutton.setText("debut");
        boutton.setTextSize(20);
        boutton.setVisibility(View.VISIBLE);
        boutton.setLayoutParams(ly);
        boutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispTimer(view);
            }
        });
        return boutton;
    }

    public Button generateButtonDFin(TableRow.LayoutParams ly){
        Button boutton = new Button(this);
        boutton.setId(this.i);
        this.i++;
        boutton.setTextColor(Color.DKGRAY);
        boutton.setGravity(Gravity.CENTER);
        boutton.setPadding(10, 2, 10, 2);
        boutton.setText("fin");
        boutton.setTextSize(20);
        boutton.setVisibility(View.VISIBLE);
        boutton.setLayoutParams(ly);
        boutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispTimer(view);
            }
        });
        return boutton;
    }

    public Button generateSwitchAutorisation(TableRow.LayoutParams ly){
        Button boutton = new Button(this);
        boutton.setId(this.i);
        this.i++;
        boutton.setTextColor(Color.DKGRAY);
        boutton.setGravity(Gravity.CENTER);
        boutton.setPadding(10, 2, 10, 2);
        boutton.setText("OUI");
        boutton.setTextSize(20);
        boutton.setVisibility(View.VISIBLE);
        boutton.setLayoutParams(ly);
        boutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button but = (Button) findViewById(view.getId());
                if(but.getText().equals("OUI")){
                    but.setText("NON");
                    listCreneaux.get((view.getId()-3)/3).setAutorisation(false);
                }else{
                    but.setText("OUI");
                    listCreneaux.get((view.getId()-3)/3).setAutorisation(true);
                }
            }
        });
        return boutton;
    }

    public void supprimerLigne(View view){
        if(this.i>1) {
            TableLayout tl = (TableLayout) findViewById(R.id.tableLayout);
            tl.removeViewAt((this.i - 1) / 3);
            this.i = this.i-3;
            this.listCreneaux.remove(listCreneaux.size()-1);
        }
    }
    /**
     * \brief MÃ©thode d'affichage du pop-up de connexion
     *
     * AppelÃ©e lors du clic sur le bouton de configuration de la connexion.
     */
    public void dispTimer(View view)
    {
        Intent intent = new Intent(this, PopUp_View.class);
        intent.putExtra("Id",view.getId());
        startActivityForResult(intent, HEURE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Vérification du code de retour
        if(requestCode == HEURE) {
            // Vérifie que le résultat est OK
            if(resultCode == RESULT_OK) {
                int heure = data.getIntExtra("heure", 0);
                int min = data.getIntExtra("minute",0);
                int id = data.getIntExtra("Id",0);
                Button but = (Button) findViewById(id);
                but.setText(heure +":" + min );
                if((id-1)%3==0) {
                    this.listCreneaux.get(id / 3).sethDebut(heure);
                    this.listCreneaux.get(id / 3).setmDebut(min);
                }else{
                    this.listCreneaux.get(id / 3).sethFin(heure);
                    this.listCreneaux.get(id / 3).setmFin(min);
                }
            }
        }
    };

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
            Jours_Model newJour = new Jours_Model(name);
            if(this.i==1) {
                Toast.makeText(this, "Jour ajouté sans  crenaux", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("Jour",newJour);
                setResult(NEWJOURS, intent);
                finish();
            }else{
                if(crenauxEstRemplit(this.listCreneaux)) {
                    newJour.setCreneauList(this.listCreneaux);
                    Intent intent = new Intent();
                    intent.putExtra("Jour",newJour);
                    setResult(NEWJOURS, intent);
                    finish();
                }
            }
        }
    }

    private boolean crenauxEstRemplit(ArrayList<Creneaux_Model> listTest){
        for(int t=0; t<listTest.size();t++){
            if(listTest.get(t).gethDebut() == 25 || listTest.get(t).gethFin() == 25) {
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

    private void ajouterCrenaux(Jours_Model joursModel){
        int nbCrenau = ((this.i-1)/3);
        Toast.makeText(this, "Crenaux :"+nbCrenau, Toast.LENGTH_SHORT).show();
        int heurePremier;
        int minPremier;
        int heureDeuxieme;
        int minDeuxieme;
        //for(int t = 0; t < nbCrenau; nbCrenau++){
            Button buttonPremier = (Button) findViewById((0 * 3) + 1);
            String inputPremier = buttonPremier.getText().toString();
            Scanner premier = new Scanner(inputPremier).useDelimiter("\\s*:\\s*");
            heurePremier = premier.nextInt();
            minPremier = premier.nextInt();
            Toast.makeText(this, "Premier crenau :"+heurePremier+":"+minPremier, Toast.LENGTH_SHORT).show();


            buttonPremier = (Button) findViewById((0 * 3) + 2);
            inputPremier = buttonPremier.getText().toString();
            Scanner deuxieme = new Scanner(inputPremier).useDelimiter("\\s*:\\s*");
            heureDeuxieme = premier.nextInt();
            minDeuxieme = premier.nextInt();
            Toast.makeText(this, "Deuxieme crenau :"+heurePremier+":"+minPremier, Toast.LENGTH_SHORT).show();

        /**
        Switch autorisationSwitch = (Switch) findViewById((((nbCrenau * 3) + 3)));
        CharSequence toto = autorisationSwitch.getText();
        if(toto.equals("OUI")){
            Toast.makeText(this, "c'est oui", Toast.LENGTH_SHORT).show();

        }
        */
        //}
    }

    public void RetourJourConfig(View view){
        finish();
    }
}
