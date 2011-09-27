package org.yawl.schedulingservice.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;


import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import java.util.Date;
import java.util.List;
import org.cobogw.gwt.user.client.CSS.V.DIRECTION;

import org.cobogw.gwt.user.client.ui.ButtonBar;
import org.openxdata.server.admin.model.TaskDef;
import org.openxdata.server.admin.model.TaskParam;
import org.yawl.schedulingservice.client.presenter.TasksPresenter;

/**
 *
 * @author kay
 */
public class TasksView extends BaseListView<TaskDef> implements TasksPresenter.Display {

        @Override
        public List<TaskDef> getSelectedTasks() {
                return super.getSelectedItems();
        }

        @Override
        protected boolean requiresCheckBox() {
              return false;
        }

        public interface TasksViewBinder extends UiBinder<Widget, TasksView> {
        }
        private TasksViewBinder binder = GWT.create(TasksViewBinder.class);


        public TasksView() {
                createButtonBar();
                binder.createAndBindUi(this);
                super.setUp();
        }

        @Override
        public String[] getHeader() {
                return new String[]{"Name", "Last Execution", "Next Execution Date", "Schedule"};
        }

        @Override
        protected int render(int rowCount, TaskDef def) {
                String name = def.getName();
                String shedule = "Wait...";
                String running = def.isRunning() ? "Task Running" : "Task Not Running";
                String lastRunSuccess = def.getParamValue("lastRunSuccess");
                if(lastRunSuccess == null){
                        running = "Task Not Yet Run";
                }else if(lastRunSuccess.equals("true")){
                        running = "Last Execution Was Succesful";
                }else{
                        String param = def.getParamValue("error");
                        running = "UnSuccesful: ("+param+")";
                }
                String scheduler = def.getCronExpression();
                if (def.isStartOnStartup())
                        scheduler = "Runs On Startup";
                if (scheduler == null || scheduler.isEmpty())
                        scheduler = "Task Has No Shechdule";

                table.setWidget(rowCount, 0, new Label(name));
                table.setWidget(rowCount, 1, new Label(running));
                table.setWidget(rowCount, 2, new Label(shedule));
                table.setText(rowCount, 3, scheduler);
                return 4;
                
        }


        
 

        @Override
        public void setNextExecution(TaskDef task, Date date) {
                int row = getRowForItem(task);
                if (row != -1) {
                        String dateStr = date != null ?
                                date.toString() : "Task Not Scheduled";
                        table.setText(row, 2, dateStr);
                }
        }

        private ButtonBar createButtonBar() {
                if (btnBar != null)
                        return btnBar;
                btnBar = new ButtonBar();
                btnBar.add(btnNew);
                btnBar.add(btnEdit);
                btnBar.setWidth("100%");
                return btnBar;
        }

}
