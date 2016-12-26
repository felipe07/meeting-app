package org.test.model;

/**
 * Created by User on 23/12/2016.
 */
public enum MeetingRole {

    PRESENTER("Presenter"),
    ATTENDEE("Attendee");

    private String role;

    MeetingRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
