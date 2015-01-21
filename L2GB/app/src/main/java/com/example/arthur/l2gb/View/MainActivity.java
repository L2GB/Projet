package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.arthur.l2gb.Model.Model;
import com.example.arthur.l2gb.R;


public class MainActivity extends Activity {

    Model model = null;
    int menu = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = 1;
        Button Retour= (Button) this.findViewById(R.id.button7);
        Button Jour= (Button) this.findViewById(R.id.button5);
        Button Semaine= (Button) this.findViewById(R.id.button6);
        Retour.setVisibility(View.GONE);
        Jour.setVisibility(View.GONE);
        Semaine.setVisibility(View.GONE);
        this.creerMVC();
    }
     private void creerMVC(){
         this.model = new Model();

     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void goConsommation(View view){
        Intent intent = new Intent(this, Consommation_View.class);
        startActivity(intent);
    }

    public void goScenario(View view){
        Button Scenario= (Button) this.findViewById(R.id.button);
        Button Objet= (Button) this.findViewById(R.id.button3);
        Button Planning= (Button) this.findViewById(R.id.button4);
        Button Consommation= (Button) this.findViewById(R.id.button2);
        Scenario.setVisibility(View.GONE);
        Objet.setVisibility(View.GONE);
        Planning.setVisibility(View.GONE);
        Consommation.setVisibility(View.GONE);

        Button Retour= (Button) this.findViewById(R.id.button7);
        Button Jour= (Button) this.findViewById(R.id.button5);
        Button Semaine= (Button) this.findViewById(R.id.button6);
        Retour.setVisibility(View.VISIBLE);
        Jour.setVisibility(View.VISIBLE);
        Semaine.setVisibility(View.VISIBLE);
    }

    public void goObjets(View view){
        Intent intent = new Intent(this, ListeObjet_View.class);
        startActivity(intent);
    }

    public void goPlanning(View view){

    }
    public void goMenu(View view){
        Button Retour= (Button) this.findViewById(R.id.button7);
        Button Jour= (Button) this.findViewById(R.id.button5);
        Button Semaine= (Button) this.findViewById(R.id.button6);
        Retour.setVisibility(View.GONE);
        Jour.setVisibility(View.GONE);
        Semaine.setVisibility(View.GONE);

        Button Scenario= (Button) this.findViewById(R.id.button);
        Button Objet= (Button) this.findViewById(R.id.button3);
        Button Planning= (Button) this.findViewById(R.id.button4);
        Button Consommation= (Button) this.findViewById(R.id.button2);
        Scenario.setVisibility(View.VISIBLE);
        Objet.setVisibility(View.VISIBLE);
        Planning.setVisibility(View.VISIBLE);
        Consommation.setVisibility(View.VISIBLE);
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
