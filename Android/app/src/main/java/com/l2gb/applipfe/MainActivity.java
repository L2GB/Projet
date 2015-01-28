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
    private TestCom test;
    private Model model;

    public final static ListeObject LISTE_OBJECT = null;
    public ListeObject o;
    public ArrayList<ListeObject> ppp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button monBouton= (Button) this.findViewById(R.id.button4);
        monBouton.setVisibility(View.GONE);
        this.test = new TestCom();
        this.model = new Model();
    }

    public void goScenario(View view){

        Intent intent = new Intent(this, ScenarioActivity.class);
        startActivity(intent);
    }
    public void goConsommation(View view){
        Intent intent = new Intent(this, Consommation_affichage.class);
        intent.putExtra("coo",o);
        startActivity(intent);
    }
    public void goObjets(View view){
        setContentView(R.layout.activity_configuration_jour);

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
