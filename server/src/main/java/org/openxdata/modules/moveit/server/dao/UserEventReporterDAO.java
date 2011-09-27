/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.server.dao;

import com.trg.dao.hibernate.GenericDAO;
import java.util.List;
import org.openxdata.modules.moveit.server.model.UserReporters;
import org.openxdata.server.admin.model.User;

/**
 *
 * @author jmaina
 * 
 * this class represents the data access layer for a collection of reporters 
 * that are under a certain user. The user represents the chief or whoever is 
 * working as oversight over the community health workers
 * 
 * find a way of getting the user by reporter id;
 * 
 * 
 */
public interface UserEventReporterDAO extends GenericDAO<UserReporters, Integer> {
    
    public void saveUserReporter(UserReporters userReporter);
    
    public void deleteUserReporter(UserReporters userReporter);
    
    public List<UserReporters> retrieveUserReporters(User user);
    
    public org.openxdata.server.admin.model.User getUserByReporter(int reporterId);
    
}
