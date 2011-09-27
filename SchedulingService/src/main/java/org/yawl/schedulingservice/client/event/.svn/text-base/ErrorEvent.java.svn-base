package org.yawl.schedulingservice.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import net.customware.gwt.presenter.client.EventBus;

/**
 *
 * @author kay
 */
public class ErrorEvent extends GwtEvent<ErrorEvent.Handler> {

        public interface Handler extends EventHandler {

                public void onError(ErrorEvent e);
        }
        public static Type<Handler> TYPE = new Type<Handler>();
        private Throwable e;
        private String msg;

        public ErrorEvent() {
        }

        public ErrorEvent(Throwable e, String msg) {
                this.e = e;
                this.msg = msg;
        }

        @Override
        protected void dispatch(Handler handler) {

                handler.onError(this);

        }

        @Override
        public Type<Handler> getAssociatedType() {
                return TYPE;
        }

        public static void fire(EventBus eventBus) {
                eventBus.fireEvent(new ErrorEvent());
        }

        public static void fire(EventBus eventBus, Throwable e, String msg) {
                eventBus.fireEvent(new ErrorEvent(e, msg));
        }

        public static void fire(EventBus eventBus, String msg) {
                eventBus.fireEvent(new ErrorEvent(null, msg));
        }

        public String getMsg() {
                return msg;
        }

        public Throwable getE() {
                return e;
        }
}
