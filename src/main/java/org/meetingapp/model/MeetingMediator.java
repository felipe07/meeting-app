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

    private Map<String, Meeting> meetings = new HashMap<>();
    private Map<String, Participant> participants = new HashMap<>();

    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public MeetingMediator() {
        createParticipants();
    }

    public String createMeeting(String meetingSubject) {
        createParticipants();
        Meeting newMeeting = new Meeting(meetingSubject);
        meetings.put(meetingSubject, newMeeting);
        return String.format("Meeting %s created on: %s", new Object[]{meetingSubject, df.format(new Date())});
    }

    public String addParticipantToMeeting(String participantName, String meetingSubject) throws ParticipantAlreadyIncluded {
        Participant meetingParticipant = participants.get(participantName);
        Meeting selectedMeeting = meetings.get(meetingSubject);
        selectedMeeting.addParticipant(meetingParticipant);
        return String.format("%s added to meeting %s on %s", new Object[]{participantName, meetingSubject, df.format(new Date())});
    }

    public String removeParticipantFromMeeting(String participantName, String meetingSubject) throws ParticipantNotIncluded {
        Participant meetingParticipant = participants.get(participantName);
        Meeting selectedMeeting = meetings.get(meetingSubject);
        selectedMeeting.removeParticipant(meetingParticipant);
        return String.format("%s removed from meeting %s on %s", new Object[]{participantName, meetingSubject, df.format(new Date())});
    }

    public Map<String, Participant> getMeetingParticipants(String meetingSubject) {
        Meeting selectedMeeting = meetings.get(meetingSubject);
        return selectedMeeting.getParticipants();
    }

    public String selectMeetingPresenter(String participantName, String meetingSubject) throws PresenterAlreadySelected {
        Meeting selectedMeeting = meetings.get(meetingSubject);
        selectedMeeting.selectPresenter(participantName);
        return String.format("%s selected as %s for meeting %s on %s", new Object[]{participantName, ParticipantRole.PRESENTER, meetingSubject, df.format(new Date())});
    }

    public String deselectMeetingPresenter(String participantName, String meetingSubject) {
        Meeting selectedMeeting = meetings.get(meetingSubject);
        selectedMeeting.deselectPresenter(participantName);
        return String.format("%s deselected as %s for meeting %s on %s", new Object[]{participantName, ParticipantRole.PRESENTER, meetingSubject, df.format(new Date())});
    }

    public String startMeeting(String meetingSubject) throws InsufficientNumberOfParticipants, MeetingInProgress {
        Meeting selectedMeeting = meetings.get(meetingSubject);
        selectedMeeting.startMeeting();
        return String.format("Meeting %s started on: %s", new Object[]{meetingSubject, df.format(new Date())});
    }

    public String finishMeeting(String meetingSubject) {
        Meeting selectedMeeting = meetings.get(meetingSubject);
        selectedMeeting.finishMeeting();
        return String.format("Meeting %s finished on: %s", new Object[]{meetingSubject, df.format(new Date())});
    }

    public boolean isThereAPresenter(String meetingSubject) {
        Meeting selectedMeeting = meetings.get(meetingSubject);
        return selectedMeeting.isThereAPresenter();
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
