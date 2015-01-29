package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.arthur.l2gb.Model.Model;
import com.example.arthur.l2gb.R;

public class ListeObjetConnecte_View extends Activity {

    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_objet_connecte__view);
        this.model = getIntent().getExtras().getParcelable("MODEL");
        TableLayout jourTableau = (TableLayout) findViewById(R.id.tableauObjetConnu);
        TableRow tr;

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT);
        layoutParams.setMargins(2, 2, 2, 2);

        for(int i = 0 ; i < this.model.getObjet_model().size() ; i++){
            if(this.model.getObjet_model().get(i).isInconnu()) {
                tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tr.addView(generateTextView(this.model.getObjet_model().get(i).getName(), layoutParams, this.model,this.model.getObjet_model().get(i).isConnecte()));
                jourTableau.addView(tr, layoutParams);
            }
        }
    }

    public Button generateTextView(String texte, TableRow.LayoutParams ly, final Model model,boolean connecte) {
        final Button result = new Button(this);
        if(connecte) {
            result.setTextColor(0xff339933);
        }else{
            result.setTextColor(0xffAC0000);
        }
        result.setGravity(Gravity.CENTER);
        result.setText(texte);
        result.setTextSize(15);
        result.setLayoutParams(ly);
        return result;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_liste_objet_connecte__view, menu);
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
