package com.l2gb.applipfe;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;


public class ConfigurationJour extends ActionBarActivity {

    private Integer crenauIndice=0;
    private LayoutParams layoutParams;
    private TableRow tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_jour);

        this.layoutParams= new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        this.layoutParams.setMargins(2, 2, 2, 2);
    }

    /** Called when the user clicks the Add button */
    public void addCreneau(View view) {
        this.crenauIndice++;
        TableLayout tl = (TableLayout) findViewById(R.id.tablelayoutId);
        tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        /*** Affiche le numéro du créneau dans le tableau ***/
        tr.addView(generateTextView(String.valueOf(crenauIndice), layoutParams));

        /*** Affiche le boutton de choix du début de créneau ***/
        tr.addView(generateButton(layoutParams));
        /*** Affiche le boutton de choix de fin de créneau ***/
        tr.addView(generateButton(layoutParams));

        tl.addView(tr, layoutParams);
    }

    public TextView generateTextView(String texte, LayoutParams ly) {
        TextView result = new TextView(this);
        result.setBackgroundColor(Color.WHITE);
        result.setTextColor(Color.DKGRAY);
        result.setGravity(Gravity.CENTER);
        result.setPadding(2, 2, 2, 2);
        result.setText(texte);
        result.setTextSize(20);
        result.setVisibility(View.VISIBLE);
        result.setLayoutParams(ly);
        return result;
    }

    public Button generateButton(LayoutParams ly){
        Button boutton = new Button(this);
        boutton.setBackgroundColor(Color.WHITE);
        boutton.setTextColor(Color.DKGRAY);
        boutton.setGravity(Gravity.CENTER);
        boutton.setPadding(2,2,2,2);
        boutton.setText("yo");
        boutton.setTextSize(20);
        boutton.setVisibility(View.VISIBLE);
        boutton.setLayoutParams(ly);
        return boutton;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration_jour, menu);
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
