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
 * This class is used to get Gyro data from the Android system
 * It registers to receive data at a given samplingRate
 * Created by virgil on 10.02.2016.
 */
public class Gyro implements SensorEventListener, Subject {

    private Context context;

    private SensorManager sensorManager;
    private Sensor gyro;
    private int samplingRate;

    private List<Observer> observerList;
    private SensorEvent sensorData;

    public Gyro(Context context) {
        System.out.println("Gyro.Gyro");
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        samplingRate = SensorManager.SENSOR_DELAY_NORMAL;

        observerList = new ArrayList<>();
    }

    public boolean registerToSensor() {
        if (gyro == null)
            return false;

        return sensorManager.registerListener(this, gyro, samplingRate);
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