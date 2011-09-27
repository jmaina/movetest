        package org.yawl.schedulingservice.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import java.util.Date;
import java.util.List;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.PlaceRequestEvent;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import org.openxdata.server.admin.model.TaskDef;
import org.yawl.schedulingservice.client.HelpCallBack;
import org.yawl.schedulingservice.client.event.EditableEvent;
import org.yawl.schedulingservice.client.event.ErrorEvent;
import org.yawl.schedulingservice.client.event.LoadRequetEvent;
import org.yawl.schedulingservice.client.event.RequestEvent;
import org.yawl.schedulingservice.client.places.ShowCasesPlace;
import org.yawl.schedulingservice.client.service.TaskServiceAsync;
import org.yawl.schedulingservice.client.wizard.CreateTaskWizard;

/**
 *
 * @author kay
 */
public class TaskPresenter extends WidgetPresenterAdapter<TaskPresenter.Display> {

        public interface Display extends WidgetDisplay {

                public HasClickHandlers btnStart();

                public HasClickHandlers btnStop();

                public HasClickHandlers btnDelete();

                public HasClickHandlers btnEdit();

                public void setTask(TaskDef taskDef);

                public void setStaus(Date result);
        }
        private final CreateTaskWizard wizard;
        private TaskDef mTaskDef;
        private TaskServiceAsync taskService;

        @Inject
        public TaskPresenter(Display display, EventBus eventBus, CreateTaskWizard wizard) {
                super(display, eventBus);
                this.wizard = wizard;
                taskService = TaskServiceAsync.Util.getInstance();
                bind();
        }

        @Override
        protected void onBind() {


                RequestEvent.addHandler(eventBus, new RequestEvent.HandlerAdaptor<TaskDef>() {

                        @Override
                        public void onReqEdit(TaskDef item) {
                                setTask(item);
                        }
                }).forClass(TaskDef.class);


                EditableEvent.addHandler(eventBus, new EditableEvent.HandlerAdaptor<TaskDef>() {

                        @Override
                        public void onLoaded(List<TaskDef> items) {
                                onItemsLoaded(items);

                        }
                }).forClass(TaskDef.class);


                display.btnEdit().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                editTask();
                        }
                });


                display.btnDelete().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                deleteAfterStop();
                        }
                });


                display.btnStop().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                stopTask();
                        }
                });


                display.btnStart().addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                startTask();
                        }
                });
        }

        private void startTask() {
                if (mTaskDef.isRunning()) {
                        Window.alert("Task Is Already Running");
                        return;
                }
                if (!mTaskDef.isStartOnStartup() && mTaskDef.getCronExpression() == null) {
                        Window.alert("Please First Schedule The Task");
                        return;
                }

                HelpCallBack<Boolean> callback = new HelpCallBack<Boolean>() {

                        @Override
                        public void onSuccessPost(Boolean result) {
                                onSetRunninSuccess(result);
                        }
                };

                taskService.startTask(mTaskDef, callback);
        }

        private void stopTask() {
                if (mTaskDef.getCronExpression() == null && !mTaskDef.isStartOnStartup()) {
                        Window.alert("Task Is Not Shecduled");
                        return;
                }
                HelpCallBack<Boolean> callBack = new HelpCallBack<Boolean>() {

                        @Override
                        public void onSuccessPost(Boolean result) {
                                onSetRunninSuccess(result);
                        }
                };
                taskService.stopTask(mTaskDef, callBack);
        }

        private void onSetRunninSuccess(Boolean result) {
                if (result) {
                        mTaskDef.setRunning(result);
                        display.setTask(mTaskDef);
                        eventBus.fireEvent(new LoadRequetEvent<TaskDef>(TaskDef.class, LoadRequetEvent.TYPE.REFRESH));
                } else {
                       Window.alert("A Problem Occured!!");
                       ErrorEvent.fire(eventBus, "A Problem Occured While Changing Task Status");
                }
        }

        private void onItemsLoaded(List<TaskDef> items) {
                if (mTaskDef == null)
                        return;
                for (TaskDef taskDef : items) {
                        if (taskDef.getId() == mTaskDef.getId()) {
                                setTask(taskDef);
                                return;
                        }
                }
                eventBus.fireEvent(new PlaceRequestEvent(new PlaceRequest(ShowCasesPlace.NAME)));
        }

        private void setTask(TaskDef item) {
                this.mTaskDef = item;
                display.setTask(item);
                setStatus();
        }

        private void editTask() {
                wizard.start(mTaskDef);
        }

        private void deleteAfterStop() {
                if (!Window.confirm("Deleting The Task Will Unschedule The Task As Well! \nAre You Sure? "))
                        return;
                if (mTaskDef.isRunning()
                        || (mTaskDef.getCronExpression() != null
                        && !mTaskDef.getCronExpression().isEmpty())) {
                        stopThenDelete();
                } else {
                        deletTask();
                }
        }

        private void stopThenDelete() {
                HelpCallBack<Boolean> callback = new HelpCallBack<Boolean>() {

                        @Override
                        public void onSuccessPost(Boolean result) {
                                if (result)
                                        deletTask();
                                else
                                        Window.alert("Unable To Delete Task Because The Task Could Not Be Stopped!!");
                        }
                };
                taskService.stopTask(mTaskDef, callback);
        }

        private void deletTask() {
                HelpCallBack<Void> callback = new HelpCallBack<Void>() {

                        @Override
                        public void onSuccessPost(Void result) {
                                goToMainPage();
                        }
                };
                taskService.deleteTask(mTaskDef, callback);
        }

        private void goToMainPage() {
                PlaceRequest request = new PlaceRequest(ShowCasesPlace.NAME);
                eventBus.fireEvent(new PlaceRequestEvent(request));
                eventBus.fireEvent(new LoadRequetEvent<TaskDef>(TaskDef.class, LoadRequetEvent.TYPE.REFRESH));
        }

        private void setStatus() {
                HelpCallBack<Date> callback = new HelpCallBack<Date>() {

                        @Override
                        public void onSuccessPost(Date result) {
                                display.setStaus(result);
                        }
                };
                taskService.getNextExecution(mTaskDef, callback);
        }
}
