package org.yawl.schedulingservice.client.wizard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.inject.Inject;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import net.customware.gwt.presenter.client.EventBus;
import org.openxdata.server.admin.model.TaskDef;
import org.openxdata.server.admin.model.TaskParam;
import org.yawl.schedulingservice.client.Context;
import org.yawl.schedulingservice.client.HelpCallBack;
import org.yawl.schedulingservice.client.event.LoadRequetEvent;
import org.yawl.schedulingservice.client.service.TaskServiceAsync;
import org.yawl.schedulingservice.client.util.UUID;

/**
 *
 * @author kay
 */
public class CreateTaskWizard extends Wizard {

        private final EventBus eventBus;
        private final PageSchedule shedulePage;
        DialogBox bx = new DialogBox(false, true);
        private final PageEnterSpec pageEnterSpec;
        private final PageEnterParameters pageEnterParams;
        private final PageSelectConfig pageSelectConfig;
        private final CreateTaskContext context;
        private final TaskServiceAsync taskService;
        private final PageSelectSpec pageSelectSpec;
        private final PageEnterParams enterParams;

        @Inject
        public CreateTaskWizard(EventBus eventBus, PageSchedule shedulePage,
                PageEnterSpec pageEnterSpec,
                PageEnterParameters enterParameters,
                PageSelectConfig pageSelectConfig,
                CreateTaskContext context,
                PageSelectSpec pageSelectSpec,
                PageEnterParams enterParams) {
                super();

                this.eventBus = eventBus;
                this.shedulePage = shedulePage;
                this.pageEnterSpec = pageEnterSpec;
                this.pageEnterParams = enterParameters;
                this.pageSelectConfig = pageSelectConfig;
                this.context = context;
                this.pageSelectSpec = pageSelectSpec;
                this.enterParams = enterParams;
                taskService = TaskServiceAsync.Util.getInstance();
                initUI();
        }

        @Override
        public void onFinishClicked() {
                createTask();
                context.clear();
                bx.hide();
        }

        @Override
        public void onCancelClicked() {
                boolean confirm = Window.confirm("Are you Sure?");
                if (confirm) {
                        bx.hide();
                        context.clear();
                }
        }

        @Override
        public void start() {
                bx.center();
                context.newSession();
                this.shedulePage.setTask(context.getTaskDef());
                super.start();
        }

        public void start(TaskDef def) {
                bx.center();
                context.setTaskDef(def);
                this.shedulePage.setTask(context.getTaskDef());
                super.start();
        }

        private void initUI() {
                bx.setHTML("Create Task Wizard");

                //size up the pages
                shedulePage.setSize("650px", "350px");
                pageEnterSpec.setSize("650px", "350px");
                pageEnterParams.setSize("650px", "350px");
                pageSelectConfig.setSize("650px", "350px");
                pageSelectSpec.setSize("650px", "350px");
                enterParams.setSize("650px", "350px");

                bx.setWidget(this);
                addPage(shedulePage);
                addPage(pageSelectConfig);
                addPage(pageSelectSpec);
                addPage(enterParams);
        }

        private void createTask() {
                TaskDef def = context.getTaskDef();
                List<TaskParam> parameters = def.getParameters();
                if(parameters != null) parameters.clear();

                def.addParam(getParam(def, "specID", context.getSpecData().getSpecificationID()));
                def.addParam(getParam(def, "version", context.getSpecData().getSpecVersion()));
                def.addParam(getParam(def, "inputParams", getXML()));
                def.addParam(getParam(def, "decomposition", context.getSpecData().getRootNetID()));
                Set<Entry<String, String>> entrySet = context.getParamAnswer().entrySet();
                for (Entry<String, String> entry : entrySet) {
                        def.addParam(getParam(def, entry.getKey(), entry.getValue()));
                }
                def.setTaskClass(Context.TASK_CLASS);
                def.setCronExpression(context.getCron());
                if (def.getName() == null || def.getName().isEmpty())
                        def.setName(context.getSpecData().getSpecificationID()+"-"+context.getSpecData().getSpecVersion() + "-" + UUID.uuid(10,34));

                taskService.saveTask(def, new HelpCallBack<Void>() {

                        @Override
                        public void onSuccessPost(Void result) {
                                eventBus.fireEvent(new LoadRequetEvent<TaskDef>(TaskDef.class, LoadRequetEvent.TYPE.REFRESH));
                        }
                });
        }

        private String getXML() {
                StringBuilder builder = new StringBuilder();
                builder.append("<").append(context.getSpecData().getRootNetID()).append(">");
                Set<Entry<String, String>> entrySet = context.getParamAnswer().entrySet();
                for (Entry<String, String> entry : entrySet) {
                        builder.append("<").append(entry.getKey()).append(">");
                        builder.append(entry.getValue());
                        builder.append("</").append(entry.getKey()).append(">");
                }
                builder.append("</").append(context.getSpecData().getRootNetID()).append(">");
                GWT.log("Building XML Params: " + builder.toString());
                return builder.toString();
        }

        public TaskParam getParam(TaskDef taskDef, String paramName, String paramValue) {
                List<TaskParam> parameters = taskDef.getParameters();
                TaskParam rtParam = null;
                if (parameters != null)
                        for (TaskParam taskParam : parameters) {
                                if (taskParam.getName().equals(paramName)) {
                                        rtParam = taskParam;
                                        rtParam.setValue(paramValue);
                                }
                        }
                if (rtParam == null)
                        rtParam = new TaskParam(taskDef, paramName, paramValue);


                return rtParam;
        }
}
