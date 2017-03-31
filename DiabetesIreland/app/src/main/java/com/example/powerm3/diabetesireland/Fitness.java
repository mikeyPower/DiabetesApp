package com.example.powerm3.diabetesireland;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Fitness extends AppCompatActivity  {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);
        textView = (TextView) findViewById(R.id.steps);
        int steps = getIntent().getIntExtra("Current steps", 0);
        textView.setText("Steps counted = " + steps);
    }

    //This code stops the weird transition effect when the back button is pressed on the phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}


