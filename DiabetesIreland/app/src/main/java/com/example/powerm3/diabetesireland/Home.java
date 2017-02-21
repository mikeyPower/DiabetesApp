package com.example.powerm3.diabetesireland;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView welcomeLabel;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeLabel = (TextView) findViewById(R.id.topTextLabel);
        user = (User) getIntent().getParcelableExtra("userData");

        //welcomeLabel.setText("Welcome" + user.getName());

    }
}
