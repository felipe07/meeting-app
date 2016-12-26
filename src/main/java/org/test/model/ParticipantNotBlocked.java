package org.test.model;

/**
 * Created by User on 25/12/2016.
 */
public class ParticipantNotBlocked extends Exception {

    public ParticipantNotBlocked() { super("Participant was not blocked for this meeting"); }
}
