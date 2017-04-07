package com.example.powerm3.diabetesireland;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;

public class Pyramid extends AppCompatActivity {

    final int NUMBER_OF_SECTIONS = 7;
    Context context = this;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    TextView[] texts,foodTypeTexts;
    TextView badText,fatText,proteinText,dairyText,carbText,vegText;
    int[] ptext,maxes;
    ImageButton[] addButtons;
    ImageButton[] subtractButtons;
    String[] str;
    ImageView[] veg,carb,dairy,protein,fat,bad;
    ImageView[][] foodArrays;
    String[] foodTypes = new String[]{"bad","fat","protein","dairy","carb","veg","water"};
    Button reset;
    TextView caloriesLabel;
    ProgressBar water_progress;
    int [][] calNumbers;
    String[] sqlnames;
    private String date;
    private int day;
    private int month;
    private int year;

    public static final String  FOOD_PROTEIN = "PROTEIN";
    public static final String FOOD_DAIRY = "DAIRY_INTAKE";
    public static final String FOOD_FRUIT_VEG = "FRUIT_VEG";
    public static final String FOOD_CARBS = "CARBOHYDRATE";
    public static final String FOOD_FAT = "FAT";
    public static final String FOOD_WATER = "WATER";
    public static final String FOOD_THREATS = "TREATS";
    public static final String FOOD_DATE = "DATE";
    public static final String FOOD_CALORIES = "CAL";

    DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyramid);


        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

        //set up arrays
        str = new String[] {"one","two","three","four","five","six","seven"};
        maxes = new int[] {1,1,2,3,5,7,9};
        ptext = new int[NUMBER_OF_SECTIONS];
        texts = new TextView[NUMBER_OF_SECTIONS];
        addButtons = new ImageButton[NUMBER_OF_SECTIONS];
        foodTypeTexts = new TextView[NUMBER_OF_SECTIONS];
        subtractButtons = new ImageButton[NUMBER_OF_SECTIONS];
        sqlnames = new String[]{FOOD_FRUIT_VEG,FOOD_CARBS,FOOD_DAIRY,FOOD_PROTEIN,FOOD_FAT,FOOD_THREATS};
        db = new DBHandler(context);
        calNumbers = new int[][]{
                { 0 },
                { 0 },
                { 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0 }
        };

        for(int i = 0; i < maxes.length - 1; i++){
            for(int j = 0; j < maxes[i]; j++){
                String fetchString = "calVal" + i + "-" + j;
                int put = sharedPref.getInt(fetchString,0);
                calNumbers[i][j] = put;
            }
        }

        caloriesLabel = (TextView) findViewById(R.id.calorie_display);
        water_progress = (ProgressBar) findViewById(R.id.water_progress);

        water_progress.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        //set up water progress bar
        boolean isMale = sharedPref.getBoolean("isMale",true);
        int waterIn = sharedPref.getInt("water",0);
        if(isMale) {
            water_progress.setMax(13);
            maxes[6] = 13;
        }else{

            maxes[6] = 9;
            if(waterIn > 9){
                water_progress.setProgress(9);
                editor.putInt("water",9);
                editor.commit();
            }
            water_progress.setMax(9);
        }

        //set up number label for each bar on pyramid
        for(int i = 0; i < texts.length; i++){
            String name = "pyramid_" + str[i];
            texts[i] = (TextView) findViewById( ( getResources().getIdentifier(name, "id", getPackageName() ) ) );
        }
        //set up labels for each bar describing food types
        for(int i = 0; i < foodTypeTexts.length; i++){
            String name = foodTypes[i] + "Text";
            foodTypeTexts[i] = (TextView) findViewById( ( getResources().getIdentifier(name, "id",getPackageName() ) ) );
        }
        //set up add addButtons
        for(int i = 0; i < addButtons.length; i++){
            String name = "button_pyramid_" + str[i];
            addButtons[i] = (ImageButton) findViewById( (getResources().getIdentifier(name, "id" , getPackageName())) );
        }
        //set up click actions for add addButtons
        for(int i = 0; i < (addButtons.length - 1); i++){
            final int j = i;
            //System.out.println("Button = i");
            addButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update_label(j);

                    final View view = (LayoutInflater.from(context)).inflate(R.layout.fragment_calorie_input,null);
                    final AlertDialog.Builder ad = new AlertDialog.Builder(context);
                    final EditText et = (EditText) view.findViewById(R.id.calorie_input);
                    final TextView tv = (TextView) view.findViewById(R.id.calorie_label);

                    ad.setCancelable(false);
                    ad.setView(view);

                    ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int send;
                            try{
                                send = Integer.parseInt(et.getText().toString());
                            }catch(Exception e){
                                send = 0;
                            }
                            int currentCalories = sharedPref.getInt("calories",0);
                            calNumbers[j][ptext[j] - 1] =  send;
                            editor.putInt("calories",currentCalories + send);
                            String putString = "calVal" + j + "-" + (ptext[j] - 1);
                            editor.putInt(putString,send);
                            editor.commit();


                            db.updateDailyFood(send,sqlnames[j],1,getDate());


                            int cal = sharedPref.getInt("calories",0);
                            caloriesLabel.setText("Calories: " + db.getTotalCalorie(getDate()));
                        }


                    });
                    ad.show();
                }
            });
        }

        //set up subtract addButtons for pyramid bars
        for(int i = 0; i < subtractButtons.length; i++){
            String name = "button_pyramid_" + str[i] + "_sub";
            subtractButtons[i] = (ImageButton) findViewById( (getResources().getIdentifier(name,"id", getPackageName() ) ) );
        }
        //set up actions for subtract addButtons
        for(int i = 0; i < subtractButtons.length - 1; i++){
            final int j = i;
            subtractButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ptext[j] > 0) {
                        subtractlabel(j);
                        int cal = sharedPref.getInt("calories", 0);
                        cal -= calNumbers[j][ptext[j]];
                        int subtractor = calNumbers[j][ptext[j]];
                        calNumbers[j][ptext[j]] = 0;
                        editor.putInt("calories", cal);
                        editor.commit();
                        subtractor *= -1;
                        db.updateDailyFood(subtractor, sqlnames[j], -1, getDate());
                        caloriesLabel.setText("Calories: " + db.getTotalCalorie(getDate()));
                    }
                }
            });
        }

        //set up click action for water add button
        addButtons[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                water_progress.incrementProgressBy(1);
                update_label(6);
            }
        });
        //set up click action for water subtract button
        subtractButtons[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(water_progress.getProgress() > 0) {
                    water_progress.incrementProgressBy(-1);
                    subtractlabel(6);
                }
            }
        });

        //sets the pyramid to blank before filling it using values in shared preferences
        reset_pyramid();

        //set up values of number labels beside each pyramid bar
        for(int i = 0; i < ptext.length - 1; i++) {
            ptext[i] = db.getFoodTypeAmount(getDate(),sqlnames[i]);
            update_label_nonAdd(i);

        }
        //sets up each bar of pyramid based on values in shared preferences
        for(int i = 0; i < ptext.length - 1; i++) {
            set_bar(i);
        }


        for(int i = 0; i < NUMBER_OF_SECTIONS; i++){

        }
        water_progress.setProgress(ptext[6]);



        //set up reset button
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < foodTypes.length; i++){
                    editor.remove(foodTypes[i]);
                }
                editor.remove("calories");
                caloriesLabel.setText("Calories: " + 0);
                editor.clear();           //uncomment if you want reset button to reset all
                //                          //sharedPreferences
                String DB_NAME = "DiabetesIreland";
                context.deleteDatabase(DB_NAME);
                editor.commit();
                reset_pyramid();

            }
        });
        if(!db.recordExistsFood(getDate())){
            reset_pyramid();
        }
        int cal = 0;
        if(db.recordExistsFood(getDate())){
            caloriesLabel.setText("Calories: " + db.getTotalCalorie(getDate()));
        }else{
            caloriesLabel.setText("Calories: " + cal);
        }



    }

    //Increments the number of a certain food group by one and updates the label on the left hand side as well as the bar
    private void update_label(int id){
        int newVal = ptext[id];
        //System.out.println("update label id = " + id + "newVal = " + newVal + " max = " + maxes[id]);
        if(newVal < maxes[id]) {

            newVal = ++ptext[id];
            if(id < 6) {
                foodArrays[id][newVal - 1].setAlpha(0xFF);
                set_bar(id);
            }
            texts[id].setText(Integer.toString(newVal));
            editor.putInt(foodTypes[id],ptext[id]);
            editor.commit();
            subtractButtons[id].setClickable(true);
        }
        if(newVal == maxes[id]){
            texts[id].setText(Integer.toString(newVal));
            foodTypeTexts[id].setTextColor(0xFFFF0000);
            addButtons[id].setClickable(false);
        }
        int cal = 0;
        if(db.recordExistsFood(getDate())){
            caloriesLabel.setText("Calories: " + db.getTotalCalorie(getDate()));
        }else{
            caloriesLabel.setText("Calories: " + cal);
        }


    }

    //Updates one of the number labels on the left of the pyramid
    private void update_label_nonAdd(int id){
        int newVal = ptext[id];
        //System.out.println("update label id = " + id + "newVal = " + newVal + " max = " + maxes[id]);
        if(newVal < maxes[id]) {
            //foodArrays[id][newVal].setAlpha(0xFF);
            if(id < 6) {
                set_bar(id);
            }
            texts[id].setText(Integer.toString(newVal));
            //editor.putInt(foodTypes[id],ptext[id]);
            //editor.commit();

        }
        if(newVal == maxes[id]){
            texts[id].setText(Integer.toString(newVal));
            foodTypeTexts[id].setTextColor(0xFFFF0000);
            addButtons[id].setClickable(false);
        }

    }

    private void subtractlabel(int id){
        int newVal = ptext[id];
        //System.out.println("update label id = " + id + "newVal = " + newVal + " max = " + maxes[id]);
        if(newVal > 0) {
            //foodArrays[id][newVal].setAlpha(0xFF);
            newVal = --ptext[id];
            if(id < 6) {
                set_bar(id);

            }
            texts[id].setText(Integer.toString(newVal));
            addButtons[id].setClickable(true);
            editor.putInt(foodTypes[id],ptext[id]);
            editor.commit();
            foodTypeTexts[id].setTextColor(0xFF000000);

        }
        if(newVal == 0){
            texts[id].setText(Integer.toString(newVal));
            if(id < 6) {
                set_bar(id);
            }
            foodTypeTexts[id].setTextColor(0xFF000000);
            subtractButtons[id].setClickable(false);
        }
    }

    //Updates a certain bar of the pyramid
    private void set_bar(int id){
        String currentFood;

        ImageView[] i = foodArrays[id];
            currentFood = foodTypes[id];

            for(int j = 0; j < foodArrays[id].length;j++){
                foodArrays[id][j] = (ImageView) findViewById( (getResources().getIdentifier( (currentFood + j), "id", getPackageName() )) );
                if(j < ptext[id]) {
                    foodArrays[id][j].setAlpha(0xFF);
                }else{
                    foodArrays[id][j].setAlpha(75);
                }
            }

    }
    //Sets the pyramid to totally empty
    private void reset_pyramid(){
        veg = new ImageView[7];
        carb = new ImageView[5];
        dairy = new ImageView[3];
        protein = new ImageView[2];
        fat = new ImageView[1];
        bad = new ImageView[1];
        foodArrays = new ImageView[][]{bad,fat,protein,dairy,carb,veg};

        String currentFood;
        for(int i = 0; i < foodArrays.length;i++){
            currentFood = foodTypes[i];

            for(int j = 0; j < foodArrays[i].length;j++){
                foodArrays[i][j] = (ImageView) findViewById( (getResources().getIdentifier( (currentFood + j), "id", getPackageName() )) );
                foodArrays[i][j].setAlpha(75);
            }
            ptext[i] = 0;
            foodTypeTexts[i].setTextColor(0xFF000000);
            addButtons[i].setClickable(true);
            update_label_nonAdd(i);
        }




    }

    private String getDate(){
        Calendar c = Calendar.getInstance();
        day = c.get(c.DAY_OF_MONTH);
        month = c.get(c.MONTH);
        year = c.get(c.YEAR);

        date = (day + "-" + month + "-" + year);
        return date;
    }

    //This code stops the weird transition effect when the back button is pressed on the phone
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(0,0);
    }
}