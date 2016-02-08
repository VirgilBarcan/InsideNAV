package com.barcan.virgil.insidenav.common.interfaces.observer;

/**
 * This interface models an Observer in the Observer design pattern.
 *
 * It provides basic methods that define an Observer: registerObserver, unregisterObserver, update
 *
 * Created by virgil on 05.02.2016.
 */
public interface Observer {

    /**
     * Register to a new Subject
     * @return true the action was completed, false otherwise
     */
    boolean register();

    /**
     * Unregister from a Subject
     * @return true the action was completed, false otherwise
     */
    boolean unregister();

    /**
     * The callback method called by the Subject in order to send updates
     * @param data the data that the Subject send
     * @return true the action was completed, false otherwise
     */
    boolean update(Object data);
}
