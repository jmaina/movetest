/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openxdata.modules.moveit.server.exceptions;

/**
 *
 * @author gmimano
 */
public class EventNotSavedException extends Exception {

    public EventNotSavedException(String string) {
        super(string+" Event not saved");

    }

    public EventNotSavedException() {
        super("Event Not saved");
    }



}
