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
            if (meetingParticipant.getParticipantRole().equals(ParticipantRole.PRESENTER)) {
                isThereAPresenter = false;
            }
            participants.remove(participantName);
        } else {
            throw new ParticipantNotIncluded();
        }
    }

    public void selectPresenter(String participantName) throws PresenterAlreadySelected {
        for (Participant meetingParticipant : participants.values()) {
            if (meetingParticipant.getParticipantRole().equals(ParticipantRole.PRESENTER)) {
               isThereAPresenter = true;
            }
        }

        if (!isThereAPresenter) {
            participants.get(participantName).setParticipantRole(ParticipantRole.PRESENTER);
            isThereAPresenter = true;
        } else {
            throw new PresenterAlreadySelected();
        }
    }

    public void deselectPresenter(String participantName) {
        participants.get(participantName).setParticipantRole(ParticipantRole.ATTENDEE);
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
