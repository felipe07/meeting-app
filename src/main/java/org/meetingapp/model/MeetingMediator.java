package org.meetingapp.model;

import org.meetingapp.model.meeting.*;
import org.meetingapp.model.participant.ParticipantRole;
import org.meetingapp.model.participant.Participant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by User on 23/12/2016.
 */
public class MeetingMediator {

    private Meeting currentMeeting;
    private Map<String, Participant> participants = new HashMap<>();

    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public MeetingMediator() {
        createParticipants();
    }

    public String getCurrentMeetingSubject() {
        return currentMeeting.getSubject();
    }

    public String createMeeting(String meetingSubject) {
        createParticipants();
        currentMeeting = new Meeting(meetingSubject);
        return String.format("Meeting %s created on: %s",
                new Object[]{meetingSubject, df.format(new Date())});
    }

    public String addParticipantToMeeting(String participantName) throws ParticipantAlreadyIncluded {
        currentMeeting.checkIfParticipantWasIncludedBefore(participantName);
        Participant meetingParticipant = participants.get(participantName);
        currentMeeting.addParticipant(meetingParticipant);
        return String.format("%s added to meeting %s on %s",
                new Object[]{participantName, getCurrentMeetingSubject(), df.format(new Date())});
    }

    public String removeParticipantFromMeeting(String participantName) throws ParticipantNotIncluded {
        currentMeeting.checkIfParticipantWasNotIncludedBefore(participantName);
        Participant meetingParticipant = participants.get(participantName);
        currentMeeting.removeParticipant(meetingParticipant);
        return String.format("%s removed from meeting %s on %s",
                new Object[]{participantName, getCurrentMeetingSubject(), df.format(new Date())});
    }

    public Map<String, Participant> getMeetingParticipants() {
        return currentMeeting.getParticipants();
    }

    public String selectMeetingPresenter(String participantName) throws PresenterAlreadySelected {
        currentMeeting.checkIfThereIsPresenterForMeeting();
        currentMeeting.selectPresenter(participantName);
        return String.format("%s selected as %s for meeting %s on %s",
                new Object[]{participantName, ParticipantRole.PRESENTER, getCurrentMeetingSubject(), df.format(new Date())});
    }

    public String deselectMeetingPresenter(String participantName) {
        currentMeeting.deselectPresenter(participantName);
        return String.format("%s deselected as %s for meeting %s on %s",
                new Object[]{participantName, ParticipantRole.PRESENTER, getCurrentMeetingSubject(), df.format(new Date())});
    }

    public String startMeeting() throws InsufficientNumberOfParticipants, MeetingInProgress {
        currentMeeting.startMeeting();
        return String.format("Meeting %s started on: %s",
                new Object[]{getCurrentMeetingSubject(), df.format(new Date())});
    }

    public String finishMeeting() {
        currentMeeting.finishMeeting();
        return String.format("Meeting %s finished on: %s",
                new Object[]{getCurrentMeetingSubject(), df.format(new Date())});
    }

    public void checkAMeetingHasBeenCreated() throws MeetingNotCreated {
        if (currentMeeting == null)
            throw new MeetingNotCreated();
    }

    public boolean isThereAPresenter() {
        return currentMeeting.isThereAPresenter();
    }

    public Participant getParticipant(String participantName) {
        return participants.get(participantName);
    }

    public Set<String> getAllParticipantNames() {
        return participants.keySet();
    }

    public void createParticipants() {
        participants.put("Lucas F.", new Participant("Lucas F.", ParticipantRole.ATTENDEE));
        participants.put("Maria B.", new Participant("Maria B.", ParticipantRole.ATTENDEE));
        participants.put("Marco M.", new Participant("Marco M.", ParticipantRole.ATTENDEE));
        participants.put("David E.", new Participant("David E.", ParticipantRole.ATTENDEE));
        participants.put("Victor C.", new Participant("Victor C.", ParticipantRole.ATTENDEE));
        participants.put("Carl T.", new Participant("Carl T.", ParticipantRole.ATTENDEE));
        participants.put("Robert G.", new Participant("Robert G.", ParticipantRole.ATTENDEE));
    }
}
