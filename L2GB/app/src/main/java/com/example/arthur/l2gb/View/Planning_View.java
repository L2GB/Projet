package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.arthur.l2gb.R;

import com.example.arthur.l2gb.Model.Model;

import java.util.ArrayList;

public class Planning_View extends Activity {

    final String NAME = "user_login";
    private TableRow tr;
    private TableRow.LayoutParams layoutParams;
    ArrayList<String> listeJour = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.layoutParams= new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        this.layoutParams.setMargins(2, 2, 2, 2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning__view);

        Intent intent = getIntent();
        this.listeJour = new ArrayList<String>();
        Model model = getIntent().getExtras().getParcelable(NAME);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        setContentView(textView);
        ajouterJour(model);
    }


    private void ajouterJour(Model model){
        TableLayout tl = (TableLayout) findViewById(R.id.tablelayoutId);
        TableLayout t2 = new TableLayout(this);
        for(Integer i = 0 ; i < model.getJours_Model().size() ; i++) {
            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView textView = new TextView(this);
            textView.setTextSize(20);
            textView.setText(model.getJours_Model().get(i).getName());
            /*** Affiche le numéro du créneau dans le tableau ***/
            tr.addView(textView);
            t2.addView(tr);
            setContentView(t2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_planning__view, menu);
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
