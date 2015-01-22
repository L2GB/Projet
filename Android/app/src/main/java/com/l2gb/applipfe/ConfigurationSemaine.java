package com.l2gb.applipfe;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.l2gb.applipfe.R;
import com.l2gb.applipfe.Test;


public class ConfigurationSemaine extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.example.pierre.myapplication.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_semaine);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration_semaine, menu);
        return true;
    }

    /** Called when the user clicks the Send button */
    public void sauvegarder(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Test.class);
        EditText editText = (EditText) findViewById(R.id.edit_name_semaine);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
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
