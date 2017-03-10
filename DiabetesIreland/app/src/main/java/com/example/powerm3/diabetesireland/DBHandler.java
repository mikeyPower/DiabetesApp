package com.example.powerm3.diabetesireland;

/**
 * Created by powerm3 on 10/03/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class DBHandler extends SQLiteOpenHelper {



    // Database Version
    private static final int DB_VERSION = 1;

    // Database Name
    public static final String DB_NAME = "DiabetesIreland";

    // User Data Table
    public static final String TABLE_USER = "USER";
    public static final String USER_AGE = "AGE";     // primary key
    public static final String USER_GENDER = "GENDER";         // male / female
    public static final String USER_HEIGHT = "HEIGHT";         // in metres or/and feet
    public static final String USER_WEIGHT= "WEIGHT";         // in kg/other
    public static final String USER_ID = "ID";
    public static final String USER_NAME = "NAME";
    //public static final String USER_GOAL = "GOAL";           // daily targets?

    // Food Data Table
    public static final String TABLE_FOOD= "FOOD";
  //  public static final String DCOL_1 = "DATE";           // primary key
  //  public static final String DCOL_2 = "FAT_INTAKE";
   public static final String  FOOD_PROTEIN = "PROTEIN_INTAKE";
    public static final String FOOD_DAIRY = "DAIRY_INTAKE";
    public static final String FOOD_FRUIT_VEG = "FRUIT_VEG_INTAKE";
    public static final String FOOD_CARBS = "CARBOHYDRATE_INTAKE";
    public static final String FOOD_ALCOHOL = "ALCOHOL_INTAKE";
    public static final String FOOD_WATER = "WATER_INTAKE";
    public static final String FOOD_THREATS = "TREATS_INTAKE";






    //Exercise Data Table
    public static final String TABLE_EXERCISE = "EXERCISE";
    public static final String EXERCISE_STEPS = "STEPS";                // calories burned stored in db?
    public static final String EXERCISE_BURNED = "BURNED";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                +USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ USER_NAME + "TEXT," +USER_AGE +"INTEGER,"
                +USER_GENDER +"TEXT,"+USER_HEIGHT+"INTEGER," +USER_WEIGHT + "INTEGER" +");";

        String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + "("
                +FOOD_PROTEIN  + "INTEGER," + FOOD_ALCOHOL + "INTEGER," +FOOD_CARBS + "INTEGER,"
                +FOOD_FRUIT_VEG + "INTEGER," +FOOD_DAIRY + "INTEGER," + FOOD_THREATS + "INTEGER" +");";


        String CREATE_EXERCISE_TABLE = "CREATE TABLE " + TABLE_EXERCISE + "("
                +EXERCISE_STEPS + "INTEGER," + EXERCISE_BURNED + "INTEGER" + ");";


        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_FOOD_TABLE);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
