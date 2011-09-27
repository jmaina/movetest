package org.openxdata.modules.workflows.server.dao.hibernate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openxdata.modules.workflows.model.shared.DBSpecStudyMap;

/**
 *
 * @author kay
 */
public class HibernateDBSpecStudyMapDAOTest
{

        public HibernateDBSpecStudyMapDAOTest()
        {
        }

        @BeforeClass
        public static void setUpClass() throws Exception
        {
        }

        @AfterClass
        public static void tearDownClass() throws Exception
        {
        }

        /**
         * Test of save method, of class HibernateDBSpecStudyMapDAO.
         */
      //  @Test
        public void testSave()
        {
                System.out.println("save");
                DBSpecStudyMap entity = null;
                HibernateDBSpecStudyMapDAO instance = new HibernateDBSpecStudyMapDAO();
                boolean expResult = false;
                boolean result = instance.save(entity);
                assertEquals(expResult, result);
                // TODO review the generated test code and remove the default call to fail.
                fail("The test case is a prototype.");
        }

        /**
         * Test of replaceIdIfZero method, of class HibernateDBSpecStudyMapDAO.
         */
        @Test
        public void testReplaceIdIfZero()
        {
                System.out.println("replaceIdIfZero");
                int id =10;
                String xmlMap = "<SpecStudyMap Id='0' >Blah Blah </SpecStudyMap>";
                HibernateDBSpecStudyMapDAO instance = new HibernateDBSpecStudyMapDAO();
                String expResult = "<SpecStudyMap Id='"+id+"' >Blah Blah </SpecStudyMap>";
                String result = instance.replaceIdIfZero(id, xmlMap);
                assertEquals(expResult, result);
        }

        /**
         * Test of getMap method, of class HibernateDBSpecStudyMapDAO.
         */
       // @Test
        public void testGetMap()
        {
                System.out.println("getMap");
                String caseID = "";
                String taskID = "";
                HibernateDBSpecStudyMapDAO instance = new HibernateDBSpecStudyMapDAO();
                DBSpecStudyMap expResult = null;
                DBSpecStudyMap result = instance.getMap(caseID, taskID);
                assertEquals(expResult, result);
                // TODO review the generated test code and remove the default call to fail.
                fail("The test case is a prototype.");
        }
}
