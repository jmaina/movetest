
package org.openxdata.modules.workflows.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.openxdata.modules.workflows.model.shared.DBSpecification;
import org.openxdata.modules.workflows.server.context.WFContext;
import org.openxdata.modules.workflows.server.service.SpecificationService;
import org.openxdata.modules.workflows.server.service.WorkItemsService;
import org.openxdata.modules.workflows.model.shared.GWTFriendlyWorkItem;
import org.openxdata.modules.workflows.model.shared.WorkItemQuestion;
import org.openxdata.server.admin.model.exception.OpenXDataException;
import org.openxdata.server.admin.model.exception.OpenXDataRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.engine.interfce.Marshaller;
import org.yawlfoundation.yawl.engine.interfce.SpecificationData;
import org.yawlfoundation.yawl.engine.interfce.TaskInformation;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.engine.interfce.interfaceB.InterfaceBWebsideController;
import org.yawlfoundation.yawl.exceptions.YAWLException;
import org.yawlfoundation.yawl.util.JDOMUtil;

/**
 * Communicates with yawl engine as a custom service
 * @author kay
 */
@Component("YawlOXDCustomService")
public class YawlOXDCustomService extends InterfaceBWebsideController {

        private String _sessionHandle = null;
        @Autowired
        SpecificationService specService;
        @Autowired
        WorkItemsService workItemsService;

        public YawlOXDCustomService() {
        }

        public static YawlOXDCustomService getInstance() {
                return WFContext.getYawlService();
        }

        @Override
        public void handleEnabledWorkItemEvent(WorkItemRecord enabledWorkItem) {
                try {
                        _log.debug("Received Enabled WorkItem: <"+enabledWorkItem.getID()+">");
                        initSessionHandle();//first initialise the session handle

                        extractAndSaveSpec(enabledWorkItem);//save the specification for this workitem

                        _log.debug("Checking out WorkItem: <"+enabledWorkItem.getID()+">");
                        enabledWorkItem = checkOut(enabledWorkItem.getID(), _sessionHandle);
                        _log.debug(String.format("Saving Checked out Workitem <%s> ", enabledWorkItem.getID()));
                        workItemsService.saveWorkItem(enabledWorkItem);

                        List<WorkItemRecord> childWirs = checkOutChildren(enabledWorkItem.getID());
                        for (WorkItemRecord workItemRecord : childWirs) {
                                workItemsService.saveWorkItem(workItemRecord);
                        }
                } catch (Exception ex) {
                        Logger.getLogger(YawlOXDCustomService.class.getName()).log(
                                Level.SEVERE, null, ex);
                        throw new RuntimeException(ex);//throw exception to notify the yawl engine
                        // about this failure
                }
        }

        @Override
        public void handleCancelledWorkItemEvent(WorkItemRecord wir) {
                WorkItemRecord workitem = workItemsService.getWorkitem(wir.getCaseID(), wir.getTaskID());
                if (workitem != null)
                        workItemsService.deleteWorkItem(workitem);
        }

        /**
         * Deletes all workItems in that are in caseID
         * @param caseID
         */
        @Override
        public void handleCancelledCaseEvent(String caseID) {
                workItemsService.deleteWorkItemInCase(caseID);
        }

        /**
         * initializes session handle.First tries to connect with current session handle
         * and on failure makes a new handshake to aquire a new session handle.
         * @return Session handle string
         * @throws IOException
         */
        private String initSessionHandle() throws IOException {

                if (!checkConnection(_sessionHandle)) {
                        _sessionHandle = connect(DEFAULT_ENGINE_USERNAME,
                                DEFAULT_ENGINE_PASSWORD);
                }
                if (!successful(_sessionHandle)) {
                        _log.error("Unsuccessful");
                        throw new RuntimeException("Failed to initialise session handle");
                }
                return _sessionHandle;
        }

        /**
         * Checks if workItem spec is available.If not then the spec is downloaded
         * from the yawl engine
         * @param wir WorkItem whose spec is to be cross checked
         * @throws IOException
         * @throws OpenXDataException
         */
        private void extractAndSaveSpec(WorkItemRecord wir) throws IOException, OpenXDataException {
                
                YSpecificationID specId = new YSpecificationID(
                        wir.getSpecificationID(), wir.getSpecVersion());

                DBSpecification dbSpec = specService.getDBSpecWithSpecID(specId.getSpecName() + specId.getVersionAsString());

                if (dbSpec == null) {
                        _log.debug("Extracting from Yawl Engine Spec for Workitem: <"+wir.getID()+">");
                        String specification = _interfaceBClient.getSpecification(specId, initSessionHandle());
                        specService.saveSpecs(specification);
                }
        }

