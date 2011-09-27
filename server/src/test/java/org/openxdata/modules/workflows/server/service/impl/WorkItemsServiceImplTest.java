package org.openxdata.modules.workflows.server.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jdom.JDOMException;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;
import org.openxdata.modules.workflows.model.shared.WorkItemQuestion;
import org.openxdata.modules.workflows.resources.Mocks;
import org.openxdata.modules.workflows.server.YawlOXDCustomService;
import org.openxdata.modules.workflows.server.dao.WorkItemDAO;
import org.openxdata.modules.workflows.server.service.SpecificationService;
import org.yawlfoundation.yawl.elements.YSpecification;
import org.yawlfoundation.yawl.elements.YTask;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

/**
 *
 * @author kay
 */
public class WorkItemsServiceImplTest
{

        private WorkItemRecord mWir;
        private YSpecification ys;
        private SpecificationService specSrvMock;
        private WorkItemDAO workItemDAO;
        private YawlOXDCustomService yawlSrv;

        public WorkItemsServiceImplTest()
        {
                mWir = Mocks.getWorkItemRecord();
                ys = Mocks.getSpecification();
                specSrvMock = createMock(SpecificationService.class);
                workItemDAO = createMock(WorkItemDAO.class);
                yawlSrv = createMock(YawlOXDCustomService.class);

        }

        /**
         * Test of getOutputParam method, of class WorkItemsServiceImpl.
         */
        @Test
        public void testGetOutputParam() throws CloneNotSupportedException
        {

                WorkItemRecord wir = this.mWir.clone();
                YTask yTask = Mocks.getYTask(ys);
                expect(specSrvMock.getTask(wir.getTaskID(), wir.getSpecificationID(), wir.getSpecVersion())).
                        andReturn(yTask);
                expect(specSrvMock.getTask(wir.getTaskID(), wir.getSpecificationID(), wir.getSpecVersion())).
                        andReturn(null);

                replayAll();
                System.out.println("getOutputParam");
                WorkItemsServiceImpl instance = new WorkItemsServiceImpl();
                instance.setSpecificationService(specSrvMock);

                Map expResult = yTask.getDecompositionPrototype().getOutputParameters();
                Map result = instance.getOutputParam(wir);
                assertEquals(expResult, result);

                Map<String, YParameter> outputParams = instance.getOutputParam(wir);
                assertTrue(outputParams.isEmpty());
                verifyAll();
        }

        /**
         * Test of submitWorkItem method, of class WorkItemsServiceImpl.
         */
        @Test
        public void testSubmitWorkItemShouldDeleteWorkItemOnSuccess() throws Exception
        {

                System.out.println("submitWorkItem");
                WorkItemRecord wir = this.mWir.clone();
                WorkItemsServiceImpl instance = createWIRServiceInstance();

                YTask yTask = Mocks.getYTask(ys);

                List<WorkItemQuestion> qnList = mockWorkItemQuestions();
                String xmlQuestionStr = getXMLDataList(yTask, qnList);

                //Set up mocks services
                expect(specSrvMock.getTask(wir.getTaskID(), wir.getSpecificationID(), wir.getSpecVersion())).
                        andReturn(yTask);
                expect(workItemDAO.remove(wir)).andReturn(Boolean.TRUE);
                yawlSrv.checkInWorkItem(wir, xmlQuestionStr);
                expectLastCall();

                replayAll();
                instance.submitWorkItem(wir, qnList);
                verifyAll();
        }

        private String getXMLDataList(YTask yTask, List<WorkItemQuestion> qnList)
        {
                return WorkItemsServiceImpl.questionListToString(yTask.getDecompositionPrototype().getID(), qnList);
        }

        @Test
        public void submitWorkItemShouldSaveWIRAsCompleteOnIOException() throws Exception
        {
                WorkItemRecord wir = this.mWir.clone();
                System.out.println("submitWorkItem");
                WorkItemsServiceImpl instance = createWIRServiceInstance();

                YTask yTask = Mocks.getYTask(ys);

                List<WorkItemQuestion> qnList = mockWorkItemQuestions();
                String xmlQuestionStr = getXMLDataList(yTask, qnList);

                //Set up mocks services
                expect(specSrvMock.getTask(wir.getTaskID(), wir.getSpecificationID(), wir.getSpecVersion())).
                        andReturn(yTask);
                expect(workItemDAO.save(wir)).andReturn(Boolean.TRUE);
                yawlSrv.checkInWorkItem(wir, xmlQuestionStr);
                expectLastCall().andThrow(new IOException("Dummy Exception"));

                replayAll();
                instance.submitWorkItem(wir, qnList);
                verifyAll();

                assertEquals(WorkItemRecord.statusComplete, wir.getStatus());
        }

        @Test
        public void shouldSaveWIRAsStatusFailedOnJDOMException() throws Exception
        {
                WorkItemRecord wir = this.mWir.clone();

                System.out.println("submitWorkItem");
                WorkItemsServiceImpl instance = createWIRServiceInstance();

                YTask yTask = Mocks.getYTask(ys);

                List<WorkItemQuestion> qnList = mockWorkItemQuestions();
                String xmlQuestionStrn = getXMLDataList(yTask, qnList);

                //Set up mocks services
                expect(specSrvMock.getTask(wir.getTaskID(), wir.getSpecificationID(), wir.getSpecVersion())).
                        andReturn(yTask);
                expect(workItemDAO.save(wir)).andReturn(Boolean.TRUE);
                yawlSrv.checkInWorkItem(wir, xmlQuestionStrn);
                expectLastCall().andThrow(new JDOMException("Dummy Exception"));

                replayAll();
                try {
                        instance.submitWorkItem(wir, qnList);
                } catch (RuntimeException ex) {
                }
                verifyAll();
                assertEquals(WorkItemRecord.statusFailed, wir.getStatus());


        }

        private WorkItemsServiceImpl createWIRServiceInstance()
        {
                WorkItemsServiceImpl instance = new WorkItemsServiceImpl();
                instance.setWorkItemDAO(workItemDAO);
                instance.setSpecificationService(specSrvMock);
                instance.setYawlService(yawlSrv);
                return instance;
        }

        private List<WorkItemQuestion> mockWorkItemQuestions()
        {
                List<WorkItemQuestion> qnList = new ArrayList<WorkItemQuestion>();
                qnList.add(new WorkItemQuestion("Name", "Blah"));
                qnList.add(new WorkItemQuestion("Sex", "Male"));
                return qnList;
        }

        @Test
        public void testQuestionListToString(){
                List<WorkItemQuestion> qnList = mockWorkItemQuestions();
                String  id = "Name";
                String xmlString = WorkItemsServiceImpl.questionListToString(id, qnList);

                String expResult = "<"+id+"><Name>Blah</Name><Sex>Male</Sex></"+id+">";
                assertEquals(xmlString,expResult );

        }
       
}
