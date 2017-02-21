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

public class User implements Parcelable {

    private double weight;
    private double height;
    private double bmi;
    private String name;

    public User(String name,double age,double height,double weight){
        this.weight = (double) weight;
        this.height = (double) height;
        this.name = name;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeDouble(weight);
        out.writeDouble(height);
        out.writeDouble(bmi);
        out.writeString(name);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        this.weight = in.readDouble();
        this.height = in.readDouble();
        this.bmi = in.readDouble();
        this.name = in.readString();
        System.out.print("USER ATTRIBUTES :\n WEIGHT: " + this.weight + "\n HEIGHT " + this.height + "\n BMI: " + this.bmi + "\n");
    }


    public User (Activity context) throws FileNotFoundException{

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

    public void setWeight(int w) {
        this.weight = w;
        updateBMI();
    }

    public void setHeight(int h) {
        this.height = h;
        updateBMI();
    }

    public void updateBMI(){
        this.bmi = weight / (height/100)*(height/100);
    }

}
