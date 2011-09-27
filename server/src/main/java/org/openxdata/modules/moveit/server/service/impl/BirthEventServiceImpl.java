/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.server.service.impl;

import java.util.List;
import org.openxdata.modules.moveit.server.dao.BirthEventDAO;
import org.openxdata.modules.moveit.server.model.BirthReport;
import org.openxdata.modules.moveit.server.service.BirthEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jmaina
 */

@Transactional
@Service("birthEventService")
public class BirthEventServiceImpl extends EventService implements BirthEventService
{
    
    @Autowired
    private BirthEventDAO birthEventDAO;

     @Override
    public boolean isEventSaved(Object obj) {
        
        boolean isSaved = false;
        
        if (obj instanceof BirthReport)
        {
            BirthReport birthReport = (BirthReport) obj;
            
            if (birthEventDAO.getBirthEvent(birthReport) != null){
                isSaved = true;
            }
            else {
                isSaved = false; }
        }
        
        return isSaved;       
    }

    @Override
    public List findEventsByReporter(int reporterId) {
        
        return birthEventDAO.getDeathEventsByReporter(reporterId);
    }


    @Override
    public boolean saveBirthEvent(BirthReport birthReport) {
        
        return birthEventDAO.saveBirthEvent(birthReport);
    }

    @Override
    public boolean deleteBirthEvent(BirthReport birthReport) {
        
        return birthEventDAO.deleteBirthEvent(birthReport);
    }


    @Override
    public List<BirthReport> getAllBirthEvents() {
        
        return birthEventDAO.getBirthEvents();
     
    }

    @Override
    public List getAllEvents() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    

}
