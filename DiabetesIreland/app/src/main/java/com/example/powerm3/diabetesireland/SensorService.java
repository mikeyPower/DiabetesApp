package com.example.powerm3.diabetesireland;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;



public class SensorService extends Service implements SensorEventListener {
    private SensorManager sensorManager = null;
    private Sensor sensor = null;
    private int mSteps = -1  ;
    private PowerManager.WakeLock mWakeLock = null;
    boolean isRunning =false;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        /*PowerManager manager =
                (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "tag");

        mWakeLock.acquire();
        */
        if(!isRunning) {
            sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_FASTEST);
            isRunning = true;
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            mSteps = (int) values[0];
            Log.d("sender", "Broadcasting message"+mSteps);
            Intent intent = new Intent("Sending steps");
            String x = "" + mSteps;
            intent.putExtra("message", x);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }

        //stop sensor and service
       // sensorManager.unregisterListener(this, sensor);
       // isRunning =false;
        stopSelf();
    }
    @Override
    public void onDestroy(){
        //mWakeLock.release();
        super.onDestroy();
    }

}
