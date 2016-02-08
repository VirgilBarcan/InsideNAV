package com.barcan.virgil.insidenav.backend.dataacquisition;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.barcan.virgil.insidenav.common.interfaces.observer.Observer;
import com.barcan.virgil.insidenav.common.interfaces.observer.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by virgil on 06.02.2016.
 */
public class Accelerometer implements SensorEventListener, Subject {

    private Context context;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int samplingRate;

    private List<Observer> observerList;
    private SensorEvent sensorData;

    public Accelerometer(Context context) {
        System.out.println("Accelerometer.Accelerometer");
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        samplingRate = SensorManager.SENSOR_DELAY_NORMAL;

        observerList = new ArrayList<>();
    }

    public boolean registerToSensor() {
        if (accelerometer == null)
            return false;

        return sensorManager.registerListener(this, accelerometer, samplingRate);
    }

    public void unregisterFromSensor() {
        sensorManager.unregisterListener(this);
    }

    public void setSamplingRate(int samplingRate) {
        this.samplingRate = samplingRate;
    }

    //region SensorEventListener methods
    @Override
    public void onSensorChanged(SensorEvent event) {
        sensorData = event;
        notifyObservers();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //TODO: Do something here
    }
    //endregion SensorEventListener methods

    //region Subject methods
    @Override
    public boolean registerObserver(Observer observer) {
        return observerList.add(observer);
    }

    @Override
    public boolean unregisterObserver(Observer observer) {
        return observerList.remove(observer);
    }

    @Override
    public boolean notifyObservers() {
        for (Observer observer : observerList)
            observer.update(sensorData);
        return false;
    }
    //endregion Subject methods
}
