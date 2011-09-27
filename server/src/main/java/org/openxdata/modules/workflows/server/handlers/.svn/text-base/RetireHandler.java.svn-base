package org.openxdata.modules.workflows.server.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.log4j.Logger;
import org.openxdata.modules.workflows.server.context.WFContext;
import org.openxdata.modules.workflows.server.service.WorkItemsService;
import org.openxdata.server.admin.model.User;
import org.openxdata.workflow.mobile.model.MWorkItem;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

/**
 *
 * @author kay
 */
public class RetireHandler implements RequestHandler {

        private static Logger log = Logger.getLogger(RetireHandler.class);
        private WorkItemsService service;

        public RetireHandler() {
                service = WFContext.getWorkItemsService();
        }

        @Override
        public void handleRequest(User user, InputStream is, OutputStream os) throws IOException {
                HandlerStreamUtil streamUtil = new HandlerStreamUtil(is, os);
                MWorkItem wir = new MWorkItem();

                try {
                        streamUtil.read(wir);
                } catch (Exception ex) {
                        log.error("Error whill reading workitem", ex);
                        throw new RuntimeException(ex);
                }
                WorkItemRecord workitem = service.getWorkitem(wir.getCaseId(), wir.getTaskId());

                if (workitem != null) {
                        workitem.setStatus(WorkItemRecord.statusExecuting);

                        service.saveWorkItem(workitem);

                        log.debug("Disabling WorkItem: " + wir.getWorkItemId());
                } else {
                        log.warn("WorkItem :<"+wir.getWorkItemId()+"> not found in database while trying to Disable it");
                }


                streamUtil.writeSucess();
                streamUtil.flush();
        }
}
