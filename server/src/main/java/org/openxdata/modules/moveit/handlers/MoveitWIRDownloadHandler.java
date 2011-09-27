/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.moveit.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import org.jdom.Document;
import org.jdom.Element;
import org.openxdata.modules.workflows.model.shared.WorkItemQuestion;
import org.openxdata.modules.workflows.server.YawlOXDCustomService;
import org.openxdata.modules.workflows.server.handlers.RequestHandler;
import org.openxdata.server.admin.model.StudyDef;
import org.openxdata.server.service.SettingService;
import org.openxdata.server.service.StudyManagerService;
import org.openxdata.workflow.mobile.model.MQuestionMap;
import org.openxdata.workflow.mobile.model.MWorkItem;
import org.yawlfoundation.yawl.util.JDOMUtil;

import org.openxdata.model.FormDef;
import org.openxdata.modules.moveit.server.model.BirthReport;
import org.openxdata.modules.moveit.server.model.DeathReport;
import org.openxdata.modules.moveit.server.service.BirthEventService;
import org.openxdata.modules.moveit.server.service.DeathEventService;
import org.openxdata.modules.moveit.server.service.UserEventReporterService;
import org.openxdata.server.Context;
import org.openxdata.server.admin.model.User;
import org.openxdata.server.service.AuthenticationService;
import org.openxdata.server.service.FormDownloadService;

/**
 *
 * @author kay
 * 
 * The code should have status codes to indicate that the phone has been downloaded to the 
 * phone or not. This is to help keep an audit trail of what has been committed to the mobile
 * and what has not.
 * 
 * 
 * 
 * Download all the forms by user. check by reporter_ids. So the trick here then is 
 * to download all the forms pertaining to a particular study, instead of getting 
 * individual forms.
 * 
 * Filter reporter id's belonging to individual supervisers. (New functionality needs to be added)
 * 
 * Issue of dealing of roles. Need to check how roles are handled in the system.
 * 
 * Need to determine the right event so that we can be able to load the right event to a WorkItem
 * 
 * 
 * 
 */

@SuppressWarnings("UseOfObsoleteCollectionType")
public class MoveitWIRDownloadHandler implements RequestHandler {

    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    private SettingService settingService;
    private StudyManagerService studyService;
    private StudyDef moveit_Study;
    private org.openxdata.server.admin.model.FormDef eventForm;
    private List<org.openxdata.server.admin.model.FormDef> formList;
    
    private HashMap<String,String> formXml;
    private List<Element> children;

    private HashMap<String,FormDef> formDefs;
    private String formid;
    org.openxdata.model.FormData formData;
    private FormDownloadService formDownloadService;
    private AuthenticationService authenticationService;
    private UserEventReporterService userEventReporterService;
    private BirthEventService birthEventService;
    private DeathEventService deathEventService;
    
    /**
     * 
     * TODO find a way of determining how an even occurs
     * 
     * 
     * finding a way to pass the user to this handler so that filtering can be done 
     * according to this specific user
     * 
     * Use the birth/death service to recieve the objects from the database.
     * '
     * Prefilling values as they are being sent to workflow items
     * 
     * 
     * 
     */

    public MoveitWIRDownloadHandler() {
        
        settingService = 
                (SettingService) Context.getBean("settingService");
        studyService = 
                (StudyManagerService) Context.getBean("studyManagerService");
        formDownloadService = 
                (FormDownloadService) Context.getBean("formDownloadService");
        authenticationService = 
                (AuthenticationService)Context.getBean("authenticationService");
        userEventReporterService =
                (UserEventReporterService) Context.getBean("userReporterService");
        birthEventService =
                (BirthEventService) Context.getBean("birthEventService");
        deathEventService =
                (DeathEventService) Context.getBean("deathEventService");
        
        
        setMoveit_STudyAndForm();
    }

    //Constructor.. simply to help me do some unit tests 
    public MoveitWIRDownloadHandler(boolean kk) {}
    
    
    /**
     * 
     * @param appoint
     * @return 
     * 
     * 
     * formData.setValue("child_name", birthReport.getEventName());
            formData.setValue("date_of_birth", birthReport.getDateOfEvent());
     * 
     */
    
