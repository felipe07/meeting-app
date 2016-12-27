package org.meetingapp.model.participant;

/**
 * Created by User on 23/12/2016.
 */
public enum ParticipantRole {

    PRESENTER("Presenter"),
    ATTENDEE("Attendee");

    private String role;

    ParticipantRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
