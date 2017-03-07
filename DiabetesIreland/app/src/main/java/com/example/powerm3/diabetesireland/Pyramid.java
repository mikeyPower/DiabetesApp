package com.example.powerm3.diabetesireland;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Pyramid extends AppCompatActivity {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    TextView[] texts,foodTypeTexts;
    TextView badText,fatText,proteinText,dairyText,carbText,vegText;
    int[] ptext,maxes;
    ImageButton[] buttons;
    String[] str;
    ImageView[] veg,carb,dairy,protein,fat,bad;
    ImageView[][] foodArrays;
    String[] foodTypes = new String[]{"bad","fat","protein","dairy","carb","veg"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyramid);

        sharedPref =  getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        //editor.clear();
        //editor.commit();
        str = new String[] {"one","two","three","four","five","six"};
        maxes = new int[] {1,1,2,3,5,7};
        ptext = new int[6];
        texts = new TextView[6];
        buttons = new ImageButton[6];
        foodTypeTexts = new TextView[6];



        for(int i = 0; i < texts.length; i++){
            String name = "pyramid_" + str[i];
            texts[i] = (TextView) findViewById( ( getResources().getIdentifier(name, "id", getPackageName() ) ) );
        }

        for(int i = 0; i < foodTypeTexts.length; i++){
            String name = foodTypes[i] + "Text";
            foodTypeTexts[i] = (TextView) findViewById( ( getResources().getIdentifier(name, "id",getPackageName() ) ) );
        }

        for(int i = 0; i < buttons.length; i++){
            String name = "button_pyramid_" + str[i];
            buttons[i] = (ImageButton) findViewById( (getResources().getIdentifier(name, "id" , getPackageName())) );
        }

        for(int i = 0; i < buttons.length;i++){
            final int j = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update_label(j);
                }
            });
        }
        reset_pyramid();
        for(int i = 0; i < ptext.length; i++) {
            ptext[i] = sharedPref.getInt(foodTypes[i], 0);
            update_label_nonAdd(i);

        }



        for(int i = 0; i < ptext.length; i++) {
            set_bar(i);
        }
    }

    //Increments the number of a certain food group by one and updates the label on the left hand side as well as the bar
    private void update_label(int id){
        int newVal = ptext[id];
        System.out.println("update label id = " + id + "newVal = " + newVal + " max = " + maxes[id]);
        if(newVal < maxes[id]) {
            foodArrays[id][newVal].setAlpha(0xFF);
            newVal = ++ptext[id];
            set_bar(id);
            texts[id].setText(Integer.toString(newVal));
            editor.putInt(foodTypes[id],ptext[id]);
            editor.commit();

        }
        if(newVal == maxes[id]){
            texts[id].setText(Integer.toString(newVal));
            foodTypeTexts[id].setTextColor(0xFFFF0000);
            buttons[id].setClickable(false);
        }

    }

    //Updates one of the number labels on the left of the pyramid
    private void update_label_nonAdd(int id){
        int newVal = ptext[id];
        System.out.println("update label id = " + id + "newVal = " + newVal + " max = " + maxes[id]);
        if(newVal < maxes[id]) {
            //foodArrays[id][newVal].setAlpha(0xFF);
            set_bar(id);
            texts[id].setText(Integer.toString(newVal));
            editor.putInt(foodTypes[id],ptext[id]);
            editor.commit();

        }
        if(newVal == maxes[id]){
            texts[id].setText(Integer.toString(newVal));
            foodTypeTexts[id].setTextColor(0xFFFF0000);
            buttons[id].setClickable(false);
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
        }

    }

    //This code stops the weird transition effect when the back button is pressed on the phone
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(0,0);
    }
}
