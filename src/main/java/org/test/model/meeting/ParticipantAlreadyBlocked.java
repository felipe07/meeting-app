package org.test.model.meeting;

/**
 * Created by User on 24/12/2016.
 */
public class ParticipantAlreadyBlocked extends Exception {

    public ParticipantAlreadyBlocked() { super("Participant was already blocked for this meeting"); }
}
