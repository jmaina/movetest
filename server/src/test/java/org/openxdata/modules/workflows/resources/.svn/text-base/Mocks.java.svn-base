package org.openxdata.modules.workflows.resources;

import java.util.HashMap;
import org.yawlfoundation.yawl.elements.YAWLServiceGateway;
import org.yawlfoundation.yawl.elements.YAtomicTask;
import org.yawlfoundation.yawl.elements.YDecomposition;
import org.yawlfoundation.yawl.elements.YNet;
import org.yawlfoundation.yawl.elements.YSpecification;
import org.yawlfoundation.yawl.elements.YTask;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

/**
 *
 * @author kay
 */
public class Mocks
{

        public static WorkItemRecord getWorkItemRecord()
        {
                final YSpecification specification = getSpecification();
                WorkItemRecord wir = new WorkItemRecord();
                wir.setTaskID(getYTask(specification).getID());
                wir.setCaseID("241.1");
                wir.setSpecVersion(specification.getSpecVersion());
                wir.setSpecificationID(getSpecification().getName());
                return wir;
        }

        public static YSpecification getSpecification()
        {
                YSpecification ySpec = new YSpecification("http://www.org.com");
                ySpec.setVersion("2.0");
                ySpec.setName("DummySpec");
               // ySpec.setRootNet(getYNet(ySpec));
                return ySpec;
        }

        public static YTask getYTask(YSpecification ys)
        {
                final YNet yNet = getYNet(ys);
                YTask yTask = new YAtomicTask("EnterName_4", YTask._AND, YTask._AND, yNet);
                yTask.setDecompositionPrototype(getDecomposition(ys));
                yNet.addNetElement(yTask);
                return yTask;
        }

        public static YNet getYNet(YSpecification ys)
        {
                YNet yNet = new YNet("Net1", ys);
                return yNet;
        }

        public static YDecomposition getDecomposition(YSpecification ys)
        {
                YDecomposition yDec = new YAWLServiceGateway("EnterName_4", ys);
                for (YParameter yParameter : getParams(yDec).values()) {
                        yDec.setOutputParameter(yParameter);
                }
                return yDec;
        }

        public static HashMap<String, YParameter> getParams(YDecomposition yd)
        {
                HashMap<String, YParameter> params = new HashMap<String, YParameter>();
                YParameter yParameter = new YParameter(yd, "outputParam");
                yParameter.setName("name");
                params.put("name", yParameter);
                yParameter = new  YParameter(yd, "outputParam");
                yParameter.setName("sex");
                params.put("sex", yParameter);
                return params;
        }
}
