package org.meetingapp.model.meeting;

/**
 * Created by User on 31/12/2016.
 */
public class MeetingNotCreated extends Exception {

    public MeetingNotCreated() { super("No meeting has been created. Please create a meeting first"); }
}
