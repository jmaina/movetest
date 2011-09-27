package org.yawl.schedulingservice.client.wizard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import java.util.HashMap;
import org.openxdata.server.admin.model.SettingGroup;
import org.yawl.schedulingservice.client.HelpCallBack;
import org.yawl.schedulingservice.client.service.SettingServiceAsync;
import org.yawl.schedulingservice.client.view.SimpleFilterListBox;

/**
 *
 * @author kay
 */
public class PageSelectConfig extends WizardPage {

        private SettingServiceAsync settingService;
        @UiField
        SimpleFilterListBox lstConfigs;
        private CreateTaskContext context;

        @Override
        public void onBackPressed() {
        }

        interface PageSelectConfigUiBinder extends UiBinder<Widget, PageSelectConfig> {
        }
        PageSelectConfigUiBinder binder = GWT.create(PageSelectConfigUiBinder.class);

        @Inject
        public PageSelectConfig(CreateTaskContext context) {
                this.settingService = SettingServiceAsync.Util.getInstance();
                this.context = context;

                Widget createAndBindUi = binder.createAndBindUi(this);
                this.lstConfigs.setVisibleItemCount(10);
                add(createAndBindUi);

        }

        @Override
        public boolean beforeNextPage() {
                int selectedIndex = lstConfigs.getSelectedIndex();
                if (selectedIndex != -1) {
                        String itemText = lstConfigs.getItemText(selectedIndex);
                        if (!itemText.equals("<NONE>")) {
                                context.setConfig(settings.get(itemText));
                        }
                }
                return true;
        }

        @Override
        public String getPageTitle() {
                return "Select this configuration";
        }

        @Override
        public void beforePageDisplay() {
                //Load Setting
                settingService.getSettingGroup("workflow.settings", new HelpCallBack<SettingGroup>() {

                        @Override
                        public void onSuccessPost(SettingGroup result) {
                                if (result == null) {
                                        saveSetting();
                                } else {
                                        loadProfilesFromGroup(result);
                                }
                        }
                });
        }

        private void saveSetting() {
                settingService.saveSettingGroup(new SettingGroup("workflow.settings"), new HelpCallBack<Void>() {

                        @Override
                        public void onSuccessPost(Void result) {
                                beforePageDisplay();
                        }
                });
        }

        private void loadProfilesFromGroup(SettingGroup result) {
                lstConfigs.clear();
                lstConfigs.addItem("<NONE>");
                for (SettingGroup settingGroup : result.getGroups()) {
                        lstConfigs.addItem(settingGroup.getName());
                        settings.put(settingGroup.getName(), settingGroup);
                }

        }
        private HashMap<String, SettingGroup> settings = new HashMap<String, SettingGroup>();
}
