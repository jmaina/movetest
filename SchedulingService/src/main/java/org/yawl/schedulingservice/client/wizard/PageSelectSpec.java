package org.yawl.schedulingservice.client.wizard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.openxdata.modules.workflows.model.shared.OSpecificationData;
import org.yawl.schedulingservice.client.HelpCallBack;
import org.yawl.schedulingservice.client.service.SpecificationServiceAsync;
import org.yawl.schedulingservice.client.util.WidgetUtil;

/**
 *
 * @author kay
 */
public class PageSelectSpec extends WizardPage {

        private static PageSelectSpecUiBinder uiBinder = GWT.create(PageSelectSpecUiBinder.class);
        private final CreateTaskContext context;

        interface PageSelectSpecUiBinder extends UiBinder<Widget, PageSelectSpec> {
        }
        private HashMap<RadioButton, OSpecificationData> btnDataMap = new HashMap<RadioButton, OSpecificationData>();
        private SpecificationServiceAsync specService;
        @UiField
        VerticalPanel radioVerticalPanel;

        @Inject
        public PageSelectSpec(CreateTaskContext context) {
                Widget widget = uiBinder.createAndBindUi(this);

                this.add(widget);
                radioVerticalPanel.add(new RadioButton("Watat", "Ubos 1"));
                specService = SpecificationServiceAsync.Util.getInstance();
                this.context = context;

        }

        @Override
        public boolean beforeNextPage() {
                Set<Entry<RadioButton, OSpecificationData>> entrySet = btnDataMap.entrySet();
                for (Entry<RadioButton, OSpecificationData> entry : entrySet) {
                        if (entry.getKey().getValue()) {
                                context.setSpecData(entry.getValue());
                                return true;
                        }
                }
                WidgetUtil.showMsg("Please Select A Specification");
                return false;
        }

        @Override
        public String getPageTitle() {
                return "Select Specicition";
        }

        @Override
        public void beforePageDisplay() {
                if (btnDataMap.isEmpty()) {
                        specService.getLoadedSpecs(new HelpCallBack<List<OSpecificationData>>() {

                                @Override
                                public void onSuccessPost(List<OSpecificationData> result) {
                                        renderSpecs(result);
                                }
                        });
                } else {
                        checkRadio();
                }
        }

        private void renderSpecs(List<OSpecificationData> result) {
                btnDataMap.clear();
                radioVerticalPanel.clear();
                for (OSpecificationData spec : result) {
                        RadioButton radioButton = new RadioButton("choiceSpecs", spec.getSpecificationID() + " : " + spec.getSpecVersion());
                        btnDataMap.put(radioButton, spec);
                        radioVerticalPanel.add(radioButton);
                        if(spec.getSpecificationID().equals(context.getSpecID()) &&
                                spec.getSpecVersion().equals(context.getSpecVersion())){
                                context.setSpecData(spec);
                                radioButton.setValue(Boolean.TRUE);
                        }
                }
        }

        private void checkRadio() {
                String specVersion = context.getSpecVersion();
                String specID = context.getSpecID();
                Set<Entry<RadioButton, OSpecificationData>> entrySet = btnDataMap.entrySet();
                for (Entry<RadioButton, OSpecificationData> entry : entrySet) {
                        if (entry.getValue().getSpecVersion().equals(specVersion)
                                && entry.getValue().getSpecificationID().equals(specID))
                                entry.getKey().setValue(true);
                        else
                                entry.getKey().setValue(false);

                }
        }
}
