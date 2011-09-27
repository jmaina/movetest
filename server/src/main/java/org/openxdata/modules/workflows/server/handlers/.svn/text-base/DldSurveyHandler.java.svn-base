package org.openxdata.modules.workflows.server.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.openxdata.db.util.PersistentInt;
import org.openxdata.server.Context;
import org.openxdata.server.admin.model.FormDef;
import org.openxdata.server.admin.model.StudyDef;
import org.openxdata.server.admin.model.User;
import org.openxdata.server.service.SettingService;
import org.openxdata.server.service.StudyManagerService;

/**
 *
 * @author kay
 */
public class DldSurveyHandler implements RequestHandler {

        private SettingService settingService;
        private StudyManagerService studyMgrSrvc;

        public DldSurveyHandler() {
                settingService = (SettingService) Context.getBean("settingService");
                studyMgrSrvc = (StudyManagerService) Context.getBean("studyManagerService");
        }

        @Override
        public void handleRequest(User user, InputStream is, OutputStream os) throws IOException {
                HandlerStreamUtil streamHelper = new HandlerStreamUtil(is, os);
                int formVersionId = streamHelper.readInt();
                StudyDef currentStudy = getCurrentStudy();
                FormDef currentForm = getCurrentForm(currentStudy);
                if (formVersionId == -1) {
                        int studyId = currentStudy == null ? -1 : currentStudy.getId();
                        int formId = currentForm == null ? -1 : currentForm.getDefaultVersion().getId();
                        streamHelper.writeSucess();
                        streamHelper.write(new PersistentInt(studyId));
                        streamHelper.write(new PersistentInt(formId));
                }
                 streamHelper.flush();
        }

        private FormDef getCurrentForm(StudyDef study) {
                String srvyFrmNm = settingService.getSetting("SurveyForm");
                List<FormDef> forms = study.getForms();
                for (FormDef formDef : forms) {
                        String formNm = formDef.getName();
                        if (formNm.equals(srvyFrmNm)) {
                                return formDef;
                        }
                }
                return null;
        }

        private StudyDef getCurrentStudy() {
                String srvyStdyNm = settingService.getSetting("SurveyStudy");
                List<StudyDef> studies = studyMgrSrvc.getStudies();
                if (srvyStdyNm != null)
                        for (StudyDef studyDef : studies) {
                                String stdyNm = studyDef.getName();
                                if (stdyNm.equals(srvyStdyNm)) {
                                        return studyDef;
                                }
                        }
                return null;
        }
}
