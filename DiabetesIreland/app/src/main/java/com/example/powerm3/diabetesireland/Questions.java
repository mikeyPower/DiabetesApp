package com.example.powerm3.diabetesireland;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.FileOutputStream;

public class Questions extends AppCompatActivity {

    EditText name;
    EditText age;
    EditText height;
    EditText weight;
    TextView bmiLabel;
    RadioButton maleButton;
    RadioButton femaleButton;
    TextView topMessageLabel;
    User newUser;
    Button submit;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    final Context context = this;
    int bmi =1;
    int h =1;
    int w =1;
    boolean isMale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        //View Components
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();
        isMale = true;


        name = (EditText) findViewById(R.id.editText);
        age = (EditText) findViewById(R.id.editText2);
        height = (EditText) findViewById(R.id.editText5);
        weight = (EditText) findViewById(R.id.editText6);
        bmiLabel = (TextView) findViewById(R.id.textView2);
        topMessageLabel = (TextView) findViewById(R.id.topTextLabel);
        maleButton = (RadioButton) findViewById(R.id.maleButton);
        femaleButton = (RadioButton) findViewById(R.id.femaleButton);
        submit = (Button) findViewById(R.id.submit);

        //Called when weight text field is entered or left
        weight.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View arg0,boolean arg1){
                updateBMILabel();
            }
        });

        //Called when height text field is entered or left
        height.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View arg0,boolean arg1){
                updateBMILabel();
            }
        });

        //Called when male radio button is pressed
        maleButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                femaleButton.setChecked(false);
                isMale = true;
                updateBMILabel();
            }
        });

        //Called when female radio button is pressed
        femaleButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                maleButton.setChecked(false);
                isMale = false;
                updateBMILabel();
            }
        });



        /*
        Function called when submit button is pressed, checks if all fields are filled in and if true then
        creates a new User object and moves to the home screen
        */
        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                EditText data[] = {name,age,height,weight};
                if(checkIfAllFieldsFilled(data)) {
                    newUser = new User(name.getText().toString(),Double.parseDouble(age.getText().toString()),Double.parseDouble(height.getText().toString()),Double.parseDouble(weight.getText().toString()),isMale);

                    editor.putString("name",newUser.getName());
                    editor.putInt("age",newUser.getAge());
                    editor.putFloat("height",(float) newUser.getHeight());
                    editor.putFloat("weight",(float) newUser.getWeight());
                    editor.putFloat("bmi",(float) newUser.getBMI());
                    editor.putBoolean("isMale",isMale);
                    editor.commit();
                    Intent intent = new Intent(context, Home.class);
                    startActivity(intent);

                    overridePendingTransition(0, 0);
                }else{
                    topMessageLabel.setText("Error: Please Complete All Fields");
                    topMessageLabel.setTextColor(0xffff0000);


                }

            }
        });
    }

    //Updates the BMI using the current values of the weight and height fields, will return 0 if either are empty
    private void updateBMILabel(){
        String formatted = String.format("%.1f",calculateBMI(weight.getText().toString(),height.getText().toString()));
        bmiLabel.setText("BMI: " + formatted);
    }

    //Uses weight and height to calculate and return BMI
    private double calculateBMI(String weight,String height){

        try{
            double w = Double.parseDouble(weight);
            double h = Double.parseDouble(height);
            h /= 100;
            double bmi =  (w/(h*h));
            return bmi;

        }catch(Exception e){

            return 0;
        }

    }

    //Function to check if all fields have been filled, returns false if any are empty
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

    //This code stops the weird transition effect when the back button is pressed on the phone
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(0,0);
    }



}