                       @Override
        public YParameter[] describeRequiredParams() {
                YParameter[] params = new YParameter[1];
                YParameter param;

                param = new YParameter(null, YParameter._OUTPUT_PARAM_TYPE);
                param.setDataTypeAndName("integer", "formDataID", XSD_NAMESPACE);
                param.setDocumentation("Id of the formDataCollected");
                params[0] = param;

                return params;
        }


        /**
         * Checks out children of a workItems
         * @param workItemId workitem id whose children should be checked out
         * @return Children of checked out workItem
         * @throws IOException
         * @throws YAWLException
         */
        private List<WorkItemRecord> checkOutChildren(String workItemId)
                throws IOException, YAWLException {
                initSessionHandle();

                List<WorkItemRecord> children = getChildren(workItemId, _sessionHandle);

                for (int i = 0; i < children.size(); i++) {

                        WorkItemRecord itemRecord = (WorkItemRecord) children.get(i);
                        if (WorkItemRecord.statusFired.equals(itemRecord.getStatus())) {
                                checkOut(itemRecord.getID(), _sessionHandle);
                        }
                }
                return children;
        }

        /**
         * Attempts to get the output parameters from the yawl engine
         * @param wir
         * @return Parameters of the workItem
         * @throws IOException If the Yawl engine is down
         */
        public List<YParameter> getOutputParams(WorkItemRecord wir)
                throws IOException {
                YSpecificationID specId = new YSpecificationID(
                        wir.getSpecificationID(), wir.getSpecVersion());
                _log.debug(String.format("Getting Output Task Information For Workitem <%s>", wir.getID()));
                TaskInformation taskInfo = getTaskInformation(specId, wir.getTaskID(),
                        _sessionHandle);
                List<YParameter> outputParams = taskInfo.getParamSchema().getOutputParams();
                return outputParams;
        }

        /**
         * Converts a workitem to an object with enough information to be loaded into the browser
         * @param wir Workitem to convert
         * @param includeOutParams true if u want the output parameters to be included
         * @return A GWTWorkitem that be be loaded into GWT
         * @throws IOException If the yawl engine is down and the u set the includeOutParams to true
         */
        public GWTFriendlyWorkItem getGWTFriendlyWorkItem(WorkItemRecord wir,
                boolean includeOutParams)
                throws IOException {
                List<WorkItemQuestion> itemQuestions = createQuestionListFromXML(wir.getDataList());
                GWTFriendlyWorkItem gwtfwi = new GWTFriendlyWorkItem(wir.getID());
                gwtfwi.getInputParams().addAll(itemQuestions);
                gwtfwi.setEnabledTime(wir.getEnablementTime());
                gwtfwi.setState(wir.getStatus());
                gwtfwi.setTaskName4Disp(wir.getTaskIDForDisplay());

                if (includeOutParams) { // Add the output params
                        List<YParameter> outputParams = getOutputParams(wir);
                        List<WorkItemQuestion> outputQuestions = createOutPutQuestionList(outputParams);
                        gwtfwi.getOutputParams().addAll(outputQuestions);
                }
                return gwtfwi;
        }

        /**
         * converts an XML element in the form <codde> <question>questionText</question> </code>
         * to a WorkItem question
         * @param dataList
         * @return
         */
        public static List<WorkItemQuestion> createQuestionListFromXML(
                Element dataList) {
                List<WorkItemQuestion> questions = new ArrayList<WorkItemQuestion>();
                if (dataList == null)
                        return questions;
                List children = dataList.getChildren();
                for (Object object : children) {
                        Element elem = (Element) object;
                        questions.add(new WorkItemQuestion(elem.getName(), elem.getText()));
                }
                return questions;
        }

        /**
         * Converts a list of YParameters to a list of WorkItemQuestions
         * @param outputParams list of parameters
         * @return List of
         */
        public static List<WorkItemQuestion> createOutPutQuestionList(
                List<YParameter> outputParams) {
                List<WorkItemQuestion> list = new ArrayList<WorkItemQuestion>();
                for (YParameter yParameter : outputParams) {
                        String name = yParameter.getName();
                        String dataTypeName = yParameter.getDataTypeName();
                        WorkItemQuestion workItemQuestion = new WorkItemQuestion(name, null);
                        workItemQuestion.setType(dataTypeName);
                        list.add(workItemQuestion);
                }
                return list;
        }

        /**
         * Checks in a workitem into the yawl engine.
         * @param workitem workItem to checkIn
         * @param datalist output data list string.
         * @throws IOException if engine is down
         * @throws JDOMException if data strings are erratic
         */
        public void checkInWorkItem(WorkItemRecord workitem, String datalist) throws IOException, JDOMException {
                cacheItem(workitem);//cache into model othere superclass will throw a NPE
                String initSessionHandle = initSessionHandle();
                Element inputData = JDOMUtil.stringToElement(workitem.getDataListString());
                Element outputData = JDOMUtil.stringToElement(datalist);
                String result = checkInWorkItem(workitem.getID(), inputData, outputData, initSessionHandle);
                if(result.startsWith("<failure>")) throw new OpenXDataRuntimeException(result);
        }

