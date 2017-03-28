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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


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
    public static final String USER_GOAL = "GOAL";           // daily targets?

    // Food Data Table
    public static final String TABLE_FOOD= "FOOD";
   public static final String  FOOD_PROTEIN = "PROTEIN_INTAKE";
    public static final String FOOD_DAIRY = "DAIRY_INTAKE";
    public static final String FOOD_FRUIT_VEG = "FRUIT_VEG_INTAKE";
    public static final String FOOD_CARBS = "CARBOHYDRATE_INTAKE";
    public static final String FOOD_ALCOHOL = "ALCOHOL_INTAKE";
    public static final String FOOD_WATER = "WATER_INTAKE";
    public static final String FOOD_THREATS = "TREATS_INTAKE";
    public static final String FOOD_DATE = "DATE_FOOD";



    //Exercise Data Table
    public static final String TABLE_EXERCISE = "EXERCISE";
    public static final String EXERCISE_STEPS = "STEPS";                // calories burned stored in db?
    public static final String EXERCISE_BURNED = "BURNED";
    public static final String EXERCISE_DATE = "DATE_EXERCISE";


    // List of Exercises
    public static final List<String> exercises = Arrays.asList(new String[] {"Aerobics", "Archery", "Backpacking/Hiking", "Badminton", "Basketball", "Bowling", "Boxing", "Calisthenics", "Canoeing", "Circuit training",
            "Construction", "Cricket", "Croquet", "Curling", "Cycling", "Dancing", "Dancing, Ballroom", "Diving", "Farming", "Fencing", "Fishing", "Football",
            "Forestry", "Frisbee", "Gardening", "Golf", "Martial arts", "Rowing", "Rugby", "Skiing", "Swimming", "Tennis", "Walking",
            "Water polo", "Weight lifting"});

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                +USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ USER_NAME + "TEXT," +USER_AGE +"INTEGER,"
                +USER_GENDER +"TEXT,"+USER_HEIGHT+"INTEGER," +USER_WEIGHT + "FLOAT" + USER_GOAL + "INTEGER" +");";

        String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + "("
                +FOOD_DATE + "TEXT,"
                +FOOD_PROTEIN  + "INTEGER," + FOOD_WATER + "INTEGER" + FOOD_ALCOHOL + "INTEGER," +FOOD_CARBS + "INTEGER,"
                +FOOD_FRUIT_VEG + "INTEGER," +FOOD_DAIRY + "INTEGER," + FOOD_THREATS + "INTEGER" +");";


        String CREATE_EXERCISE_TABLE = "CREATE TABLE " + TABLE_EXERCISE + "("
                +EXERCISE_DATE + "TEXT,"
                +EXERCISE_STEPS + "INTEGER," + EXERCISE_BURNED + "INTEGER" + ");";


        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_FOOD_TABLE);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
// Creating tables again
        onCreate(db);

    }






    public void addIndividual(String name, int age, String gender, int height, int weight){

        SQLiteDatabase db = this.getWritableDatabase();
        String ROW1 = "INSERT INTO " + TABLE_USER  + " ("
                + USER_NAME +", "+ USER_AGE + ", "+ USER_GENDER+", "+USER_HEIGHT+", "+USER_WEIGHT+") Values ( '" +name
        +", "+age+", "+gender+", "+height+", "+weight+"')";
        db.execSQL(ROW1);
        db.close();
    }


    // Modify user info to reflect profile changes
    public void updateWeight(double newWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_WEIGHT + " = " + newWeight);
    }

    public void updateHeight(double newHeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_WEIGHT + " = " + newHeight);
    }

    public void updateAge (int newAge)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_AGE + " = " + newAge);
    }

    public void updateGender( String newGender)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_GENDER + " = " + newGender);
    }











    // Update daily data with food intake
    public void updateDailyFood(float portionSize, String type) {
        // currently only updating current day's data
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        if (recordExistsFood(date)){
            db.execSQL("UPDATE " +  TABLE_FOOD+ " SET " + type + " = " + type + " + " + portionSize + " WHERE " + FOOD_DATE + " = " + '"'+date+'"');
        }
        else{

            db.execSQL("INSERT " + TABLE_FOOD + " SET " + type + " = " + type + " + " + portionSize + " WHERE " + FOOD_DATE + " = " + '"'+date+'"');
        }
    }

    // Update daily steps
    public void stepsUpdate(int steps){
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        if (recordExistsExercise(date)){
            db.execSQL("UPDATE " + TABLE_EXERCISE + " SET " + EXERCISE_STEPS + " = " + steps + " WHERE " + EXERCISE_DATE + " = " + '"'+date+'"');
        }
        else{

            db.execSQL("INSERT " + TABLE_EXERCISE + " SET " + EXERCISE_STEPS + " = " + steps + " WHERE " + EXERCISE_DATE + " = " + '"'+date+'"');
        }
    }



    // method to check if column (date) exists in db
    public boolean recordExistsFood(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM "
                + TABLE_FOOD + " WHERE " + FOOD_DATE + "=?";
        Cursor cursor = db.rawQuery(query, new String[] {date});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }


    public boolean recordExistsExercise(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM "
                + TABLE_EXERCISE + " WHERE " + EXERCISE_DATE + "=?";
        Cursor cursor = db.rawQuery(query, new String[] {date});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }


    // Add excersise that isn't walking
    public void add_excercise(int type, int duration){
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        int kcal = Calculate_cal(type, duration);
        if (recordExistsExercise(date)){
            db.execSQL("UPDATE " + TABLE_EXERCISE + " SET " + EXERCISE_BURNED + " = " + kcal + " WHERE " + EXERCISE_DATE + " = " + '"'+date+'"');
        }
        else{

            db.execSQL("INSERT " + TABLE_EXERCISE + " SET " + EXERCISE_BURNED + " = " + kcal + " WHERE " + EXERCISE_DATE + " = " + '"'+date+'"');
        }
    }

    // TODO calculate clories burned for given excercise
    public int Calculate_cal(int type, int duration){
        SQLiteDatabase db = this.getWritableDatabase();
        String thth = "SELECT " + USER_WEIGHT + " FROM " + TABLE_USER;
        String wei = "";
        Cursor cursor = db.rawQuery(thth, new String[] {wei});
        int weight = cursor.;
        cursor.close();
        return 0;
    }

}
