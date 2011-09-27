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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.yawl.schedulingservice.client.wizard.ButtonPanel.ButtonType;

/**
 * @author hturksoy
 * 
 */
public class Wizard extends Composite {
        private HTML title;

        private void setPageTitle(String title) {
                this.title.setHTML(title);
        }

        /**
         * @author hturksoy
         *
         */
        private final class NextHandler implements ClickHandler {

                /*
                 * (non-Javadoc)
                 *
                 * @see
                 * com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt
                 * .event.dom.client.ClickEvent)
                 */
                @Override
                public void onClick(ClickEvent event) {
                        if (validateCurrentPage()) {
                                showPage(impl.gotoNext());
                                onPageChanged();
                        } else {
                                GWT.log("validation failed at page id : " + impl.getCurrentPageId(), null);
                        }
                }
        }

        /**
         * @author hturksoy
         *
         */
        private final class BackHandler implements ClickHandler {

                /*
                 * (non-Javadoc)
                 *
                 * @see
                 * com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt
                 * .event.dom.client.ClickEvent)
                 */
                @Override
                public void onClick(ClickEvent event) {
                        getCurrentPage().onBackPressed();
                        showPage(impl.gotoBack());
                        onPageChanged();
                }
        }

        /**
         * @author hturksoy
         *
         */
        private final class HelpHandler implements ClickHandler {

                /*
                 * (non-Javadoc)
                 *
                 * @see
                 * com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt
                 * .event.dom.client.ClickEvent)
                 */
                @Override
                public void onClick(ClickEvent event) {
                        showHelp(impl.getCurrentPageId());
                }
        }

        /**
         * @author hturksoy
         *
         */
        private final class FinishHandler implements ClickHandler {

                /*
                 * (non-Javadoc)
                 *
                 * @see
                 * com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt
                 * .event.dom.client.ClickEvent)
                 */
                @Override
                public void onClick(ClickEvent event) {
                        if (validateCurrentPage()) {
                         onFinishClicked();
                        } else {
                                GWT.log("validation failed at page id : " + impl.getCurrentPageId(), null);
                        }
                }
        }

        /**
         * @author hturksoy
         *
         */
        private final class CancelHandler implements ClickHandler {

                /*
                 * (non-Javadoc)
                 *
                 * @see
                 * com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt
                 * .event.dom.client.ClickEvent)
                 */
                @Override
                public void onClick(ClickEvent event) {
                        onCancelClicked();
                }
        }
        private WizardImpl impl;
        private DockPanel mainPanel = new DockPanel();
        private VerticalPanel formPanel = new VerticalPanel();
        private ButtonPanel buttonPanel = new ButtonPanel();

        public Wizard() {
                super();
                title = new HTML("Center");

                impl = new WizardImpl();

                initGUI();
        }

        /**
         * Called by the wizard when the user pressed Next or Finish buttons to make
         * necessary validations. If possible, calls current page's validate method
         * ({@link WizardPage#validate()}) otherwise returns true by default.
         *
         * If you want to enable/disable Next or Finish buttons according to the page content,
         * use {@link Wizard#isComplete()} method instead.
         *
         * @return Current page's validation result or true (by default)
         */
        public boolean validateCurrentPage() {
                final WizardPage currPage = getCurrentPage();
                if (currPage != null)
                        return currPage.beforeNextPage();
                return true;
        }

        WizardPage getCurrentPage() {
                return impl.getPage(impl.getCurrentPageId());
        }

        int nextId() {
                return impl.nextId();
        }

        /**
         *
         */
        private void onPageChanged() {
                updateButtonStates();
        }

        /**
         *
         */
        private void initGUI() {
                mainPanel.setSpacing(4);

                mainPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);

                mainPanel.add(title, DockPanel.NORTH);
                mainPanel.add(formPanel, DockPanel.CENTER);
                mainPanel.add(buttonPanel, DockPanel.SOUTH);

