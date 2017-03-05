package com.example.powerm3.diabetesireland;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Pyramid extends AppCompatActivity {

    ImageButton pyramid_Button_One, pyramid_Button_Two,pyramid_Button_Three,pyramid_Button_Four,pyramid_Button_Five,pyramid_Button_Six;


    TextView pyramid_one,pyramid_two,pyramid_three,pyramid_four,pyramid_five,pyramid_six;
    TextView[] texts;
    int[] ptext;
    int[] maxes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyramid);

        ptext = new int[6];
        for(int i = 0;i < ptext.length;i++ ){
            ptext[i] = 0;
        }

        maxes = new int[] {1,1,2,3,5,7};
        String[] str = {"one","two","three","four","five","six"};
        pyramid_Button_One = (ImageButton) findViewById(R.id.button_pyramid_one);
        pyramid_Button_Two = (ImageButton) findViewById(R.id.button_pyramid_two);
        pyramid_Button_Three = (ImageButton) findViewById(R.id.button_pyramid_three);
        pyramid_Button_Four = (ImageButton) findViewById(R.id.button_pyramid_four);
        pyramid_Button_Five = (ImageButton) findViewById(R.id.button_pyramid_five);
        pyramid_Button_Six = (ImageButton) findViewById(R.id.button_pyramid_six);
        ImageButton[] buttons = {pyramid_Button_One,pyramid_Button_Two,pyramid_Button_Three,pyramid_Button_Four,pyramid_Button_Five,pyramid_Button_Six};
        pyramid_one = (TextView) findViewById(R.id.pyramid_one);
        pyramid_two = (TextView) findViewById(R.id.pyramid_two);
        pyramid_three = (TextView) findViewById(R.id.pyramid_three);
        pyramid_four = (TextView) findViewById(R.id.pyramid_four);
        pyramid_five = (TextView) findViewById(R.id.pyramid_five);
        pyramid_six = (TextView) findViewById(R.id.pyramid_six);

        texts = new TextView[6];
        texts[0] = pyramid_one;
        texts[1] = pyramid_two;
        texts[2] = pyramid_three;
        texts[3] = pyramid_four;
        texts[4] = pyramid_five;
        texts[5] = pyramid_six;

        for(int i = 0; i < buttons.length;i++){
            final int j = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    update_label(j);
                }
            });
        }
    }

    private void update_label(int id){
        int newVal = ptext[id];

        if(newVal < maxes[id])
            newVal = ++ptext[id];

        texts[id].setText(Integer.toString(newVal));
    }

    //This code stops the weird transition effect when the back button is pressed on the phone
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(0,0);
    }
}
