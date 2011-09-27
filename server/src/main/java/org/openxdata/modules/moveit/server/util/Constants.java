/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openxdata.modules.moveit.server.util;

/**
 *
 * @author gmimano
 */
public class Constants 
{
    
    public final static String EVENT_ID="event_id";
    //public final static String EVENT_TYPE="event_type";
    public final static String EVENT_NAME="event_name";
    public final static String EVENT_DATE= "event_date";
    public final static String EVENT_REPORT_DATE = "event_report_date";
    public final static String EVENT_REPORTER = "reporter_id";
    public final static String EVENT_CONTACT = "contact_phone";
    public final static String LOCATION = "loc";
    public final static String EVENT_PLACE = "place";
    public final static String NOTIFICATION_NO = "nid";
    public final static String SEX = "sex";
    public final static String DOB = "dob";
    
    public final static String MANAGER = "manager";
    public final static String REPPORTER = "reporter";
    public final static String CHWNAME = "chwname";
    
    /**
     * 
     *  Reporter (identified by telephone no)  =>  reporter_id
        Event ID => event_id
        Date event reported => event_report_date
        Death of Birth => dob => event_date
        Contact => contact_phone
        Sex =>sex
        Location code =>  loc
        Full Name => event_name
        Place of event (Hospital/Clinic or Home) =>  place
        Notification no. ( Incase Place is hospital and there is D1 filled) => nid
     * 
     */


}
