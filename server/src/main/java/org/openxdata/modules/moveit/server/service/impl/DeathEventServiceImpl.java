/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.server.service.impl;

import java.util.List;
import org.openxdata.modules.moveit.server.dao.DeathEventDAO;
import org.openxdata.modules.moveit.server.model.DeathReport;
import org.openxdata.modules.moveit.server.service.DeathEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jmaina
 */

@Transactional
@Service("deathEventService")
public class DeathEventServiceImpl  extends EventService implements DeathEventService
{
    @Autowired
    private DeathEventDAO deathEventDAO;
    

    /**
     * this is a generic method for testing whether the Event in question was successfully saved.
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean isEventSaved(Object obj) {
        
        boolean isSaved = false;
        
        if (obj instanceof DeathReport)
        {
            DeathReport deathReport = (DeathReport) obj;
            
            if (deathEventDAO.getDeathEvent(deathReport) != null)
                isSaved = true;
            else
                isSaved = false;
        }       
        
        return isSaved;
        
    }

    @Override
    public List<DeathReport> getAllEvents() {
        return deathEventDAO.findAll();
    }

    @Override
    public List<DeathReport> findEventsByReporter(int reporterId) {
       
        return deathEventDAO.getDeathEventsByReporter(reporterId);
    }

    @Override
    public DeathReport getDeathEvent(DeathReport deathReport) {
        
        return deathEventDAO.getDeathEvent(deathReport);
    }

    /**
     * 
     * we need an indicator whether saving was successful or not
     * 
     * @param deathEvent
     * @return 
     */
    @Override
    public boolean saveDeathEvent(DeathReport deathEvent) {
        
        return deathEventDAO.saveDeathEvent(deathEvent);
    }

    @Override
    public boolean deleteDeathEvent(DeathReport deathEvent) {
        
        return deathEventDAO.deleteDeathEvent(deathEvent);
    }

    @Override
    public List<DeathReport> getAllDeathEvents() {
        
        return deathEventDAO.getAllDeathEvents();
    }

    
    
}
