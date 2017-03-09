package com.example.powerm3.diabetesireland;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    TextView[] labels;
    String[] labelNames;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

        labels = new TextView[6];
        labelNames = new String[]{"name","age","weight","height","bmi","isMale"};
        String name;
        for(int i = 0; i < labels.length; i++){
            name = "label_" + labelNames[i];
            labels[i] = (TextView) findViewById( ( getResources().getIdentifier(name,"id",getPackageName())));
        }

        labels[0].setText(sharedPref.getString(labelNames[0],labelNames[0]));
        labels[1].setText(Integer.toString(sharedPref.getInt(labelNames[1],1)));
        labels[2].setText(Float.toString(sharedPref.getFloat(labelNames[2],2)));
        labels[3].setText(Float.toString(sharedPref.getFloat(labelNames[3],3)));
        labels[4].setText(Float.toString(sharedPref.getFloat(labelNames[4],4)));
        boolean isMale = sharedPref.getBoolean(labelNames[5],true);
        labels[5].setText( (isMale)?("Male"):("Female"));
    }

    //This code stops the weird transition effect when the back button is pressed on the phone
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(0,0);
    }
}
