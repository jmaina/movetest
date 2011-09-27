package org.yawl.schedulingservice.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import org.cobogw.gwt.user.client.ui.ButtonBar;
import org.openxdata.server.admin.model.Setting;
import org.yawl.schedulingservice.client.presenter.SettingPresenter;

/**
 *
 * @author kay
 */
public class SettingView extends BaseListView<Setting> implements SettingPresenter.Display {

        private static SettingViewUiBinder uiBinder = GWT.create(SettingViewUiBinder.class);

        @Override
        protected boolean requiresCheckBox() {
              return true;
        }

        @UiTemplate("TasksView.ui.xml")
        interface SettingViewUiBinder extends UiBinder<Widget, SettingView> {
        }

        public SettingView() {
                createButtonBar();
                uiBinder.createAndBindUi(this);
                super.setUp();
        }

        @Override
        protected String[] getHeader() {
                return new String[]{"Name", "Value", "Description"};
        }

        @Override
        protected int render(int i, Setting item) {
                int col=0;
                table.setText(i, col++, item.getName());
                table.setText(i, col++, item.getValue());
                table.setText(i, col++, item.getDescription());
                return col;
        }

        @Override
        public Widget asWidget() {
                return vp;
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
}
