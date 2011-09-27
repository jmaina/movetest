package org.yawl.schedulingservice.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.yawl.schedulingservice.client.presenter.LoginPresenter;

/**
 *
 * @author kay
 */
public class LoginView implements LoginPresenter.Display {

        interface LoginUIBinder extends UiBinder<Widget, LoginView> {
        }
        private static LoginUIBinder loginBinder = GWT.create(LoginUIBinder.class);
        @UiField
        Button btnLogin;
        @UiField
        TextBox txtUserName;
        @UiField
        PasswordTextBox txtPassword;
        @UiField
        VerticalPanel vPanel;
        Widget createAndBindUi;
        VerticalPanel panel = new VerticalPanel();

        public LoginView() {
                createAndBindUi = loginBinder.createAndBindUi(this);
                Window.addResizeHandler(new ResizeHandler() {

                        @Override
                        public void onResize(ResizeEvent event) {
                                panel.setSize("100%", Window.getClientHeight() + "px");
                        }
                });
        }

        @Override
        public HasText txtUserName() {
                return txtUserName;
        }

        @Override
        public HasText txtPassword() {
                return txtPassword;
        }

        @Override
        public HasClickHandlers btnLogin() {
                return btnLogin;
        }

        @Override
        public Widget asWidget() {
                panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
                panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
                panel.setSize("100%", Window.getClientHeight() + "px");
                panel.add(createAndBindUi);
                return panel;
        }
}