        private synchronized void cacheItem(WorkItemRecord workitem) {
                _model.addWorkItem(workitem);
        }

        /**
         *function to launch cases.
         * @param caseID -case id for which case data will be input variables
         * @param caseData - xml representing input variables and
         * @return List of caseIDs that have been started
         */
        public List<String> launchcases(YSpecificationID specID, List<String> caseData) throws IOException {
                List<String> caseIDs = new ArrayList<String>();

                initSessionHandle();
                for (String data : caseData) {
                        String caseID = _interfaceBClient.launchCase(specID, data, _sessionHandle);
                        if (caseID != null) {
                                caseIDs.add(caseID);
                        }
                }


                return caseIDs;
        }

        public Set<String> getAllRunningCaseIDs() throws IOException {
                Set<String> result = new HashSet<String>();
                Set<SpecificationData> specDataSet = getSpecList(initSessionHandle());
                if (specDataSet != null) {
                        for (SpecificationData specData : specDataSet) {
                                List<String> caseIDs = getRunningCasesAsList(specData.getSpecID(),
                                        initSessionHandle());
                                if (caseIDs != null)
                                        result.addAll(caseIDs);
                        }
                }
                return result;
        }

        public Set<SpecificationData> getSpecList(String handle) {
                Set<SpecificationData> result = new HashSet<SpecificationData>();
                try {
                        Iterator itr = getSpecificationPrototypesList(handle).iterator();
                        while (itr.hasNext()) {
                                result.add((SpecificationData) itr.next());
                        }
                } catch (IOException ioe) {
                        _log.error("IO Exception retrieving specification list", ioe);
                        result = null;
                }
                return result;
        }

        public Set<SpecificationData> getSpecList() throws IOException {
                return getSpecList(initSessionHandle());
        }

        public List<String> getRunningCasesAsList(YSpecificationID specID, String handle) {
                try {
                        String casesAsXML = _interfaceBClient.getCases(specID, handle);
                        if (_interfaceBClient.successful(casesAsXML))
                                return Marshaller.unmarshalCaseIDs(casesAsXML);
                } catch (IOException ioe) {
                        _log.error("IO Exception retrieving running cases list", ioe);
                }
                return null;
        }

        /**
         * Cancels the case & removes its workitems (if any) from the service's queues
         * & caches. Note: this method is synchronised to prevent any clash with
         * 'handleCancelledCaseEvent', which is triggered by the call to cancelCase in
         * this method.
         * @param caseID the case to cancel
         * @param handle a valid session handle with the engine
         * @return a message from the engine indicating success or otherwise
         * @throws IOException if there's trouble talking to the engine
         */
        public synchronized String cancelCase(String caseID, String handle) throws IOException {


                // cancel the case in the engine
                String result = _interfaceBClient.cancelCase(caseID, _sessionHandle);

                // remove live items for case from workqueues and cache
                if (successful(result)) {
                } else
                        _log.error("Error attempting to Cancel Case.");

                return result;
        }
        private static org.apache.log4j.Logger _log = org.apache.log4j.Logger.getLogger(YawlOXDCustomService.class);

        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                response.setContentType("text/html");
                PrintWriter outputWriter = response.getWriter();
                StringBuffer output = new StringBuffer();

                output.append(
                        "<html><head><title>Your Custom YAWL Service Welcome Page</title>"
                        + "</head><body>"
                        + "<H3>Please create a welcome page for your custom YAWL Service</H3>"
                        + "<p>One option is to just override the method "
                        + "\"doGet()\" of  InterfaceBWebsideController, when you extend this "
                        + "class with your own code.</p>"
                        + "<p>Alternatively you could redirect to a JSP of your design.</p>"
                        + "</body>"
                        + "</html>");

                outputWriter.write(output.toString());
                outputWriter.flush();
                String parameter = request.getParameter("cancel");
                Set<String> allRunningCaseIDs = getAllRunningCaseIDs();
                outputWriter.write("Cancelling Cases: {" + allRunningCaseIDs + "}\n");
                for (String caseID : allRunningCaseIDs) {
                        if (caseID.startsWith(parameter) || parameter.equals("all")) {
                                cancelCase(caseID, _sessionHandle);
                                outputWriter.write("Cancelled Case: " + caseID + "\n");
                                outputWriter.flush();
                        }
                }
                outputWriter.close();
        }
}
