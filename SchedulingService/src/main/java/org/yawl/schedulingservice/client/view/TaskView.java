package org.yawl.schedulingservice.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import java.util.Date;
import java.util.List;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.place.TokenFormatter;
import org.openxdata.server.admin.model.TaskDef;
import org.openxdata.server.admin.model.TaskParam;
import org.yawl.schedulingservice.client.places.ShowCasesPlace;
import org.yawl.schedulingservice.client.presenter.TaskPresenter;

/**
 *
 * @author kay
 */
public class TaskView implements TaskPresenter.Display {

        private TaskDef taskDef;
        //Panels
        @UiField
        HTMLPanel htmlPanel;
        @UiField(provided = true)
        DisclosurePanel dPanel;
        @UiField
        VerticalPanel datesPanel;
        @UiField
        FlexTable paramFlexTable;
        //Buttons
        @UiField
        Button btnStart;
        @UiField
        Button btnEdit;
        @UiField
        Button btnDelete;
        @UiField
        Button btnStop;
        //Hyperlinks
        @UiField(provided = true)
        Hyperlink backLink;
        @UiField
        TextBox txtTaskID;
        @UiField
        TextBox txtDescription;
        @UiField
        TextBox txtStatus;

        interface TaskViewUiBinder extends UiBinder<Widget, TaskView> {
        }
        TaskViewUiBinder uiBinder = GWT.create(TaskViewUiBinder.class);

        @Inject
        public TaskView(TokenFormatter formatter) {
                PlaceRequest req = new PlaceRequest(ShowCasesPlace.NAME);
                backLink = new Hyperlink("<<Back", formatter.toHistoryToken(req));
                dPanel = new DisclosurePanel("Parameters");
                uiBinder.createAndBindUi(this);
        }

        @Override
        public HasClickHandlers btnStart() {
                return btnStart;
        }

        @Override
        public HasClickHandlers btnStop() {
                return btnStop;
        }

        @Override
        public HasClickHandlers btnEdit() {
                return btnEdit;
        }

        @Override
        public HasClickHandlers btnDelete() {
                return btnDelete;
        }

        @Override
        public void setStaus(Date result) {
                datesPanel.clear();
                String status = null;

                if (taskDef.isStartOnStartup()) {
                        status = "Task Will Run On Next Startup";
                } else {
                        status = result != null ? result.toString() : "Task Not Scheduled";
                }
                datesPanel.add(new Label(status));
        }

        @Override
        public void setTask(TaskDef taskDef) {
                this.taskDef = taskDef;
                txtTaskID.setText(taskDef.getName());
                txtDescription.setText(taskDef.getDescription());
                String running = taskDef.isRunning() ? "Task Running" : "Task Not Running";
                txtStatus.setText(running);
                renderParams(taskDef.getParameters());

        }

        private void renderParams(List<TaskParam> parameters) {
                paramFlexTable.removeAllRows();
                for (int i = 0; i < parameters.size(); i++) {
                        TaskParam taskParam = parameters.get(i);
                        paramFlexTable.setText(i, 0, taskParam.getName());
                        paramFlexTable.setText(i, 1, taskParam.getValue());
                }

        }

        @Override
        public Widget asWidget() {
                return htmlPanel;

        }
}
