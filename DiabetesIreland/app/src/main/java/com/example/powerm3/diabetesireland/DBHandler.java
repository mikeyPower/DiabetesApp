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
    public static final String USER_CALORIES = "GOAL";           // daily targets?

    // Food Data Table
    public static final String TABLE_FOOD= "FOOD";
    public static final String  FOOD_PROTEIN = "PROTEIN";
    public static final String FOOD_DAIRY = "DAIRY_INTAKE";
    public static final String FOOD_FRUIT_VEG = "FRUIT_VEG";
    public static final String FOOD_FAT = "FAT";
    public static final String FOOD_CARBS = "CARBOHYDRATE";
    public static final String FOOD_ALCOHOL = "ALCOHOL";
    public static final String FOOD_WATER = "WATER";
    public static final String FOOD_THREATS = "TREATS";
    public static final String FOOD_DATE = "DATE";
    public static final String FOOD_CALORIES = "CAL";
    public static final String FOOD_ROW_ID = "FoodID";
    public static final String FOOD_FOREIGN_ID = "Food_Foreign_ID";


    //Exercise Data Table
    public static final String TABLE_EXERCISE = "EXERCISE";
    public static final String EXERCISE_STEPS = "STEPS";                // calories burned stored in db?
    public static final String EXERCISE_TOTAL_BURNED = "BURNED";
    public static final String EXERCISE_DATE = "DATE_EXERCISE";
    public static final String EXERCISE_ROW_ID = "ExerciseID";
    public static final String EXERCISE_FOREIGN_ID = "ExerciseForeignID";
    public static final String EXERCISE_DURATION = "DURATION";

    public static final String EXERCISE_TYPE_AEROBICS ="AEROBICS";
    public static final String EXERCISE_TYPE_ARCHERY ="ARCHERY";
    public static final String EXERCISE_TYPE_BACKPACKING ="BACKPACKING";
    public static final String EXERCISE_TYPE_HIKING ="HIKING";
    public static final String EXERCISE_TYPE_BADMINTON ="BADMINTON";
    public static final String EXERCISE_TYPE_DANCING ="DANCING";
    public static final String EXERCISE_TYPE_BALLROOM ="BALLROOM DANCING";
    public static final String EXERCISE_TYPE_BASKETBALL ="BASKETBALL";
    public static final String EXERCISE_TYPE_BOWLING ="BOWLING";
    public static final String EXERCISE_TYPE_BOXING ="BOXING";
    public static final String EXERCISE_TYPE_CALISTHENICS ="CALISTHENICS";
    public static final String EXERCISE_TYPE_CANOEING ="CANOEING";
    public static final String EXERCISE_TYPE_ROWING ="ROWING";
    public static final String EXERCISE_TYPE_CARPENTRY ="CARPENTRY";
    public static final String EXERCISE_TYPE_CARRYING ="CARRYING INFANT";
    public static final String EXERCISE_TYPE_CHILDREN ="CHILDRENS GAMES";
    public static final String EXERCISE_TYPE_CIRCUIT ="CIRCUIT TRAINING";
    public static final String EXERCISE_TYPE_CLEANING ="CLEANING";
    public static final String EXERCISE_TYPE_CLIMBING ="CLIMBING HILLS";
    public static final String EXERCISE_TYPE_CONSTRUCTION ="CONSTRUCTION";
    public static final String EXERCISE_TYPE_CRICKET ="CRICKET";
    public static final String EXERCISE_TYPE_CROQUET ="CROQUET";
    public static final String EXERCISE_TYPE_CURLING ="CURLING";
    public static final String EXERCISE_TYPE_CYCLING ="CYCLING";
    public static final String EXERCISE_TYPE_DARTS ="DARTS";
    public static final String EXERCISE_TYPE_DRIVING ="DRIVING";
    public static final String EXERCISE_TYPE_DOWNHILL ="DOWNHILL SNOW SKIING";
    public static final String EXERCISE_TYPE_FARMING ="FARMING";
    public static final String EXERCISE_TYPE_FENCING ="FENCING";
    public static final String EXERCISE_TYPE_FISHING ="FISHING";
    public static final String EXERCISE_TYPE_FOOTBALL ="FOOTBALL";
    public static final String EXERCISE_TYPE_FORESTRY ="FORESTRY";
    public static final String EXERCISE_TYPE_FRISBEE ="FRISBEE";
    public static final String EXERCISE_TYPE_GARDENING ="GARDENING";
    public static final String EXERCISE_TYPE_GOLF ="GOLF";
    public static final String EXERCISE_TYPE_TENIS ="TENIS";
    public static final String EXERCISE_TYPE_WALKING ="WALKING";
    public static final String EXERCISE_TYPE_SWIMMING ="SWIMMING LAPS";
    public static final String EXERCISE_TYPE_WATER ="WATER POLO";
    public static final String EXERCISE_TYPE_RUGBY ="RUGBY";
    public static final String EXERCISE_TYPE_MARTIAL ="MARTIAL ARTS";
    public static final String EXERCISE_TYPE_WEIGHT ="WEIGHT LIFTING";

    public static final double[] calorieInfoArray = new double [] { 6.4,7.6,8.8,10,3.45,4.1,4.7,5.4,6.8,8.2,9.5,10.8,
            4.4,5.2,6.1,7,5.9,7,8.2,9.3,3,3.5,4.1,4.7,8.8,10.6,12.2,14,3.5,4.1,4.8,5.4,3.9,4.7,5.5,6.2,7.9,
            9.4,10.9,12.4,5.4,6.5,7.5,8.5,4.9,5.9,6.8,7.8,2.5,2.9,3.4,3.9,3.9,4.7,5.5,6.2,7.9,9.4,10.9,12.4,
            //  Dancing
            4.4
            ,  5.3
            , 6.1
            ,7
            //     Ballroom dancing
            , 3.7
            ,5
            ,7
            ,8
            //      Diving
            ,3
            ,3.5
            ,4.1
            ,4.7
            //        Farming, baling hay, cleaning barn
            ,7.9
            ,9.4
            ,10.9
            ,12.4
            //        Fencing
            , 5.9
            ,7
            , 8.2
            ,9.3
            //        Fishing
            , 3
            ,    3.5
            ,   4.1
            ,  4.7
            //        Football
            , 8.8
            ,10.6
            ,12.2
            ,14
            //        Forestry, ax chopping
            ,  4.9
            ,      5.9
            ,     6.8
            ,    7.8
            //        Frisbee, ultimate frisbee
            ,     7.9
            ,     9.4
            ,     10.9
            ,     12.4
            //        Gardening
            ,     3.9
            ,     4.7
            ,     5.5
            ,     6.2
            //      Golf
            ,     4.4
            ,     5.3
            ,     6.1
            ,     7
            //        Martial arts, kick boxing
            ,     9.8
            ,     11.7
            ,     13.6
            ,     15.5
            //        Rowing
            ,    6.9
            ,    8.2
            ,    9.5
            ,    10.8
            //        Rugby
            ,    9.8
            ,    11.7
            ,    13.6
            ,    15.5
            //       Skiing
            ,   5.9
            ,  7
            ,  8.2
            , 9.3
            //        Swimming laps
            ,6.9
            ,8.2
            ,9.5
            ,10.8
            //       Tennis
            ,7.9
            ,9.4
            ,10.9
            ,12.4
            //        Walking
            ,3
            ,3.5
            ,4.1
            ,4.7
            //        Water polo
            ,9.8
            ,11.7
            ,13.6
            ,15.5
            //        Weight lifting, workout
            ,3.7
            ,5.2
            ,7.4
            ,8.3 };

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
                +USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ USER_NAME + " TEXT," +USER_AGE +" INTEGER,"
                +USER_GENDER +" TEXT,"+USER_HEIGHT+" INTEGER," +USER_WEIGHT + " FLOAT," + USER_CALORIES + " INTEGER" +");";

        String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + "("
                +FOOD_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +FOOD_FOREIGN_ID + " INTEGER,"
                +FOOD_DATE + " TEXT,"
                +FOOD_CALORIES + " INTEGER,"
                +FOOD_FAT + " INTEGER,"
                +FOOD_PROTEIN  + " INTEGER,"
                + FOOD_WATER + " INTEGER,"
                + FOOD_ALCOHOL + " INTEGER,"
                +FOOD_CARBS + " INTEGER,"
                +FOOD_FRUIT_VEG + " INTEGER,"
                +FOOD_DAIRY + " INTEGER,"
                + FOOD_THREATS + " INTEGER,"

                + "FOREIGN KEY ("+FOOD_FOREIGN_ID+") REFERENCES " + TABLE_USER +" ("+USER_ID+")"
                +");";


        String CREATE_EXERCISE_TABLE = "CREATE TABLE " + TABLE_EXERCISE + " ("
               +EXERCISE_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EXERCISE_DURATION + " INTEGER,"
                +EXERCISE_DATE + " TEXT,"
                + EXERCISE_TOTAL_BURNED + " FLOAT,"
                + EXERCISE_FOREIGN_ID + " INTEGER,"
                +EXERCISE_STEPS + " INTEGER,"
                +EXERCISE_TYPE_ARCHERY + " FLOAT,"
                +EXERCISE_TYPE_AEROBICS + " FLOAT,"
                +EXERCISE_TYPE_BACKPACKING + " FLOAT,"
                +EXERCISE_TYPE_HIKING + " FLOAT,"
                +EXERCISE_TYPE_BADMINTON + " FLOAT,"
                +EXERCISE_TYPE_DANCING + " FLOAT,"
                +EXERCISE_TYPE_BALLROOM + " FLOAT,"
                +EXERCISE_TYPE_BASKETBALL + " FLOAT,"
                +EXERCISE_TYPE_BOWLING + " FLOAT,"
                +EXERCISE_TYPE_BOXING + " FLOAT,"
                +EXERCISE_TYPE_CALISTHENICS + " FLOAT,"
                +EXERCISE_TYPE_CANOEING + " FLOAT,"
                +EXERCISE_TYPE_ROWING + " FLOAT,"
                +EXERCISE_TYPE_CARPENTRY + " FLOAT,"
                +EXERCISE_TYPE_CARRYING + " FLOAT,"
                +EXERCISE_TYPE_CHILDREN + " FLOAT,"
                +EXERCISE_TYPE_CIRCUIT + " FLOAT,"
                +EXERCISE_TYPE_CLEANING+ " FLOAT,"
                +EXERCISE_TYPE_CLIMBING + " FLOAT,"
                +EXERCISE_TYPE_CONSTRUCTION + " FLOAT,"
                +EXERCISE_TYPE_CRICKET + " FLOAT,"
                +EXERCISE_TYPE_CROQUET + " FLOAT,"
                +EXERCISE_TYPE_CURLING+ " FLOAT,"
                +EXERCISE_TYPE_CYCLING + " FLOAT,"
                +EXERCISE_TYPE_DARTS + " FLOAT,"
                +EXERCISE_TYPE_DRIVING + " FLOAT,"
                +EXERCISE_TYPE_DOWNHILL + " FLOAT,"
                +EXERCISE_TYPE_FARMING + " FLOAT,"
                +EXERCISE_TYPE_FENCING + " FLOAT,"
                +EXERCISE_TYPE_FISHING+ " FLOAT,"
                +EXERCISE_TYPE_FOOTBALL + " FLOAT,"
                +EXERCISE_TYPE_FORESTRY + " FLOAT,"
                +EXERCISE_TYPE_FRISBEE + " FLOAT,"
                +EXERCISE_TYPE_GARDENING + " FLOAT,"
                +EXERCISE_TYPE_GOLF + " FLOAT,"
                +EXERCISE_TYPE_TENIS + " FLOAT,"
                +EXERCISE_TYPE_WALKING + " FLOAT,"
                +EXERCISE_TYPE_SWIMMING + " FLOAT,"
                +EXERCISE_TYPE_WATER + " FLOAT,"
                +EXERCISE_TYPE_RUGBY+ " FLOAT,"
                +EXERCISE_TYPE_MARTIAL + " FLOAT,"
                +EXERCISE_TYPE_WEIGHT + " FLOAT,"

                + " FOREIGN KEY ("+EXERCISE_FOREIGN_ID+") REFERENCES " + TABLE_USER +" ("+USER_ID+")"
                + ");";


        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_FOOD_TABLE);



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_EXERCISE);
// Creating tables again
        onCreate(db);

    }






    public void addIndividual(String name, int age, String gender, float height, float weight, int calories){

        SQLiteDatabase db = this.getWritableDatabase();
        String ROW1 = "INSERT INTO " + TABLE_USER  + " ("
                + USER_NAME +", "+ USER_AGE + ", "+ USER_GENDER+", "+USER_HEIGHT+", "+USER_WEIGHT+ ", " + USER_CALORIES+") " +
                "Values ( " + "'" + name + "'" + ", "  + "'" +age+ "'" + ", " + "'" + gender + "'" +" ," + "'" +height+ "'" +", "+ "'" +weight+ "'" +", "+ "'" +calories+"')";
        db.execSQL(ROW1);
        db.close();
    }


    // Modify user info to reflect profile changes
    public void updateWeight(float newWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_WEIGHT + " = " + newWeight+ " WHERE "+ USER_ID +" = "+1);
    }

    public void updateHeight(float newHeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_WEIGHT + " = " + newHeight+ " WHERE "+ USER_ID +" = "+1);
    }

    public void updateAge (int newAge)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_AGE + " = " + newAge+ " WHERE "+ USER_ID +" = "+1);
    }

    public void updateGender( String newGender)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_GENDER + " = " + newGender + " WHERE "+ USER_ID +" = "+1);
    }
    public void updateCalories( int newCalories)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_USER + " SET " + USER_CALORIES + " = " + newCalories  + " WHERE "+ USER_ID +" = "+1);
    }





    // Update daily data with food intake
    public void updateDailyFood(int calSize, String type, int amount, String date) {
        // currently only updating current day's data
        SQLiteDatabase db = this.getWritableDatabase();
        int sumCalorie = 0;
        //System.out.println("Record Exists Food? " + recordExistsFood(date) + " Sum to add = " + calSize);
        int a = getFoodTypeAmount(date,type) + amount;
        if (recordExistsFood(date)){
            sumCalorie =  getTotalCalorie(date) + calSize;
            db.execSQL("UPDATE " +  TABLE_FOOD+ " SET " + type + " = " +  a +","+ FOOD_CALORIES + " = "+sumCalorie+ " WHERE " + FOOD_DATE + " = " + '"'+date+'"');
        }
        else{

            String ROW1 = "INSERT INTO " + TABLE_FOOD  + " ("
                    +FOOD_DATE + ","
                    + FOOD_CALORIES+ "," +  FOOD_FOREIGN_ID
                    + ") Values ('" +date + "'"
                    +", '" +calSize + "'," + "'"  +1+ "'" + ")";
            //System.out.println(ROW1);
            db.execSQL(ROW1);


            db.execSQL("UPDATE " +  TABLE_FOOD+ " SET " + type + " = " +  amount +","+ FOOD_CALORIES + " = "+calSize+ " WHERE " + FOOD_DATE + " = " + "'"+date+"'");


            db.close();

        }
    }

    // Update daily steps
    public void stepsUpdate(int steps, float calBurned, String date){
        SQLiteDatabase db = this.getWritableDatabase();
       // String date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(new Date());


        SQLiteDatabase sqldb = this.getReadableDatabase();




        if (recordExistsExercise(date)){

            String s = "SELECT " + EXERCISE_STEPS + " FROM " + TABLE_EXERCISE + " WHERE " + EXERCISE_DATE + " = " + "'" +date+ "'";

            try {
                Cursor cursor = sqldb.rawQuery(s, null);
                cursor.moveToFirst();
                float accounted = cursor.getInt(0);
                cursor.close();
                float acc = (float) Calculate_cal(33,accounted);
                calBurned -= accounted;
                db.execSQL("UPDATE " + TABLE_EXERCISE + " SET " + EXERCISE_STEPS + " = " + steps + "," + EXERCISE_TOTAL_BURNED + " = " + calBurned + " WHERE " + EXERCISE_DATE + " = " + '"' + date + '"');
                System.out.println("STEPS UPDATED!");
            }catch(Exception e){
                System.out.println("ERROR CAUGHT IN STEPS UPDATE");
            }
        }
        else{


            String ROW1 = "INSERT INTO " + TABLE_EXERCISE  + " ("
                    + EXERCISE_DATE +", "
                    +EXERCISE_TOTAL_BURNED+", "
                    +EXERCISE_STEPS+ ", "

                    + EXERCISE_FOREIGN_ID+") Values ( '" +date +", "

                    +calBurned+", "
                    +steps+", "
                  +1+"')";
            db.execSQL(ROW1);


           // db.execSQL("UPDATE " + TABLE_EXERCISE + " SET " + EXERCISE_STEPS + " = " + steps + ","+ EXERCISE_TOTAL_BURNED +" = "+calBurned+" WHERE " + EXERCISE_DATE + " = " + '"'+date+'"');
            db.close();
        }
    }



    // Add exercise that isn't walking
    public void add_exercise(String type, int duration, float burned, String date){
        SQLiteDatabase db = this.getWritableDatabase();


        int sumDuration = 0;
        float sumBurned = 0;
        //System.out.println("RecordExists? " + recordExistsExercise(date));
        if (recordExistsExercise(date)){
            sumDuration = getExerciseDuration(date) + duration;
            sumBurned = getTotalExerciseBurned(date)+ burned;
            db.execSQL("UPDATE " + TABLE_EXERCISE + " SET " + type + " = " + sumBurned +"," +EXERCISE_DURATION+" = "+sumDuration+ " WHERE " + EXERCISE_DATE + " = " + '"'+date+'"');
            db.execSQL("UPDATE " + TABLE_EXERCISE + " SET " + EXERCISE_TOTAL_BURNED + " = " + sumBurned + " WHERE " + EXERCISE_DATE + " = " + "'" +date+ "'");
        }
        else{
            String ROW1 = "INSERT INTO " + TABLE_EXERCISE  + " ("
                    + EXERCISE_DATE +", " + EXERCISE_TOTAL_BURNED + ", "
                    + EXERCISE_FOREIGN_ID+") Values ( '" +date + "'" + ", " + "'" +burned+ "'"
                    +", '"
                   +1+"')";
            db.execSQL(ROW1);


            db.execSQL("UPDATE " + TABLE_EXERCISE + " SET " +type + " = " + burned +" WHERE " + EXERCISE_DATE + " = " + '"'+date+'"');
            db.close();
        }
    }



    public void deleteWeekExercise ()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_EXERCISE + "WHERE "+ EXERCISE_ROW_ID+ " BEWTEEN "+ 1 + " AND "+ 7);
    }



    public void deleteFoodWeek ()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_FOOD + "WHERE "+ FOOD_ROW_ID+ " BEWTEEN "+ 1 + " AND "+ 7);
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



    public boolean recordExistsExercise(String date){
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "Select * from " + TABLE_EXERCISE + " where " + EXERCISE_DATE + " = " + "'" + date + "'";
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
        String Query = "Select * from " + TABLE_FOOD + " where " + FOOD_DATE + " = " + "'" +date+ "'";
        //System.out.println(Query);
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public int howManyDaysFood()
    {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "select count(*) from " + TABLE_FOOD;
        Cursor cursor = sqldb.rawQuery(Query, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }


    public int howManyDaysExercise()
    {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "select count(*) from " + TABLE_EXERCISE;
        Cursor cursor = sqldb.rawQuery(Query, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public int getExerciseDuration(String date)
    {


        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "select SUM("+EXERCISE_DURATION+") from " + TABLE_EXERCISE+" WHERE " + EXERCISE_DATE + " = " + '"'+date+'"';
        Cursor cursor = sqldb.rawQuery(Query, null);
        cursor.moveToFirst();
        int cnt = cursor.getInt(0);
        cursor.close();
        return cnt;
    }

    public String[] getLastFiveExercises(){
        String[] ex = new String[5];

        SQLiteDatabase sqldb = this.getReadableDatabase();


        return null;
    }

        public float getTotalExerciseBurned(String date)
    {

        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "select "+EXERCISE_TOTAL_BURNED+ " from " + TABLE_EXERCISE+" WHERE " + EXERCISE_DATE + " = " + "'" +date+"'";
        //System.out.println(Query);
        Cursor cursor = sqldb.rawQuery(Query, null);
        cursor.moveToFirst();
        float cnt = cursor.getFloat(0);
        System.out.println(cnt);
        cursor.close();
        return cnt;
    }

    public int getFoodTypeAmount(String date, String type){
        if(recordExistsFood(date)){
            SQLiteDatabase sqldb = this.getReadableDatabase();
            try {
                String query = "SELECT " + type + " FROM " + TABLE_FOOD + " WHERE " + FOOD_DATE + " = " + "'" + date + "'";
                Cursor cursor = sqldb.rawQuery(query, null);
                cursor.moveToFirst();
                if (cursor.getCount() <= 0) {
                    System.out.println("NO VALUES");
                    return 0;
                }else{
                    int ret = cursor.getInt(0);
                    System.out.println("ret = " + ret);
                    cursor.close();
                    return ret;
                }
            }catch(Exception e){
                e.printStackTrace();
                return 0;
            }

        }else{
            return 0;
        }


    }

    public int getTotalCalorie(String date)
    {

        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "select "+FOOD_CALORIES+" from " + TABLE_FOOD+" WHERE " + FOOD_DATE + " = " + "'" +date+"'";
        Cursor cursor = sqldb.rawQuery(Query, null);
        cursor.moveToFirst();
        int cnt = cursor.getInt(0);

        cursor.close();
        return cnt;
    }



    public double Calculate_cal(int type, double duration){
        SQLiteDatabase db = this.getWritableDatabase();
        String thth = "SELECT " + USER_WEIGHT + " FROM " + TABLE_USER;
        System.out.println(thth);
        Cursor cursor = db.rawQuery(thth, null);
        cursor.moveToFirst();
        int weight = cursor.getInt(0);
        System.out.println("Weight = " + weight);
        cursor.close();

        int weightInd = 0;
        if(weight < 70)
            weightInd = 0;
        else if(weight >= 70 && weight < 81)
            weightInd = 1;
        else if(weight >= 81 && weight < 92)
            weightInd = 2;
        else
            weightInd = 3;

        //create2DArray();
        int index = (type * 4) + weightInd;
        double burnt = calorieInfoArray[index];
        System.out.println("Burnt = " + burnt);
        burnt *= duration;
        return burnt;
    }
    public String getUserName(){
        SQLiteDatabase db = this.getReadableDatabase();
            return "Biplanes";
    }
}


