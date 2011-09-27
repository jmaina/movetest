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
import org.openxdata.modules.moveit.server.dao.DeathEventDAO;
import org.openxdata.modules.moveit.server.model.DeathReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *To check on the inheritance mechanism
 * 
 * 
 * @author jmaina
 */

@Repository("deathEventDAO")
public class HibernateDeathEventDAO extends GenericDAOImpl<DeathReport, Integer>  implements DeathEventDAO
{
    
    /**
     * we need an indicator as to whether saving of the event was successful or not
     * @param deathEvent
     * @return 
     */
    
    @Autowired
    @Override
    public void setSessionFactory(SessionFactory sessionFactory)
    {
                super.setSessionFactory(sessionFactory);
    }
   

    @Override
    public boolean saveDeathEvent(DeathReport deathEvent) {
        
        boolean status = false;
        
        if (deathEvent != null)
        {
            Session session = getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            tx.begin();
            session.saveOrUpdate(deathEvent);
            tx.commit(); 
            return status = true;
        }
        
        else
        {
            System.out.println(deathEvent.getEventId());
            System.out.println("the object is null");
            return status;
        }
      
    }

    @Override
    public boolean deleteDeathEvent(DeathReport deathEvent) {
        
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        tx.begin();
        session.delete(deathEvent);
        tx.commit();
        return true;
    }

    /**
     * @param deathReport
     * @return 
     */
    
    @Override
    public DeathReport getDeathEvent(DeathReport deathReport) {
        
        Criteria eventCriteria = getSession().createCriteria(DeathReport.class).
                                               add(Restrictions.eq("deathReportId", deathReport.getId()));
        
        return (DeathReport) eventCriteria.list().get(0);
        
    }

    /**
     * this method retrieves all the reports entered by an individual reporter. identified by 
     * the reporter id. 
     * 
     * @param reporterId
     * @return 
     */
    @Override
    public List<DeathReport> getDeathEventsByReporter(int reporterId) {
        
        Criteria eventCriteria = getSession().createCriteria(DeathReport.class).
                                               add(Restrictions.eq("reporterId",reporterId ));
        
        List reporterEvents = eventCriteria.list();
        
        return reporterEvents;
        
        
    }

    @Override
    public List<DeathReport> getAllDeathEvents() {
           List <DeathReport> deathReportList = findAll();
        
        return deathReportList;
    }
    
}
