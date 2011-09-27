/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.server.dao.hibernate;

import com.trg.dao.hibernate.GenericDAOImpl;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.openxdata.modules.moveit.server.dao.BirthEventDAO;
import org.openxdata.modules.moveit.server.model.BirthReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jmaina
 */

@Repository("birthEventDAO")
public class HibernateBirthEventDAO extends GenericDAOImpl<BirthReport, Integer> implements BirthEventDAO
{
    
        @Autowired
        @Override
        public void setSessionFactory(SessionFactory sessionFactory)
        {
                super.setSessionFactory(sessionFactory);
        }

        
        /**
         * the error is appearing because it is performing an update, this is due
         * to the fact that it is being seen as the same object
         * @param birthReport
         * @return 
         */
    @Override
    public boolean saveBirthEvent(BirthReport birthReport) {
        
        /*
         *return save(birthReport);
         *  
         * we are going to try something to see whether there will
         * be an increment
         * 
         * 
         * works only once. could it be it is because there is only one single session
         * for one particular object.
         * 
         * 
         */
        
        boolean status = false;
        
        if (birthReport != null)
        {
            Session session = getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            tx.begin();
            session.saveOrUpdate(birthReport);
            tx.commit(); 
            return status = true;
        }
        
        else
        {
            System.out.println(birthReport.getEventId());
            System.out.println("the object is null");
            return status;
        }
      
        
    }

    @Override
    public boolean deleteBirthEvent(BirthReport birthReport) {
               
        return remove(birthReport);
    }

    @Override
    public List<BirthReport> getBirthEvents() {
       return findAll();
    }
   
   

    @Override
    public List<BirthReport> getDeathEventsByReporter(int reporterId) {
        
        Criteria birthCriteria = getSession().createCriteria(BirthReport.class).
                                                add(Restrictions.eq("reporterId",reporterId ));
        
        return birthCriteria.list();
    }

    @Override
    public BirthReport getBirthEvent(BirthReport birthReport) {
        
        Criteria birthCriteria = getSession().createCriteria(BirthReport.class).
                                                add(Restrictions.eq("birthReportId", birthReport.getBirthReportId()));
        
        return (BirthReport) birthCriteria.list().get(0);
    }

    

    
}
