/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.server.service;

import java.util.List;
import org.openxdata.modules.moveit.server.model.UserReporters;
import org.openxdata.server.admin.model.User;

/**
 *
 * @author jmaina
 * 
 * 
 */
public interface UserEventReporterService {
    
    public List<UserReporters> retrieveReportersByUser(User user);
    
    public void saveReportersByUser(UserReporters userReporter);
    
    public void deleteReportersByUser(UserReporters userReporters);
    
    public org.openxdata.server.admin.model.User getUserByReporter(int reporterId);
    
}
