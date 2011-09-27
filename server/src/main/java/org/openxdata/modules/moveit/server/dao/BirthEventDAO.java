/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.server.dao;

import com.trg.dao.hibernate.GenericDAO;
import java.util.List;
import org.openxdata.modules.moveit.server.model.BirthReport;

/**
 *
 * @author jmaina
 */
public interface BirthEventDAO extends GenericDAO<BirthReport, Integer>{
    
    
    
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
     
     
     /**
      * Returns a list of Birth Events
      * @return 
      */
     public List<BirthReport> getBirthEvents();
     
     
     /**
     * TODO -> this should be in the service layer.
     * 
     * this is a handler for returning to the service layer a success status
     * whether saving was successful or not. It is to be used when replying to 
     * the RapidSMS system that the sms has been successfully saved.
     * @return 
     */
     public BirthReport getBirthEvent(BirthReport birthReport);
     
     
     public List<BirthReport> getDeathEventsByReporter(int reporterId);
     
     
}
