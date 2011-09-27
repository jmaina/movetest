package org.openxdata.modules.workflows.client.maps.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openxdata.modules.workflows.model.shared.OParameter;
import org.purc.purcforms.client.model.QuestionDef;
import static org.purc.purcforms.client.model.QuestionDef.*;

/**
 *
 * @author kay
 */
public class DafaultTypeValidator implements TypeValidator {

        private HashMap rules = new HashMap();
        private String errorMessage;

        public DafaultTypeValidator() {
                initRules();
        }

        public boolean isValid(OParameter parameter, QuestionDef def) {
                if (parameter == null || def == null) {
                        errorMessage = "Inavalid Question or Parameter";
                        return false;
                }

                boolean valid = false;

                List compatibleTypes = (List) rules.get(def.getDataType());

                valid = compatibleTypes != null
                        && compatibleTypes.contains(parameter.getType());

                if (!valid) {
                        createErrorMessage(parameter, def);
                } else {//Check once more for correct flow matching
                        valid = isAllowedFlowMatch(parameter, def);
                }

                if(valid)
                valid = checkParamaterNameTypes(parameter, def);

                return valid;

        }

        private boolean isAllowedFlowMatch(OParameter parameter, QuestionDef def) {
                if (parameter.isInput() || parameter.isInputOutput()) {
                        if (def.getDataType() == QTN_TYPE_LIST_EXCLUSIVE) {
                                errorMessage = XFormTypes.toText(def.getDataType())
                                        + " Cannot be used with Input Parameters";
                                return false;
                        }
                }
                return true;
        }

        private void initRules() {
                ArrayList<String> typeText = new ArrayList<String>();
                rules.put(QTN_TYPE_TEXT, typeText);
                typeText.add(OParameter.TYPE_STRING);

                ArrayList<String> typeNumeric = new ArrayList<String>();
                rules.put(QTN_TYPE_NUMERIC, typeNumeric);
                typeNumeric.add(OParameter.TYPE_INTEGER);
                typeNumeric.add(OParameter.TYPE_INT);

                ArrayList<String> typeDateTime = new ArrayList<String>();
                rules.put(QTN_TYPE_DATE_TIME, typeDateTime);
                typeDateTime.add(OParameter.TYPE_DATE_TIME);

                ArrayList<String> typeBoolen = new ArrayList<String>();
                rules.put(QTN_TYPE_BOOLEAN, typeBoolen);
                typeBoolen.add(OParameter.TYPE_BOOLEAN);

                ArrayList<String> typeDecimal = new ArrayList<String>();
                rules.put(QTN_TYPE_DECIMAL, typeDecimal);
                typeDecimal.add(OParameter.TYPE_DECIMAL);

                ArrayList<String> dateType = new ArrayList<String>();
                rules.put(QTN_TYPE_DATE, dateType);
                dateType.add(OParameter.TYPE_DATE);

                ArrayList<String> listExclusiveType = new ArrayList<String>();
                rules.put(QTN_TYPE_LIST_EXCLUSIVE, listExclusiveType);
                listExclusiveType.add(OParameter.TYPE_STRING);

                ArrayList<String> listRepeat = new ArrayList<String>();
                rules.put(QTN_TYPE_REPEAT, listRepeat);
                listRepeat.add(OParameter.TYPE_STRING);

                ArrayList<String> listGpsType = new ArrayList<String>();
                rules.put(QTN_TYPE_GPS, listGpsType);
                listGpsType.add(OParameter.TYPE_STRING);

                ArrayList<String> listImageType = new ArrayList<String>();
                rules.put(QTN_TYPE_IMAGE, listImageType);
                listImageType.add(OParameter.TYPE_STRING);

                ArrayList<String> listMultiple = new ArrayList<String>();
                rules.put(QTN_TYPE_LIST_MULTIPLE, listMultiple);
                listMultiple.add(OParameter.TYPE_STRING);

        }

        private void createErrorMessage(OParameter parameter, QuestionDef def) {
                errorMessage = "Impatible types ("
                        + parameter.getType() + " , " + XFormTypes.toText(def.getDataType()) + ")";
        }

        public String getErrorMessage() {
                return errorMessage;
        }

        /**
         * This method assumes assumes all other data type checking has been checked and its just making sure
         * the suffixed end with the required data type
         */
        private boolean checkParamaterNameTypes(OParameter parameter, QuestionDef def) {

                if (def.getDataType() == QTN_TYPE_IMAGE && !parameter.getName().endsWith("_img")) {
                        errorMessage = XFormTypes.toText(QTN_TYPE_IMAGE) + " Questions Can Only Be Mapped With Paramet That End With _img";
                        return false;
                }
                if (def.getDataType() == QTN_TYPE_AUDIO && !parameter.getName().endsWith("_aud")) {
                        errorMessage = XFormTypes.toText(QTN_TYPE_AUDIO) + " Questions Can Only Be Mapped With Paramet That End With _aud";
                        return false;
                }
                if (def.getDataType() == QTN_TYPE_VIDEO && !parameter.getName().endsWith("_vid")) {
                        errorMessage = XFormTypes.toText(QTN_TYPE_VIDEO) + " Questions Can Only Be Mapped With Paramet That End With _vid";
                        return false;
                }
                if (def.getDataType() == QTN_TYPE_REPEAT && !parameter.getName().endsWith("_rpt")) {
                        errorMessage = XFormTypes.toText(QTN_TYPE_REPEAT) + " Questions Can Only Be Mapped With Paramet That End With _rpt";
                        return false;
                }

                return true;

        }
}
