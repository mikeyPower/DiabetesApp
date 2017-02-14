package com.example.powerm3.diabetesireland;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Questions extends AppCompatActivity {

    EditText name;
    EditText age;
    EditText height;
    EditText weight;
    TextView mText;
    final Context context = this;
    int bmi =1;
    int h =1;
    int w =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

       name = (EditText) findViewById(R.id.editText);
       age = (EditText) findViewById(R.id.editText2);
        height = (EditText) findViewById(R.id.editText5);
       weight = (EditText) findViewById(R.id.editText6);


        final Button button = (Button) findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(context, Registration.class);
                startActivity(intent);

            }
        });
    }



}