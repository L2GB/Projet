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
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.arthur.l2gb.Model.Model;
import com.example.arthur.l2gb.R;

public class Pop_up_choixjour extends Activity {

    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_choixjour);
        this.model = getIntent().getExtras().getParcelable("JOUR");


        TableLayout jourTableau = (TableLayout) findViewById(R.id.choixJourPourSemaine);
        TableRow tr;

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT);
        layoutParams.setMargins(2, 2, 2, 2);

        for(int i = 0 ; i < this.model.getJours_modelArrayList().size() ; i++){
            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(generateTextView(this.model.getJours_modelArrayList().get(i).getName(), layoutParams,this.model));
            jourTableau.addView(tr, layoutParams);
        }

    }

    public Button generateTextView(String texte, TableRow.LayoutParams ly, final Model model) {
        final Button result = new Button(this);
        result.setTextColor(Color.DKGRAY);
        result.setGravity(Gravity.CENTER);
        result.setPadding(10, 2, 10, 2);
        result.setText(texte);
        result.setTextSize(20);
        result.setLayoutParams(ly);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                for(int a=0;a<model.getJours_modelArrayList().size();a++) {
                    if (result.getText().equals(model.getJours_modelArrayList().get(a).getName())){
                        intent.putExtra("JourChoisi", model.getJours_modelArrayList().get(a));
                    }
                }
                setResult(SemaineConfiguration_View.OKJOUR, intent);
                finish();
            }
        });
        return result;
    }

    public void  retourConfigSemaine(View view){
        finish();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pop_up_choixjour, menu);
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
