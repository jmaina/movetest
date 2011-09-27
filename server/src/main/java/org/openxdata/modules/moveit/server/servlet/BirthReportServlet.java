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
import org.openxdata.modules.moveit.server.model.BirthReport;
import org.openxdata.server.Context;
import org.openxdata.modules.moveit.server.service.BirthEventService;
import org.openxdata.modules.moveit.server.util.Constants;
import org.openxdata.server.admin.model.FormData;
import org.openxdata.server.admin.model.exception.OpenXDataValidationException;
import org.openxdata.server.serializer.KxmlSerializerUtil;
import org.openxdata.server.service.AuthenticationService;
import org.openxdata.server.service.FormDownloadService;
import org.openxdata.server.service.FormService;
import org.openxdata.server.service.UserService;

/**
 *
 * @author gmimano
 */

@Singleton
public class BirthReportServlet extends HttpServlet{
    
    BirthEventService birthService;   
    BirthReport birthReport;
    AuthenticationService authService;
    FormService formService;
    UserService userService;
    FormDownloadService formDownloadService;
    KxmlSerializerUtil kxmlSerializer;



    private Calendar calendar;

    @Override
    public void init() throws ServletException {
        
        super.init();
        birthService = (BirthEventService)Context.getBean("birthEventService");
        authService = (AuthenticationService)Context.getBean("authenticationService");
        formService = (FormService)Context.getBean("formService");
        userService = (UserService) Context.getBean("userService");
        
    }




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        try {
            
            processRequest(req, resp);
        } 
        catch (EventNotSavedException ex) {
            Logger.getLogger(BirthReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(OpenXDataValidationException oxdErr){
            Logger.getLogger(BirthReportServlet.class.getName()).log(Level.SEVERE, null, oxdErr);
        }
        catch (Exception ex) {
                Logger.getLogger(BirthReportServlet.class.getName()).log(Level.SEVERE, null, ex);    
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        try {
            birthReport = new BirthReport();
            processRequest(req, resp);        
        } 
        catch (EventNotSavedException ex) {
            Logger.getLogger(BirthReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(OpenXDataValidationException oxdErr){
            Logger.getLogger(BirthReportServlet.class.getName()).log(Level.SEVERE, null, oxdErr);
        }
        catch (Exception ex) {
                Logger.getLogger(BirthReportServlet.class.getName()).log(Level.SEVERE, null, ex);    
        }
    }


    //TODO: need to figure out which request will not be handled get vs post
    
    /**
     * Process triggers to start of the information which will be 
     * sent to the mobile client
     * @param req
     * @param resp
     * @throws EventNotSavedException
     * @throws IOException 
     */
    public void processRequest(HttpServletRequest req,HttpServletResponse resp) throws EventNotSavedException,IOException, OpenXDataValidationException, Exception{
        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();
        
        try {
            birthReport = new BirthReport();
            //changing some github code
            checkParams(req, birthReport);
            //eventId = Integer.valueOf(getParam(""))
        } catch (ParamNotSetException ex) {
            out.print("FAIL");
            Logger.getLogger(BirthReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!birthService.saveBirthEvent(birthReport)){
            throw new EventNotSavedException(birthReport.getEventId());
        }else{
            out.print("SUCCESS");
            
            /**
             * 
             * TODO 
             * It would provide better abstraction if this code goes to one class 
             * 
             */
            
            DataHandlerUtil dataHandler = new DataHandlerUtil(birthReport.getReporterId());
            org.openxdata.model.FormData formData = dataHandler.initFormData(birthReport);
            formData.setValue("child_name", birthReport.getEventName());
            formData.setValue("date_of_birth", birthReport.getDateOfEvent());
            String xml = kxmlSerializer.fromFormData2XformModel(formData);
            
            
            FormData frmData = new FormData();
            frmData.setFormDefVersionId(formData.getDef().getId());
            frmData.setCreator(dataHandler.getUser());
            frmData.setDateCreated(birthReport.getDateOfReport());
            frmData.setData(xml);
            frmData.setDescription(formData.getDataDescription());
            formService.saveFormData(frmData);
        }

    }

    //check params and set the event details
    private void checkParams(HttpServletRequest req, BirthReport birthReport) throws ParamNotSetException {
        
        String tmpParam=null;
        
        calendar = Calendar.getInstance();
        
        if((tmpParam=req.getParameter(Constants.EVENT_ID))!=null){

            birthReport.setEventId(tmpParam);
            System.out.println(tmpParam);

            //reset tmpParam variable for next check
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_ID);
        }

        if((tmpParam=req.getParameter(Constants.EVENT_NAME))!=null){
            birthReport.setEventName(tmpParam);
            System.out.println(tmpParam);

            //reset tmpParam variable for next check
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_NAME);
        }

        if((tmpParam=req.getParameter(Constants.EVENT_DATE))!=null){
            //TODO: change to chack date formart of incoming string
            long timestamp = Long.valueOf(tmpParam).longValue();
            birthReport.setDateOfEvent(new Date(timestamp));
            //reset tmpParam variable for next check
            System.out.println(tmpParam);
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_DATE);
        }


        if((tmpParam=req.getParameter(Constants.EVENT_REPORTER))!=null){
            birthReport.setReporterId(Integer.valueOf(tmpParam));
            //reset tmpParam variable for next check
            System.out.println(tmpParam);
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_REPORTER);
        }

        if((tmpParam=req.getParameter(Constants.EVENT_REPORT_DATE))!=null){
            long timestamp = Long.valueOf(tmpParam).longValue();
            birthReport.setDateOfReport(new Date(timestamp));
            //reset tmpParam variable for next check
            System.out.println(tmpParam);
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_REPORT_DATE);
        }

        if((tmpParam=req.getParameter(Constants.EVENT_CONTACT))!=null){
            birthReport.setContactPhone(tmpParam);
            System.out.println(tmpParam);
            //reset tmpParam variable for next check
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_CONTACT);
        }
        
        if ((tmpParam=req.getParameter(Constants.SEX)) != null){
            birthReport.setSex(tmpParam);
            //reset tmpParam variable for next check
            tmpParam =  null;           
        }else{
            throw new ParamNotSetException(Constants.SEX);
        }
        
        if ((tmpParam=req.getParameter(Constants.LOCATION)) != null){
            birthReport.setLocation(tmpParam);
            //reset tmpParam variable for next check
            tmpParam =  null;           
        }else{
            throw new ParamNotSetException(Constants.LOCATION);
        }
        
        if ((tmpParam=req.getParameter(Constants.EVENT_PLACE)) != null){
            birthReport.setPlace_of_event(tmpParam);
            //reset tmpParam variable for next check
            tmpParam = null;
        }else{
            throw new ParamNotSetException(Constants.EVENT_PLACE);
        }
        
        if ((tmpParam=req.getParameter(Constants.NOTIFICATION_NO)) != null){
            birthReport.setNotificationNumber(Integer.valueOf(tmpParam));
            //reset tmpParam variable for next check
            tmpParam =  null;           
        }else{
            throw new ParamNotSetException(Constants.NOTIFICATION_NO);
        }


        //set the date captured in oxd
        birthReport.setDateTimeStamp(calendar.getTime());
        System.out.println(calendar.getTime());

    }

}
