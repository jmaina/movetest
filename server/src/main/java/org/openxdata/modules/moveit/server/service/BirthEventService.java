/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.server.service;

import java.util.List;
import org.openxdata.modules.moveit.server.model.BirthReport;

/**
 *
 * @author jmaina
 */
public interface BirthEventService
{  
    /**
     * Save an instance of a birth event 
     * @param birthReport 
     */
    
    public boolean saveBirthEvent(BirthReport birthReport);
     
     
     /**
      * Delete an instance of a birth event
      * @param birthReport 
      */
    
    public boolean deleteBirthEvent(BirthReport birthReport);
    
    
    
    public List<BirthReport> getAllBirthEvents();
}
