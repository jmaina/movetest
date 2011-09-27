/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.server.dao.hibernate;

import com.trg.dao.hibernate.GenericDAOImpl;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openxdata.modules.moveit.server.dao.UserEventReporterDAO;
import org.openxdata.modules.moveit.server.model.UserReporters;
import org.openxdata.server.admin.model.User;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jmaina
 * 
 * 
 */

@Repository("userEventReporterDAO")
public class HibernateUserEventReporter extends GenericDAOImpl<UserReporters, Integer> implements UserEventReporterDAO {

    @Override
    public void saveUserReporter(UserReporters userReporter) {      
        save(userReporter);
    }

    @Override
    public void deleteUserReporter(UserReporters userReporter) {
        remove(userReporter);
    }
    
    @Override
    public List<UserReporters> retrieveUserReporters(User user) {
        
        Criteria criteria = getSessionFactory().
                                getCurrentSession().
                                    createCriteria(UserReporters.class).
                                        add(Restrictions.eq("user_id", user.getId()));
        
        List<UserReporters> userReportersList = criteria.list();
        
        return userReportersList;
    }

    /**
     * 
     * TODO this method is not complete. It needs further work.
     * 
     * @return 
     */
    
    
    @Override
    public User getUserByReporter(int reporterId) {
        
        Criteria criteria = getSessionFactory().
                                getCurrentSession().
                                    createCriteria(User.class).
                                        createCriteria("user").
                                            add(Restrictions.eq("reporterid", reporterId));
        
        List<User> userLst = criteria.list();
        
        org.openxdata.server.admin.model.User user = userLst.get(0);
        
        return user;
    }
   
}
