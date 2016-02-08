package com.barcan.virgil.insidenav.backend.dataprocessing.step;

import android.content.Context;
import android.hardware.SensorEvent;

import com.barcan.virgil.insidenav.backend.dataacquisition.Accelerometer;
import com.barcan.virgil.insidenav.common.interfaces.observer.Observer;
import com.barcan.virgil.insidenav.common.interfaces.observer.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by virgil on 06.02.2016.
 */
public class SimpleStepDetection implements Subject, Observer {

    private float ACCELERATION_LIMIT = 11.5f; //the lower limit of the acceleration before the value is considered a step (m/s^2)
    private int TIME_LIMIT = 350000000; //the least amount of time required between 2 steps (nanoseconds)

    private Context context;
    private Accelerometer accelerometer;

    private int noOfSteps = 0;
    private long lastStepTimestamp;

    private List<Observer> observerList;

    public SimpleStepDetection(Context context) {
        System.out.println("SimpleStepDetection.SimpleStepDetection");
        this.context = context;

        observerList = new ArrayList<>();

        accelerometer = new Accelerometer(context);
        accelerometer.registerToSensor();
    }

    private boolean stepAlgorithm(SensorEvent sensorData) {
        float xValue = sensorData.values[0];
        float yValue = sensorData.values[1];
        float zValue = sensorData.values[2];

        if (noOfSteps == 0) { //first step

            if (zValue > ACCELERATION_LIMIT) {
                //update lastStepTimestamp
                lastStepTimestamp = sensorData.timestamp;

                //add one step
                ++noOfSteps;
                notifyObservers();

                System.out.println("SimpleStepDetection.stepAlgorithm: first step detected!");
            }
        }
        else { //not the first step

            if (zValue > ACCELERATION_LIMIT) {
                //get the current timestamp
                long currentTimestamp = sensorData.timestamp;

                System.out.println("SimpleStepDetection.stepAlgorithm: " + (currentTimestamp - lastStepTimestamp));

                if (currentTimestamp - lastStepTimestamp > TIME_LIMIT) {
                    //update lastStepTimestamp
                    lastStepTimestamp = currentTimestamp;

                    //add one step
                    ++noOfSteps;
                    notifyObservers();

                    System.out.println("SimpleStepDetection.stepAlgorithm: another step detected!");
                }
            }
        }

        return true;
    }

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

    //region Observer methods
    @Override
    public boolean register() {
        return accelerometer.registerObserver(this);
    }

    @Override
    public boolean unregister() {
        return accelerometer.unregisterObserver(this);
    }

    @Override
    public boolean update(Object data) {

        //System.out.println("sensorData=" + Arrays.toString(sensorData.values));

        /*
            Check if the current value represents a step
            A step is counted if the z value is greater then 12 and the last recorded step was counted
         at least 350 ms ago
         */
        stepAlgorithm((SensorEvent) data);

        return true;
    }
    //endregion Observer methods
}
