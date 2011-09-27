package org.yawl.schedulingservice.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import java.util.List;
import net.customware.gwt.presenter.client.EventBus;
import org.openxdata.server.admin.model.Setting;
import org.openxdata.server.admin.model.SettingGroup;
import org.yawl.schedulingservice.client.HelpCallBack;
import org.yawl.schedulingservice.client.event.EditableEvent;
import org.yawl.schedulingservice.client.event.LoadRequetEvent;
import org.yawl.schedulingservice.client.event.RequestEvent;
import org.yawl.schedulingservice.client.service.SettingServiceAsync;

/**
 *
 * @author kay
 */
public class SettingPresenter extends BaseListPresenter<Setting, SettingPresenter.Display> {

        public interface Display extends BaseListDisplay<Setting> {
        }
        private SettingGroup group;
        private SettingServiceAsync settingService;

        @Inject
        public SettingPresenter(EventBus eventBus, Display display) {
                super(display, eventBus, Setting.class);
                settingService = SettingServiceAsync.Util.getInstance();
                bind();
        }

        @Override
        protected void onBind() {
                RequestEvent.addHandler(eventBus, new RequestEvent.HandlerAdaptor<SettingGroup>() {

                        @Override
                        public void onReqEdit(SettingGroup item) {
                                GWT.log("SettingPresenter :Handling Request Event for: SettingGroup: >>" + item.getName());
                                setSettingGroup(item);
                        }
                }).forClass(SettingGroup.class);
                EditableEvent.addHandler(eventBus, new EditableEvent.HandlerAdaptor<SettingGroup>() {

                        @Override
                        public void onLoaded(List<SettingGroup> items) {
                                for (SettingGroup settingGroup : items) {
                                        if (group != null &&group.getId() == settingGroup.getId())
                                                setSettingGroup(settingGroup);
                                }
                        }
                }).forClass(SettingGroup.class);
                display.btnDelete().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                deleteItems();
                        }
                });
        }

        private void setSettingGroup(SettingGroup item) {
                this.group = item;
                renderItems();
        }

        @Override
        protected void loadItems(boolean reload) {
        }

        @Override
        protected void onNew() {
                Setting setting = new Setting();
                setting.setSettingGroup(group);
                GWT.log("Firing RequestEditEvent: new Setting()");
                eventBus.fireEvent(new RequestEvent<Setting>(setting));
        }

        @Override
        protected void onItemSelected(Setting item) {
                eventBus.fireEvent(new RequestEvent<Setting>(item));
        }

        private void renderItems() {
                if (group == null)
                        return;
                display.clear();
                List<Setting> settings = group.getSettings();
                for (Setting setting : settings) {
                        display.addItem(setting);
                }
        }

        private void deleteItems() {
                if (!Window.confirm("Are You Sure?"))
                        return;
                List<Setting> selectedItems = display.getSelectedItems();
                for (Setting setting : selectedItems) {
                        group.removeSetting(setting);
                }
                settingService.saveSettingGroup(group, new HelpCallBack<Void>() {

                        @Override
                        public void onSuccessPost(Void result) {
                                eventBus.fireEvent(new LoadRequetEvent<SettingGroup>(SettingGroup.class, LoadRequetEvent.TYPE.REFRESH));
                        }
                });

        }
}
