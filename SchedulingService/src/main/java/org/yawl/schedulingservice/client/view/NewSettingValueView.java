package org.yawl.schedulingservice.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import org.openxdata.server.admin.model.Setting;
import org.yawl.schedulingservice.client.presenter.NewSettingValuePresenter;

/**
 *
 * @author kay
 */
public class NewSettingValueView extends Composite implements NewSettingValuePresenter.Display {

        private static NewSettingValueViewUiBinder uiBinder = GWT.create(NewSettingValueViewUiBinder.class);

        @Override
        public void setSetting(Setting item) {
                txtName.setText(null);
                txtDesc.setText(null);
                txtValue.setText(null);
                txtName.setText(item.getName());
                txtDesc.setText(item.getDescription());
                txtValue.setText(item.getValue());
        }

        interface NewSettingValueViewUiBinder extends UiBinder<Widget, NewSettingValueView> {
        }
        @UiField
        Button btnOk;
        @UiField
        TextBox txtName;
        @UiField
        TextBox txtDesc;
        @UiField
        Button btnCancel;
        @UiField
        TextBox txtValue;
        DialogBox dlg;

        public NewSettingValueView() {
                dlg = new DialogBox(false, true);
                dlg.setHTML("New Settings Group");
                initWidget(uiBinder.createAndBindUi(this));
                dlg.setWidget(this);
                btnCancel.addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                dlg.hide();
                        }
                });

        }

        @Override
        public HasText txtName() {
                return txtName;
        }

        @Override
        public HasText txtDesc() {
                return txtDesc;
        }

        @Override
        public HasClickHandlers btnOk() {
                return btnOk;
        }

        @Override
        public HasText txtValue() {
                return txtValue;
        }

        @Override
        public void show() {
                dlg.center();
        }

        @Override
        public void hide() {
                dlg.hide();
        }
}
