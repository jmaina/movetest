package org.openxdata.server.admin.client.view.event;

import org.openxdata.server.admin.model.Report;
import org.openxdata.server.admin.model.ReportGroup;
import org.openxdata.server.admin.model.Role;
import org.openxdata.server.admin.model.Setting;
import org.openxdata.server.admin.model.SettingGroup;
import org.openxdata.server.admin.model.TaskDef;
import org.openxdata.server.admin.model.User;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Composite;

/**
 * 
 * @author kay
 */
public class ItemSelectedEvent extends GwtEvent<ItemSelectedEvent.Handler> {

        public static Type<Handler> TYPE = new Type<Handler>();

        public interface Handler extends EventHandler {

                public void onUserSelected(Composite sender, User item);

                public void onSelected(Object sender, Object item);

                public void onSelected(Composite sender, Object item);

                public void onTaskDefSelected(Composite sender, TaskDef taskDef);

                public void onSettingSelected(Composite sender, Setting setting);

                public void onSettingGroupSelected(Composite sender, SettingGroup group);

                public void onReportSelected(Composite sender, Report report);

                public void onRoleSelected(Composite sender, Role role);

                public void onReportGroupSelected(Composite composite,
                        ReportGroup reportGroup);
        }

        public static class HandlerAdaptor implements Handler {

                @Override
                public void onUserSelected(Composite sender, User user) {
                }

                @Override
                public void onSelected(Object sender, Object item) {
                }

                @Override
                public void onTaskDefSelected(Composite sender, TaskDef taskDef) {
                }

                @Override
                public void onSelected(Composite sender, Object item) {
                }

                @Override
                public void onRoleSelected(Composite sender, Role role) {
                }

                @Override
                public void onSettingSelected(Composite sender, Setting setting) {
                }

                @Override
                public void onSettingGroupSelected(Composite sender, SettingGroup group) {
                }

                @Override
                public void onReportSelected(Composite sender, Report report) {
                }

                @Override
                public void onReportGroupSelected(Composite composite,
                        ReportGroup reportGroup) {
                }
        }
        private Object item;

        public ItemSelectedEvent(Object item) {
                this.item = item;

        }

        @Override
        public Type<Handler> getAssociatedType() {
                return TYPE;
        }

        @Override
        protected void dispatch(Handler handler) {
                Object sender = getSource();

                Composite composite = (sender != null) && (sender instanceof Composite)
                        ? (Composite) sender : null;

                if (item instanceof User) {
                        handler.onUserSelected(composite, (User) item);
                } else if (item instanceof TaskDef) {
                        handler.onTaskDefSelected(composite, (TaskDef) item);
                } else if (item instanceof Role) {
                        handler.onRoleSelected(composite, (Role) item);
                } else if (item instanceof Setting) {
                        handler.onSettingSelected(composite, (Setting) item);
                } else if (item instanceof SettingGroup) {
                        handler.onSettingGroupSelected(composite, (SettingGroup) item);
                } else if (item instanceof Report) {
                        handler.onReportSelected(composite, (Report) item);
                } else if (item instanceof ReportGroup) {
                        handler.onReportGroupSelected(composite, (ReportGroup) item);
                } else if (composite != null) {
                        handler.onSelected(composite, item);
                }
                handler.onSelected(sender, item);

        }
}
