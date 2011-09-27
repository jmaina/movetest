package org.yawl.schedulingservice.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import org.openxdata.server.admin.model.User;
import org.yawl.schedulingservice.client.Context;
import org.yawl.schedulingservice.client.HelpCallBack;
import org.yawl.schedulingservice.client.places.ShowCasesPlace;
import org.yawl.schedulingservice.client.service.UserServiceAsync;

/**
 *
 * @author kay
 */
public class LoginPresenter extends WidgetPresenterAdapter<LoginPresenter.Display> {

        public interface Display extends WidgetDisplay {

                public HasText txtUserName();

                public HasText txtPassword();

                public HasClickHandlers btnLogin();
        }
        UserServiceAsync userService;

        @Inject
        public LoginPresenter(EventBus eventBus, Display display) {
                super(display, eventBus);
                userService = UserServiceAsync.Util.getInstance();
                Context.setEventBus(eventBus);
                bind();
                display.txtUserName().setText("admin");
                display.txtPassword().setText("admin");

        }

        @Override
        protected void onBind() {
                display.btnLogin().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                login();
                        }
                });
        }

        @Override
        protected void onRevealDisplay() {
                super.onRevealDisplay();
        }

        public Widget asWidget() {
                return display.asWidget();
        }

        private void login() {

                userService.authenticate(display.txtUserName().getText(), display.txtPassword().getText(), new HelpCallBack<User>() {

                        @Override
                        public void onSuccessPost(User result) {
                                if(result == null) return;
                                Context.setUser(result);
                                PlaceRequest req = new PlaceRequest(ShowCasesPlace.NAME);
                                eventBus.fireEvent(new PlaceRequestEvent(req));
                        }
                });
        }
}
