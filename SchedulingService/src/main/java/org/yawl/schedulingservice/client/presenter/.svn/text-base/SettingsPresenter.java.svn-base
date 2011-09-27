package org.yawl.schedulingservice.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import java.util.HashMap;
import java.util.List;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;
import org.openxdata.server.admin.model.Setting;
import org.openxdata.server.admin.model.SettingGroup;
import org.yawl.schedulingservice.client.HelpCallBack;
import org.yawl.schedulingservice.client.event.EditableEvent;
import org.yawl.schedulingservice.client.event.LoadRequetEvent;
import org.yawl.schedulingservice.client.event.RequestEvent;
import org.yawl.schedulingservice.client.places.EditSettingPlace;
import org.yawl.schedulingservice.client.service.SettingServiceAsync;

/**
 *
 * @author kay
 */
public class SettingsPresenter extends BaseListPresenter<SettingGroup, SettingsPresenter.Display> {

        private SettingServiceAsync settingService;
        private HashMap<Integer, SettingGroup> settingGroups = new HashMap<Integer, SettingGroup>();
        private final NewSettingPresenter p;
        private final SettingPresenter settingPresenter;
        private SettingGroup parentGorup;

        public interface Display extends BaseListDisplay<SettingGroup> {

                public void addRequestHandler(RequestEvent.Handler<SettingGroup> handler);
        }

        @Inject
        public SettingsPresenter(EventBus eventBus, Display display,
                NewSettingPresenter p, SettingPresenter settingPresenter) {
                super(display, eventBus, SettingGroup.class);
                settingService = SettingServiceAsync.Util.getInstance();
                this.p = p;
                this.settingPresenter = settingPresenter;

                super.bind();
        }

        @Override
        protected void onBind() {
                display.addRequestHandler(new RequestEvent.HandlerAdaptor<SettingGroup>() {

                        @Override
                        public void onReqEdit(SettingGroup item) {
                                edit(item);
                        }
                });
                display.btnDelete().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                delete();
                        }
                });
        }

        @Override
        protected void loadItems(boolean reload) {
                if (!settingGroups.isEmpty() && !reload)
                        return;
                settingService.getSettingGroup("workflow.settings", new HelpCallBack<SettingGroup>() {

                        @Override
                        public void onSuccessPost(SettingGroup result) {
                                SettingsPresenter.this.parentGorup = result;
                                setSettings(result.getGroups());
                        }
                });

        }

        private void setSettings(List<SettingGroup> groups) {
                display.clear();
                settingGroups.clear();
                for (SettingGroup settingGroup : groups) {
                        settingGroups.put(settingGroup.getId(), settingGroup);
                        display.addItem(settingGroup);
                }
                eventBus.fireEvent(new EditableEvent<SettingGroup>(groups, SettingGroup.class));
        }

        @Override
        protected void onNew() {
                SettingGroup group = new SettingGroup();
                Setting setting = new Setting("specID", "Set Yawl Specification Id Here", null);
                setting.setSettingGroup(group);
                group.addSetting(setting);
                Setting setting1 = new Setting("specVersion", "Set Yawl Specification Version Number Here", null);
                setting1.setSettingGroup(group);
                group.addSetting(setting1);
                group.setParentSettingGroup(this.parentGorup);
                eventBus.fireEvent(new RequestEvent<SettingGroup>(group));
        }

        private void edit(SettingGroup item) {
                eventBus.fireEvent(new RequestEvent<SettingGroup>(item));
        }

        @Override
        protected void onItemSelected(SettingGroup item) {
                PlaceRequest request = new PlaceRequest(EditSettingPlace.NAME).with(EditSettingPlace.PARAM_ID, item.getId() + "");
                PlaceRequestEvent placeReqEvent = new PlaceRequestEvent(request);
                eventBus.fireEvent(placeReqEvent);

        }

        public void onEditPlaceRequest(int settingID) {
                SettingGroup get = settingGroups.get(settingID);
                if (get == null)
                        return;
                GWT.log("Firing Request Event for: SettingGroup: >>" + get.getName());
                eventBus.fireEvent(new RequestEvent<SettingGroup>(get));
                display.switchToView(settingPresenter.getDisplay());
                p.getDisplay().hide();//TODO: Replace Request Events with item Selected Event
        }

        private void delete() {
                if(!Window.confirm("Are You Sure")) return;
                List<SettingGroup> selectedItems = display.getSelectedItems();
                for (SettingGroup settingGroup : selectedItems) {
                        settingService.deleteSettingGroup(settingGroup, new HelpCallBack<Void>() {

                                @Override
                                public void onSuccessPost(Void result) {
                               eventBus.fireEvent(new LoadRequetEvent<SettingGroup>(SettingGroup.class, LoadRequetEvent.TYPE.REFRESH));
                                }
                        });
                }
        }
}
