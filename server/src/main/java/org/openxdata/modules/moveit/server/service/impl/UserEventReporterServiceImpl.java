/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.server.service.impl;

import java.util.List;
import org.openxdata.modules.moveit.server.dao.UserEventReporterDAO;
import org.openxdata.modules.moveit.server.model.UserReporters;
import org.openxdata.modules.moveit.server.service.UserEventReporterService;
import org.openxdata.server.admin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jmaina
 * 
 * Service implementation of retrieving a list of reporters that belong to a certain chief
 * or whoever has oversight over a number of community health workers
 * 
 * 
 */

@Transactional
@Service("userReporterService")
public class UserEventReporterServiceImpl implements UserEventReporterService {
    
    @Autowired
    private UserEventReporterDAO userEventReporterDao;

    @Override
    public List<UserReporters> retrieveReportersByUser(User user) {
        return userEventReporterDao.retrieveUserReporters(user);
    }

    @Override
    public void saveReportersByUser(UserReporters userReporter) {
        userEventReporterDao.saveUserReporter(userReporter);
    }

    @Override
    public void deleteReportersByUser(UserReporters userReporters) {
        userEventReporterDao.deleteUserReporter(userReporters);
    }

    @Override
    public User getUserByReporter(int reporterId) {
        return userEventReporterDao.getUserByReporter(reporterId);
    }
    
    
}
