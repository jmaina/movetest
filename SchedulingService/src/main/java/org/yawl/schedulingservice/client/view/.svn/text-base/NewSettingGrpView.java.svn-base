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
import org.yawl.schedulingservice.client.presenter.NewSettingPresenter;

/**
 *
 * @author kay
 */
public class NewSettingGrpView extends Composite implements NewSettingPresenter.Display {

        @UiField
        Button btnOk;
        @UiField
        TextBox txtName;
        @UiField
        TextBox txtDesc;
        @UiField Button btnCancel;
        DialogBox dlg;
        private static NewSettingGrpViewUiBinder uiBinder = GWT.create(NewSettingGrpViewUiBinder.class);

        @Override
        public void show() {
                dlg.center();
        }

        @Override
        public void hide() {
                dlg.hide();
        }

        interface NewSettingGrpViewUiBinder extends UiBinder<Widget, NewSettingGrpView> {
        }

        public NewSettingGrpView() {
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
}
