package com.barcan.virgil.insidenav.common.interfaces.others;

/**
 * This interface models a Sensor.
 *
 * It provides basic methods that define what a Sensor class can do: register, unregister, setSamplingRate.
 *
 * Created by virgil on 05.02.2016.
 */
public interface Sensor {

    /**
     * Register to an Android sensor
     * @return true if the action was completed, false otherwise
     */
    boolean register();

    /**
     * Unregister to an Android sensor
     * @return true if the action was completed, false otherwise
     */
    boolean unregister();

    /**
     * Set the sampling rate at which data is requested from the sensor
     * @param samplingRate the sampling rate value
     */
    void setSamplingRate(int samplingRate);

}
