package org.yawl.schedulingservice.client.wizard;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sample1 implements EntryPoint {

        private final class CustomThirdPage extends WizardPage {

                final TextBox testField = new TextBox();

                private CustomThirdPage() {
                        init();
                }

                private void init() {
                        HorizontalPanel form = new HorizontalPanel();

                        testField.addKeyUpHandler(new KeyUpHandler() {

                                @Override
                                public void onKeyUp(KeyUpEvent event) {
                                        checkCompleteChanged();
                                }
                        });

                        form.add(new HTML("<b>Test Label : * </b>"));
                        form.add(testField);
                        this.add(form);
                }

                @Override
                public boolean isComplete() {
                        return !testField.getText().isEmpty();
                }

                @Override
                public boolean beforeNextPage() {
                        return true;
                }

                @Override
                public String getPageTitle() {
                        return "Third Page";
                }

                @Override
                public void beforePageDisplay() {
                      
                }
        }
        /**
         * The message displayed to the user when the server cannot be reached or
         * returns an error.
         */
        private static final String SERVER_ERROR = "An error occurred while "
                + "attempting to contact the server. Please check your network "
                + "connection and try again.";

        /**
         * This is the entry point method.
         */
        @Override
        public void onModuleLoad() {
                Wizard wizard = createWizard();
                wizard.addPage(getFirstPage());
                wizard.addPage(getSecondPage());
                wizard.addPage(getThirdPage());
                wizard.start();

                //   RootPanel.get("wizardContainer").add(wizard);

        }

        public Wizard createWizard() {
                Wizard wizard = new Wizard();
                wizard.setSize("400px", "350px");
                wizard.addPage(getFirstPage());
                wizard.addPage(getSecondPage());
                wizard.addPage(getThirdPage());
                wizard.start();
                return wizard;
        }

        /**
         * @return
         */
        private WizardPage getSecondPage() {
                final Button sendButton = new Button("Send");
                final TextBox nameField = new TextBox();

                WizardPage page2 = new WizardPage() {

                        // page is valid if the name field is not empty
                        @Override
                        public boolean beforeNextPage() {
                                return !nameField.getText().isEmpty();
                        }

                        @Override
                        public String getPageTitle() {
                                return "Second Page";
                        }

                        @Override
                        public void beforePageDisplay() {
                             
                        }
                };

                nameField.setText("GWT User");

                // We can add style names to widgets
                sendButton.addStyleName("sendButton");

                // Add the nameField and sendButton to the page
                page2.add(nameField);
                page2.add(sendButton);

                // Focus the cursor on the name field when the app loads
                nameField.setFocus(true);
                nameField.selectAll();

                // Create the popup dialog box
                final DialogBox dialogBox = new DialogBox();
                dialogBox.setText("Remote Procedure Call");
                dialogBox.setAnimationEnabled(true);
                final Button closeButton = new Button("Close");
                // We can set the id of a widget by accessing its Element
                closeButton.getElement().setId("closeButton");
                final Label textToServerLabel = new Label();
                final HTML serverResponseLabel = new HTML();
                VerticalPanel dialogVPanel = new VerticalPanel();
                dialogVPanel.addStyleName("dialogVPanel");
                dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
                dialogVPanel.add(textToServerLabel);
                dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
                dialogVPanel.add(serverResponseLabel);
                dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
                dialogVPanel.add(closeButton);
                dialogBox.setWidget(dialogVPanel);

                // Add a handler to close the DialogBox
                closeButton.addClickHandler(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                                dialogBox.hide();
                                sendButton.setEnabled(true);
                                sendButton.setFocus(true);
                        }
                });

                // Create a handler for the sendButton and nameField
                class MyHandler implements ClickHandler, KeyUpHandler {

                        /**
                         * Fired when the user clicks on the sendButton.
                         */
                        @Override
                        public void onClick(ClickEvent event) {
                                sendNameToServer();
                        }

                        /**
                         * Fired when the user types in the nameField.
                         */
                        @Override
                        public void onKeyUp(KeyUpEvent event) {
                                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                                        sendNameToServer();
                                }
                        }

                        /**
                         * Send the name from the nameField to the server and wait for a
                         * response.
                         */
                        private void sendNameToServer() {
                                sendButton.setEnabled(false);
                                String textToServer = nameField.getText();
                                textToServerLabel.setText(textToServer);
                                serverResponseLabel.setText("");
                                dialogBox.setText("Remote Procedure Call");
                                serverResponseLabel.removeStyleName("serverResponseLabelError");
                                serverResponseLabel.setHTML("Hello from server");
                                dialogBox.center();
                                closeButton.setFocus(true);
                        }
                }

                // Add a handler to send the name to the server
                MyHandler handler = new MyHandler();
                sendButton.addClickHandler(handler);
                nameField.addKeyUpHandler(handler);

                return page2;

        }

        /**
         * @return
         */
        private WizardPage getFirstPage() {
                WizardPage page = new WizardPage() {

                        @Override
                        public boolean beforeNextPage() {
                                return true;
                        }

                        @Override
                        public String getPageTitle() {
                                return "First Page";
                        }

                        @Override
                        public void beforePageDisplay() {
                             
                        }
                };
                VerticalPanel form = new VerticalPanel();
                form.add(new Button("Test Button"));
                page.add(form);
                return page;
        }

        /**
         * @return
         */
        private WizardPage getThirdPage() {

                final WizardPage page = new CustomThirdPage();
                return page;
        }
}
