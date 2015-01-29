package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arthur.l2gb.Model.Jours_Model;
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

    public void sauvegarder(View view){
        EditText editText = (EditText) findViewById(R.id.edit_name_semaine);
        String name = editText.getText().toString();
        if(semaineExistePas(name)){
            if(crenauxEstRemplit()){
                Intent intent = new Intent();
                this.nouvelSemaine.setName(name);
                intent.putExtra("Semaine",this.nouvelSemaine);
                setResult(NEWSEMAINE, intent);
                finish();
            }
        }
    }

    private boolean crenauxEstRemplit(){
        for(int t=0; t<this.listBoolean.size();t++){
            if(this.listBoolean.get(t).booleanValue()==false) {
                Toast.makeText(this, "Tous les creneaux ne sont pas rempli", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

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

    public void choixLundi(View view){
        Intent intent = new Intent(this, Pop_up_choixjour.class);
        intent.putExtra("JOUR",this.model);
        this.vaChanger=0;
        startActivityForResult(intent, MainActivity.CODE_RETOUR);
    }
    public void choixMardi(View view){
        Intent intent = new Intent(this, Pop_up_choixjour.class);
        intent.putExtra("JOUR",this.model);
        this.vaChanger=1;
        startActivityForResult(intent, MainActivity.CODE_RETOUR);
    }
    public void choixMercredi(View view){
        Intent intent = new Intent(this, Pop_up_choixjour.class);
        intent.putExtra("JOUR",this.model);
        this.vaChanger=2;
        startActivityForResult(intent, MainActivity.CODE_RETOUR);
    }
    public void choixJeudi(View view){
        Intent intent = new Intent(this, Pop_up_choixjour.class);
        intent.putExtra("JOUR",this.model);
        this.vaChanger=3;
        startActivityForResult(intent, MainActivity.CODE_RETOUR);
    }
    public void choixVendredi(View view){
        Intent intent = new Intent(this, Pop_up_choixjour.class);
        intent.putExtra("JOUR",this.model);
        this.vaChanger=4;
        startActivityForResult(intent, MainActivity.CODE_RETOUR);
    }
    public void choixSamedi(View view){
        Intent intent = new Intent(this, Pop_up_choixjour.class);
        intent.putExtra("JOUR",this.model);
        this.vaChanger=5;
        startActivityForResult(intent, MainActivity.CODE_RETOUR);
    }
    public void choixDimanche(View view){
        Intent intent = new Intent(this, Pop_up_choixjour.class);
        intent.putExtra("JOUR",this.model);
        this.vaChanger=6;
        startActivityForResult(intent, MainActivity.CODE_RETOUR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Vérification du code de retour
        if(requestCode == MainActivity.CODE_RETOUR) {
            // Vérifie que le résultat est OK
            if(resultCode == OKJOUR) {
                // On récupére le paramètre "Nom" de l'intent
                Jours_Model jour = new Jours_Model("merde");
                jour = data.getParcelableExtra("JourChoisi");
                this.nouvelSemaine.getProfilJourList().add(this.vaChanger, jour);
                switch (vaChanger){
                    case 0 :
                        this.listBoolean.set(vaChanger,true);
                        Button butLun = (Button) findViewById(R.id.choixLundi);
                        butLun.setText(jour.getName());
                        break;
                    case 1 :
                        this.listBoolean.set(vaChanger,true);
                        Button butMar = (Button) findViewById(R.id.choixMardi);
                        butMar.setText(jour.getName());
                        break;
                    case 2 :
                        this.listBoolean.set(vaChanger,true);
                        Button butMer = (Button) findViewById(R.id.choixMercredi);
                        butMer.setText(jour.getName());
                        break;
                    case 3 :
                        this.listBoolean.set(vaChanger,true);
                        Button butJeu = (Button) findViewById(R.id.choixJeudi);
                        butJeu.setText(jour.getName());
                        break;
                    case 4 :
                        this.listBoolean.set(vaChanger,true);
                        Button butVen = (Button) findViewById(R.id.choixVendredi);
                        butVen.setText(jour.getName());
                        break;
                    case 5 :
                        this.listBoolean.set(vaChanger,true);
                        Button butSam = (Button) findViewById(R.id.choixSamedi);
                        butSam.setText(jour.getName());
                        break;
                    case 6 :
                        this.listBoolean.set(vaChanger,true);
                        Button butDim = (Button) findViewById(R.id.choixDimanche);
                        butDim.setText(jour.getName());
                        break;
                }
                // Création de l'intent


            } else if (resultCode == RESULT_CANCELED) {
                // On affiche que l'opération est annulée
                Toast.makeText(this, "Opération annulé", Toast.LENGTH_SHORT).show();
            }

        }
    }

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
