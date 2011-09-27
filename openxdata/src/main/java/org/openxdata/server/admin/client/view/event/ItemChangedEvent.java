package org.openxdata.server.admin.client.view.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Composite;

import org.openxdata.server.admin.model.FormDef;
import org.openxdata.server.admin.model.FormDefVersion;

import org.openxdata.server.admin.model.Report;
import org.openxdata.server.admin.model.ReportGroup;
import org.openxdata.server.admin.model.Role;
import org.openxdata.server.admin.model.Setting;
import org.openxdata.server.admin.model.SettingGroup;
import org.openxdata.server.admin.model.StudyDef;
import org.openxdata.server.admin.model.TaskDef;
import org.openxdata.server.admin.model.User;

/**
 * 
 * @author kay
 */
//TODO Replace this class with specific even objects
public class ItemChangedEvent extends GwtEvent<ItemChangedEvent.Handler> {

        public interface Handler extends EventHandler {

                public void onUserChanged(Composite sender, User item);

                public void onChanged(Object sender, Object item);

                public void onChanged(Composite sender, Object item);

                public void onTaskDefChanged(Composite sender, TaskDef taskDef);

                public void onSettingChanged(Composite sender, Setting setting);

                public void onSettingGroupChanged(Composite sender, SettingGroup group);

                public void onReportChanged(Composite sender, Report report);

                public void onRoleChanged(Composite sender, Role role);

                public void onReportGroupChanged(Composite composite,
                        ReportGroup reportGroup);

                public void onStudyChanged(Composite composite, StudyDef studyDef);

                public void onFormVersionChanged(Composite composite, FormDefVersion formDefVersion);

                public void onFormChanged(Composite composite, FormDef formDef);
        }

        public static class HandlerAdaptor implements Handler {

                @Override
                public void onUserChanged(Composite sender, User item) {
                }

                @Override
                public void onChanged(Object sender, Object item) {
                }

                @Override
                public void onChanged(Composite sender, Object item) {
                }

                @Override
                public void onTaskDefChanged(Composite sender, TaskDef taskDef) {
                }

                @Override
                public void onSettingChanged(Composite sender, Setting setting) {
                }

                @Override
                public void onSettingGroupChanged(Composite sender, SettingGroup group) {
                }

                @Override
                public void onReportChanged(Composite sender, Report report) {
                }

                @Override
                public void onRoleChanged(Composite sender, Role role) {
                }

                @Override
                public void onReportGroupChanged(Composite composite, ReportGroup reportGroup) {
                }

                @Override
                public void onStudyChanged(Composite composite, StudyDef studyDef) {
                }

                @Override
                public void onFormVersionChanged(Composite composite, FormDefVersion formDefVersion) {
                }

                @Override
                public void onFormChanged(Composite composite, FormDef formDef) {
                }
        }
        public static Type<Handler> TYPE = new Type<Handler>();
        private final Object item;

        public ItemChangedEvent(Object item) {
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
                        handler.onUserChanged(composite, (User) item);
                } else if (item instanceof TaskDef) {
                        handler.onTaskDefChanged(composite, (TaskDef) item);
                } else if (item instanceof Role) {
                        handler.onRoleChanged(composite, (Role) item);
                } else if (item instanceof Setting) {
                        handler.onSettingChanged(composite, (Setting) item);
                } else if (item instanceof SettingGroup) {
                        handler.onSettingGroupChanged(composite, (SettingGroup) item);
                } else if (item instanceof Report) {
                        handler.onReportChanged(composite, (Report) item);
                } else if (item instanceof ReportGroup) {
                        handler.onReportGroupChanged(composite, (ReportGroup) item);
                } else if (item instanceof StudyDef) {
                        handler.onStudyChanged(composite, (StudyDef) item);
                } else if (item instanceof FormDef) {
                        handler.onFormChanged(composite, (FormDef) item);
                } else if (item instanceof FormDefVersion) {
                        handler.onFormVersionChanged(composite, (FormDefVersion) item);
                } else if (composite != null) {
                        handler.onChanged(composite, item);
                }
                handler.onChanged(sender, item);
        }
}
