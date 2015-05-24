package com.example.apple.clicking;

import android.app.Activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private double autovalue = 0;

    public void timer() {

    CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {       //we use this as a counter/timer for auto clicks

        @Override
        public void onTick(long millisUntilFinished) {
            total += autovalue*1000;                            //due to odd interactions with milliseconds autovalue is a very low number
            TextView myTextView = (TextView)
                    findViewById(R.id.textView);
            myTextView.setText(Double.toString(round(total,2)));
        }

        @Override
        public void onFinish() {
            timer();                    //resets the countdown so time is still ticking
        }
    }.start();
    }
    private double total = 0;
    private int value = 1;
    public void onButtonClick(View v) {                     //adds value to the total
        total += value;
        TextView myTextView = (TextView)                    //displays total
                findViewById(R.id.textView);
        myTextView.setText(Double.toString(round(total,2)));
    }
    public void upgradeClick(View v) {              //'upgrades' value by increasing it
        if((total - value) >= 0)  {                 //checks if you have enough money for upgrades
            total -= value;
            value++;
            TextView TextValue = (TextView)                 //displays value per click
                    findViewById(R.id.TextValue);
            TextValue.setText(Integer.toString(value));


            TextView myTextView = (TextView)
                    findViewById(R.id.textView);
            myTextView.setText(Double.toString(round(total,2)));
        }
    }
    private boolean timertrue = true;
    public void upgradeAuto(View v){              //'upgrades' the autoclick/click per second by increasing it
        if(timertrue){                              //to make sure that multiple instances of timer are not created
            timer();
            timertrue = false;
        }

        if((total - autovalue*10000) >= 0) {
            total -= autovalue * 10000;
            autovalue += 0.001;
            TextView TextAuto = (TextView)
                    findViewById(R.id.TextAuto);            //displays autoclicks per second
            TextAuto.setText(Double.toString(round(autovalue*1000,2)));


            TextView myTextView = (TextView)
                    findViewById(R.id.textView);
            myTextView.setText(Double.toString(round(total,2)));
        }
    }
    public static double round(double value, int places) {          //a rounding method so the app doesn't display many many places
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
