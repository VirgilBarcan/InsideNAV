package com.barcan.virgil.insidenav.common.interfaces.observer;

/**
 * This interface models a Subject in the Observer design pattern.
 *
 * It provides methods that define a subject: registerObserver, unregisterObserver, update.
 *
 * Created by virgil on 05.02.2016.
 */
public interface Subject {

    /**
     * Register a new Observer
     * @param observer the Observer that will be notified of every change
     * @return true the action was completed, false otherwise
     */
    boolean registerObserver(Observer observer);

    /**
     * Unregister a new Observer
     * @param observer the Observer that will be notified of every change
     * @return true the action was completed, false otherwise
     */
    boolean unregisterObserver(Observer observer);

    /**
     * Notify the observers that something changed
     * @return true the action was completed, false otherwise
     */
    boolean notifyObservers();

}
