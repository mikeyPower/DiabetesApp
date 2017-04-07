package com.example.powerm3.diabetesireland;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;

public class graph extends AppCompatActivity {

    int[] caloriesWeekBurned;
    int[] caloriesWeekIntake;
    DBHandler db;
    final Context context = this;
    LegendRenderer lr;
    Button addButton,subtractButton;
    TextView dateLabel,burnedLabel,consumedLabel;
    final String dateLabelStr = " days ago";
    final String today = "Today";
    int currentDaysAgo;
    float currentDaysCalories;
    float currentDaysExercise;
    GraphView graph;
    LineGraphSeries<DataPoint> test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        db = new DBHandler(context);
        caloriesWeekBurned = new int[7];
        caloriesWeekIntake = new int[7];

        currentDaysAgo = 0;

        dateLabel = (TextView) findViewById(R.id.date_label);
        burnedLabel = (TextView) findViewById(R.id.burned_label);
        consumedLabel = (TextView) findViewById(R.id.consumed_label);

        addButton = (Button) findViewById(R.id.add_button);
        subtractButton = (Button) findViewById(R.id.subtract_button);

        graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(7);
        graph.getGridLabelRenderer().setNumHorizontalLabels(7);
        graph.setTitle("Number of Calories Burned vs Consumed \nLast Seven Days");

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setFixedPosition(10,10);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        getLastSevenDaysIntake();
        getLastSevenDaysBurned();



        LineGraphSeries<DataPoint> burnedSeries = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, caloriesWeekBurned[6]),
                new DataPoint(2, caloriesWeekBurned[5]),
                new DataPoint(3, caloriesWeekBurned[4]),
                new DataPoint(4, caloriesWeekBurned[3]),
                new DataPoint(5, caloriesWeekBurned[2]),
                new DataPoint(6, caloriesWeekBurned[1]),
                new DataPoint(7, caloriesWeekBurned[0])
        });


        LineGraphSeries<DataPoint> intakeSeries = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, caloriesWeekIntake[6]),
                new DataPoint(2, caloriesWeekIntake[5]),
                new DataPoint(3, caloriesWeekIntake[4]),
                new DataPoint(4, caloriesWeekIntake[3]),
                new DataPoint(5, caloriesWeekIntake[2]),
                new DataPoint(6, caloriesWeekIntake[1]),
                new DataPoint(7, caloriesWeekIntake[0])
        });

        intakeSeries.setColor(Color.RED);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph.addSeries(intakeSeries);
        graph.addSeries(burnedSeries);
        drawIndicator();

        intakeSeries.setTitle("Calories Consumed");
        burnedSeries.setTitle("Calories Burned");



        dateLabel.setText(today);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentDaysAgo > 0){
                    subtractButton.setClickable(true);

                    if(--currentDaysAgo > 0){
                        dateLabel.setText(currentDaysAgo + dateLabelStr);
                    }else{
                        dateLabel.setText(today);
                        addButton.setClickable(false);
                    }

                }else{
                    addButton.setClickable(false);
                }
                updateRecords();
                drawIndicator();
                System.out.println("CurrentDaysAgo = " + currentDaysAgo);
            }
        });

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton.setClickable(true);
                if (currentDaysAgo < 6) {
                    currentDaysAgo++;
                    updateRecords();
                    if (currentDaysAgo != 1) {
                        dateLabel.setText(currentDaysAgo + dateLabelStr);
                    }else{
                        dateLabel.setText(currentDaysAgo + "day ago");
                    }
                    if(currentDaysAgo == 6){
                        subtractButton.setClickable(false);
                    }

                } else {
                    updateRecords();
                    dateLabel.setText(currentDaysAgo + dateLabelStr);
                    subtractButton.setClickable(false);

                }
                drawIndicator();
                System.out.println("CurrentDaysAgo = " + currentDaysAgo);
            }
        });

        addButton.setClickable(false);
        updateRecords();
    }

    private void getLastSevenDaysBurned(){
        String query;
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        String testDate;

        for(int i = 0; i < 7; i ++){
            testDate  = ((day - i) + "-" + month + "-" + year);
            if(db.recordExistsExercise(testDate)) {
                caloriesWeekBurned[i] = (int) db.getTotalExerciseBurned(testDate);
            }else{
                caloriesWeekBurned[i] = 0;
            }
        }

    }

    private void getLastSevenDaysIntake(){
        String query;
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        String testDate;

        for(int i = 0; i < 7; i ++){
            testDate  = ((day - i) + "-" + month + "-" + year);
            if(db.recordExistsFood(testDate)) {
                caloriesWeekIntake[i] = (int) db.getTotalCalorie(testDate);
            }else{
                caloriesWeekIntake[i] = 0;
            }
        }



    }

    private String getDate(int modifier){
        Calendar c = Calendar.getInstance();
        int day = c.get(c.DAY_OF_MONTH) - modifier ;
        int month = c.get(c.MONTH);
        int year = c.get(c.YEAR);

        String date = (day + "-" + month + "-" + year);
        return date;
    }

    private void updateLabels(){
        consumedLabel.setText("Calories Consumed: " + currentDaysCalories);
        burnedLabel.setText("Calories Burned " + currentDaysExercise);

    }

    private void updateRecords(){
        if(db.recordExistsExercise(getDate(currentDaysAgo))) {
            currentDaysExercise = db.getTotalExerciseBurned(getDate(currentDaysAgo));
        }else{
            currentDaysExercise = 0;
        }

        if(db.recordExistsFood(getDate(currentDaysAgo))) {
            currentDaysCalories = db.getTotalCalorie(getDate(currentDaysAgo));
        }else{
            currentDaysCalories = 0;
        }
        updateLabels();
    }

    private void drawIndicator(){
        graph.removeSeries(test);
        float cals;
        if(caloriesWeekIntake[currentDaysAgo] > caloriesWeekBurned[currentDaysAgo]){
            cals = caloriesWeekIntake[currentDaysAgo];
        }else{
            cals = caloriesWeekBurned[currentDaysAgo];
        }
        test = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(7 - currentDaysAgo,0),
                new DataPoint(7 - currentDaysAgo,cals)


        });

        test.setColor(Color.BLACK);

        test.setDataPointsRadius(5);
        test.setDrawDataPoints(true);
        test.setTitle("Selected Day");
        graph.addSeries(test);

    }
    //This code stops the weird transition effect when the back button is pressed on the phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
