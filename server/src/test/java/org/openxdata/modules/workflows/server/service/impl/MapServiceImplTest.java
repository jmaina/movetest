package org.openxdata.modules.workflows.server.service.impl;

import java.util.Map;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.openxdata.modules.workflows.model.shared.DBSpecStudyMap;
import org.openxdata.modules.workflows.resources.Mocks;
import org.openxdata.modules.workflows.server.dao.DBSpecStudyMapDAO;
import org.openxdata.modules.workflows.server.maps.parser.MatcherHelper;
import org.openxdata.modules.workflows.server.service.WorkItemsService;
import org.openxdata.server.admin.model.FormDef;
import org.openxdata.server.dao.FormDAO;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.yawlfoundation.yawl.elements.data.YParameter;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.verifyAll;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

/**
 *
 * @author kay
 */
@PrepareForTest(MatcherHelper.class)
@RunWith(PowerMockRunner.class)
public class MapServiceImplTest
{

        private String caseId = "241.1";
        private String taskId = "EnterName";
        private WorkItemRecord wir;
        DBSpecStudyMap map;
        Mocks mocks = new Mocks();

        public MapServiceImplTest()
        {
                wir = new WorkItemRecord();
                wir.setTaskID(taskId);
                wir.setCaseID(caseId);
                map = new DBSpecStudyMap();
        }

        /**
         * Test of findMatchForWorkItem method, of class MapServiceImpl.
         */
        @Test
        public void testFindMatchForWorkItem()
        {
                FormDef formDef = new FormDef();
                DBSpecStudyMapDAO mocDao = createDaoMockGetMap(caseId, taskId, map);
                createMatcherHelperGetFormId(taskId, map.getXml(), "0");
                FormDAO formDao = createMock(FormDAO.class);
                EasyMock.expect(formDao.find(new Long(0))).andReturn(formDef);//Todo Remove the long


                System.out.println("findMatchForWorkItem");

                replayAll();
                MapServiceImpl instance = new MapServiceImpl();
                instance.setSpecStudyMapDAO(mocDao);
                instance.setFormDAO(formDao);

                FormDef result = instance.findMatchForWorkItem(wir);
                assertEquals(formDef, result);
                verifyAll();
        }

        /**
         * Test of getMatchingFormIdForWorkItem method, of class MapServiceImpl.
         */
        @Test
        public void testGetMatchingFormIdForWorkItem()
        {
                System.out.println("getMatchingFormIdForWorkItem");
                DBSpecStudyMapDAO mapDao = createDaoMockGetMap(wir.getCaseID(), wir.getTaskID(), map);
                createMatcherHelperGetFormId(wir.getTaskID(), map.getXml(), "0");

                replayAll();
                MapServiceImpl instance = new MapServiceImpl();
                instance.setSpecStudyMapDAO(mapDao);
                int expResult = 0;
                int result = instance.getMatchingFormIdForWorkItem(wir);
                assertEquals(expResult, result);
                verifyAll();

                FormDef formDef1 = instance.findMatchForWorkItem(null);
                assertEquals(null, formDef1);
        }

        @Test
        public void testGetMatchingFormIdForWorkItemReturnNegOneIfNoMap()
        {
                System.out.println("testGetMatchingFormIdForWorkItemReturnNullIfNoMap()");
                DBSpecStudyMapDAO mapDao = createDaoMockGetMap(wir.getCaseID(), wir.getTaskID(), null);

                replayAll();
                MapServiceImpl instance = new MapServiceImpl();
                instance.setSpecStudyMapDAO(mapDao);
                int expResult = -1;
                int result = instance.getMatchingFormIdForWorkItem(wir);
                assertEquals(expResult, result);
                verifyAll();
        }

        private void createMatcherHelperGetFormId(String taskId, String mapXML, String ret)
        {
                mockStatic(MatcherHelper.class);
                EasyMock.expect(MatcherHelper.getFormIdForTask(taskId, mapXML)).andReturn(ret);
        }

        private DBSpecStudyMapDAO createDaoMockGetMap(String caseId, String taskId, DBSpecStudyMap mapR)
        {
                DBSpecStudyMapDAO mapDao = createMock(DBSpecStudyMapDAO.class);
                EasyMock.expect(mapDao.getMap(caseId, taskId)).andReturn(mapR);
                return mapDao;
        }

        /**
         * Test of getOutPutParamQuestionMap method, of class MapServiceImpl.
         */
        @Test
        public void testGetOutPutParamQuestionMap()
        {
                int formVersionId = 0;
                Map<String, YParameter> params = getParams();

                DBSpecStudyMapDAO mockMapDao = createDaoMockGetMap(caseId, taskId, map);
                WorkItemsService wirServiceMock = createMock(WorkItemsService.class);
                EasyMock.expect(wirServiceMock.getOutputParam(wir)).andReturn(params);

                mockStatic(MatcherHelper.class);
                EasyMock.expect(MatcherHelper.getQuestionText(taskId, formVersionId + "", "name", map.getXml())).andReturn("name");
                EasyMock.expect(MatcherHelper.getQuestionText(taskId, formVersionId + "", "sex", map.getXml())).andReturn("sex");

                replayAll();
                System.out.println("getOutPutParamQuestionMap");

                MapServiceImpl instance = new MapServiceImpl();
                instance.setWirSrv(wirServiceMock);
                instance.setSpecStudyMapDAO(mockMapDao);

                Map result = instance.getOutPutParamQuestionMap(formVersionId, wir);
                assertEquals(2, result.size());
                verifyAll();
        }

        private Map<String, YParameter> getParams()
        {
                return Mocks.getDecomposition(Mocks.getSpecification()).
                        getOutputParameters();

        }
}
