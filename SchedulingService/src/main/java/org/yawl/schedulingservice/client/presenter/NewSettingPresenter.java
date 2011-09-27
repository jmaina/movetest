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
import org.openxdata.server.admin.model.SettingGroup;
import org.yawl.schedulingservice.client.HelpCallBack;
import org.yawl.schedulingservice.client.event.EditableEvent;
import org.yawl.schedulingservice.client.event.LoadRequetEvent;
import org.yawl.schedulingservice.client.event.RequestEvent;
import org.yawl.schedulingservice.client.service.SettingServiceAsync;
import org.yawl.schedulingservice.client.util.WidgetUtil;

/**
 *
 * @author kay
 */
public class NewSettingPresenter extends WidgetPresenterAdapter<NewSettingPresenter.Display> {

        private SettingGroup grpToEdit;

        public interface Display extends WidgetDisplay {

                HasText txtName();

                HasText txtDesc();

                HasClickHandlers btnOk();

                void show();

                void hide();
        }
        private SettingServiceAsync settingService;
        private List<SettingGroup> mSettigGroups;

        @Inject
        public NewSettingPresenter(Display display, EventBus eventBus) {
                super(display, eventBus);
                settingService = SettingServiceAsync.Util.getInstance();
                bind();
        }

        @Override
        protected void onBind() {
                EditableEvent.addHandler(eventBus, new EditableEvent.HandlerAdaptor<SettingGroup>() {

                        @Override
                        public void onLoaded(List<SettingGroup> items) {
                                NewSettingPresenter.this.mSettigGroups = items;

                        }
                }).forClass(SettingGroup.class);

                RequestEvent.addHandler(eventBus, new RequestEvent.HandlerAdaptor<SettingGroup>() {

                        @Override
                        public void onReqEdit(SettingGroup item) {
                                editGroup(item);
                        }
                }).forClass(SettingGroup.class);

                display.btnOk().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                saveSettingGroup();
                        }
                });
        }

        private void saveSettingGroup() {
                String name = display.txtName().getText().trim();

                if (!isNameValid(name))
                        return;

                String desc = display.txtDesc().getText().trim();

                
                grpToEdit.setDescription(desc);
                grpToEdit.setName(name);

                settingService.saveSettingGroup(grpToEdit, new HelpCallBack<Void>() {

                        @Override
                        public void onSuccessPost(Void result) {
                                display.hide();
                                eventBus.fireEvent(new LoadRequetEvent<SettingGroup>(SettingGroup.class, LoadRequetEvent.TYPE.REFRESH));

                        }
                });
        }

        private boolean isNameValid(String name) {
                if (name.isEmpty()) {
                        WidgetUtil.showMsg("Cannot Have an Empty Name");
                        return false;
                }
                for (SettingGroup settinGrp : mSettigGroups) {
                        if (settinGrp.getName().equals(name) &&  grpToEdit!= settinGrp) {
                                WidgetUtil.showMsg("You Entered A Name That Already Exists");
                                return false;
                        }
                }
                return true;
        }

        private void editGroup(SettingGroup item) {
                this.grpToEdit = item;
                GWT.log("NewSettingPresenter: Editing Group: >>"+item.getName());
                display.txtName().setText(item.getName());
                display.txtDesc().setText(item.getDescription());
                display.show();

        }
}
