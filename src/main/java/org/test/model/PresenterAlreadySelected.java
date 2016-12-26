package org.test.model;

/**
 * Created by User on 26/12/2016.
 */
public class PresenterAlreadySelected extends Exception {

    public PresenterAlreadySelected() { super("A presenter was already selected for this meeting"); }
}
