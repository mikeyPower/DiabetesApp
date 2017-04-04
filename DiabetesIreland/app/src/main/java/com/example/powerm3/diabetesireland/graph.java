package com.example.powerm3.diabetesireland;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;

public class graph extends AppCompatActivity {

    int[] caloriesWeekBurned;
    int[] caloriesWeekIntake;
    DBHandler db;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        db = new DBHandler(context);
        caloriesWeekBurned = new int[7];
        caloriesWeekIntake = new int[7];

        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(7);
        graph.getGridLabelRenderer().setNumHorizontalLabels(7);
        graph.setTitle("Number of Calories Burned vs Consumed \nLast Seven Days");
        getLastSevenDaysBurned();
        getLastSevenDaysIntake();

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

        graph.addSeries(burnedSeries);
        graph.addSeries(intakeSeries);
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

    //This code stops the weird transition effect when the back button is pressed on the phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
