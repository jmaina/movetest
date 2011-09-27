package org.openxdata.modules.workflows.model.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author kay
 */
public class OSpecificationData implements Serializable {

        private String _betaFormat;
        private String _rootNetID;
        private String _schema;
        private String _specVersion = "0.1";
        private String _specificationID;
        private String _specificationName;
        private String _documentation;
        private String _status;
        private String _specAsXML;
        private HashMap<String, OParameter> mParams = new HashMap<String, OParameter>();

        public ArrayList<OParameter> getInputOnlyParams() {

                ArrayList<OParameter> params = new ArrayList<OParameter>();
                for (OParameter oParameter : mParams.values()) {
                        if (oParameter.isInput()) {
                                params.add(oParameter);
                        }
                }
                return params;
        }

        public ArrayList<OParameter> getInputParams() {

                ArrayList<OParameter> params = new ArrayList<OParameter>();
                for (OParameter oParameter : mParams.values()) {
                        if (oParameter.isInput()||oParameter.isInputOutput()) {
                                params.add(oParameter);
                        }
                }
                return params;
        }

        public ArrayList<OParameter> getOutputOnlyParams() {
                ArrayList<OParameter> params = new ArrayList<OParameter>();
                for (OParameter oParameter : mParams.values()) {
                        if (oParameter.isOutput()) {
                                params.add(oParameter);
                        }
                }
                return params;
        }

        public void addParameters(List<OParameter> params) {
                for (OParameter oParameter : params) {
                        addParameter(oParameter);
                }
        }

        public void addParameter(OParameter param) {
                if (param != null) {
                        mParams.put(param.getName(), param);
                }
        }

        public String getBetaFormat() {
                return _betaFormat;
        }

        public void setBetaFormat(String _betaFormat) {
                this._betaFormat = _betaFormat;
        }

        public String getDocumentation() {
                return _documentation;
        }

        public void setDocumentation(String _documentation) {
                this._documentation = _documentation;
        }

        public String getRootNetID() {
                return _rootNetID;
        }

        public void setRootNetID(String _rootNetID) {
                this._rootNetID = _rootNetID;
        }

        public String getSchema() {
                return _schema;
        }

        public void setSchema(String _schema) {
                this._schema = _schema;
        }

        public String getSpecAsXML() {
                return _specAsXML;
        }

        public void setSpecAsXML(String _specAsXML) {
                this._specAsXML = _specAsXML;
        }

        public String getSpecVersion() {
                return _specVersion;
        }

        public void setSpecVersion(String _specVersion) {
                this._specVersion = _specVersion;
        }

        public String getSpecificationID() {
                return _specificationID;
        }

        public void setSpecificationID(String _specificationID) {
                this._specificationID = _specificationID;
        }

        public String getSpecificationName() {
                return _specificationName;
        }

        public void setSpecificationName(String _specificationName) {
                this._specificationName = _specificationName;
        }

        public String getStatus() {
                return _status;
        }

        public void setStatus(String _status) {
                this._status = _status;
        }

        public HashMap<String, OParameter> getmParams() {
                return mParams;
        }

        public void setmParams(HashMap<String, OParameter> mParams) {
                this.mParams = mParams;
        }
}