    public Vector<MWorkItem> toMWorkItems(String appoint) {
        
        Document document = JDOMUtil.stringToDocument(appoint);
        
        //for births
        if (document.getRootElement().getChildren("new_study1_new_study1_form3_v1") != null)
        {
            
            List<BirthReport>  birthReportList = birthEventService.getAllBirthEvents();
            
            BirthReport birthReport = birthReportList.get(birthReportList.size() -1);
            Element childNameElement = document.getRootElement().getChild("child_name");
            Element dateOfBirthElement = document.getRootElement().getChild("date_of_birth");
            childNameElement.setText(birthReport.getEventName());
            dateOfBirthElement.setText(birthReport.getDateOfEvent().toString());
            
            children = document.getRootElement().getChildren("new_study1_new_study1_form3_v1");
            
            System.out.println("This is the xml representation for birth event \n" +document.toString());
    
        }
        
        /**
         * 
         * formData.setValue("name", deathReport.getEventName());
            formData.setValue("dateofdeath", deathReport.getDateOfEvent());
         * 
         */
        
        //for deaths
        else if (document.getRootElement().getChildren("new_study1_new_study1_form1_v1") != null)
        {
            List<DeathReport>  deathReportList = deathEventService.getAllDeathEvents();
            DeathReport deathReport = deathReportList.get(deathReportList.size() -1);
            Element eventNameElement = document.getRootElement().getChild(deathReport.getEventName());
            Element eventDateElement = document.getRootElement().getChild(deathReport.getDateOfEvent().toString());
            eventNameElement.setText(deathReport.getEventName());
            eventDateElement.setText(deathReport.getDateOfEvent().toString());
            
            children = document.getRootElement().getChildren("new_study1_new_study1_form1_v1");
            
            System.out.println("This is the xml representation for death event \n" +document.toString());
        }
        
        
        Vector<MWorkItem> workItemList = new Vector<MWorkItem>();
        for (Element appintmentElemet : children) {
            MWorkItem wir = new MWorkItem();
            List<WorkItemQuestion> workItemQuestions = YawlOXDCustomService.createQuestionListFromXML(appintmentElemet);
            Vector<MQuestionMap> quenMaps = toQuestionMaps(workItemQuestions);
            wir.setPrefilledQns(quenMaps);
            workItemList.add(wir);

            setStudyAndCaseAttributes(wir);
        }
        return workItemList;
    }

    private Vector<MQuestionMap> toQuestionMaps(List<WorkItemQuestion> workItemQuestions) {
        Vector<MQuestionMap> quenMaps = new Vector<MQuestionMap>();
        for (WorkItemQuestion workItemQuestion : workItemQuestions) {
            MQuestionMap map = new MQuestionMap();

            map.setQuestion(workItemQuestion.getQuestion());
            map.setValue(workItemQuestion.getAnswer());
            map.setParameter(workItemQuestion.getQuestion());
            quenMaps.add(map);
        }
        return quenMaps;
    }

    private void setStudyAndCaseAttributes(MWorkItem wir) {
        wir.setStudyId(moveit_Study != null ? moveit_Study.getId() : 0);
        wir.setFormId(eventForm != null ? eventForm.getDefaultVersion().getId() : 0);
        
        //Here I set the case ID and taskiD with appoint_id and dose_id to enable uniquely identinfying the
        //MWorkitems uniquely.. Its hack but am sure u can find a way around  this
        //wir.setCaseId(wir.getParam("appointment_id").getValue());
        //wir.setTaskId(wir.getParam("dose_id").getValue());
    }

    /**
     * Initialises the study and formDef for this workItem, the initilizer needs
     * to determine which form to load for which event
     */
    private void setMoveit_STudyAndForm() {
        //Get the IIS studyName from the setting service
        String studName = settingService.getSetting("moveit.it");
        List<StudyDef> studies = studyService.getStudies();
        for (StudyDef studyDef : studies) {
            if (studyDef.getName().equals(studName)) {
                moveit_Study = studyDef;
                break;
            }
        }
        
        if (moveit_Study == null) {
            return;
        }
        formList = moveit_Study.getForms();
             
    }
    
    /**
     * get the xml representation of the xform and pass it as a string
     * 
     * need to figure out at run time which form to load whether birth or death
     * 
     * @return 
     */
    
        private List<String> getEventXML(User user) {
                
		formDefs = new HashMap<String,FormDef>();
		formXml = new HashMap<String,String>();

		
                String username = user.getName();
                String password = user.getPassword();
                user = authenticationService.authenticate(username, password);
                System.out.println("@geteventxml Gotten User"+user.getName());
		
                //TODO Need to use proper locale
		List<String> forms = formDownloadService.getFormsDefaultVersionXml(user,"en");
                
                log.debug("this is the xml representation... " + forms.get(0).toString());
                
                /**
                 * 
                 * the idea is to save the a Map: key -> value pairs. So for each form it will
                 * be associated with an appropriate key which is the object/event to which it 
                 * belongs /DeathReport/BirthReport.
                 * 
                 */
                
                return forms;
	}
        
        
        
    /**
         * 
         * 
         * Pass the desired object which needs to be downloaded as this is the moethod which is
         * doing the handling. Or alternatively create another interface that does something 
         * similar. It would make more sense to re-invent the wheel.
         * 
         * @param user
         * @param is
         * @param os
         * @throws IOException 
         */

    @Override
    public void handleRequest(User user, InputStream is, OutputStream os) throws IOException {
      
            //Get the list of all xform representation of data. Need a 
            //way of breaking it down to births and deaths
            List<String> events = getEventXML(user);
            //Parse the apponintments XML and convert them to mWorkitems
            for(String xmlEvent : events){
                System.out.println("@handle  request ->"+xmlEvent);
//                Vector<MWorkItem> workItems = toMWorkItems(xmlEvent);
//                log.debug("Downloading workitems for User: " + user.getName());
//                HandlerStreamUtil streamHelper = new HandlerStreamUtil(is, os);
//                streamHelper.writeSucess();
//                streamHelper.writeSmallVector(new Vector(workItems));
//                streamHelper.flush();
            }
            
    }
    
        private Integer getFormId(String id){		
            try{		
                return Integer.parseInt(id);		
            }catch(Exception ex){}
		return null;
	}
    
   
}
