/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yawl.schedulingservice.server;

import com.google.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import org.openxdata.modules.workflows.model.shared.OSpecification;
import org.openxdata.modules.workflows.model.shared.OSpecificationData;
import org.openxdata.modules.workflows.server.YawlOXDCustomService;
import org.openxdata.modules.workflows.server.service.SpecificationService;
import org.openxdata.modules.workflows.server.util.SpecificationMapperUtil;

import org.openxdata.server.admin.model.exception.OpenXDataException;
import org.yawlfoundation.yawl.elements.YSpecification;
import org.yawlfoundation.yawl.engine.interfce.SpecificationData;

/**
 *
 * @author kay
 */
@Singleton
public class SpecificationServiceImpl extends OxdPersistentRemoteService
        implements
        org.yawl.schedulingservice.client.service.SpecificationService {

        private SpecificationService specService;
        private YawlOXDCustomService oxdCustomService;

        @Override
        public void init() throws ServletException {
                super.init();
                specService = (org.openxdata.modules.workflows.server.service.SpecificationService) getApplicationContext().getBean("specificationsService");
                oxdCustomService = (YawlOXDCustomService) getApplicationContext().getBean("YawlOXDCustomService");
        }

        //TODO: Shift the specification mapper to the serverside
        @Override
        public List<OSpecification> getOSpecifications() throws OpenXDataException {

                try {
                        List<YSpecification> specifications = specService.getSpecifications();
                        List<OSpecification> oSpecs = new ArrayList<OSpecification>();
                        for (YSpecification ySpecification : specifications) {
                                OSpecification oSpec = SpecificationMapperUtil.mapToSimpleOXDModel(ySpecification);
                                oSpecs.add(oSpec);
                        }
                        return oSpecs;
                } catch (Throwable t) {
                        throw new OpenXDataException(t);
                }
        }

        @Override
        public List<OSpecificationData> getLoadedSpecs() throws OpenXDataException {
                try {
                        Set<SpecificationData> specList = oxdCustomService.getSpecList();
                        
                        if (specList != null)
                                return SpecificationMapperUtil.toOSpecData(specList);
                } catch (IOException ex) {
                        throw new OpenXDataException(ex);
                }
                return null;
        }
}
