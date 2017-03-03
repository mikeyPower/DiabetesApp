package com.example.powerm3.diabetesireland;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class Home extends AppCompatActivity {

    TextView welcomeLabel;
    User user;
    ImageButton infoButton;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        infoButton = (ImageButton) findViewById(R.id.infoButton);
        user = (User) getIntent().getParcelableExtra("userData");

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Information.class);
                startActivity(intent);

                overridePendingTransition(0, 0);
            }
        });


        //welcomeLabel.setText("Welcome" + user.getName());

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(0,0);
    }
}
