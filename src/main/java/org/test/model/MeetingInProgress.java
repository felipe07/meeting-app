package org.test.model;

/**
 * Created by User on 26/12/2016.
 */
public class MeetingInProgress extends Exception {

    public MeetingInProgress() { super("Meeting was already started and is in progress"); }
}
