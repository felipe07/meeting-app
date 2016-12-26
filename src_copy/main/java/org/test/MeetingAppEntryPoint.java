package org.test;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.test.model.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MeetingAppEntryPoint extends UI {

    private MeetingMediator meetingMediator = new MeetingMediator();
    private String currentMeeting;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final HorizontalLayout mainLayout = new HorizontalLayout();
        final VerticalLayout controlsLayout = new VerticalLayout();

        final TextField meetingSubjectTF = new TextField();
        meetingSubjectTF.setCaption("Type Meeting Subject:");
        meetingSubjectTF.focus();

        HorizontalLayout meetingConfLayout = new HorizontalLayout();
        meetingConfLayout.setSpacing(true);

        Button createMeetingBtn = new Button("Create Meeting");
        createMeetingBtn.addClickListener(e -> {
            meetingConfLayout.removeAllComponents();

            currentMeeting = meetingSubjectTF.getValue();
            meetingMediator.createMeeting(currentMeeting);

            Table table = new Table("Setup Your Meeting: " + meetingSubjectTF.getValue());
            table.setSizeFull();
            table.addContainerProperty("Name", String.class, null);
            table.addContainerProperty("Add/Remove To Meeting", Button.class, null);
            table.setColumnAlignment("Add/Remove To Meeting", Table.Align.CENTER);
            for (String name : meetingMediator.getAllParticipantNames()) {
                Button addButton = new Button("Add", AddButtonClickListener);
                addButton.setData(name);
                table.addItem(new Object[] {name, addButton}, name);
            }
            table.setPageLength(table.size());

            meetingConfLayout.addComponent(table);

            mainLayout.addComponent(meetingConfLayout);
        });

        Button startMeetingBtn = new Button("Start Meeting");
        startMeetingBtn.setEnabled(false);

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
                if (addButton.getCaption().equals("Add")) {
                    meetingMediator.addParticipantToMeeting(personName, currentMeeting);
                    addButton.setCaption("Remove");
                } else {
                    meetingMediator.removeParticipantFromMeeting(personName, currentMeeting);
                    addButton.setCaption("Add");
                }

            } catch (ParticipantAlreadyIncluded | ParticipantNotIncluded e) {
                new Notification(e.getMessage()).show(Page.getCurrent());
            }
        }
    };

    @WebServlet(urlPatterns = "/*", name = "MeetingAppServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MeetingAppEntryPoint.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {}
}
