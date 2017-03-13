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
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    final Context context = this;
    TextView[] labels;
    ImageButton[] editButtons;
    String[] labelNames;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    String[] hints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

        labels = new TextView[6];
        editButtons = new ImageButton[6];
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
        }

        for(int i = 0; i < 4; i++){
            final int j = i;
            editButtons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    View view = (LayoutInflater.from(context)).inflate(R.layout.fragment_edit__alert,null);

                    final AlertDialog.Builder ad;
                    ad = new AlertDialog.Builder(context);
                    final EditText et = (EditText) view.findViewById(R.id.text_edit);
                    et.setHint(hints[j]);
                    ad.setCancelable(true);
                    ad.setView(view);

                    TextView tv = (TextView) view.findViewById(R.id.text_label_popup);
                    tv.setText("Enter New " + hints[j]);


                    ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String text = et.getText().toString();
                                    System.out.println("String = " + text);
                                    if(text.equals("") || text.equals(null)){
                                        updateLabels();
                                    }else if(j == 0){
                                        editor.putString(labelNames[j],text);
                                        editor.commit();
                                        updateLabels();
                                    }else{
                                        Float newAge;
                                        try{
                                            newAge = Float.parseFloat(text);
                                            editor.putFloat(labelNames[j],newAge);
                                            editor.commit();
                                            updateLabels();
                                        }catch(Exception e){

                                        }

                                    }

                                }
                            }
                    );




                    ad.show();
                }
            });
        }
        //Setting the text of the each label, loding the name, height, weight etc from SharedPreferences
        updateLabels();
    }

    private void updateLabels(){
        float newBmi = (float) Questions.calculateBMI(Float.toString(sharedPref.getFloat("weight",0)),Float.toString(sharedPref.getFloat("height",0)));
        editor.putFloat("bmi",newBmi);
        editor.commit();
        
        labels[0].setText(sharedPref.getString(labelNames[0],labelNames[0]));
        labels[1].setText(Float.toString(sharedPref.getFloat(labelNames[1],0)));
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