                initButtonPanel();

                SimplePanel decPanel = new SimplePanel();
                decPanel.setWidget(mainPanel);
                initWidget(decPanel);
        }

        /**
         *
         */
        private void initButtonPanel() {
                buttonPanel.setButtonLayout(buttonPanel.new ButtonLayout().add(ButtonType.HelpButton).add(
                        ButtonType.BackButton).add(ButtonType.NextButton).add(ButtonType.FinishButton).add(
                        ButtonType.CancelButton));

                connectButtonHandlers();
        }

        /**
         *
         */
        private void connectButtonHandlers() {
                buttonPanel.addButtonClickHandler(ButtonType.NextButton, new NextHandler());
                buttonPanel.addButtonClickHandler(ButtonType.BackButton, new BackHandler());
                buttonPanel.addButtonClickHandler(ButtonType.HelpButton, new HelpHandler());
                buttonPanel.addButtonClickHandler(ButtonType.FinishButton, new FinishHandler());
                buttonPanel.addButtonClickHandler(ButtonType.CancelButton, new CancelHandler());
        }

        void updateButtonStates() {
                final WizardPage currPage = getCurrentPage();

                final boolean historyEmpty = impl.isHistoryEmpty();
                final boolean canContinue = impl.canContinue();
                final boolean isComplete = (currPage != null) && currPage.isComplete();
                final boolean canFinish = (currPage != null) && currPage.isFinalPage();

                buttonPanel.enableButton(ButtonType.BackButton, !historyEmpty);
                buttonPanel.enableButton(ButtonType.NextButton, canContinue && isComplete);
                buttonPanel.enableButton(ButtonType.FinishButton, canFinish && isComplete);

                buttonPanel.showButton(ButtonType.NextButton, canContinue);
                buttonPanel.showButton(ButtonType.FinishButton, canFinish);
        }

        public void start() {
                showPage(impl.gotoStartPage());
                onPageChanged();// TODO showPage'in in one place
                 // Can be called?
        }

        /**
         *
         */
        public void setStartId(int id) {
                impl.setStartId(id);
        }

        /**
         *
         */
        public void addPage(WizardPage page) {
                int pageIndex = impl.newPageId();
                setPage(pageIndex, page);
                impl.acceptNewPageId();
        }

        /**
         *
         */
        public void setPage(int index, WizardPage page) {
                if (page == null) {
                        GWT.log("can not set NULL page for id : " + index, null);
                        return;
                }

                if (index < 0) {
                        GWT.log("can not set page with negative id value : " + index, null);
                        return;
                }

                page.getImpl().setWizard(this);
                impl.setPage(index, page);
        }

        protected  void showPage(WizardPage page) {
                // TODO find a better way to make old page invisible, and new page visible
                formPanel.clear();
                // TODO find a better way for the internal page size
               // page.setSize("380px", "300px");
                setPageTitle(page.getPageTitle());
                DecoratorPanel decPanel = new DecoratorPanel();
                //SimplePanel decPanel = new SimplePanel();
                page.beforePageDisplay();
                decPanel.setWidget(page);
                formPanel.add(decPanel);
        }

        /**
         * Fired when the Finish button clicked. Override this to make the necessary finish operations
         * for the wizard.
         *
         */
        public void onFinishClicked() {
                GWT.log("Finish button clicked", null);
        }

        /**
         * Fired when the Cancel button clicked.  Override this to make the necessary cancel operations
         * for the wizard.
         *
         */
        public void onCancelClicked() {
                GWT.log("Cancel button clicked", null);
        }

        /**
         * Called when the user pressed Help button. To show some help
         * content/behaviour override this method.
         *
         * @param currentPageId Page id at which help requested
         */
        protected void showHelp(int currentPageId) {
                GWT.log("Help button clicked", null);
        }

        public void showHelpButton(boolean show) {
                buttonPanel.showButton(ButtonType.HelpButton, show);
        }
}
