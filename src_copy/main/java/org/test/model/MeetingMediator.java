package org.test.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 23/12/2016.
 */
public class MeetingMediator {

    private Map<String, Meeting> meetings = new HashMap<>();
    private Map<String, Participant> participants = new HashMap<>();

    private Logger logger = Logger.getLogger(MeetingMediator.class.getName());
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public MeetingMediator() {
        createParticipants();
    }

    public void createMeeting(String meetingSubject) {
        Meeting newMeeting = new Meeting(meetingSubject);
        meetings.put(meetingSubject, newMeeting);
        logger.log(Level.INFO, "Meeting {0} created on: {1}", new Object[]{meetingSubject, df.format(new Date())});
    }

    public void addParticipantToMeeting(String participantName, String meetingSubject) throws ParticipantAlreadyIncluded {
        Participant meetingParticipant = participants.get(participantName);
        Meeting selectedMeeting = meetings.get(meetingSubject);
        selectedMeeting.addParticipant(meetingParticipant);
        logger.log(Level.INFO, "{0} added to meeting {1}", new Object[]{participantName, meetingSubject});
    }

    public void removeParticipantFromMeeting(String participantName, String meetingSubject) throws ParticipantNotIncluded {
        Participant meetingParticipant = participants.get(participantName);
        Meeting selectedMeeting = meetings.get(meetingSubject);
        selectedMeeting.removeParticipant(meetingParticipant);
        logger.log(Level.INFO, "{0} removed from meeting {1}", new Object[]{participantName, meetingSubject});
    }

    public void blockParticipantFromMeeting(String participantName, String meetingSubject) throws ParticipantAlreadyBlocked {
        Participant meetingParticipant = participants.get(participantName);
        Meeting selectedMeeting = meetings.get(meetingSubject);
        selectedMeeting.blockParticipant(meetingParticipant);
        logger.log(Level.INFO, "{0} blocked from meeting {1}", new Object[]{participantName, meetingSubject});
    }

    public void unblockParticipantFromMeeting(String participantName, String meetingSubject) throws ParticipantNotBlocked {
        Participant meetingParticipant = participants.get(participantName);
        Meeting selectedMeeting = meetings.get(meetingSubject);
        selectedMeeting.unblockParticipant(meetingParticipant);
        logger.log(Level.INFO, "{0} unblocked from meeting {1}", new Object[]{participantName, meetingSubject});
    }

    public void selectMeetingPresenter(String participantName, String meetingSubject) {
        Meeting selectedMeeting = meetings.get(meetingSubject);
        selectedMeeting.selectPresenter(participantName);
        logger.log(Level.INFO, "{0} selected as {1} for meeting {2}", new Object[]{participantName, MeetingRole.PRESENTER, meetingSubject});
    }

    public void startMeeting(String meetingSubject) throws InsufficientNumberOfParticipants {
        Meeting selectedMeeting = meetings.get(meetingSubject);
        selectedMeeting.startMeeting();
        logger.log(Level.INFO, "Meeting {0} started on: {1}", new Object[]{meetingSubject, df.format(new Date())});
    }

    public void finishMeeting(String meetingSubject) {
        Meeting selectedMeeting = meetings.get(meetingSubject);
        selectedMeeting.finishMeeting();
        logger.log(Level.INFO, "Meeting {0} finished on: {1}", new Object[]{meetingSubject, df.format(new Date())});
    }

    public Set<String> getAllParticipantNames() {
        return participants.keySet();
    }

    public void createParticipants() {
        participants.put("Lucas F.", new Participant("Lucas F.", MeetingRole.ATTENDEE));
        participants.put("Maria B.", new Participant("Maria B.", MeetingRole.ATTENDEE));
        participants.put("Marco M.", new Participant("Marco M.", MeetingRole.ATTENDEE));
        participants.put("David E.", new Participant("David E.", MeetingRole.ATTENDEE));
        participants.put("Victor C.", new Participant("Victor C.", MeetingRole.ATTENDEE));
        participants.put("Carl T.", new Participant("Carl T.", MeetingRole.ATTENDEE));
        participants.put("Robert G.", new Participant("Robert G.", MeetingRole.ATTENDEE));
    }
}
