
package org.yawl.schedulingservice.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import java.util.List;
import org.openxdata.modules.workflows.model.shared.OSpecification;
import org.openxdata.modules.workflows.model.shared.OSpecificationData;
import org.openxdata.server.admin.model.exception.OpenXDataException;

/**
 *
 * @author kay
 */
public interface SpecificationService extends RemoteService {

    public List<OSpecification> getOSpecifications() throws OpenXDataException;

    public List<OSpecificationData> getLoadedSpecs() throws OpenXDataException;
}
