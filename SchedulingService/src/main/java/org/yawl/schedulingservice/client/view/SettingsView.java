package org.yawl.schedulingservice.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cobogw.gwt.user.client.ui.ButtonBar;
import org.openxdata.server.admin.model.SettingGroup;
import org.yawl.schedulingservice.client.event.RequestEvent.Handler;
import org.yawl.schedulingservice.client.presenter.SettingsPresenter;

/**
 *
 * @author kay
 */
public class SettingsView extends BaseListView<SettingGroup> implements SettingsPresenter.Display, ClickHandler {

        @Override
        public void onClick(ClickEvent event) {
                Object source = event.getSource();
                if (!(source instanceof Widget))
                        return;

                Widget anchor = (Widget) source;
                SettingGroup group = hyperLinkMap.get(anchor);
                if(group != null && requestHandler != null){
                        requestHandler.onReqEdit(group);
                }
        }

        @UiTemplate("TasksView.ui.xml")
        interface SettingsViewUiBinder extends UiBinder<Widget, SettingsView> {
        }
        private SettingsViewUiBinder binder = GWT.create(SettingsViewUiBinder.class);
        private Map<Widget, SettingGroup> hyperLinkMap = new HashMap<Widget, SettingGroup>();
        private Handler<SettingGroup> requestHandler;

        public SettingsView() {
                createButtonBar();
                binder.createAndBindUi(this);
                super.setUp();
        }

        @Override
        protected String[] getHeader() {
                return new String[]{"Name", "Description",""};
        }

        @Override
        protected int render(int i, SettingGroup item) {
                Anchor ac = new Anchor("Edit");
                ac.addClickHandler(this);
                hyperLinkMap.put(ac, item);
                table.setText(i, 0, item.getName());
                table.setText(i, 1, item.getDescription() + "{" + item.getSettings() + "}");
                table.setWidget(i, 2, ac);
                return 3;
        }

        @Override
        protected boolean requiresCheckBox() {
                return true;
        }



        @Override
        public void addRequestHandler(Handler<SettingGroup> handler) {
                this.requestHandler = handler;
        }

        @Override
        public Widget asWidget() {
                return sp;
        }

        private ButtonBar createButtonBar() {
                if (btnBar != null)
                        return btnBar;
                btnBar = new ButtonBar();
                btnBar.add(btnNew);
                btnBar.add(btnDelete);
                btnBar.setWidth("100%");
                return btnBar;
        }

        @Override
        public void clear() {
                super.clear();
                hyperLinkMap.clear();
        }

        @Override
        protected List<Integer> getSkipCols() {
                return new ArrayList<Integer>(){{add(2);}};
        }


}
