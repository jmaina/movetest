package org.yawl.schedulingservice.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import java.util.List;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import org.openxdata.server.admin.model.Setting;
import org.openxdata.server.admin.model.SettingGroup;
import org.yawl.schedulingservice.client.HelpCallBack;
import org.yawl.schedulingservice.client.event.LoadRequetEvent;
import org.yawl.schedulingservice.client.event.RequestEvent;
import org.yawl.schedulingservice.client.service.SettingServiceAsync;
import org.yawl.schedulingservice.client.util.WidgetUtil;

/**
 *
 * @author kay
 */
public class NewSettingValuePresenter extends WidgetPresenterAdapter<NewSettingValuePresenter.Display> {

        public interface Display extends WidgetDisplay {

                HasText txtName();

                HasText txtDesc();

                HasText txtValue();

                HasClickHandlers btnOk();

                void show();

                void hide();

                void setSetting(Setting item);
        }
        private SettingServiceAsync settingService;
        private Setting mSetting;

        @Inject
        public NewSettingValuePresenter(Display display, EventBus eventBus) {
                super(display, eventBus);
                super.bind();
                settingService = SettingServiceAsync.Util.getInstance();
        }

        @Override
        protected void onBind() {

                display.btnOk().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                onOkPressed();
                        }
                });
                RequestEvent.addHandler(eventBus, new RequestEvent.HandlerAdaptor<Setting>() {

                        @Override
                        public void onReqEdit(Setting item) {
                                GWT.log("Handling RequestEditEvent: for Setting");
                                setSetting(item);
                        }
                }).forClass(Setting.class);
        }

        private void onOkPressed() {
                if (!validateAndMap())
                        return;
                final SettingGroup settingGroup = mSetting.getSettingGroup();
                settingGroup.addSetting(mSetting);

                settingService.saveSettingGroup(settingGroup, new HelpCallBack<Void>() {

                        @Override
                        public void onFailurePostProcessing(Throwable throwable) {
                                settingGroup.removeSetting(mSetting);
                        }

                        @Override
                        public void onSuccessPost(Void result) {
                                GWT.log("Succesfully Saved New Setting");
                                eventBus.fireEvent(new LoadRequetEvent<SettingGroup>(SettingGroup.class,LoadRequetEvent.TYPE.REFRESH));
                                display.hide();
                        }
                });
        }

        public void setSetting(Setting item) {
                display.setSetting(item);
                this.mSetting = item;
                display.show();
        }

        private boolean validateAndMap() {
                SettingGroup settingGroup = mSetting.getSettingGroup();
                 String name = display.txtName().getText().trim();
                 String value = display.txtValue().getText().trim();
                if(name.isEmpty() || value.isEmpty()){
                        WidgetUtil.showMsg("Value Or Name Cannot Be Empty");
                        return false;
                }

                if (settingGroup != null) {
                        List<Setting> settings = settingGroup.getSettings();
                        for (Setting setting : settings) {
                                if (setting.getName().equals(name)&&setting!=mSetting){
                                        WidgetUtil.showMsg("You Have Entered A Duplicate Setting Name");
                                        return false;
                                }
                        }
                }

                mSetting.setName(name);
                mSetting.setValue(value);
                mSetting.setDescription(display.txtDesc().getText().trim());

                return true;
        }
}
