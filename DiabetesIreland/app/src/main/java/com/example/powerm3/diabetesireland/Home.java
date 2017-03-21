package com.example.powerm3.diabetesireland;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Home extends AppCompatActivity {


    User user;
    ImageButton infoButton,trackerButton,profileButton, fitnessButton;
    final Context context = this;
    TextView welcomeLabel;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //set up shared preferences to retrieve user data
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();


        //retrieve the user's name stored in shared preferences
        userName = sharedPref.getString("name","");

        //attach the addButtons on the home screen to declared variables
        profileButton = (ImageButton) findViewById(R.id.profile_button);
        infoButton = (ImageButton) findViewById(R.id.infoButton);
        trackerButton = (ImageButton) findViewById(R.id.tracker_button);
        fitnessButton = (ImageButton) findViewById(R.id.fitness_button);

        //attach the text labels
        welcomeLabel = (TextView) findViewById(R.id.welcome_label);
        welcomeLabel.setText("Welcome " + userName);

        //Sets up the information button to transition to the info screen
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Information.class);
                startActivity(intent);

                overridePendingTransition(0, 0);
            }
        });


        //Sets up the tracker button to transition to the food screen
        trackerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Pyramid.class);
                startActivity(intent);

                overridePendingTransition(0, 0);
            }
        });

        //Sets up profile button to change to the profile screen
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Profile.class);
                startActivity(intent);

                overridePendingTransition(0,0);
            }
        });



//Sets up profile button to change to the fitness screen
        fitnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Fitness.class);
                startActivity(intent);

                overridePendingTransition(0,0);
            }
        });

    }

    //updated welcome label with user's name when view is reopened
    //necessary when coming back from profile screen where name may have been changed
    public void onResume(){
        super.onResume();
        userName = sharedPref.getString("name","");
        welcomeLabel.setText("Welcome " + userName);

    }

    //This code stops the weird transition effect when the back button is pressed on the phone
    //Also stops it from going back to the registration questions after first launch
    @Override
    public void onBackPressed(){
        moveTaskToBack(true);

        overridePendingTransition(0,0);
    }
}
