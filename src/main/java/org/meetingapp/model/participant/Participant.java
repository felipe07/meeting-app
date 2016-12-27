package org.meetingapp.model.participant;

/**
 * Created by User on 23/12/2016.
 */
public class Participant {

    private String name;
    private ParticipantRole participantRole;

    public Participant(String name, ParticipantRole participantRole) {
        this.name = name;
        this.participantRole = participantRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParticipantRole getParticipantRole() {
        return participantRole;
    }

    public void setParticipantRole(ParticipantRole participantRole) {
        this.participantRole = participantRole;
    }
}
