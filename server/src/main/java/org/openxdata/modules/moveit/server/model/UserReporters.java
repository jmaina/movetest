/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.server.model;

import org.openxdata.server.admin.model.User;

/**
 *
 * @author jmaina
 * 
 * 
 */

public class UserReporters {
    //add CHW Name
    private int userReportId;
        
    private User user;
    
    private int reporterId;
    
    private String chwName;
    

    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }

    public int getUserReportId() {
        return userReportId;
    }

    public void setUserReportId(int userReportId) {
        this.userReportId = userReportId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getChwName() {
        return chwName;
    }

    public void setChwName(String chwName) {
        this.chwName = chwName;
    }
    
    

   
    
    
    
    
    
}
