package com.example.powerm3.diabetesireland;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;


public class Home extends AppCompatActivity {


    User user;
    ImageButton infoButton,trackerButton,profileButton, fitnessButton,graphButton;
    final Context context = this;
    TextView welcomeLabel;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Intent mIntent ;
    String userName;
    int steps;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Starts pedometer service
         mIntent = new Intent(this, SensorService.class);
        startService(mIntent);
       // bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE);
        //isServiceRunning(SensorService.class);

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
        graphButton = (ImageButton) findViewById(R.id.graph_button);

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

        //Sets up graph button to change to the graph screen
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,graph.class);
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
                intent.putExtra("Current steps", steps);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

    }

    //updated welcome label with user's name when view is reopened
    //necessary when coming back from profile screen where name may have been changed
    public void onResume(){
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter("Sending steps"));
        userName = sharedPref.getString("name","");
        welcomeLabel.setText("Welcome " + userName);
    }

    public void onPause(){
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    //This code stops the weird transition effect when the back button is pressed on the phone
    //Also stops it from going back to the registration questions after first launch
    @Override
    public void onBackPressed(){
        moveTaskToBack(true);

        overridePendingTransition(0,0);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        stopService(mIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
            steps =  Integer.parseInt(message);
        }
    };

}
