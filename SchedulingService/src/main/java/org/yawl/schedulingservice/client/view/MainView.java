package org.yawl.schedulingservice.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.cobogw.gwt.user.client.CSS;
import org.yawl.schedulingservice.client.presenter.RootPresenter;
import org.yawl.schedulingservice.client.presenter.CenterTabPanelPreseter;

/**
 *
 * @author kay
 */
public class MainView implements RootPresenter.Display {

        @Override
        public void showError(String text) {
                label.setText(text);
                label.setVisible(true);
                CSS.setProperty(label.getElement(), CSS.A.BACKGROUND_COLOR, "orange");
               //DOM.setStyleAttribute(label.getElement(), "background-color", "orange");
        }

        @Override
        public void hideError() {
              label.setVisible(false);
        }

        @Override
        public HasClickHandlers btnLogout() {
                return anchor;
        }

       
        interface MainViewUiBinder extends UiBinder<Widget, MainView> {
        }
        MainViewUiBinder binder = GWT.create(MainViewUiBinder.class);
        @UiField
        DockLayoutPanel parantPanel;
        @UiField(provided=true)
        VerticalPanel westPanel;

        @UiField Label label;
        @UiField Anchor anchor;

        @Inject
        public MainView(CenterTabPanelPreseter.Display display) {
                westPanel = (VerticalPanel) display.asWidget();
                binder.createAndBindUi(this);
                maximiseDeck();
                initHandlers();
        }

        private void initHandlers() {
                Window.addResizeHandler(new ResizeHandler() {

                        @Override
                        public void onResize(ResizeEvent event) {
                                maximiseDeck();
                        }
                });
        }

        private void maximiseDeck() {
                parantPanel.setSize("100%", Window.getClientHeight() + "px");
        }

        @Override
        public Widget asWidget() {
                return parantPanel;
        }
        
}
