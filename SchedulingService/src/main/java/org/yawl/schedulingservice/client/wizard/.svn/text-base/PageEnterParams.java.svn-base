package org.yawl.schedulingservice.client.wizard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.openxdata.modules.workflows.model.shared.OParameter;
import org.openxdata.modules.workflows.model.shared.OSpecificationData;
import org.openxdata.server.admin.model.User;
import org.yawl.schedulingservice.client.HelpCallBack;
import org.yawl.schedulingservice.client.service.UserServiceAsync;
import org.yawl.schedulingservice.client.util.WidgetUtil;
import org.yawl.schedulingservice.client.view.BasePropertyDisplay;
import org.yawl.schedulingservice.client.view.SimpleFilterListBox;

/**
 *
 * @author kay
 */
public class PageEnterParams extends WizardPage {

        private CreateTaskContext context;
        private PageImpl page;
        private Map<OParameter, TextBox> paramBxMap = new HashMap<OParameter, TextBox>();
        private Map<OParameter, SimpleFilterListBox> paramLstMap = new HashMap<OParameter, SimpleFilterListBox>();
        private UserServiceAsync userService;
        private List<User> users;
        private HashMap<String, String> userMap;

        @Inject
        public PageEnterParams(CreateTaskContext context) {
                this.context = context;
                page = new PageImpl();
                this.add(page.asWidget());
                userService = UserServiceAsync.Util.getInstance();
        }

        @Override
        public boolean beforeNextPage() {
                context.getParamAnswer().clear();
                Set<Entry<OParameter, TextBox>> entrySet = paramBxMap.entrySet();
                for (Entry<OParameter, TextBox> entry : entrySet) {
                        context.getParamAnswer().put(entry.getKey().getName(), entry.getValue().getText());
                }
                Set<Entry<OParameter, SimpleFilterListBox>> entrySet1 = paramLstMap.entrySet();
                for (Entry<OParameter, SimpleFilterListBox> entry : entrySet1) {
                        context.getParamAnswer().put(entry.getKey().getName(), WidgetUtil.getListBoxValue(entry.getValue()));
                }
                return true;
        }

        @Override
        public String getPageTitle() {
                return "Enter Params";
        }

        @Override
        public void beforePageDisplay() {
                if (users == null) {
                        loadUsers();
                        return;
                }
                OSpecificationData currentSpec = context.getSpecData();//Maybe optimise this to avoid rerendering
                ArrayList<OParameter> inputParams = currentSpec.getInputParams();
                page.reset();
                paramBxMap.clear();
                paramLstMap.clear();
                for (OParameter oParameter : inputParams) {

                        if (oParameter.getName().endsWith("_u")) {
                                SimpleFilterListBox addListProperty = page.addListProperty(oParameter.getName(), userMap);
                                paramLstMap.put(oParameter, addListProperty);
                                String answer = context.getParamAnswer().get(oParameter.getName());
                                GWT.log("Getting Set Answer for Param: " + oParameter.getName() + " Answer: " + answer);
                        } else {
                                TextBox addTextProperty = page.addTextProperty(oParameter.getName());
                                paramBxMap.put(oParameter, addTextProperty);
                                String answer = context.getParamAnswer().get(oParameter.getName());
                                GWT.log("Getting Set Answer for Param: " + oParameter.getName() + " Answer: " + answer);
                                addTextProperty.setText(answer);
                        }

                }

        }

        private void loadUsers() {
                if (users != null)
                        return;
                userService.getUsers(new HelpCallBack<List<User>>() {

                        @Override
                        public void onSuccessPost(List<User> result) {
                                users = result;
                                userMap = getUserMap();
                                beforePageDisplay();
                        }
                });
        }

        private HashMap<String, String> getUserMap() {

                HashMap<String, String> map = new HashMap<String, String>();
                for (User user : users) {
                        map.put(user.getName(), user.getName());
                }
                return map;
        }

        private class PageImpl extends BasePropertyDisplay {

                public PageImpl() {
                        super.init("Role Properties");
                }
        }
}
