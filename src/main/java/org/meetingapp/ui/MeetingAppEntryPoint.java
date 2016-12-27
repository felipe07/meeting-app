package org.meetingapp.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.meetingapp.model.MeetingMediator;
import org.meetingapp.model.meeting.*;
import org.meetingapp.model.participant.ParticipantRole;
import org.meetingapp.model.participant.Participant;

import java.util.concurrent.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p/>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MeetingAppEntryPoint extends UI {

    private MeetingMediator meetingMediator = new MeetingMediator();
    private String currentMeeting = new String();
    private Table confMeetingTable = new Table();
    private Table meetingTable = new Table();
    private HorizontalLayout meetingLayout;
    private TextArea logArea;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final HorizontalLayout mainLayout = new HorizontalLayout();
        final VerticalLayout controlsLayout = new VerticalLayout();

        final TextField meetingSubjectTF = new TextField();
        meetingSubjectTF.setCaption("Type Meeting Subject:");
        meetingSubjectTF.focus();

        meetingLayout = new HorizontalLayout();
        meetingLayout.setSpacing(true);

        Button createMeetingBtn = new Button("Create Meeting");
        createMeetingBtn.addClickListener(e -> {
            if (!meetingSubjectTF.getValue().isEmpty()) {
                currentMeeting = meetingSubjectTF.getValue();
                String response = meetingMediator.createMeeting(currentMeeting);
                meetingSubjectTF.clear();

                confMeetingTable.removeAllItems();
                confMeetingTable.setCaption("Setup Your Meeting: " + meetingSubjectTF.getValue());
                confMeetingTable.setSizeFull();
                confMeetingTable.addContainerProperty("Name", String.class, null);
                confMeetingTable.addContainerProperty("Add/Remove To Meeting", Button.class, null);
                confMeetingTable.setColumnAlignment("Add/Remove To Meeting", Table.Align.CENTER);
                for (String name : meetingMediator.getAllParticipantNames()) {
                    Button addButton = new Button("Add", AddButtonClickListener);
                    addButton.setData(name);
                    confMeetingTable.addItem(new Object[]{name, addButton}, name);
                }
                confMeetingTable.setPageLength(confMeetingTable.size());

                logArea = new TextArea("Application log:");
                logArea.setHeight("400px");
                logArea.setWidth("550px");
                logArea.setRows(18);
                logArea.setEnabled(false);
                logArea.setValue(logArea.getValue() + "\n" + response);
                logArea.setCursorPosition(logArea.getValue().length());

                meetingLayout.removeAllComponents();
                meetingLayout.addComponent(confMeetingTable);
                meetingLayout.addComponent(logArea);
                mainLayout.addComponent(meetingLayout);
            } else {
                new Notification("Please type the subject of the meeting first").show(Page.getCurrent());
            }
        });

        Button startMeetingBtn = new Button("Start Meeting");
        startMeetingBtn.addClickListener(startButtonClickListener);

        controlsLayout.addComponents(meetingSubjectTF, createMeetingBtn, startMeetingBtn);
        controlsLayout.setMargin(true);
        controlsLayout.setSpacing(true);

        mainLayout.setSpacing(true);
        mainLayout.addComponent(controlsLayout);
        setContent(mainLayout);
    }

    Button.ClickListener AddButtonClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            try {
                Button addButton = clickEvent.getButton();
                String personName = (String) clickEvent.getButton().getData();
                String response;
                if (addButton.getCaption().equals("Add")) {
                    response = meetingMediator.addParticipantToMeeting(personName, currentMeeting);
                    addButton.setCaption("Remove");
                } else {
                    response = meetingMediator.removeParticipantFromMeeting(personName, currentMeeting);
                    addButton.setCaption("Add");
                }
                logArea.setValue(logArea.getValue() + "\n" + response);
                logArea.setCursorPosition(logArea.getValue().length());

            } catch (ParticipantAlreadyIncluded | ParticipantNotIncluded e) {
                new Notification(e.getMessage()).show(Page.getCurrent());
            }
        }
    };

    Button.ClickListener startButtonClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            if (!currentMeeting.isEmpty()) {
                try {
                    String response = meetingMediator.startMeeting(currentMeeting);
                    logArea.setValue(logArea.getValue() + "\n" + response);
                    meetingLayout.removeComponent(confMeetingTable);
                    updateMeetingTable();
                    waitPresenterSelection();
                    logArea.setValue(logArea.getValue() + "\n" + "You have 5 seconds to select the presenter!");
                    logArea.setCursorPosition(logArea.getValue().length());
                } catch (InsufficientNumberOfParticipants | MeetingInProgress e) {
                    new Notification(e.getMessage()).show(Page.getCurrent());
                }
            } else {
                new Notification("Please create a meeting first").show(Page.getCurrent());
            }
        }
    };

    public void waitPresenterSelection() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        ScheduledFuture scheduledFuture =
            scheduledExecutorService.schedule(
                new Callable() {
                    public Object call() throws Exception {
                        boolean wasPresenterSelected = meetingMediator.isThereAPresenter(currentMeeting);
                        if (!wasPresenterSelected) {
                            Page.getCurrent().reload();
                        }
                        return "0";
                    }
                },
            5, TimeUnit.SECONDS);
    }

    Button.ClickListener removeAbsentClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            try {
                String participantName = (String) clickEvent.getButton().getData();
                String response = meetingMediator.removeParticipantFromMeeting(participantName, currentMeeting);
                logArea.setValue(logArea.getValue() + "\n" + response);
                meetingLayout.removeComponent(meetingTable);
                updateMeetingTable();

                Participant removedParticipant = meetingMediator.getParticipant(participantName);
                if (removedParticipant.getParticipantRole().equals(ParticipantRole.PRESENTER)) {
                    waitPresenterSelection();
                    logArea.setValue(logArea.getValue() + "\n" + "You have 5 seconds to select the presenter!");
                }
                logArea.setCursorPosition(logArea.getValue().length());
            } catch (ParticipantNotIncluded e) {
                new Notification(e.getMessage()).show(Page.getCurrent());
            }
        }
    };

    Button.ClickListener selectPresenterClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            try {
                Button presenterButton = clickEvent.getButton();
                String personName = (String) clickEvent.getButton().getData();
                String response;
                if (presenterButton.getCaption().equals(ParticipantRole.PRESENTER.getRole())) {
                    response = meetingMediator.selectMeetingPresenter(personName, currentMeeting);
                    presenterButton.setCaption(ParticipantRole.ATTENDEE.getRole());
                } else {
                    response = meetingMediator.deselectMeetingPresenter(personName, currentMeeting);
                    presenterButton.setCaption(ParticipantRole.PRESENTER.getRole());
                }
                logArea.setValue(logArea.getValue() + "\n" + response);
                logArea.setCursorPosition(logArea.getValue().length());

                meetingLayout.removeComponent(meetingTable);
                updateMeetingTable();

            } catch (PresenterAlreadySelected e) {
                new Notification(e.getMessage()).show(Page.getCurrent());
            }
        }
    };

    public void updateMeetingTable() {
        HorizontalLayout parent = (HorizontalLayout) meetingTable.getParent();
        if (parent != null ) {
            parent.removeComponent(meetingTable);
        }

        meetingTable.removeAllItems();
        meetingTable.setCaption("Welcome To Meeting: " + currentMeeting);
        meetingTable.setSizeFull();
        meetingTable.addContainerProperty("Name", String.class, null);
        meetingTable.addContainerProperty("Role", ParticipantRole.class, null);
        meetingTable.addContainerProperty("Remove Absent", Button.class, null);
        meetingTable.addContainerProperty("Select Presenter/Attendee", Button.class, null);
        meetingTable.setColumnAlignment("Remove Absent", Table.Align.CENTER);
        meetingTable.setColumnAlignment("Select Presenter/Attendee", Table.Align.CENTER);
        for (Participant participant : meetingMediator.getMeetingParticipants(currentMeeting).values()) {
            Button removeButton = new Button("Remove", removeAbsentClickListener);
            removeButton.setData(participant.getName());
            Button presenterButton = new Button(ParticipantRole.PRESENTER.getRole(), selectPresenterClickListener);
            presenterButton.setData(participant.getName());
            meetingTable.addItem(new Object[]{participant.getName(), participant.getParticipantRole(),
                    removeButton, presenterButton}, participant.getName());
        }
        meetingTable.setPageLength(meetingTable.size());
        meetingLayout.addComponent(meetingTable, 0);
    }

    @WebServlet(urlPatterns = "/*", name = "MeetingAppServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MeetingAppEntryPoint.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
