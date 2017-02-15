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
    TextView bmiLabel;
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
        bmiLabel = (TextView) findViewById(R.id.textView2);

        weight.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View arg0,boolean arg1){
                String formatted = String.format("%.1f",calculateBMI(weight.getText().toString(),height.getText().toString()));
                bmiLabel.setText("BMI: " + formatted);
            }
        });

        height.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View arg0,boolean arg1){
                String formatted = String.format("%.1f",calculateBMI(weight.getText().toString(),height.getText().toString()));
                bmiLabel.setText("BMI: " + formatted);
            }
        });



        final Button button = (Button) findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(context, Registration.class);
                startActivity(intent);

            }
        });
    }

    public double calculateBMI(String weight,String height){

        try{
            //System.out.println("Weight = " + weight.toString());
            //System.out.println("Height = " + height.toString());
            double w = Double.parseDouble(weight);
            double h = Double.parseDouble(height);
            h /= 100;
            double bmi =  (w/(h*h));
            return bmi;

        }catch(Exception e){

            return 0;
        }

    }



}