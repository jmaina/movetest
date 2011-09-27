package org.yawl.schedulingservice.client.event;

import org.openxdata.server.admin.model.Editable;


import com.google.gwt.event.shared.GwtEvent;
import net.customware.gwt.presenter.client.EventBus;

/**
 * Fire this Event if to load Give Item
 * @author kay
 */
public class LoadRequetEvent<T extends Editable> extends GwtEvent<LoadRequetEvent.Handler<T>> {

        private Class<T> clazz;
        private TYPE type = TYPE.LOAD;

        public LoadRequetEvent(Class<T> clazz) {
                this.clazz = clazz;
        }

        public LoadRequetEvent(Class<T> clazz, TYPE type){
                this.clazz = clazz;
                this.type = type;

        }

        @Override
        protected void dispatch(Handler<T> handler) {
                switch (type) {
                        case LOAD:
                                handler.onLoadRequest();
                                break;
                        case REFRESH:
                                handler.onRefresh();
                                break;
                }

        }

        public interface Handler<T extends Editable> extends BaseHandler<T> {

                public void onLoadRequest();

                public void onRefresh();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Type<Handler<T>> getAssociatedType() {
                return EventRegistration.getType(LoadRequetEvent.class, clazz);
        }

        public enum TYPE {

                LOAD, REFRESH
        }

        public static <T extends Editable> EventRegistration<T, LoadRequetEvent.Handler<T>> addHandler(EventBus eventBus, Handler<T> handler) {
                return new EventRegistration<T, LoadRequetEvent.Handler<T>>(eventBus, handler, LoadRequetEvent.class);
        }
}
