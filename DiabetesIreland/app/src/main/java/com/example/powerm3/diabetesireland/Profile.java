package com.example.powerm3.diabetesireland;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    final Context context = this;
    TextView[] labels;
    ImageButton[] editButtons;
    String[] labelNames;
    String[] hints;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    DBHandler db;
    public static final String USER_AGE = "AGE";     // primary key
    public static final String USER_GENDER = "GENDER";         // male / female
    public static final String USER_HEIGHT = "HEIGHT";         // in metres or/and feet
    public static final String USER_WEIGHT= "WEIGHT";         // in kg/other
    public static final String USER_ID = "ID";
    public static final String USER_NAME = "NAME";
    public static final String USER_CALORIES = "GOAL";           // daily targets?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //set up database
        db = new DBHandler(context);

        //set up shared preferences to load and update user data
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

        //instantiate arrays to store text labels and addButtons
        labels = new TextView[6];
        editButtons = new ImageButton[6];

        //create arrays to store label names and hint strings
        labelNames = new String[]{"name","age","height","weight","bmi","isMale"};
        hints = new String[]{"Name","Age","Height","Weight"};

        //Initialise label variables, connecting them to components on screen and putting them in array labels[]
        String name;
        for(int i = 0; i < labels.length; i++){
            name = "label_" + labelNames[i];
            labels[i] = (TextView) findViewById( ( getResources().getIdentifier(name,"id",getPackageName())));
        }

        //Initialise editButton variables, connecting them to components on screen and putting them in array editButtons[]
        String buttonName;
        for(int i = 0; i < editButtons.length; i++){
            buttonName = "button_" + labelNames[i];
            editButtons[i] = (ImageButton) (findViewById( getResources().getIdentifier(buttonName,"id",getPackageName())));
            if(i == 4){
                editButtons[i].setClickable(false);
            }
        }

        //setting up the edit addButtons from name to weight, bmi is calculated automatically and does not need a button
        for(int i = 0; i < 4; i++){
            final int j = i;
            editButtons[i].setOnClickListener(new View.OnClickListener() {

                //sets up alert dialog to pop up when edit button is pressed
                @Override
                public void onClick(View v) {

                    final View view = (LayoutInflater.from(context)).inflate(R.layout.fragment_edit__alert,null);
                    final AlertDialog.Builder ad = new AlertDialog.Builder(context);
                    final EditText et = (EditText) view.findViewById(R.id.text_edit);
                    final TextView tv = (TextView) view.findViewById(R.id.text_label_popup);

                    ad.setCancelable(true);
                    ad.setView(view);
                    et.setHint(hints[j]);
                    tv.setText("Enter New " + hints[j]);

                    //Setting update button for edit dialog
                    ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String text = et.getText().toString();
                                    System.out.println("String = " + text);

                                    //Checking if there is any text in the input dialog
                                    if(text.equals("") || text.equals(null)){
                                        updateLabels();
                                    }//if there is text in the input and it is the 'name' field then
                                     //update the name value in sharedPrefrences
                                    else if(j == 0){
                                        editor.putString(labelNames[j],text);
                                        editor.commit();
                                        updateLabels();
                                    }//else it's a number input so try to parse int or float and
                                     // store in the appropriate sharedPreferences location
                                    else{
                                        //check whether or not it's age (int) or weight/height (float)
                                        if(j == 1) {
                                            int newAge;
                                            try {
                                                newAge = Integer.parseInt(text);
                                                editor.putInt(labelNames[j], newAge);
                                                editor.commit();
                                                updateLabels();
                                            } catch (Exception e) {

                                            }

                                        }else{
                                            Float newVal;
                                            try {
                                                newVal = Float.parseFloat(text);
                                                editor.putFloat(labelNames[j], newVal);
                                                editor.commit();
                                                updateLabels();
                                            } catch (Exception e) {

                                            }
                                        }

                                    }

                                }
                            }
                    );
                    ad.show();
                }
            });
        }

        editButtons[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = (LayoutInflater.from(context)).inflate(R.layout.fragment_male_female_picker,null);
                final AlertDialog.Builder ad = new AlertDialog.Builder(context);
                final RadioButton maleButton = (RadioButton) view.findViewById(R.id.male_button);
                final RadioButton femaleButton = (RadioButton) view.findViewById(R.id.female_button);



                maleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        femaleButton.setChecked(false);
                    }
                });

                femaleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        maleButton.setChecked(false);
                    }
                });
                ad.setCancelable(true);
                ad.setView(view);

                ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(maleButton.isChecked()){
                            editor.putBoolean("isMale",true);
                        }else if(femaleButton.isChecked()){
                            editor.putBoolean("isMale",false);
                        }
                        updateLabels();
                    }
                });
                ad.show();
            }
        });

        //Setting the text of the each label, loding the name, height, weight etc from SharedPreferences
        updateLabels();
    }

    private void updateLabels(){
        //recalculates bmi and then updates all labels with new values from shared preferences
        float newBmi = (float) Questions.calculateBMI(Float.toString(sharedPref.getFloat("weight",0)),Float.toString(sharedPref.getFloat("height",0)));
        editor.putFloat("bmi",newBmi);
        editor.commit();

        labels[0].setText(sharedPref.getString(labelNames[0],labelNames[0]));
        labels[1].setText(Integer.toString(sharedPref.getInt(labelNames[1],0)));
        labels[2].setText(Float.toString(sharedPref.getFloat(labelNames[2],0)));
        labels[3].setText(Float.toString(sharedPref.getFloat(labelNames[3],0)));
        String BMIstr = String.format("%.1f",sharedPref.getFloat(labelNames[4],0));
        labels[4].setText(BMIstr);
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
