package org.test.ui;

import com.vaadin.ui.Notification;
import com.vaadin.ui.renderers.ClickableRenderer;

/**
 * Created by User on 25/12/2016.
 */
public class AddButtonListener implements ClickableRenderer.RendererClickListener {

    private String name;

    public AddButtonListener(String name) {
        this.name = name;
    }

    @Override
    public void click(ClickableRenderer.RendererClickEvent rendererClickEvent) {
        /*try {
            meetingMediator.addParticipantToMeeting(name, currentMeeting);
        } catch (ParticipantAlreadyIncluded participantAlreadyIncluded) {
            new Notification(participantAlreadyIncluded.getMessage());
        }*/
        new Notification(name);
    }
}
