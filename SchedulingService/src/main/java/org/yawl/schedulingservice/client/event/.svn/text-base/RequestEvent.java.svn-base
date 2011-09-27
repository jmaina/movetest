package org.yawl.schedulingservice.client.event;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import net.customware.gwt.presenter.client.EventBus;
import org.openxdata.server.admin.model.Editable;

/**
 *
 * @author kay
 */
public class RequestEvent<T> extends GwtEvent<RequestEvent.Handler<T>> {

        public interface Handler<T> extends BaseHandler<T> {

                public void onRequestLoad(T item);

                public void onReqEdit(T item);

                public void onReqCreate(T item);

                public void onReqDelete(T item);

                public void onRefresh(T item);
        }

        public static class HandlerAdaptor<T extends Editable> implements Handler<T> {

                @Override
                public void onRequestLoad(T item) {
                }

                @Override
                public void onReqEdit(T item) {
                }

                @Override
                public void onReqCreate(T item) {
                }

                @Override
                public void onReqDelete(T item) {
                }

                @Override
                public void onRefresh(T item) {
                }
        }
        private Class<?> clazz;
        private TYPE type = TYPE.EDIT;
        T obj;

        public RequestEvent(T obj) {
                this.clazz = obj.getClass();
                this.obj = obj;

        }

        public RequestEvent(T obj, TYPE type) {
                this.clazz = obj.getClass();
                this.type = type;
                this.obj = obj;
        }

        @Override
        public Type<Handler<T>> getAssociatedType() {
                return EventRegistration.getType(RequestEvent.class, clazz);
        }

        @Override
        protected void dispatch(Handler<T> handler) {
                GWT.log("RequestEvent "+obj.getClass());
                switch (type) {
                        case DELETE:
                                handler.onReqDelete(obj);
                                break;
                        case CREATE:
                                handler.onReqCreate(obj);
                                break;
                        case EDIT:
                                handler.onReqEdit(obj);
                                break;
                        case REFRESH:
                                handler.onRefresh(obj);
                }
        }

        public enum TYPE {
                EDIT, CREATE, DELETE, REFRESH
        }

        public static <T> EventRegistration<T, RequestEvent.Handler<T>> addHandler(EventBus eventBus, Handler<T> handler) {
                return new EventRegistration<T, RequestEvent.Handler<T>>(eventBus, handler, RequestEvent.class);
        }
}
