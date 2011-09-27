/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.server.dao;

import com.trg.dao.hibernate.GenericDAO;
import java.util.List;
import org.openxdata.modules.moveit.server.model.DeathReport;

/**
 *
 * @author jmaina
 */
public interface DeathEventDAO extends GenericDAO<DeathReport, Integer>
{
    
    /**
     * Save an instance of a death event
     * 
     * @param deathEvent 
     */
    public boolean saveDeathEvent(DeathReport deathEvent);
    
    
    /**
     * Get a specific Death Record
     * 
     * @param deathReport
     * @return 
     */
    public DeathReport getDeathEvent(DeathReport deathReport);
    
    /**
     * deletes an instance of a death event
     * @param deathEvent 
     */
    public boolean deleteDeathEvent(DeathReport deathEvent);
    
    
    
    /**
     * Returns a list of saved Death Events
     * @return 
     */
    public List<DeathReport>  getAllDeathEvents();
    
    
  
    public List<DeathReport> getDeathEventsByReporter(int reporterId);
    
    
}
