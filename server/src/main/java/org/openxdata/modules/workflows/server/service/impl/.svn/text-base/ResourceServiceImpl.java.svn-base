/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openxdata.modules.workflows.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.openxdata.modules.workflows.server.dao.WorkItemDAO;
import org.openxdata.modules.workflows.server.model.WorkItemFormMapHolder;
import org.openxdata.modules.workflows.server.service.ResourceService;
import org.openxdata.server.admin.model.User;
import org.openxdata.server.service.UserService;
import org.openxdata.workflow.mobile.model.MQuestionMap;
import org.openxdata.workflow.mobile.model.MWorkItemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

/**
 *
 * @author kay
 */
@Transactional
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

        @Autowired
        private UserService userService;
        @Autowired
        private WorkItemDAO dao;

        @Override
        public List<WorkItemFormMapHolder> getCurrentUserWorkItemFormMapHolders() {
                User loggedInUser = userService.getLoggedInUser();
                List<WorkItemFormMapHolder> workItemWithForms = dao.getWorkItemWithForms(WorkItemRecord.statusExecuting, loggedInUser);
                return filterWorkAssignedWorkItems(workItemWithForms, loggedInUser);
        }

        private List<WorkItemFormMapHolder> filterWorkAssignedWorkItems(List<WorkItemFormMapHolder> workItemWithForms, User loggedInUser) {
                List<WorkItemFormMapHolder> filteredWorkItems = new ArrayList<WorkItemFormMapHolder>();
                for (WorkItemFormMapHolder workItemFormMapHolder : workItemWithForms) {
                        if (isAssigned(workItemFormMapHolder, loggedInUser)) {
                                filteredWorkItems.add(workItemFormMapHolder);
                        }
                }
                return filteredWorkItems;
        }

        @Override
        public List<WorkItemRecord> getWorkItemsForUser(User user) {
                throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<WorkItemRecord> getWorkItemsForCurrentUser() {
                throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<WorkItemFormMapHolder> getCurrentUserWorkItemFormMapHolders(List<MWorkItemInfo> wirInfos) {
                List<WorkItemFormMapHolder> workItemFormMapHolders = dao.getWorkItemFormMapHolders(WorkItemRecord.statusExecuting, wirInfos, userService.getLoggedInUser());
                return filterWorkAssignedWorkItems(workItemFormMapHolders, userService.getLoggedInUser());
        }

        private boolean isAssigned(WorkItemFormMapHolder workItemFormMapHolder, User user) {
                Vector<MQuestionMap> prefilledQnAndAnswer = workItemFormMapHolder.toMWorkItem().getPrefilledQns();
                for (MQuestionMap mQuestionMap : prefilledQnAndAnswer) {
                        if (mQuestionMap.getParameter().endsWith("_u")) {
                                return mQuestionMap.getValue().equals(user.getName());
                        }
                }
                return true;
        }
}
