/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.server.service.impl;

import java.util.List;

/**
 *
 * @author jmaina
 */
public abstract class EventService {
    
     /**
     * This method is responsible for returning a success code to the servlet this 
     * is to respond whether the record received from RapidSMS saved successfully.
     * 
     * It returns an abstraction of a status code. Either true for success or 
     * false for failure.
     * 
     * @param birthReport
     * @return 
     */
    
    public abstract boolean isEventSaved(Object obj);
    
    
    /**
      * Returns a list of Birth Events
      * @return 
      */
    
    public abstract List getAllEvents();
    
    
     /**
     * Get all the BirthRecords that a specific user sent
     * @param birthReport
     * @return 
     */
    public abstract List findEventsByReporter(int reporterId);
    
}
