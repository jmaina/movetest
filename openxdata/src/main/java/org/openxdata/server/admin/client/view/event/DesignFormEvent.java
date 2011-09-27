package org.openxdata.server.admin.client.view.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import org.openxdata.server.admin.model.FormDefVersion;

/**
 *
 * @author kay
 */
public class DesignFormEvent extends GwtEvent<DesignFormEvent.Handler> {

        public static Type<Handler> TYPE = new Type<Handler>();
        private final FormDefVersion formVersion;
        private boolean readOnly = false;

        public interface Handler extends EventHandler {

                public void onDesignReadOnly(FormDefVersion version);

                public void onDesign(FormDefVersion version);
        }

        public DesignFormEvent(FormDefVersion formVersion) {
                this.formVersion = formVersion;
        }

        public DesignFormEvent(FormDefVersion formVersion, boolean readOnly) {
                this.formVersion = formVersion;
                this.readOnly = readOnly;
        }

        @Override
        public Type<Handler> getAssociatedType() {
                return TYPE;
        }

        @Override
        protected void dispatch(Handler handler) {
                if (readOnly)
                        handler.onDesignReadOnly(formVersion);
                else
                        handler.onDesign(formVersion);
        }
}
