package com.example.powerm3.diabetesireland;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class Registration extends AppCompatActivity {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();
        String test = sharedPref.getString("name","");
        if(test.equals("")){

        }else{
            Intent intent = new Intent(context, Home.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }

        addListenerOnButton();
    }

    //Function to run when register button is pressed
    public void addListenerOnButton() {
        Button button;
        final Context context = this;

        button = (Button) findViewById(R.id.RegButton);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Questions.class);

                startActivity(intent);
                overridePendingTransition(0, 0);

            }

        });

    }
    //This code stops the weird transition effect when the back button is pressed on the phone
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(0,0);
    }

}
