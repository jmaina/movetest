package org.yawl.schedulingservice.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;
import org.openxdata.server.admin.model.TaskDef;
import org.yawl.schedulingservice.client.Context;
import org.yawl.schedulingservice.client.HelpCallBack;
import org.yawl.schedulingservice.client.event.EditableEvent;
import org.yawl.schedulingservice.client.event.EventType;
import org.yawl.schedulingservice.client.event.LoadRequetEvent;
import org.yawl.schedulingservice.client.event.RequestEvent;
import org.yawl.schedulingservice.client.places.EditTaskPlace;
import org.yawl.schedulingservice.client.service.TaskServiceAsync;
import org.yawl.schedulingservice.client.wizard.CreateTaskWizard;

/**
 *
 * @author kay
 */
public class TasksPresenter extends WidgetPresenterAdapter<TasksPresenter.Display> {

        private final CreateTaskWizard wizard;
        private TaskServiceAsync taskService;
        private Map<Integer, TaskDef> tasks = new HashMap<Integer, TaskDef>();
        private Map<Date, TaskDef> dates = new HashMap<Date, TaskDef>();
        private final TaskPresenter.Display taskDisplay;

        public interface Display extends BaseListDisplay<TaskDef> {

                public HasClickHandlers btnEdit();

                public HasClickHandlers btnStart();

                public List<TaskDef> getSelectedTasks();

                public void setNextExecution(TaskDef task, Date date);
        }

        @Inject
        public TasksPresenter(Display display, EventBus eventBus, CreateTaskWizard wizard,
                TaskPresenter taskDisplay) {
                super(display, eventBus);
                this.wizard = wizard;
                taskService = TaskServiceAsync.Util.getInstance();
                bind();
                this.taskDisplay = taskDisplay.getDisplay();

        }

        @Override
        protected void onBind() {

                display.getBtnNew().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                wizard.start();
                        }
                });

                display.btnRefresh().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                loadTasks(true);
                        }
                });


                display.btnEdit().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                String prompt = Window.prompt("Enter number", null);
                                int parseInt = Integer.parseInt(prompt);
                                for (int i = 0; i < parseInt; i++) {
                                        TaskDef def = new TaskDef();
                                        eventBus.fireEvent(new EditableEvent<TaskDef>(def, EventType.CREATED));

                                }
                        }
                });



                display.btnStart().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                startTasks();
                        }
                });

                display.btnDelete().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                deleteTasks();
                        }
                });


                EditableEvent.addHandler(eventBus, new EditableEvent.HandlerAdaptor<TaskDef>() {

                        @Override
                        public void onCreated(TaskDef item) {
                                display.addItem(item);
                        }
                }).forClass(TaskDef.class);


                LoadRequetEvent.addHandler(eventBus, new LoadRequetEvent.Handler<TaskDef>() {

                        @Override
                        public void onLoadRequest() {
                                loadTasks(false);
                        }

                        @Override
                        public void onRefresh() {
                                loadTasks(true);
                        }
                }).forClass(TaskDef.class);
                display.addSelectionHandler(new SelectionHandler<TaskDef>() {

                        @Override
                        public void onSelection(SelectionEvent<TaskDef> event) {
                                TaskDef selectedItem = event.getSelectedItem();
                                editItem(selectedItem);
                        }
                });
        }

        private void loadTasks(boolean reload) {
                if (!tasks.isEmpty() && !reload)
                        return;
                taskService.getTasks(new HelpCallBack<List<TaskDef>>() {

                        @Override
                        public void onSuccessPost(List<TaskDef> result) {
                                filterTasks(result);
                        }
                });
        }

        private void filterTasks(List<TaskDef> result) {
                display.clear();
                tasks.clear();
                for (TaskDef taskDef : result) {
                        if (taskDef.getTaskClass().equals(Context.TASK_CLASS)) {
                                display.addItem(taskDef);
                                tasks.put(taskDef.getId(), taskDef);
                        }
                }
                loadExecutionDates();
                eventBus.fireEvent(new EditableEvent<TaskDef>(result, TaskDef.class));

        }

        private void startTasks() {
                List<TaskDef> selectedTasks = display.getSelectedTasks();

                for (TaskDef taskDef : selectedTasks) {
                        taskService.startTask(taskDef, new HelpCallBack<Boolean>() {

                                @Override
                                public void onSuccessPost(Boolean result) {
                                        if (result)
                                                GWT.log("Tasks Successfuly Started");
                                        else
                                                GWT.log("Failed to Start the Tasks");
                                }
                        });
                }
        }

        public TaskDef getSelectTask() {
                return selectedTask;
        }

        public void onEditPlaceRequest(int taskID) {
                TaskDef task = tasks.get(taskID);
                GWT.log("eventBus.fireEvent(new RequestEvent<TaskDef>(task));");
                eventBus.fireEvent(new RequestEvent<TaskDef>(task));
                display.switchToView(taskDisplay);
        }
        private TaskDef selectedTask;

        private void editItem(TaskDef item) {
                PlaceRequest request = new PlaceRequest(EditTaskPlace.NAME).with(EditTaskPlace.PARAM_ID, String.valueOf(item.getId()));
                this.selectedTask = item;
                eventBus.fireEvent(new RequestEvent<TaskDef>(item));
                eventBus.fireEvent(new PlaceRequestEvent(request));
        }

        private void deleteTasks() {
                List<TaskDef> selectedTasks = display.getSelectedTasks();

                for (int i = 0; i < selectedTasks.size(); i++) {
                        TaskDef taskDef = selectedTasks.get(i);
                        if (taskDef.getCronExpression() != null || taskDef.isStartOnStartup()) {
                                Window.alert("Batch Delete Can Only Delete Unscheduled Tasks");
                                return;
                        }
                }



                for (int i = 0; i < selectedTasks.size(); i++) {
                        TaskDef taskDef = selectedTasks.get(i);
                        taskService.deleteTask(taskDef, new HelpCallBack<Void>() {

                                @Override
                                public void onFailurePostProcessing(Throwable throwable) {
                                }

                                @Override
                                public void onSuccessPost(Void result) {
                                }
                        });
                }
        }

        private void loadExecutionDates() {
                Collection<TaskDef> values = tasks.values();
                dates.clear();
                for (final TaskDef taskDef : values) {
                        taskService.getNextExecution(taskDef, new HelpCallBack<Date>() {

                                @Override
                                public void onFailurePostProcessing(Throwable throwable) {
                                        display.setNextExecution(taskDef, null);
                                }

                                @Override
                                public void onSuccessPost(Date result) {
                                        GWT.log(" NextFireDate for Task <" + taskDef.getName() + "> is <" + result + ">");
                                        dates.put(result, taskDef);
                                        display.setNextExecution(taskDef, result);
                                }
                        });
                }
        }
}
