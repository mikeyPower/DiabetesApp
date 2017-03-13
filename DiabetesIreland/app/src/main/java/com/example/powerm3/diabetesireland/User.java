package com.example.powerm3.diabetesireland;

import android.app.Activity;

import android.os.Parcelable;
import android.os.Parcel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Conal on 15/02/2017.
 */

public class User  {

    private double weight;
    private double height;
    private double bmi;
    private int age;
    private boolean isMale;
    private String name;

    public User(String name,double age,double height,double weight,boolean isMale){
        this.weight = (double) weight;
        this.height = (double) height;
        this.name = name;
        this.age = (int) age;
        this.isMale = isMale;
        updateBMI();
    }







    public double getWeight(){
        return weight;
    }

    public double getHeight(){
        return height;
    }

    public double getBMI(){
        return bmi;
    }

    public String getName(){ if(name != null) return name;
    else return null;}

    public int getAge(){
        return age;
    }

    public void setWeight(int w) {
        this.weight = w;
        updateBMI();
    }

    public void setHeight(int h) {
        this.height = h;
        updateBMI();
    }

    public void updateBMI(){
        try{
            double w = weight;
            double h = height;
            h /= 100;
            double newbmi =  (w/(h*h));
            this.bmi =  newbmi;

        }catch(Exception e){

            this.bmi =  0;
        }
    }

}
