package com.l2gb.applipfe;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.TimePicker;

import com.l2gb.applipfe.model.Jours_Model;
import com.l2gb.applipfe.model.TestCom;


public class ConfigurationJour extends ActionBarActivity {

    private Integer crenauIndice=0;
    private LayoutParams layoutParams;
    private TableRow tr;
    private Jours_Model jour;
    private Button saveBoutton;
    private EditText edit_name_jour;
    private TestCom testCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_jour);

        this.layoutParams= new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        this.layoutParams.setMargins(2, 2, 2, 2);

        Button saveBoutton = (Button) findViewById(R.id.save);
        edit_name_jour = (EditText) findViewById(R.id.edit_name_jour);

        this.testCom = new TestCom();
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

    private void sauvegarde(){
        jour = new Jours_Model();
        jour.setName(edit_name_jour.getText().toString());

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
        boutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispTimer(view);
            }
        });
        return boutton;
    }

    /**
     * \brief MÃ©thode d'affichage du pop-up de connexion
     *
     * AppelÃ©e lors du clic sur le bouton de configuration de la connexion.
     */
    public void dispTimer(View view)
    {
        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(this);
        final View timerPopUpView = factory.inflate(R.layout.activity_popup, null);

        //CrÃ©ation de l'AlertDialog
        AlertDialog.Builder timerPopUp = new AlertDialog.Builder(this);

        //On affecte la vue personnalisÃ© que l'on a crÃ©e Ã  notre AlertDialog
        timerPopUp.setView(timerPopUpView);

        //On donne un titre Ã  l'AlertDialog
        timerPopUp.setTitle("Test nigger");
        final TimePicker timer = (TimePicker) timerPopUpView.findViewById(R.id.timePicker);
        timerPopUp.show();
    }


    public void saveDisplay(View view)
    {
        //On instancie notre layout en tant que View
        LayoutInflater fac = LayoutInflater.from(this);
        final View testView = fac.inflate(R.layout.activity_test, null);

        //CrÃ©ation de l'AlertDialog
        AlertDialog.Builder testPopUp = new AlertDialog.Builder(this);

        //On affecte la vue personnalisÃ© que l'on a crÃ©e Ã  notre AlertDialog
        testPopUp.setView(testView);

        //On donne un titre Ã  l'AlertDialog
        testPopUp.setTitle("TEST");
        //final TextView textp = (TextView) testView.findViewById(R.id.testTextView);


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
