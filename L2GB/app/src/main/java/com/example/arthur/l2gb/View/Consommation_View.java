package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.example.arthur.l2gb.Model.Model;
import com.example.arthur.l2gb.R;

public class Consommation_View extends Activity {

    MainActivity mainActivity = null;
    private TableRow tr;
    private TableRow.LayoutParams layoutParams;
    Model model = null;

    public Consommation_View() {
    }

    public Consommation_View(MainActivity mainActivity, Model model) {
        this.mainActivity = mainActivity;
        this.model = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consommation__view);

        Model model = getIntent().getExtras().getParcelable("MODEL");


        TableLayout consommationTableau = (TableLayout) findViewById(R.id.tableauConsommation);
        TableRow tr;

        LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
        layoutParams.setMargins(2, 2, 2, 2);

        for(int i = 0 ; i < model.getObjet_model().size() ; i++){
            tr = new TableRow(this);
            tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            tr.addView(generateTextView(model.getObjet_model().get(i).getName(), layoutParams));
            tr.addView(generateTextView(String.valueOf(model.getObjet_model().get(i).getConsommation()), layoutParams));
            consommationTableau.addView(tr, layoutParams);
        }
    }


    public TextView generateTextView(String texte, LayoutParams ly) {
        TextView result = new TextView(this);
        result.setBackgroundColor(Color.LTGRAY);
        result.setTextColor(Color.DKGRAY);
        result.setGravity(Gravity.CENTER);
        result.setPadding(2, 2, 2, 2);
        result.setText(texte);
        result.setTextSize(20);
        result.setVisibility(View.VISIBLE);
        result.setLayoutParams(ly);
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_consommation__view, menu);
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
