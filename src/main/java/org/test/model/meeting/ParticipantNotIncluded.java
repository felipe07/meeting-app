package org.test.model.meeting;

/**
 * Created by User on 24/12/2016.
 */
public class ParticipantNotIncluded extends Exception {

    public ParticipantNotIncluded() { super("Participant was not included in this meeting"); }
}
