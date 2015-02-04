package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;

import com.example.arthur.l2gb.R;

public class PopUp_View extends Activity {

    private int id;
    private int crenau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up__view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pop_up__view, menu);
        Intent intent = getIntent();
        this.id = intent.getIntExtra("id",2048);
        this.crenau = intent.getIntExtra("crenau",2048);
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

    public void okHeure(View view) {
        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        int heure = tp.getCurrentHour();
        int minute = tp.getCurrentMinute();
        Intent intent = new Intent();
        // On rajoute le nom saisie dans l'intent
        intent.putExtra("heure", heure);
        intent.putExtra("minute", minute);
        intent.putExtra("id", this.id);
        intent.putExtra("crenau",this.crenau);
        // On retourne le résultat avec l'intent
        setResult(RESULT_OK, intent);
        // On termine cette activité
        finish();
    }

    public void annulerHeure(View view){
        finish();
    }
}
