package org.test.model;

/**
 * Created by User on 24/12/2016.
 */
public class InsufficientNumberOfParticipants extends Exception {

    public InsufficientNumberOfParticipants() { super("A minimum of 2 participants is required to start a meeting"); }
}
