package org.meetingapp.model.meeting;

import org.meetingapp.model.participant.ParticipantRole;
import org.meetingapp.model.participant.Participant;

import java.util.*;

/**
 * Created by User on 23/12/2016.
 */
public class Meeting {

    private String subject;
    private boolean inProgress, isThereAPresenter;
    private Map<String, Participant> participants = new HashMap<>();

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

    public void addParticipant(Participant meetingParticipant) {
        String participantName = meetingParticipant.getName();
        participants.put(participantName, meetingParticipant);
    }

    public void checkIfParticipantWasIncludedBefore(String participantName) throws ParticipantAlreadyIncluded {
        if (participants.containsKey(participantName))
            throw new ParticipantAlreadyIncluded();
    }

    public void removeParticipant(Participant meetingParticipant) {
        String participantName = meetingParticipant.getName();
        participants.remove(participantName);
        if (meetingParticipant.getParticipantRole().equals(ParticipantRole.PRESENTER))
            isThereAPresenter = false;
    }

    public void checkIfParticipantWasNotIncludedBefore(String participantName) throws ParticipantNotIncluded {
        if (!participants.containsKey(participantName))
            throw new ParticipantNotIncluded();
    }

    public void selectPresenter(String participantName) {
        participants.get(participantName).setParticipantRole(ParticipantRole.PRESENTER);
        isThereAPresenter = true;
    }

    public void deselectPresenter(String participantName) {
        participants.get(participantName).setParticipantRole(ParticipantRole.ATTENDEE);
        isThereAPresenter = false;
    }

    public void checkIfThereIsPresenterForMeeting() throws PresenterAlreadySelected {
        for (Participant meetingParticipant : participants.values()) {
            if (meetingParticipant.getParticipantRole().equals(ParticipantRole.PRESENTER)) {
               isThereAPresenter = true;
            }
        }
        if (isThereAPresenter)
            throw new PresenterAlreadySelected();
    }

    public void startMeeting() throws InsufficientNumberOfParticipants, MeetingInProgress {
        if (participants.size() < 2) {
            inProgress = false;
            throw new InsufficientNumberOfParticipants();
        } else if (inProgress) {
            throw new MeetingInProgress();
        } else {
            inProgress = true;
        }
    }

    public void finishMeeting() {
        if (inProgress) {
            inProgress = false;
        }
    }

    public boolean isThereAPresenter() {
        return isThereAPresenter;
    }
}
