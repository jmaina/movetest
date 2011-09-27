package org.yawl.schedulingservice.client.wizard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 *
 * @author kay
 */
public class PageEnterSpec extends WizardPage {

        @UiField
        TextBox specId;
        @UiField
        TextBox specVersion;
        @UiField
        TextBox specDecomposition;
        private CreateTaskContext context;

        @Override
        public void onBackPressed() {
                beforeNextPage();
        }

        interface PageEnterSpecUiBinder extends UiBinder<Widget, PageEnterSpec> {
        }
        PageEnterSpecUiBinder binder = GWT.create(PageEnterSpecUiBinder.class);

        @Inject
        public PageEnterSpec(CreateTaskContext context) {
                super();
                Widget widget = binder.createAndBindUi(this);
                this.context = context;
                this.add(widget);

        }

        @Override
        public boolean beforeNextPage() {
//                context.specID = specId.getText();
//                context.specVersion = specVersion.getText();
//                context.decomposition = specDecomposition.getText();
                return true;
        }

        @Override
        public String getPageTitle() {
                return "Enter Specification Details";
        }

        @Override
        public void beforePageDisplay() {
                specId.setText(context.getSpecID());
                specVersion.setText(context.getSpecVersion());
                specDecomposition.setText(context.getDecomposition());
        }
}
