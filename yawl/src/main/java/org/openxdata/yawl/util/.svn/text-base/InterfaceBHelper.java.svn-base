package org.openxdata.yawl.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.engine.interfce.Marshaller;
import org.yawlfoundation.yawl.engine.interfce.SpecificationData;
import org.yawlfoundation.yawl.engine.interfce.TaskInformation;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.engine.interfce.interfaceB.InterfaceBWebsideController;
import org.yawlfoundation.yawl.engine.interfce.interfaceB.InterfaceB_EnvironmentBasedClient;
import org.yawlfoundation.yawl.exceptions.YAWLException;
import org.yawlfoundation.yawl.util.JDOMUtil;
import org.yawlfoundation.yawl.util.StringUtil;

/**
 *
 * @author kay
 */
public class InterfaceBHelper {

    private static org.apache.log4j.Logger _log = org.apache.log4j.Logger.getLogger(InterfaceBHelper.class);
    private InterfaceBWebsideController controller;
    private String _sessionHandle;
    private final String yawlUserName;
    private final String yawlPassword;
    private InterfaceB_EnvironmentBasedClient _interfaceBClient;

    public InterfaceBHelper(InterfaceBWebsideController controller,
            InterfaceB_EnvironmentBasedClient _interfaceBClient,
            String yawlUserName, String yawlPassword) {
        this.controller = controller;
        this.yawlUserName = yawlUserName;
        this.yawlPassword = yawlPassword;
        this._interfaceBClient = _interfaceBClient;

    }

    public WorkItemRecord checkOut(WorkItemRecord wir) throws IOException, YAWLException {
        WorkItemRecord checkOut = controller.checkOut(wir.getID(), getSessionHandle());
        return checkOut;
    }

    /**
     * initializes session handle.First tries to connect with current session handle
     * and on failure makes a new handshake to aquire a new session handle.
     * @return Session handle string
     * @throws IOException
     */
    public String initSessionHandle() throws IOException {

        if (!controller.checkConnection(_sessionHandle)) {
            _sessionHandle = controller.connect(yawlUserName,
                    yawlPassword);
        }
        if (!controller.successful(_sessionHandle)) {
            _log.error("Unsuccessful");
            throw new RuntimeException("Failed to initialise session handle");
        }
        return _sessionHandle;
    }

    public List<WorkItemRecord> checkOutChildren(String workItemId) throws IOException, YAWLException {

        List<WorkItemRecord> children = controller.getChildren(workItemId, getSessionHandle());

        for (int i = 0; i < children.size(); i++) {

            WorkItemRecord itemRecord = (WorkItemRecord) children.get(i);
            if (WorkItemRecord.statusFired.equals(itemRecord.getStatus())) {
                controller.checkOut(itemRecord.getID(), getSessionHandle());
            }
        }
        return children;
    }

    public String getDecompositionID(WorkItemRecord wrkItem) throws IOException {
        YSpecificationID ySpecId = new YSpecificationID(wrkItem.getSpecificationID(), wrkItem.getSpecVersion());
        TaskInformation taskInformation = controller.getTaskInformation(ySpecId, wrkItem.getTaskID(), getSessionHandle());
        _log.debug("Getting taskInformation for workitem" + wrkItem.getID());
        return taskInformation.getDecompositionID();
    }

    public static String getValueFromWorkItem(WorkItemRecord wir, String param) {
        String dataListString = wir.getDataListString();
        Element element = JDOMUtil.stringToElement(dataListString);
        String name = element.getChildTextNormalize(param);
        return name;
    }

    public void checkInWorkItem(WorkItemRecord wir, String outPut) throws IOException, JDOMException {
        Element outPutElement = JDOMUtil.stringToElement(outPut);
        Element inputElement = JDOMUtil.stringToElement(wir.getDataListString());

        controller.checkInWorkItem(wir.getID(), inputElement, outPutElement, getSessionHandle());
        _log.debug("Checking in workitem " + wir.getID());
    }

