package com.barcan.virgil.insidenav.backend.dataprocessing.step;

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
 * Created by virgil on 10.02.2016.
 */
public class AndroidStepDetection implements SensorEventListener, Subject {

    private Context context;

    private SensorManager sensorManager;
    private Sensor stepDetector;
    private int samplingRate;

    private int noOfSteps = 0;

    private List<Observer> observerList;

    public AndroidStepDetection(Context context) {
        System.out.println("AndroidStepDetection.AndroidStepDetection");
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        samplingRate = SensorManager.SENSOR_DELAY_FASTEST;

        observerList = new ArrayList<>();

        registerToSensor();
    }

    public boolean registerToSensor() {
        if (stepDetector == null)
            return false;

        return sensorManager.registerListener(this, stepDetector, samplingRate);
    }

    public void unregisterFromSensor() {
        sensorManager.unregisterListener(this);
    }

    public void setSamplingRate(int samplingRate) {
        this.samplingRate = samplingRate;
    }

    private boolean stepAlgorithm(SensorEvent sensorData) {
        if (sensorData.values[0] == 1.0) {
            ++noOfSteps;
            notifyObservers();

            System.out.println("AndroidStepDetection.stepAlgorithm: another step detected!");
        }

        return true;
    }

    //region SensorEventListener methods
    @Override
    public void onSensorChanged(SensorEvent event) {
        stepAlgorithm(event);
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
            observer.update(noOfSteps);
        return false;
    }
    //endregion Subject methods
}
