package com.l2gb.applipfe;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.l2gb.applipfe.model.Model;
import com.l2gb.applipfe.model.TestCom;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private Consommation_affichage consommation_affichage;
    private TestCom test1;
    private Model model;
    boolean test = false;

    public final static ListeObject LISTE_OBJECT = null;
    public ListeObject o;
    public ArrayList<ListeObject> ppp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button monBouton= (Button) this.findViewById(R.id.button4);
        monBouton.setVisibility(View.GONE);
        this.test1 = new TestCom();
        this.model = new Model();
        //this.model.creerfakeModel();
    }

    public void goScenario(View view){
        //Intent intent = new Intent(this, ScenarioActivity.class);
        //startActivity(intent);

        /** Vérification des chaine de charactere créée en JSON à partir du model **/
        /**
        System.out.println("test getJoursModel: "+this.model.getCommunication().getJoursModel());
        System.out.println("test getSemaineModel: "+this.model.getCommunication().getSemaineModel());
        System.out.println("test getObjetModel: "+this.model.getCommunication().getObjetModel());
        System.out.println("test semainetostring: "+this.model.getCommunication().semaineModelToString(this.model.getProfilSemaine_model().get(0)));
        System.out.println("test jourstostring: "+this.model.getCommunication().joursModelToString(this.model.getJours_Model().get(0)));
        System.out.println("test objettostring: "+this.model.getCommunication().objetModelToString(this.model.getObjet_model().get(0)));
        **/
        this.model.askJourList();
    }

    public void goConsommation(View view){
        if(test==false) {
            //System.out.println("appuy conso " + this.test);
            this.model.powerOn("prise1");
            test = true;
        }
        else if(test==true)
        {
            //System.out.println("appuy conso " + this.test);
            model.powerOff("prise1");
            test=false;
        }

    }
    public void goObjets(View view){
        //System.out.println("appuy objet");
        this.model.askJourList();
    }
    public void goPlanning(View view){
        Button monBouton= (Button) this.findViewById(R.id.button4);
        if(monBouton.getVisibility()==View.VISIBLE) {
            monBouton.setVisibility(View.GONE);
        }else{
            monBouton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
