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
public class PageEnterParameters extends WizardPage {

        private static PageEnterParametersUiBinder uiBinder = GWT.create(PageEnterParametersUiBinder.class);
        private final CreateTaskContext context;

        @Override
        public void onBackPressed() {
               beforeNextPage();
        }

        interface PageEnterParametersUiBinder extends UiBinder<Widget, PageEnterParameters> {
        }
        @UiField  TextBox txtName;
        @UiField  TextBox txtNumber;

        @Inject
        public PageEnterParameters(CreateTaskContext context) {
                add(uiBinder.createAndBindUi(this));
                this.context = context;
        }

        @Override
        public boolean beforeNextPage() {
//               context.usrName = txtName.getText();
//                context.number = txtNumber.getText();
                return true;
        }

        @Override
        public String getPageTitle() {
                return "Enter Paramater For the WorkFlow";
        }

        @Override
        public void beforePageDisplay() {
//             txtName.setText(context.usrName);
//             txtNumber.setText(context.number);
        }


}
