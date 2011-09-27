package org.yawl.schedulingservice.client.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openxdata.modules.workflows.model.shared.OSpecificationData;
import org.openxdata.server.admin.model.Setting;
import org.openxdata.server.admin.model.SettingGroup;
import org.openxdata.server.admin.model.TaskDef;
import org.openxdata.server.admin.model.TaskParam;

public class CreateTaskContext {

        private Map<String, String> paramAnswer = new HashMap<String, String>();
        private String specID;
        private String specVersion;
        private String cron;
        private TaskDef taskDef;
        private String decomposition;
        private OSpecificationData specData;

        public void newSession() {
                taskDef = new TaskDef();
                taskDef.setStartOnStartup(true);
                clear();

        }

        public void setTaskDef(TaskDef taskDef) {
                clear();
                this.taskDef = taskDef;
                specID = taskDef.getParamValue("specID");
                specVersion = taskDef.getParamValue("version");
                decomposition = taskDef.getParamValue("decomposition");
                List<TaskParam> parameters = taskDef.getParameters();
                for (TaskParam taskParam : parameters) {
                        paramAnswer.put(taskParam.getName(), taskParam.getValue());
                }
                cron = taskDef.getCronExpression();
        }

        public void setConfig(SettingGroup group) {
                if (group == null)
                        return;
                //                List<SettingGroup> groupList = new ArrayList<SettingGroup>();
                //                groupList.add(group);
                //
                //                specID = SettingGroup.getSetting("specID", groupList, specID);
                //                specVersion = SettingGroup.getSetting("version", groupList, specVersion);
                //                decomposition = SettingGroup.getSetting("decomposition", groupList, decomposition);
                List<Setting> settings = group.getSettings();
                for (Setting setting : settings) {
                        String name = setting.getName();
                        if("specID".equals(name))
                                specID = setting.getValue();
                        else if("specVersion".equals(name))
                                specVersion = setting.getValue();
                        else if("decomposition".equals(name))
                                decomposition = setting.getValue();
                        else 
                        paramAnswer.put(setting.getName(), setting.getValue());
                }
        }

        public void clear() {
                specID = null;
                specVersion = null;
                decomposition = null;
                cron = null;
                specData = null;
                paramAnswer.clear();
        }

        public void setSpecData(OSpecificationData value) {
                if (value == null) {
                        this.specID = null;
                        this.specVersion = null;
                        this.decomposition = null;
                        return;
                }
                this.specData = value;
                this.specID = value.getSpecificationID();
                this.specVersion = value.getSpecVersion();
                this.decomposition = value.getRootNetID();
        }

        public Map<String, String> getParamAnswer() {
                return paramAnswer;
        }

        public TaskDef getTaskDef() {
                return taskDef;
        }

        public String getCron() {
                return cron;
        }

        public OSpecificationData getSpecData() {
                return specData;
        }

        public String getSpecID() {
                return specID;
        }

        public String getSpecVersion() {
                return specVersion;
        }

        public String getDecomposition() {
                return decomposition;
        }

        public void setCron(String cron) {
                this.cron = cron;
        }
}
