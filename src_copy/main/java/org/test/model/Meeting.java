package org.test.model;

import java.util.*;

/**
 * Created by User on 23/12/2016.
 */
public class Meeting {

    private String subject;
    private Boolean inProgress;
    private Map<String, Participant> participants = new HashMap<>();
    private Map<String, Participant> blockedParticipants = new HashMap<>();

    public Meeting(String subject) {
        this.subject = subject;
        this.inProgress = false;
    }

    public String getSubject() {
        return subject;
    }

    public Map<String, Participant> getParticipants() {
        return participants;
    }

    public void addParticipant(Participant meetingParticipant) throws ParticipantAlreadyIncluded {
        String participantName = meetingParticipant.getName();
        if (!participants.containsKey(participantName)) {
            participants.put(participantName, meetingParticipant);
        } else {
            throw new ParticipantAlreadyIncluded();
        }
    }

    public void removeParticipant(Participant meetingParticipant) throws ParticipantNotIncluded {
        String participantName = meetingParticipant.getName();
        if (participants.containsKey(participantName)) {
            participants.remove(participantName);
        } else {
            throw new ParticipantNotIncluded();
        }
    }

    public void blockParticipant(Participant meetingParticipant) throws ParticipantAlreadyBlocked {
        String participantName = meetingParticipant.getName();
        if (!blockedParticipants.containsKey(participantName)) {
            blockedParticipants.put(participantName, meetingParticipant);
        } else {
            throw new ParticipantAlreadyBlocked();
        }
    }

    public void unblockParticipant(Participant meetingParticipant) throws ParticipantNotBlocked {
        String participantName = meetingParticipant.getName();
        if (blockedParticipants.containsKey(participantName)) {
            blockedParticipants.remove(participantName, meetingParticipant);
        } else {
            throw new ParticipantNotBlocked();
        }
    }

    public void selectPresenter(String participantName) {
        Participant meetingParticipant = participants.get(participantName);
        meetingParticipant.setMeetingRole(MeetingRole.PRESENTER);
    }

    public void startMeeting() throws InsufficientNumberOfParticipants {
        if (participants.size() < 2) {
            throw new InsufficientNumberOfParticipants();
        } else {
            inProgress = true;
        }
    }

    public void finishMeeting() {
        if (inProgress) {
            inProgress = false;
        }
    }
}
