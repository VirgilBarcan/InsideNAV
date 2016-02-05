package com.barcan.virgil.insidenav.common.interfaces.observer;

/**
 * This interface models an Observer in the Observer design pattern.
 *
 * It provides basic methods that define an Observer: register, unregister, update
 *
 * Created by virgil on 05.02.2016.
 */
public interface Observer {

    /**
     * Register to a new Subject
     * @param subject the Subject for whose notifications the Observer registers
     * @return true was the action is completed, false otherwise
     */
    boolean register(Subject subject);

    /**
     * Unregister from a Subject
     * @param subject the Subject from whose notifications the Observer unregisters
     * @return true was the action is completed, false otherwise
     */
    boolean unregister(Subject subject);

    /**
     * The callback method called by the Subject in order to send updates
     * @return true was the action is completed, false otherwise
     */
    boolean update();

}
