package org.yawl.schedulingservice.client.presenter;

import com.google.gwt.core.client.GWT;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

/**
 *
 * @author kay
 */
public class WidgetPresenterAdapter<D extends WidgetDisplay> extends WidgetPresenter<D> {

        public WidgetPresenterAdapter(D display, EventBus eventBus) {
                super(display, eventBus);
        }

        @Override
        protected void onBind() {
                GWT.log(getClass().getName() +" : onBind()");
        }

        @Override
        protected void onUnbind() {
                GWT.log(getClass().getName() +" : onUnBind()");
        }

        @Override
        protected void onRevealDisplay() {
                GWT.log(getClass().getName() +" : onRevealDisplay()");
        }
}
