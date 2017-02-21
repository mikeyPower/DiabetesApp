package com.example.powerm3.diabetesireland;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;

public class Questions extends AppCompatActivity {

    EditText name;
    EditText age;
    EditText height;
    EditText weight;
    TextView bmiLabel;
    TextView topMessageLabel;
    User newUser;

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
        topMessageLabel = (TextView) findViewById(R.id.topTextLabel);

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
                EditText data[] = {name,age,height,weight};
                if(checkIfAllFieldsFilled(data)) {
                    newUser = new User(name.getText().toString(),Double.parseDouble(age.getText().toString()),Double.parseDouble(height.getText().toString()),Double.parseDouble(weight.getText().toString()));

                    Intent intent = new Intent(context, Home.class);
                    startActivity(intent);
                    intent.putExtra("userData", newUser);
                    overridePendingTransition(0, 0);
                }else{
                    topMessageLabel.setText("Error: Please Complete All Fields");
                    topMessageLabel.setTextColor(0xffff0000);


                }

            }
        });
    }

    private double calculateBMI(String weight,String height){

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

    private boolean checkIfAllFieldsFilled(EditText fields[]){
        for(int i =0; i < fields.length; i++){
            if(i > 0){
                try{
                    int test = Integer.parseInt(fields[i].getText().toString());
                }catch(Exception e){
                    return false;
                }
            }
            if(fields[i].getText().toString().equals("")){
                return false;
            }
        }
        return true;
    }

    public User getUser(){
        if(newUser != null) {
            return newUser;
        }
        return null;
    }



}