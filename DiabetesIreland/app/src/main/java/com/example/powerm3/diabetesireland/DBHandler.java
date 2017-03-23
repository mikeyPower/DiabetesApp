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
import java.util.Date;
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
    public static final String USER_CALORIES = "GOAL";           // daily targets?

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
    public static final String FOOD_CALORIES = "CAL";
    public static final String FOOD_ID = "FoodID";
    public static final String FOOD_FOREIGN_ID = "Food_Foreign_ID";


    //Exercise Data Table
    public static final String TABLE_EXERCISE = "EXERCISE";
    public static final String EXERCISE_STEPS = "STEPS";                // calories burned stored in db?
    public static final String EXERCISE_BURNED = "BURNED";
    public static final String EXERCISE_DATE = "DATE_EXERCISE";
    public static final String EXERCISE_ID = "ExerciseID";
    public static final String EXERCISE_FOREIGN_ID = "ExerciseForeignID";
    public static final String EXERCISE_TYPE ="TYPE";
    public static final String EXERCISE_DURATION = "DURATION";

    public DBHandler(Context context) {

        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                +USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ USER_NAME + "TEXT," +USER_AGE +"INTEGER,"
                +USER_GENDER +"TEXT,"+USER_HEIGHT+"INTEGER," +USER_WEIGHT + "FLOAT" + USER_CALORIES + "INTEGER" +");";

        String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + "("
                +FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +FOOD_DATE + "TEXT,"
                +FOOD_PROTEIN  + "INTEGER," + FOOD_WATER + "INTEGER," + FOOD_ALCOHOL + "INTEGER," +FOOD_CARBS + "INTEGER,"
                +FOOD_FRUIT_VEG + "INTEGER," +FOOD_DAIRY + "INTEGER," + FOOD_THREATS + "INTEGER,"
                + "FOREIGN KEY ("+FOOD_FOREIGN_ID+") REFERENCES " + TABLE_USER +" ("+USER_ID+")"
                +");";


        String CREATE_EXERCISE_TABLE = "CREATE TABLE " + TABLE_EXERCISE + "("
                +EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +EXERCISE_DATE + "TEXT,"
                +EXERCISE_STEPS + "INTEGER," + EXERCISE_BURNED + "INTEGER,"
                +EXERCISE_TYPE + "TEXT,"+ EXERCISE_DURATION + "INTEGER,"
                + "FOREIGN KEY ("+EXERCISE_FOREIGN_ID+") REFERENCES " + TABLE_USER +" ("+USER_ID+")"
                + ");";


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






    public void addIndividual(String name, int age, String gender, float height, float weight, int calories){

        SQLiteDatabase db = this.getWritableDatabase();
        String ROW1 = "INSERT INTO " + TABLE_USER  + " ("
                + USER_NAME +", "+ USER_AGE + ", "+ USER_GENDER+", "+USER_HEIGHT+", "+USER_WEIGHT+ ", " + USER_CALORIES+") Values ( '" +name
        +", "+age+", "+gender+", "+height+", "+weight+", "+calories+"')";
        db.execSQL(ROW1);
        db.close();
    }


    // Modify user info to reflect profile changes
    public void updateWeight(float newWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_WEIGHT + " = " + newWeight+ "WHERE "+ USER_ID +" = "+1);
    }

    public void updateHeight(float newHeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_WEIGHT + " = " + newHeight+ "WHERE "+ USER_ID +" = "+1);
    }

    public void updateAge (int newAge)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_AGE + " = " + newAge+ "WHERE "+ USER_ID +" = "+1);
    }

    public void updateGender( String newGender)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_GENDER + " = " + newGender + "WHERE "+ USER_ID +" = "+1);
    }
    public void updateCalories( int newCalories)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_CALORIES + " = " + newCalories  + "WHERE "+ USER_ID +" = "+1);
    }



    

    // Update daily data with food intake
    public void updateDailyFood(float calSize, String type, int amount) {
        // currently only updating current day's data
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        if (recordExistsFood(date)){
            db.execSQL("UPDATE " +  TABLE_FOOD+ " SET " + type + " = " +  amount +","+ FOOD_CALORIES + " = "+calSize+ " WHERE " + FOOD_DATE + " = " + '"'+date+'"');
        }
        else{
            //insert everything null then update the column we want
          //  db.execSQL("UPDATE " +  TABLE_FOOD+ " SET " + type + " = " +  amount + " WHERE " + FOOD_DATE + " = " + '"'+date+'"');

            String ROW1 = "INSERT INTO " + TABLE_FOOD  + " ("
                    +FOOD_DATE + ","
                    +FOOD_PROTEIN  + "," + FOOD_WATER + "" + FOOD_ALCOHOL + "," +FOOD_CARBS + ","
                    +FOOD_FRUIT_VEG + "," +FOOD_DAIRY + "," + FOOD_THREATS + ","
                    +FOOD_FOREIGN_ID


                    +") Values ( '" +date
                    +", "+null+", "+null+", "+null+", "+null+", "+", "+null+", "
                    +", "+null+", "+", "+null+","+1+"')";
            db.execSQL(ROW1);


            db.execSQL("UPDATE " +  TABLE_FOOD+ " SET " + type + " = " +  amount +","+ FOOD_CALORIES + " = "+calSize+ " WHERE " + FOOD_DATE + " = " + '"'+date+'"');
            db.close();

        }
    }

    // Update daily steps
    public void stepsUpdate(int steps, float calBurned){
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        if (recordExistsExercise(date)){
            db.execSQL("UPDATE " + TABLE_EXERCISE + " SET " + EXERCISE_STEPS + " = " + steps + ","+ EXERCISE_BURNED +" = "+calBurned+" WHERE " + EXERCISE_DATE + " = " + '"'+date+'"');
        }
        else{


            String ROW1 = "INSERT INTO " + TABLE_EXERCISE  + " ("
                    + EXERCISE_DATE +", "+ EXERCISE_TYPE + ", "+ EXERCISE_DURATION+", "
                    +EXERCISE_BURNED+", "+EXERCISE_STEPS+ ", " + EXERCISE_FOREIGN_ID+") Values ( '" +date
                    +", "+null+", "+null+", "+calBurned+", "+steps+", "+1+"')";
            db.execSQL(ROW1);


            db.execSQL("UPDATE " + TABLE_EXERCISE + " SET " + EXERCISE_STEPS + " = " + steps + ","+ EXERCISE_BURNED +" = "+calBurned+" WHERE " + EXERCISE_DATE + " = " + '"'+date+'"');
            db.close();
        }
    }



    // Add excersise that isn't walking
    public void add_excercise(String type, int duration, float burned){
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());
        // int kcal = Calculate_cal(type, duration);
        if (recordExistsExercise(date)){
            db.execSQL("UPDATE " + TABLE_EXERCISE + " SET " + EXERCISE_BURNED + " = " + burned + " WHERE " + EXERCISE_DATE + " = " + '"'+date+'"');
        }
        else{
            String ROW1 = "INSERT INTO " + TABLE_EXERCISE  + " ("
                    + EXERCISE_DATE +", "+ EXERCISE_TYPE + ", "+ EXERCISE_DURATION+", "
                    +EXERCISE_BURNED+", "+EXERCISE_STEPS+ ", " + EXERCISE_FOREIGN_ID+") Values ( '" +date
                    +", "+type+", "+duration+", "+burned+", "+null+", "+1+"')";
            db.execSQL(ROW1);
            db.close();
        }
    }













    public void deleteExerciseDate (String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_EXERCISE + "WHERE "+ EXERCISE_DATE+ "="+ date);
    }


    public void deleteFoodDate (String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_FOOD + "WHERE "+ FOOD_DATE+ "="+ date);

    }






    // method to check if column (date) exists in db
  /*  public boolean recordExistsFood(String date){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM "
                + TABLE_FOOD + " WHERE " + FOOD_DATE + "=?";
        Cursor cursor = db.rawQuery(query, new String[] {date});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
*/

    public boolean recordExistsExercise(String date){
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "Select * from " + TABLE_EXERCISE + " where " + EXERCISE_DATE + " = " + date;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean recordExistsFood(String date){
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "Select * from " + TABLE_FOOD + " where " + FOOD_DATE + " = " + date;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

































    // TODO calculate clories burned for given excercise
    public int Calculate_cal(String type, int duration){
        return 0;
    }

}
