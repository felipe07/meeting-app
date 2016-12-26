package org.test.model;

/**
 * Created by User on 23/12/2016.
 */
public class Participant {

    private String name;
    private MeetingRole meetingRole;

    public Participant(String name, MeetingRole meetingRole) {
        this.name = name;
        this.meetingRole = meetingRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MeetingRole getMeetingRole() {
        return meetingRole;
    }

    public void setMeetingRole(MeetingRole meetingRole) {
        this.meetingRole = meetingRole;
    }
}
