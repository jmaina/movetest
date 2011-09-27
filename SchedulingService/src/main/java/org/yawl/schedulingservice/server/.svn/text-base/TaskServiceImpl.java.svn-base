package org.yawl.schedulingservice.server;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import org.openxdata.server.admin.model.TaskDef;
import org.openxdata.server.admin.model.exception.OpenXDataSecurityException;
import org.openxdata.server.service.SchedulerService;
import org.springframework.web.context.WebApplicationContext;
import org.yawl.schedulingservice.client.service.TaskService;

/**
 * Default Implementation for the <code>TaskService Interface.</code>
 */
public class TaskServiceImpl extends OxdPersistentRemoteService implements
        TaskService {

        /**
         * Generated Serialisation ID.
         */
        private static final long serialVersionUID = -7724890075405637511L;
        private org.openxdata.server.service.TaskService taskService;
        private SchedulerService scheduleService ;

        public TaskServiceImpl() {
        }

        @Override
        public void init() throws ServletException {
                super.init();
                WebApplicationContext ctx = getApplicationContext();
                taskService = (org.openxdata.server.service.TaskService) ctx.getBean("taskService");

        }

        @Override
        public void deleteTask(TaskDef task) {
                taskService.deleteTask(task);
        }

        @Override
        public List<TaskDef> getTasks() {
                return taskService.getTasks();
        }

        @Override
        public void saveTask(TaskDef task) {
                boolean startTask = task.getId()==0;
                
                taskService.saveTask(task);
                if(!startTask) return;
                TaskDef task1 = taskService.getTask(task.getName());
                taskService.startTask(task1);
        }

        @Override
        public Boolean startTask(TaskDef taskDef) {
                return taskService.startTask(taskDef);
        }

        @Override
        public Boolean stopTask(TaskDef taskDef) {
                return taskService.stopTask(taskDef);
        }


        @Override
        public Date getNextExecution(TaskDef task) throws OpenXDataSecurityException {
            return taskService.getNextExecution(task);
        }
}
