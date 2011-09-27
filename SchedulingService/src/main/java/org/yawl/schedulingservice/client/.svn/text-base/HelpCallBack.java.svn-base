package org.yawl.schedulingservice.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import net.customware.gwt.presenter.client.EventBus;
import org.openxdata.server.admin.model.exception.OpenXDataSecurityException;
import org.openxdata.server.admin.model.exception.OpenXDataSessionExpiredException;
import org.yawl.schedulingservice.client.event.ErrorEvent;
import org.yawl.schedulingservice.client.util.WidgetUtil;
import org.yawl.schedulingservice.client.view.ReLoginDialog;

/**
 *
 * @author kay
 */
public abstract class HelpCallBack<T> implements AsyncCallback<T> {
        private final boolean show;

        public HelpCallBack() {
                WidgetUtil.showProgress();
                show = true;
        }

        public HelpCallBack(boolean show){
                this.show = show;
                if(show) WidgetUtil.showProgress();

        }

        /**
         * Method to override if there is some custom post exception handling work to be done.
         * Called after client has been notified of errors.
         * @param throwable
         */
        public void onFailurePostProcessing(Throwable throwable) {
        }

        /**
         * Implements some auto error handling for SpringSecurityException and general errors.
         */
        @Override
        final public void onFailure(Throwable throwable) {
                GWT.log("Error caught while performing an action on the server: " + throwable.getMessage(), throwable);
                if(show)
                WidgetUtil.hideProgress();
                if (throwable instanceof OpenXDataSessionExpiredException) {
                        ReLoginDialog.instanceOfReLoginDialog("Session Expired!!").center();
                } else if (throwable instanceof OpenXDataSecurityException) {
                        // access denied
                        showMessage(throwable, "Access Denied");
                } else {
                        showMessage(throwable, "An Error Occured Please Try Again Later!!!");
                        // all other errors
//            MessageBox.alert(appMessages.error(), appMessages.pleaseTryAgainLater(throwable.getMessage()), null);
                }
                onFailurePostProcessing(throwable);
        }

        @Override
        final public void onSuccess(T result) {
                if(show)
                WidgetUtil.hideProgress();
                onSuccessPost(result);
        }

        public abstract void onSuccessPost(T result);

        public void showMessage(Throwable e, String msg) {
                EventBus eventBus = Context.getEventBus();
                if (eventBus != null) {
                        ErrorEvent.fire(eventBus, e, msg);
                } else {
                        Window.alert(msg + "\n" + e);
                }
        }
}
