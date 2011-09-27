package org.openxdata.modules.workflows.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.openxdata.modules.workflows.server.context.WFContext;
import org.openxdata.server.OpenXDataPropertyPlaceholderConfigurer;
import org.openxdata.server.Task;
import org.openxdata.server.admin.model.TaskDef;
import org.openxdata.server.admin.model.TaskParam;
import org.openxdata.server.admin.model.User;
import org.openxdata.server.service.AuthenticationService;
import org.openxdata.server.service.SchedulerService;
import org.openxdata.server.service.TaskService;
import org.openxdata.server.service.UserService;
import org.openxdata.server.sms.OpenXDataAbstractJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.security.context.SecurityContextHolder;
import org.yawlfoundation.yawl.engine.YSpecificationID;

/**
 *
 * @author kay
 */
//@Component("launchTask")
public class LaunchTask extends OpenXDataAbstractJob implements Task {

        private static Logger log = Logger.getLogger(LaunchTask.class);
        YawlOXDCustomService customService;
        private TaskDef taskDef;
        private TaskService taskService;
        private AuthenticationService userService;
        private User user;

        public LaunchTask() {
                customService = WFContext.getOXDCustomService();
        }

        @Override
        protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
                log.debug("Exection Launch Tasks");
                OpenXDataPropertyPlaceholderConfigurer propHolder = (OpenXDataPropertyPlaceholderConfigurer) getBean("appSettings", context);
                String property = propHolder.getResolvedProps().getProperty("workflow.launch_tasks");
                String username = propHolder.getResolvedProps().getProperty("task.username");
                String password = propHolder.getResolvedProps().getProperty("task.password");


                if (property == null || !Boolean.valueOf(property)) {
                        log.info("This WebApp is not configured to launch tasks");
                        throw new JobExecutionException(false);
                }

                taskService = (TaskService) getBean("taskService", context);
                userService = (AuthenticationService) getBean("authenticationService", context);

                SchedulerService schedulerService = (SchedulerService) getBean("schedulerService", context);

                if (taskDef == null) {
                        taskDef = (TaskDef) context.getJobDetail().getJobDataMap().get("taskdef");
                        schedulerService.registerTaskRunningInstance(this);
                }

                String specID = taskDef.getParamValue("specID");
                String version = taskDef.getParamValue("version");
                String inputXML = taskDef.getParamValue("inputParams");

                YSpecificationID ySpecID = new YSpecificationID(specID, version);

                List<String> caseData = new ArrayList<String>();
                caseData.add(inputXML);
                try {
                        List<String> launchcases = customService.launchcases(ySpecID, caseData);
                        for (String string : launchcases) {
                                if (string.startsWith("<failure>")) {
                                        throw new Exception(string);
                                } else {
                                        log.info("Launched Case With ID: " + string);
                                }
                        }
                        log.debug("Authentication TaskUser: " + username + " password: " + password);
                        user = userService.authenticate(username, password);
                        saveLastSuccess();
                } catch (IOException ex) {
                        log.error("Error While launching Task(Engine May Not Be Running): " + taskDef.getName(), ex);
                        log.debug("Authentication TaskUser: " + username + " password: " + password);
                        user = userService.authenticate(username, password);
                        logFailure(ex);
                } catch (Exception e) {
                        log.error("Error While launching Task(Engine May Not Be Running): " + taskDef.getName(), e);
                        log.debug("Authentication TaskUser: " + username + " password: " + password);
                        user = userService.authenticate(username, password);
                        logFailure(e);
                }
                finally{
                SecurityContextHolder.clearContext();
                }
        }

        private void logFailure(Throwable ex) {
                if (user == null) {
                        log.debug("User Not Authenticated. Not Saving Last Failure");
                        return;
                }
                TaskDef task = taskService.getTask(taskDef.getName());
                TaskParam param = task.getParam("error");
                if (param == null)
                        param = new TaskParam(task, "error", "Yawl Engine May Not Be Running: " + ex.getMessage());
                param.setValue(ex.getMessage());
                TaskParam lastRun = task.getParam("lastRunSuccess");
                if (lastRun == null)
                        lastRun = new TaskParam(task, "lastRunSuccess", "false");
                lastRun.setValue("false");
                task.addParam(param);
                task.addParam(lastRun);
                log.debug("Saving Task With New Error [" + param + "]");
                taskService.saveTask(task);
        }

        private void saveLastSuccess() {
                if (user == null) {
                        log.debug("User Not Authenticated. Not Saving Last Success");
                        return;
                }
                log.debug("User Authenticatet.Saving Last Success");
                TaskDef task = taskService.getTask(taskDef.getName());
                TaskParam lastRun = task.getParam("lastRunSuccess");
                if (lastRun == null)
                        lastRun = new TaskParam(task, "lastRunSuccess", "true");
                lastRun.setValue("true");
                task.addParam(lastRun);
                taskService.saveTask(task);
        }

        @Override
        public void stop() {
                taskDef.setRunning(false);
        }

        @Override
        public TaskDef getTaskDef() {
                return taskDef;
        }

        @Override
        public boolean isRunning() {
                return taskDef.isRunning();
        }
}
