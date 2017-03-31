package com.example.powerm3.diabetesireland;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Fitness extends AppCompatActivity  {
    private TextView textView;

    Spinner spin;
    Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);
        textView = (TextView) findViewById(R.id.steps);
        int steps = getIntent().getIntExtra("Current steps", 0);
        spin = (Spinner) findViewById(R.id.spinner);
        submit_button = (Button) findViewById(R.id.submit_button);

        textView.setText("Steps counted = " + steps);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.activity_names,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        spin.getSelectedItem();

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(spin.getSelectedItemId());
            }
        });
    }

    //This code stops the weird transition effect when the back button is pressed on the phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}