    /**
     *function to launch cases.
     * @param caseID -case id for which case data will be input variables
     * @param caseData - xml representing input variables and
     * @return List of caseIDs that have been started
     */
    public List<String> launchcases(YSpecificationID specID, List<String> caseData) throws IOException {
        List<String> caseIDs = new ArrayList<String>();
        for (String data : caseData) {
            String caseID = _interfaceBClient.launchCase(specID, data, getSessionHandle());
            if (caseID != null) {
                caseIDs.add(caseID);
            }
        }


        return caseIDs;
    }

    public Set<String> getAllRunningCaseIDs() throws IOException {
        Set<String> result = new HashSet<String>();
        Set<SpecificationData> specDataSet = getSpecList(getSessionHandle());
        if (specDataSet != null) {
            for (SpecificationData specData : specDataSet) {
                List<String> caseIDs = getRunningCasesAsList(specData.getSpecID(),
                        getSessionHandle());
                if (caseIDs != null)
                    result.addAll(caseIDs);
            }
        }
        return result;
    }

    public Set<SpecificationData> getSpecList(String handle) {
        Set<SpecificationData> result = new HashSet<SpecificationData>();
        try {
            Iterator itr = controller.getSpecificationPrototypesList(handle).iterator();
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
        return getSpecList(getSessionHandle());
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
        String result = _interfaceBClient.cancelCase(caseID, getSessionHandle());

        // remove live items for case from workqueues and cache
        if (controller.successful(result)) {
        } else
            _log.error("Error attempting to Cancel Case.");

        return result;
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
        TaskInformation taskInfo = controller.getTaskInformation(specId, wir.getTaskID(),
                getSessionHandle());
        List<YParameter> outputParams = taskInfo.getParamSchema().getOutputParams();
        return outputParams;
    }

    public List<WorkItemRecord> checkOutWorkitemAndChildren(WorkItemRecord enabledWorkItem) throws YAWLException, IOException {
        List<WorkItemRecord> checkOutWirs = checkOutChildren(enabledWorkItem.getID());
        if (checkOutWirs == null)
            checkOutWirs = new ArrayList<WorkItemRecord>();
        enabledWorkItem = checkOut(enabledWorkItem);
        checkOutWirs.add(enabledWorkItem);
        return checkOutWirs;
    }
    private boolean selfInitialiseHandle = false;

    public String getSessionHandle() throws IOException {
        if (selfInitialiseHandle)
            return initSessionHandle();
        return _sessionHandle;
    }

    public void selfInitialiseHandle(boolean selfInitialiseHandle) {
        this.selfInitialiseHandle = selfInitialiseHandle;
    }

    public void checkInWorkItem(WorkItemRecord wir) throws IOException, JDOMException {
        String wrap = StringUtil.wrap(null, getDecompositionID(wir));
        checkInWorkItem(wir, wrap);
    }

    public YParameter[] describeRequiredParams(List<YParam> params) {
        YParameter[] yParams = new YParameter[params.size()];
        for (int i = 0; i < params.size(); i++) {
            YParam strings = params.get(i);
            YParameter parameter = new YParameter(null, YParameter._INPUT_PARAM_TYPE);
            parameter.setDataTypeAndName(strings.getDataType(), strings.getVariableName(), "http://www.w3.org/2001/XMLSchema");
            parameter.setDocumentation(strings.getDesCription());
            yParams[i] = parameter;
        }
        return yParams;
    }

        public YParameter[] describeRequiredParams(YParam... params) {
        YParameter[] yParams = new YParameter[params.length];
        for (int i = 0; i < params.length; i++) {
            YParam strings = params[i];
            YParameter parameter = new YParameter(null, YParameter._INPUT_PARAM_TYPE);
            parameter.setDataTypeAndName(strings.getDataType(), strings.getVariableName(), "http://www.w3.org/2001/XMLSchema");
            parameter.setDocumentation(strings.getDesCription());
            yParams[i] = parameter;
        }
        return yParams;
    }

    public static class YParam {

        private String variableName;
        private String dataType;
        private int flowType;
        private String desCription;

        public YParam(String variableName, String dataType, int flowType, String desCription) {
            this.variableName = variableName;
            this.dataType = dataType;
            this.flowType = flowType;
            this.desCription = desCription;
        }

        public String getDataType() {
            return dataType;
        }

        public String getDesCription() {
            return desCription;
        }

        public int getFlowType() {
            return flowType;
        }

        public String getVariableName() {
            return variableName;
        }
    }
}
