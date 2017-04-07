package com.example.powerm3.diabetesireland;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Context;

import java.util.Calendar;

public class Fitness extends AppCompatActivity  {
    private TextView textView;
    private TextView caloriesLabel;
    private EditText durationInput;
    private Button addExercise;
    private TextView totalCalories;

    private int inputDuration;
    private long activityID;
    private String[] activities;
    private float calories_burned;
    private String date;
    private int day;
    private int month;
    private int year;
    final Context context = this;
    Spinner spin;
    Button submit_button;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);
        textView = (TextView) findViewById(R.id.steps);
        caloriesLabel = (TextView) findViewById(R.id.calories_label);
        durationInput = (EditText) findViewById(R.id.duration_input);
        addExercise = (Button) findViewById(R.id.add_to_tracker_button);
        addExercise.setClickable(false);
        totalCalories = (TextView) findViewById(R.id.total_cal_label);

        int steps = getIntent().getIntExtra("Current steps", 0);
        spin = (Spinner) findViewById(R.id.spinner);
        submit_button = (Button) findViewById(R.id.submit_button);
        inputDuration = 0;
        calories_burned = 0;
        db = new DBHandler(context);
        getDate();
        updateTotalCalories();

        System.out.println(date);
        activities = getResources().getStringArray(R.array.activity_names);
        textView.setText("Steps counted = " + steps);

        double calsBurned = db.Calculate_cal(33,0.01 * steps);

        db.stepsUpdate(steps,(float) calsBurned,getDate());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.activity_names,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(spin.getSelectedItemId());
                int duration = 0;
                boolean input = false;
                try{
                    duration = Integer.parseInt(durationInput.getText().toString());
                    input = true;
                    activityID = spin.getSelectedItemId();
                    addExercise.setClickable(true);
                }catch(Exception e){
                    input = false;
                    inputDuration = 0;
                    activityID = spin.getSelectedItemId();
                    addExercise.setClickable(false);
                }
                if (input) {


                    double cal = db.Calculate_cal((int) spin.getSelectedItemId(), duration);
                    inputDuration = (int) duration;
                    System.out.printf("%.1f%n",cal);
                    String calStr = String.format("%.1f%n",cal);
                    caloriesLabel.setTextColor(0x8a000000);
                    caloriesLabel.setText("Calories Burned: " + calStr);
                    calories_burned = (float) cal;
                }else{
                    caloriesLabel.setTextColor(0xffff0000);
                    caloriesLabel.setText("Error: Please Enter A Duration");
                    calories_burned = 0;
                }
            }
        });

        durationInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int duration = 0;
                try{
                    duration = Integer.parseInt(durationInput.getText().toString());
                    activityID = spin.getSelectedItemId();
                    addExercise.setClickable(true);
                }catch(Exception e){
                    inputDuration = 0;
                    activityID = spin.getSelectedItemId();
                    addExercise.setClickable(false);
                }
            }
        });

        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Calories burned = " + calories_burned);
                db.add_exercise(activities[(int)activityID],inputDuration,calories_burned,getDate());

                System.out.println(db.getExerciseDuration(date));
                updateTotalCalories();
            }
        });
    }


    private void updateActivityLog(){


    }

    private void updateTotalCalories(){
        float totcal;
        if(db.recordExistsExercise(date)) {
            totcal = db.getTotalExerciseBurned(date);
        }else{
            totcal = 0;
        }
        totalCalories.setText("Total Calories Burned Today: " + totcal);
    }

    private String getDate(){
        Calendar c = Calendar.getInstance();
        day = c.get(c.DAY_OF_MONTH) ;
        month = c.get(c.MONTH);
        year = c.get(c.YEAR);

        date = (day + "-" + month + "-" + year);
        return date;
    }
    //This code stops the weird transition effect when the back button is pressed on the phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}