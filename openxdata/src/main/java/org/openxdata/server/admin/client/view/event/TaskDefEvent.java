package org.openxdata.server.admin.client.view.event;

import org.openxdata.server.admin.model.TaskDef;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * @author kay
 */
public class TaskDefEvent extends GwtEvent<TaskDefEvent.Handler> {

        public interface Handler extends EventHandler {

                public void onChanged(TaskDef taskDef);

                public void onDeleted(TaskDef taskDef);
        }

        public static class HandlerAdaptor implements Handler {

                @Override
                public void onChanged(TaskDef taskDef) {
                }

                @Override
                public void onDeleted(TaskDef taskDef) {
                }
        }
        public static Type<Handler> TYPE = new Type<Handler>();
        private final TaskDef taskDef;
        private EventType type;

        public TaskDefEvent(TaskDef taskDef) {
                this.taskDef = taskDef;
                type = EventType.EDITED;
        }

        public TaskDefEvent(TaskDef taskDef, EventType type) {
                this(taskDef);
                this.type = type;

        }

        @Override
        protected void dispatch(Handler handler) {
                if (type == EventType.EDITED)
                        handler.onChanged(taskDef);
                else if (type == EventType.DELETED)
                        handler.onDeleted(taskDef);

        }

        @Override
        public Type<Handler> getAssociatedType() {
                return TYPE;
        }
}
