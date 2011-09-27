package org.yawl.schedulingservice.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceRevealedEvent;
import net.customware.gwt.presenter.client.place.PlaceRevealedHandler;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import org.yawl.schedulingservice.client.Context;
import org.yawl.schedulingservice.client.HelpCallBack;
import org.yawl.schedulingservice.client.event.ErrorEvent;
import org.yawl.schedulingservice.client.service.UserServiceAsync;

/**
 *
 * @author kay
 */
public class RootPresenter extends WidgetPresenterAdapter<RootPresenter.Display> {

        public interface Display extends WidgetDisplay {

                public void showError(String text);

                public void hideError();

                HasClickHandlers btnLogout();
        }

        private UserServiceAsync userService;

        @Inject
        public RootPresenter(Display display, EventBus eventBus) {
                super(display, eventBus);
                userService = UserServiceAsync.Util.getInstance();
                bind();
        }

        @Override
        protected void onBind() {
                super.onBind();
                bindUI();
                eventBus.addHandler(ErrorEvent.TYPE, new ErrorEvent.Handler() {

                        @Override
                        public void onError(ErrorEvent e) {
                                handleError(e);
                        }
                });
                eventBus.addHandler(PlaceRevealedEvent.getType(), new PlaceRevealedHandler() {

                        @Override
                        public void onPlaceRevealed(PlaceRevealedEvent event) {
                                display.hideError();
                        }
                });
        }

        private void bindUI() {
                display.btnLogout().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                logout();
                        }

                });
        }

        @Override
        protected void onRevealDisplay() {
                RootPanel.get().clear();
                RootPanel.get().add(display.asWidget());
        }

        private void handleError(ErrorEvent e) {
                String msg = e.getMsg();
                if (msg == null)
                        msg = "A problem Occured";

                display.showError(msg);

        }

        private void logout() {
                Context.setUser(null);
                userService.logOut(new HelpCallBack<Void>() {

                        @Override
                        public void onSuccessPost(Void result) {
                                Window.Location.reload();
                        }
                });

        }
}
