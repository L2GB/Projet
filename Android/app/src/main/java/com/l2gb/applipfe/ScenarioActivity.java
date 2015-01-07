package com.l2gb.applipfe;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ScenarioActivity extends ActionBarActivity {

        Button configJour;
        Button listeJour;
        Button jour;
        Button configSemaine;
        Button listeSemaine;
        Button semaine;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        public void goJours(View view) {
            Intent intent = new Intent(this, ScenarioActivity.class);
            startActivity(intent);
        }
        public void goSemaine(View view) {
            Intent intent = new Intent(this, ScenarioActivity.class);
            startActivity(intent);
        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_scenario, menu);
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

