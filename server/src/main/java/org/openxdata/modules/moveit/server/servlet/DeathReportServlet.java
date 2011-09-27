/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openxdata.modules.moveit.server.servlet;

import com.google.inject.Singleton;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.openxdata.modules.moveit.handlers.DataHandlerUtil;
import org.openxdata.modules.moveit.server.exceptions.EventNotSavedException;
import org.openxdata.modules.moveit.server.exceptions.ParamNotSetException;
import org.openxdata.modules.moveit.server.model.DeathReport;
import org.openxdata.modules.moveit.server.service.DeathEventService;
import org.openxdata.modules.moveit.server.util.Constants;
import org.openxdata.server.Context;
import org.openxdata.server.admin.model.FormData;
import org.openxdata.server.serializer.KxmlSerializerUtil;
import org.openxdata.server.service.AuthenticationService;
import org.openxdata.server.service.FormService;
import org.openxdata.server.service.UserService;


/**
 *
 * @author gmimano
 * 
 */

@Singleton
public class DeathReportServlet extends HttpServlet{
    DeathReport deathReport;
    DeathEventService deathService;
    AuthenticationService authService;
    Calendar calendar;
    FormService formService;
    UserService userService;
    
    
    @Override
    public void init() throws ServletException {
        super.init();
        deathService = (DeathEventService)Context.getBean("deathEventService");
        authService = (AuthenticationService)Context.getBean("authenticationService");
        formService = (FormService)Context.getBean("formService");
        userService = (UserService) Context.getBean("userService");
    }




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        try {

            processRequest(req, resp);

        } catch (EventNotSavedException ex) {
            Logger.getLogger(DeathReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex) {
                Logger.getLogger(BirthReportServlet.class.getName()).log(Level.SEVERE, null, ex);    
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        try {
            processRequest(req, resp);
        } catch (EventNotSavedException ex) {
            Logger.getLogger(DeathReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex) {
                Logger.getLogger(BirthReportServlet.class.getName()).log(Level.SEVERE, null, ex);    
        }
    }


    //TODO: need to figure out which request will not be handled get vs post
    public void processRequest(HttpServletRequest req,HttpServletResponse resp) throws EventNotSavedException,IOException, Exception{
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        
        


        try {
            //changing some github code
            deathReport = new DeathReport();
            checkParams(req, deathReport);
            //eventId = Integer.valueOf(getParam(""))
        } catch (ParamNotSetException ex) {
            out.print("FAIL");
            Logger.getLogger(BirthReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }


        if (deathService!=null) {
            System.out.println("deaTH SERVICE NOT NULL");
            
        }
               
        if (deathReport!=null) {
            System.out.println("deaTH REPORT NOT NULL");
            System.out.println(deathReport.getDateOfEvent() + " " + deathReport.getEventName());
        }
        
        if(!deathService.saveDeathEvent(deathReport)){
            throw new EventNotSavedException(deathReport.getEventId());
        }else{
            out.print("SUCCESS");
            DataHandlerUtil dataHandler = new DataHandlerUtil(deathReport.getReporterId());
            org.openxdata.model.FormData formData = dataHandler.initFormData(deathReport);
            formData.setValue("name", deathReport.getEventName());
            formData.setValue("dateofdeath", deathReport.getDateOfEvent());
            String xml = KxmlSerializerUtil.fromFormData2XformModel(formData);
            
            
            FormData frmData = new FormData();
            frmData.setFormDefVersionId(formData.getDef().getId());
            frmData.setCreator(dataHandler.getUser());
            frmData.setDateCreated(deathReport.getDateOfReport());
            frmData.setData(xml);
            frmData.setDescription(formData.getDataDescription());
            formService.saveFormData(frmData);
                                
        }

    }

    //check params and set the event details
    private void checkParams(HttpServletRequest req, DeathReport deathReport) throws ParamNotSetException {
        
        String tmpParam=null;
        
        calendar = Calendar.getInstance();
        
        if((tmpParam=req.getParameter(Constants.EVENT_ID))!=null){

            deathReport.setEventId(tmpParam);

            //reset tmpParam variable for next check
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_ID);
        }

        if((tmpParam=req.getParameter(Constants.EVENT_NAME))!=null){
            deathReport.setEventName(tmpParam);

            //reset tmpParam variable for next check
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_NAME);
        }

        if((tmpParam=req.getParameter(Constants.EVENT_DATE))!=null){
            //TODO: change to chack date formart of incoming string
            long timestamp = Long.valueOf(tmpParam).longValue();
            deathReport.setDateOfEvent(new Date(timestamp));
            //reset tmpParam variable for next check
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_DATE);
        }


        if((tmpParam=req.getParameter(Constants.EVENT_REPORTER))!=null){
            deathReport.setReporterId(Integer.valueOf(tmpParam));
            //reset tmpParam variable for next check
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_REPORTER);
        }

        if((tmpParam=req.getParameter(Constants.EVENT_REPORT_DATE))!=null){
            
            long timestamp = Long.valueOf(tmpParam).longValue();
            
            deathReport.setDateOfReport(new Date(timestamp));
            //reset tmpParam variable for next check
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_REPORT_DATE);
        }

        if((tmpParam=req.getParameter(Constants.EVENT_CONTACT))!=null){
            deathReport.setContactPhone(tmpParam);
            //reset tmpParam variable for next check
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_CONTACT);
        }
        
        if ((tmpParam=req.getParameter(Constants.DOB)) != null){
            
            long timestamp = Long.valueOf(tmpParam).longValue();
            deathReport.setDateOfBirth(new Date(timestamp));
            //reset tmpParam variable for next check
            tmpParam =  null;           
        }else{
            throw new ParamNotSetException(Constants.DOB);
        }
        
        
        if ((tmpParam=req.getParameter(Constants.SEX)) != null){
            deathReport.setSex(tmpParam);
            //reset tmpParam variable for next check
            tmpParam =  null;           
        }else{
            throw new ParamNotSetException(Constants.SEX);
        }
        
        if ((tmpParam=req.getParameter(Constants.LOCATION)) != null){
            deathReport.setLocation(tmpParam);
            //reset tmpParam variable for next check
            tmpParam =  null;           
        }else{
            throw new ParamNotSetException(Constants.LOCATION);
        }
        
        if ((tmpParam=req.getParameter(Constants.EVENT_PLACE)) != null){
            deathReport.setPlace_of_event(tmpParam);
            //reset tmpParam variable for next check
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_PLACE);
        }
        
        if ((tmpParam=req.getParameter(Constants.NOTIFICATION_NO)) != null){
            deathReport.setNotificationNumber(Integer.valueOf(tmpParam));
            //reset tmpParam variable for next check
            tmpParam =  null;           
        }else{
            throw new ParamNotSetException(Constants.NOTIFICATION_NO);
        }
        
        
        //set the date captured in oxd
        deathReport.setDateTimeStamp(calendar.getTime());



    }

}
