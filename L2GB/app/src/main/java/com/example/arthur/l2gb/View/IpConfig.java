package com.example.arthur.l2gb.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.arthur.l2gb.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpConfig extends Activity implements GestureDetector.OnGestureListener {

    private GestureDetector mGestureDetector;

    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }


    public boolean onDown(MotionEvent arg0) {
        // Don't forget to return true here to get the following touch events
        return true;
    }

    public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        return false;
    }

    public void onLongPress(MotionEvent arg0) {
        ImageView im =(ImageView) findViewById(R.id.imageView);
        im.setVisibility(View.GONE);
    }

    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        // You can do here whatever you want to handle scrolling
        return true;
    }

    public void onShowPress(MotionEvent arg0) {
    }

    public boolean onSingleTapUp(MotionEvent arg0) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_config);
        ImageView im =(ImageView) findViewById(R.id.imageView);
        im.setVisibility(View.GONE);
        mGestureDetector = new GestureDetector(this);
    }

    /**
     * Permet de passer à la prochaine vue si ip validé
     * @param v sur l'appuis d'un bouton
     */
    public void connection(View v){
        EditText ip = (EditText) findViewById(R.id.editText);
        if (validIP(ip.getText().toString())) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("ip",ip.getText().toString());
            startActivity(intent);
        }else {
            Toast.makeText(this, "Ip rentré non valide", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Vérifie l'ip (format) et une petite surprise.
     * @param ip à verifier
     * @return
     */
    public  boolean validIP(String ip) {
        if (ip == null || ip.isEmpty()) return false;
        ip = ip.trim();
        if(ip.equals("42.51.69")){
            ImageView im =(ImageView) findViewById(R.id.imageView);
            im.setVisibility(View.VISIBLE);
        }
        if ((ip.length() < 6) & (ip.length() > 15)) return false;
            Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
            Matcher matcher = pattern.matcher(ip);
            return matcher.matches();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ip_config, menu);
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
