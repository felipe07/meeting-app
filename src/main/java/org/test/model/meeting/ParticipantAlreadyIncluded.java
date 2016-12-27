package org.test.model.meeting;

/**
 * Created by User on 23/12/2016.
 */
public class ParticipantAlreadyIncluded extends Exception {

    public ParticipantAlreadyIncluded() { super("Participant already included in this meeting"); }
}
