/*
 * Copyright 2009 Hasan Turksoy.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.yawl.schedulingservice.client.wizard;

import com.google.gwt.user.client.ui.FlowPanel;

/**
 * @author hturksoy
 * 
 */
public abstract class WizardPage extends FlowPanel {

        private WizardPageImpl impl;

        public WizardPage() {
                super();

                impl = new WizardPageImpl(this);

                setVisible(false);
        }

        WizardPageImpl getImpl() {
                return impl;
        }

        /**
         * by default page don't return any page id. however, implementers can
         * customize it.
         */
        public int nextId() {
                return WizardConstants.INVALID_PAGE_ID;
        }

        /**
         * Called by the {@link Wizard#validateCurrentPage()} method to perform
         * validations related to the page.
         *
         * @return true if valid and ready to continue, false if the page content
         *         including invalid entries.
         */
        public abstract boolean beforeNextPage();

        /**
         * This method is called to determine whether the Next/Finish button should be enabled or
         * disabled. If you override this method, make sure to call fireCompleteStateChanged event
         * whenever the completeness state of the page is changed.
         *
         * @return Completeness state of the page
         */
        public boolean isComplete() {
                // TODO Run registered WizardField validations - e.g. mandatory fields
                return true;
        }

        public void setFinalPage(boolean finalPage) {
                impl.setExplicitlyFinal(finalPage);
        }

        /**
         * @return
         */
        boolean isFinalPage() {
                return impl.isFinalPage();
        }

        public void checkCompleteChanged() {
                impl.checkCompleteChanged();
        }

        public abstract String getPageTitle();

        public abstract void beforePageDisplay() ;

       public   void onBackPressed() {};
}
