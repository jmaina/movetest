package org.openxdata.modules.workflows.server.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.openxdata.db.util.PersistentInt;
import org.openxdata.model.FormData;
import org.openxdata.model.FormDef;
import org.openxdata.server.Context;
import org.openxdata.server.admin.model.FormDefVersion;
import org.openxdata.server.admin.model.User;
import org.openxdata.server.serializer.KxmlSerializerUtil;
import org.openxdata.server.service.DataExportService;
import org.openxdata.server.service.SettingService;
import org.yawlfoundation.yawl.util.StringUtil;

/**
 *
 * @author kay
 */
public class InspectUploadHandler implements RequestHandler {

        private DataExportService exportService;
        private SettingService settingService;
        private String url;
        private static String INSPECTION_PATH = "inspection";

        public InspectUploadHandler() {
                exportService = (DataExportService) Context.getBean("dataExportService");
                settingService = (SettingService) Context.getBean("settingService");
                url = settingService.getSetting("tickets.url");
        }

        @Override
        public void handleRequest(User user, InputStream is, OutputStream os) throws IOException {
                HandlerStreamUtil streamHelper = new HandlerStreamUtil(is, os);
                try {
                        streamHelper.readInt();
                        Vector<FormData> readBigVector = streamHelper.readBigVector(FormData.class);
                        streamHelper.writeSucess();

                        try {
                                for (FormData formData : readBigVector) {
                                        formData.setDef(getMatchingMobileFormDef(formData.getDefId()));


                                        processFormData(formData);
                                        streamHelper.writeByte((byte)1);
                                        streamHelper.writeUTF(null);

                                }
                        } catch (RuntimeException ex) {
                                streamHelper.writeByte((byte)0);
                                streamHelper.writeUTF(ex.getMessage());
                        }
                        streamHelper.flush();
                } catch (Exception ex) {
                        throw new RuntimeException(ex);
                }

        }
        private HashMap<Integer, FormDef> formDefVersionCache = new HashMap<Integer, FormDef>();
        private HashMap<Integer, org.openxdata.server.admin.model.FormDefVersion> xFormVersionCache = new HashMap<Integer, org.openxdata.server.admin.model.FormDefVersion>();

        private FormDef getMatchingMobileFormDef(int formId) {
                FormDef fDef = formDefVersionCache.get(formId);
                if (fDef == null) {
                        FormDefVersion formDefVersion = getFormVersion(formId);
                        fDef = KxmlSerializerUtil.fromXform2FormDef(new StringReader(formDefVersion.getXform()));
                        formDefVersionCache.put(formId, fDef);
                }
                return fDef;
        }

        private FormDefVersion getFormVersion(int formId) {
                FormDefVersion formDefVersion = xFormVersionCache.get(formId);
                if (formDefVersion == null) {
                        formDefVersion = exportService.getFormDefVersion(formId);
                }
                return formDefVersion;
        }

        private void processFormData(FormData formData) throws Exception {
                String variableName = formData.getDef().getVariableName();
                Set<String> variables = getVariables();
                StringBuilder builder = new StringBuilder("<inspection>");
                for (String string : variables) {
                        String questionVar = "/" + variableName + "/" + string;
                        String textValue = formData.getTextValue(questionVar);
                        builder.append(StringUtil.wrapEscaped(string, textValue));
                }
                builder.append("</inspection>");

                buildURL(builder.toString());



        }
        Set<String> vars = new HashSet<String>();

        public Set<String> getVariables() {
                if (vars.isEmpty()) {
                        vars.add("title_of_inspector");
                        vars.add("type_of_water_point");
                        vars.add("is_there_a_latrine_within_10m_of_the_well");
                        vars.add("is_the_nearest_latrine_uphill_of_the_well");
                        vars.add("any_source_of_pollution_within_10m_of_the_well");
                        vars.add("source_of_pollution");
                        vars.add("is_the_drainage_faulty_allowing_ponding_within_2m_of_the_well");
                        vars.add("is_the_drainage_channel_cracked_broken_or_need_cleaning");
                        vars.add("is_the_fence_missing_or_faulty");
                        vars.add("is_the_cement_less_than_1m_in_radius_around_the_top_of_the_well");
                        vars.add("does_spilt_water_collect_in_the_apron_area");
                        vars.add("are_there_cracks_in_the_cement_floor");
                        vars.add("is_the_handpump_loose_at_the_point_of_attachment_to_well_head");
                        vars.add("is_the_well-cover_in_place");
                        vars.add("is_the_apron_less_than_1m_in_radius");
                        vars.add("is_the_handpump_loose_at_the_point_of_attachment_to_apron");
                        vars.add("is_the_apron_cracked_or_damaged");
                        vars.add("recommendations");
                }
                return vars;
        }

        private void buildURL(String xml) throws IOException {
                HttpClient client = new HttpClient();
                PostMethod method = new PostMethod(url + INSPECTION_PATH);
                method.addParameter("username", "admin");
                method.addParameter("password", "xxxx");
                method.addParameter("action", "inspectionUpdate");
                method.addParameter("xml", xml);
                int executeMethod = client.executeMethod(method);
                if (executeMethod != HttpStatus.SC_OK)
                        throw new RuntimeException("Problem While Connection to The Ticketing Server. Try Again Later");
                byte[] responseBody = method.getResponseBody();
                String responseXML = new String(responseBody).trim();
                if (responseXML.indexOf("<success>") != 0)
                        throw new RuntimeException(StringUtil.unwrap(responseXML));


        }
}
