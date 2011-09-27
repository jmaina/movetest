package org.yawl.schedulingservice.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import org.yawl.schedulingservice.client.gin.SchedulerInjector;
import org.yawl.schedulingservice.client.presenter.LoginPresenter;

/**
 *
 * @author kay
 */
public class Sheduler implements EntryPoint {

        private final SchedulerInjector injector = GWT.create(SchedulerInjector.class);
        private LoginPresenter presenter;

        @Override
        public void onModuleLoad() {
                presenter = injector.getLoginPresenter();

                RootPanel.get().clear();
                RootPanel.get().add(presenter.asWidget());
        }
}
