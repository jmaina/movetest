package org.yawl.schedulingservice.client.wizard;

import com.google.inject.Inject;
import org.openxdata.server.admin.model.TaskDef;
import org.yawl.schedulingservice.client.view.ScheduleView;

/**
 *
 * @author kay
 */
public class PageSchedule extends WizardPage {

        private final ScheduleView view;
        private final CreateTaskContext context;

        @Inject
        public PageSchedule(ScheduleView view, CreateTaskContext context) {
                this.view = view;
                initUI();
                this.context = context;
        }

        private void initUI() {
                this.add(view);
        }

        @Override
        public boolean isComplete() {
                return true;
        }

        @Override
        public String getPageTitle() {
                return "Shedule Task Execution";
        }

        @Override
        public boolean beforeNextPage() {
                context.setCron(view.getCronExpression());
                return true;
        }

         public void setTask(TaskDef task) {
                 view.onItemSelected(task);
        }

        @Override
        public void onBackPressed() {
              beforeNextPage();
        }

        @Override
        public void beforePageDisplay() {
                
        }
}
